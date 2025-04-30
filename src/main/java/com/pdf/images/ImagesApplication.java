package com.pdf.images;

import com.pdf.images.converter.Converter;
import com.pdf.images.download.DownloadSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class ImagesApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(ImagesApplication.class, args);
		DownloadSource download = context.getBean(DownloadSource.class);
		var pdfPath = download.download("https://pdfdrive.com.co/wp-content/pdfh/PERSONAL_FINANCIAL_PLANNING-h51555.pdf");

		Converter converter = context.getBean(Converter.class);
		converter.pdfImagesWithPython(pdfPath);

	}

}
