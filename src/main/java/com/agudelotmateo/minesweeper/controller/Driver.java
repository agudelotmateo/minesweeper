package com.agudelotmateo.minesweeper.controller;

import java.util.Scanner;

import javax.activity.InvalidActivityException;

import com.agudelotmateo.minesweeper.model.Board;

/**
 * Controls the execution of the game.
 * 
 * @author Mateo Agudelo Toro
 */
public class Driver {
    private Board gameboard;
    private Scanner scanner;

    /**
     * Creates a new game driver.
     */
    public Driver() {
        this.gameboard = null;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the error message.
     * 
     * @param exception the actual error
     */
    private void printError(Exception exception) {
        System.out.println("Error: " + exception.getMessage() + ". Please try again:");
    }

    /**
     * Welcomes the user to the new game session.
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
            int rows = this.scanner.nextInt();
            int columns = this.scanner.nextInt();
            int bombs = this.scanner.nextInt();
            try {
                this.gameboard = new Board(rows, columns, bombs);
            } catch (IllegalArgumentException e) {
                printError(e);
            }
        }

        // Print the result
        System.out.println(this.gameboard);
    }

    /**
     * Runs the game loop.
     */
    public void playGame() {
        // Loop until game ends
        while (!this.gameboard.isGameOver()) {
            // Print current state and ask for new input
            System.out.println("Enter command: ");
            int row = -1;
            int column = -1;
            String operation = null;
            try {
                row = this.scanner.nextInt() - 1;
                column = this.scanner.nextInt() - 1;
                operation = this.scanner.next();
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid coordinates. Please try again:");
            }

            // Perform the new action accordingly if valid
            if (operation == null)
                System.out.println("Error: invalid operation. Please try again:");
            // Mark/Flag (toggle)
            else if (operation.equals("M"))
                try {
                    this.gameboard.toggleCellFlag(row, column);
                } catch (InvalidActivityException | IllegalArgumentException e) {
                    printError(e);
                }
            // Uncover
            else if (operation.equals("U"))
                try {
                    this.gameboard.uncoverCell(row, column);
                } catch (InvalidActivityException | IllegalArgumentException e) {
                    printError(e);
                }
            else
                System.out.println("Invalid operation code. Please try again:");
            System.out.println(this.gameboard);
        }

        // Close user input
        this.scanner.close();
    }

    /**
     * Tells the user whether he won and says bye
     */
    public void thankUser() {
        if (this.gameboard.isGameWon())
            System.out.println("CONGRATULATIONS!!! You win!");
        else
            System.out.println("Well, you just lost...");
        System.out.println("Thanks for playing Minesweeper! Please come back soon =)");
    }
}
