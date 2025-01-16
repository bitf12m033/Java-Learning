package com.umermajeed.filemanager;

public class FileApp implements UIEventHandler{

    FileManager fm = null;
    public FileApp() {
        this.fm = new FileManager();
    }
    public static void main(String[] args) {

        System.out.println("Welcome to FileManager");
        UserInterface ui = new TextBasedInterface();
         ui.subscribe(new FileApp());
         ui.start();
    }

    @Override
    public void onList(String directoryPath) {
        this.fm.listFiles(directoryPath);
    }

    @Override
    public void onCreate(String directoryPath) {
        this.fm.createDirectory(directoryPath);
    }

    @Override
    public void onDelete(String directoryPath) {
        this.fm.deleteFileOrDirectory(directoryPath);
    }
}
