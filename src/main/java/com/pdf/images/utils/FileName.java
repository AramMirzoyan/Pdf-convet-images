package com.pdf.images.utils;

public class FileName {

    public static String extractFileName(final String filePath){
        int lastIndexPdf = filePath.lastIndexOf(".pdf");
        int lastIndexFileName = filePath.lastIndexOf("/");
       return filePath.substring(lastIndexFileName+1, lastIndexPdf);
    }
}
