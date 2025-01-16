package com.umermajeed.filemanager;

import java.io.File;

public class FileManager {
    public void listFiles(String directoryPath) {
        File directory = new File(directoryPath);
        if(directory.exists() && directory.isDirectory()) {
            File[] contents = directory.listFiles();
            if(contents != null) {
                for(File file : contents) {
                    if(file.isDirectory()) {
                        System.out.println("[DIR] : "+file.getName());
                    }
                    else {
                        System.out.println("[FILE] : "+file.getName());
                    }
                }
            }
            else {
                System.out.println("Directory is Empty");
            }
        }
        else {
            System.out.println("Directory does not exist");
        }
    }

    public void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if(!directory.exists()) {
            if(!directory.mkdir()) {
                System.out.println("[ERROR] : Failed to create directory");
            }
            else {
                System.out.println("Directory created");
            }
        }
    }

    public void deleteFileOrDirectory(String directoryPath) {

        File directory = new File(directoryPath);

        if(directory.delete()){
            System.out.println("Directory deleted");
        }
        else {
            System.out.println("Directory does not exist");
        }
    }
}
