package com.cvanalyzer.utils;


import java.io.File;

public class FileParserUtilities_SID_2355019 {

    public static File[] getFilesFromFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            return folder.listFiles(File::isFile); 
        }
        return new File[0];
    }
}
