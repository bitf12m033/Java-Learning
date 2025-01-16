package com.umermajeed.filemanager;

public interface UIEventHandler {
    public void onList(String directoryPath);
    public void onCreate(String directoryPath);
    public void onDelete(String directoryPath);
}
