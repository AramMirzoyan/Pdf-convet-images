package com.pdf.images.download;

import com.pdf.images.utils.FileName;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class DownloadSource {


    public  String  download(final String filePath) throws IOException, InterruptedException {
        String fileName = FileName.extractFileName(filePath);
        var path ="src/main/resources/pdf/".concat(fileName).concat(".pdf");
        var destination= Paths.get(path);
        Files.createDirectories(destination.getParent());
        var client= HttpClient.newHttpClient();
        var request= HttpRequest.newBuilder()
                .uri(URI.create(filePath))
                .build();
        var response= client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        Files.copy(response.body(),destination, StandardCopyOption.REPLACE_EXISTING);

        return path;
    }

}
