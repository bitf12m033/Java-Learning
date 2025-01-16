package com.umermajeed.passwords;

public interface UserInterface {

    public int getPasswordLength();
    public boolean useLowercase();
    public boolean useUppercase();
    public boolean useSpecialCharacters();
    public boolean useNumbers();
    public void displayPassword(String password);
}