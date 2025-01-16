package com.umermajeed.passwords;

public class App {
    public static void main(String[] args) {
         UserInterface ui = new TextBasedInterface();

         int length = ui.getPasswordLength();
         boolean useLowercase = ui.useLowercase();
         boolean useUppercase = ui.useUppercase();
         boolean useNumbers = ui.useNumbers();
         boolean useSpecialCharacters = ui.useSpecialCharacters();

        PasswordGenerator generator = new PasswordGenerator();
//        String pass = generator.generatedPassword(10, true , true , true , true);
        String pass = generator.generatedPassword(length, useLowercase , useUppercase   , useNumbers , useSpecialCharacters);

        ui.displayPassword(pass);
    }
}
