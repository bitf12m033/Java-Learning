package com.umermajeed.wordgame;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String[] words =  {"Java", "PHP", "Python", "apple", "orange"};
        System.out.println("Welcome to the Word Guessing Game!");
        System.out.println("You will be given a word and you need to guess the letters in it.");
        System.out.println("You have 3 attempts to guess the word.");
        System.out.println("Good luck!");

        int score = 0;
        char playAgain = 'y';
        do {
            WordGuessingGame game = new WordGuessingGame(words , 3);
            String result = game.play();
            System.out.println("You " + result + " the game.");
            if(result.equals("Win")) {
                score++;
            }   

            System.out.println("Your score is " + score);

            System.out.println("Do you want to play again? (y/n)");
            playAgain = sc.nextLine().charAt(0);
            if(playAgain == 'n') {
                break;
            }   
        } while (playAgain == 'y');
        
        sc.close();
    }
}
