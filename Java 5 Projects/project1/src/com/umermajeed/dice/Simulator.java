package com.umermajeed.dice;

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;
public class Simulator {
    public static void main(String[] args) {
        System.out.println("Welcome to the Dice Simulator!");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello " + name + "!");

        boolean done = false;
        while (!done) {
            try {
                System.out.println("How many dices would you like to play?");
                int numberOfDice = scanner.nextInt();
                System.out.println("About to play " + numberOfDice + " dice!");

                Random rand = new Random();

                for (int i = 0; i < numberOfDice; i++) {
                    int rolledNumber = rand.nextInt(6) + 1;
                    System.out.println(display(rolledNumber));
                }
                done = true;
            }
            catch (InputMismatchException e) {
                System.out.println("You entered an invalid number!");
                scanner.next();
            }
        }
    }

    static String display(int value) {
        return switch (value) {
            case 1 -> "--------\n|     |\n|  o  |\n|     |\n--------";
            case 2 -> "--------\n| o   |\n|     |\n|   o |\n--------";
            case 3 -> "--------\n| o   |\n|  o  |\n|   o |\n--------";
            case 4 -> "--------\n| o o |\n|     |\n| o o |\n--------";
            case 5 -> "--------\n| o o |\n|  o  |\n| o o |\n--------";
            case 6 -> "--------\n| o o |\n| o o |\n| o o |\n--------";
            default -> "Not a valid number";
        };
    }
}
