package com.umermajeed.wordgame;

import java.util.Random;
import java.util.Scanner;

public class WordGuessingGame {

    private final String wordToGuess;
    private final char[] guessedLetters;
    private int attempts;
    public WordGuessingGame(String[] wordList, int maxAttempts) {
        Random rand = new Random();
        this.wordToGuess = wordList[rand.nextInt(wordList.length)];
        this.guessedLetters = new char[this.wordToGuess.length()];

        for (int i = 0; i < this.guessedLetters.length; i++) {
            this.guessedLetters[i] = '_';
        }
        this.attempts = maxAttempts;
    }

    public String play(){
        Scanner sc = new Scanner(System.in);
        boolean gameOver = false;
        System.out.println("word: " + wordToGuess);
        while (this.attempts > 0 && !gameOver) {
            displayGuessedLetters();
            System.out.println("You have " + this.attempts + " attempts left.");
            System.out.print("Guess a letter: ");
            char guessedLetter = sc.nextLine().toLowerCase().charAt(0);

            if(processGuess(guessedLetter)) {
              
                if(new String(guessedLetters).equalsIgnoreCase(wordToGuess)) {
                    System.out.println("Congratulations! You guessed it!");
                    return "Win";
//                    gameOver = true;
                }
            }
            else {
                System.out.println("You have not guessed " + guessedLetter + ".");
                attempts--;
            }
        }

//        if(!gameOver) {
//            System.out.println("You are out of guesses.");
//        }
        return "Lost";
    }

    private boolean processGuess(char letter) {
        boolean found = false;
        for (int i = 0; i < this.wordToGuess.length(); i++) {
            if (this.wordToGuess.toLowerCase().charAt(i) == letter) {
                this.guessedLetters[i] = letter;
                found = true;

            }
        }
        return found;
    }

    private void displayGuessedLetters() {
        System.out.println("Guessed: " + new String(this.guessedLetters));
    }
}
