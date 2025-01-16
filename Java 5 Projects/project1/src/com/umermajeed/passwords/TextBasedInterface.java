package com.umermajeed.passwords;

import java.util.Scanner;

public class TextBasedInterface implements UserInterface{

    Scanner scanner;
    public TextBasedInterface() {
         this.scanner = new Scanner(System.in);
    }
    @Override
    public int getPasswordLength() {
        System.out.print("Enter password length: ");
        return scanner.nextInt();
    }

    @Override
    public boolean useLowercase() {
        System.out.print("Do you want to lowercase (y/n): ");
        return scanner.next().equalsIgnoreCase("y");
    }

    @Override
    public boolean useUppercase() {
        System.out.print("Do you want to uppercase (y/n): ");
        return scanner.next().equalsIgnoreCase("y");
    }

    @Override
    public boolean useSpecialCharacters() {
        System.out.print("Do you want to special characters (y/n): ");
        return scanner.next().equalsIgnoreCase("y");
    }

    @Override
    public boolean useNumbers() {
        System.out.print("Do you want to use numbers (y/n): ");
        return scanner.next().equalsIgnoreCase("y");
    }

    @Override
    public void displayPassword(String password) {
        System.out.println(password);
    }
}
