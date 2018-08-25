package com.agudelotmateo.minesweeper.controller;

import java.util.Scanner;

import javax.activity.InvalidActivityException;

import com.agudelotmateo.minesweeper.model.Board;

/**
 * pending!
 */
public class Driver {
    private Board gameboard;
    private Scanner scanner;

    /**
     * pending!
     */
    public Driver() {
        gameboard = null;
        scanner = new Scanner(System.in);
    }

    /**
     * pending!
     */
    public void welcomeUser() {
        System.out.println("Welcome to Minesweeper!");
    }

    /**
     * Creates the gameboard based on the specifications given by the user through
     * the standard input.
     */
    public void createBoardFromStandardInput() {
        // Print the instructions to the user
        System.out.println("Please input three space-separated numbers indicating the board's"
                + " height, width and number of mines respectively: ");

        // Keep reading until properly built
        while (gameboard == null) {
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();
            int bombs = scanner.nextInt();
            try {
                gameboard = new Board(rows, columns, bombs);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + ". Please try again:");
            }
        }
    }

    /**
     * pending!
     */
    public void playGame() {
        // Loop until game ends
        while (!gameboard.isGameOver()) {
            // Print current state and ask for new input
            System.out.println(gameboard);
            System.out.println("Enter command: ");
            int row = -1;
            int column = -1;
            String operation = null;
            try {
                row = scanner.nextInt() - 1;
                column = scanner.nextInt() - 1;
                operation = scanner.next();
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid coordinates. Please try again:");
            }

            // Perform the new action accordingly if valid
            // Mark/Flag (toggle)
            if (operation.equals("M")) {
                try {
                    gameboard.toggleCellFlag(row, column);
                } catch (InvalidActivityException e) {
                    System.out.println("Error: " + e.getMessage() + ". Please try again:");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage() + ". Please try again:");
                }
            }
            // Uncover
            else if (operation.equals("U")) {
                try {
                    gameboard.uncoverCell(row, column);
                } catch (InvalidActivityException e) {
                    System.out.println("Error: " + e.getMessage() + ". Please try again:");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage() + ". Please try again:");
                }
            } else {
                System.out.println("Invalid operation code. Please try again:");
            }
        }

        // End game
        System.out.println(gameboard);
        if (gameboard.isGameWon())
            System.out.println("CONGRATULATIONS!!! You win!");
        else
            System.out.println("Well, you just lost...");
    }

    /**
     * pending!
     */
    public void thankUser() {
        System.out.println("Thanks for playing Minesweeper! Please come back soon =)");
    }
}
