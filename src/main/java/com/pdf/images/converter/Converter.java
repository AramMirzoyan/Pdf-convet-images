package com.pdf.images.converter;

import com.pdf.images.utils.FileName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@Slf4j
public class Converter {

    public void pdfImagesWithPython(final String pdfPath){
        String fileName = FileName.extractFileName(pdfPath);
        var outputFolder="src/main/resources/images/".concat(fileName);
        String[] command={"./venv/bin/python3","./python/pdf_to_jpegs.py",pdfPath,outputFolder};
        ProcessBuilder processBuilder=new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process= null;
        File folder= new File(outputFolder);
        try{
            process=processBuilder.start();
            String processOutput = getProcessOutput(process);
            log.info(processOutput);
            if (!folder.exists() && !folder.isDirectory()){
                throw new ConverterException("Error converting pdf to images");
            }

        } catch (IOException e){
            Supplier<String> messageProvider =() ->
                    "%s_%s".formatted("Error Converting pdf to images", e.getMessage());
            throw exceptionToReThrow(e,any-> new ConverterException(messageProvider.get()));
        } finally {
            if(process!=null){
                process.destroy();
            }
        }

    }
    private static RuntimeException exceptionToReThrow(
            final Exception ex,
            final Function<Exception, RuntimeException> wrapperExceptionProvider) {
        if (ex instanceof ConverterException converterException) {
            return converterException;
        }
        return wrapperExceptionProvider.apply(ex);
    }


    private String getProcessOutput(Process process) throws IOException {
        StringBuilder output = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        return output.toString();
    }
}
