package com.umermajeed.passwords;

import java.util.Random;

public class PasswordGenerator {

    private boolean passwordIsValid(String pass , String salt) {
        for (int i = 0; i < pass.length(); i++) {
            if (salt.contains(String.valueOf(pass.charAt(i)))) {
                return true;
            }
        }
        return false;
    }
    String generatedPassword(int length , boolean useLowercase, boolean useUpperCase,
                             boolean useNumbers, boolean useSpecialChars) {

        String lowercaase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*()_";

        StringBuilder sb = new StringBuilder();

        if(useLowercase) {
            sb.append(lowercaase);
        }
        if(useUpperCase) {
            sb.append(uppercase);
        }
        if(useNumbers) {
            sb.append(numbers);
        }
        if(useSpecialChars) {
            sb.append(symbols);
        }

        String allChars = sb.toString();
        StringBuilder password = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            char nextLetter = allChars.charAt(rand.nextInt(allChars.length()));
            password.append(nextLetter);
        }

         return password.toString();
    }
}
