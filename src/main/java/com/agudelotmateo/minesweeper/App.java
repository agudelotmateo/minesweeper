package com.agudelotmateo.minesweeper;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Minesweeper's entry point.
 * 
 * @author Mateo Agudelo Toro
 */
public class App {
    public static void main(String[] args) {
        // pending!
        Board gameboard = createBoardFromStandardInput();
    }

    /**
     * Creates the gameboard based on the specifications given by the user through
     * the standard input.
     * 
     * @return the gameboard according to the input specifications
     */
    private static Board createBoardFromStandardInput() {
        // Print welcome and initial instructions
        System.out.println("Welcome to Minesweeper!");
        System.out.println("Please input three space-separated numbers indicating the board's"
                + " height, width and number of mines respectively: ");

        // Read and validate input
        Scanner sc = new Scanner(System.in);
        Board gameboard = null;
        while (gameboard == null) {
            int rows = sc.nextInt();
            int columns = sc.nextInt();
            int bombs = sc.nextInt();
            try {
                gameboard = new Board(rows, columns, bombs);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + ". Please try again:");
            }
        }

        // Return when valid
        sc.close();
        return gameboard;
    }
}
