package com.umermajeed.filemanager;

import java.util.Scanner;

public class TextBasedInterface implements UserInterface {

    private UIEventHandler handler = null;

    @Override
    public void display(String message) {
        System.out.println(message);
    }

    @Override
    public void subscribe(UIEventHandler handler) {
        if(this.handler == null) {
            this.handler = handler;
        }
    }

    @Override
    public void start() {
        System.out.println("Welcome to the text based interface!");

        System.out.println("1. List Files");
        System.out.println("2. Create Folders");
        System.out.println("3. Delete Files");
        System.out.println("4. Exit");
        System.out.println("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        while(true) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter the name of the file: ");
                    String filename = scanner.next();
                    handler.onList(filename);
                    break;
                case 2:
                    System.out.println("Enter the name of the folder: ");
                    String folder = scanner.next();
                    handler.onCreate(folder);
                    break;
                case 3:
                    System.out.println("Enter the name of the file: ");
                    String filename2 = scanner.next();
                    handler.onDelete(filename2);
                    break;
                case 4:
                    System.exit(0);
                    scanner.close();
                    break;
                default:
                    break;

        }
        }
    }
}
