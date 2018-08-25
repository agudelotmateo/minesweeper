package com.agudelotmateo.minesweeper.model;

import java.util.HashSet;

import javax.activity.InvalidActivityException;

import com.agudelotmateo.minesweeper.util.Random;

/**
 * Represents the gameboard.
 * 
 * @author Mateo Agudelo Toro
 */
public class Board {
    // IllegalArgumentException messages
    private static final String TOO_FEW_ROWS = "rows must be positive";
    private static final String TOO_FEW_COLUMNS = "columns must be positive";
    private static final String TOO_FEW_BOMBS = "bombs must be positive";
    private static final String TOO_MUCH_BOMBS = "bombs cannot be greater than the total number of cells";
    private static final String INVALID_CELL = "invalid cell coordinates";
    private static final String GAME_OVER = "game already finished";

    // Define the valid displacements
    private static final int NUMBER_OF_DISPLACEMENTS = 8;
    private static final int[] ROW_DISPLACEMENTS = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] COLUMN_DISPLACEMENTS = { -1, 0, 1, -1, 1, -1, 0, 1 };

    // Board state
    private Cell cells[][];
    private int rows, columns;
    private boolean gameOver, gameWon;
    private HashSet<Integer> bombLocations, flaggedLocations;

    /**
     * pending!
     */
    public Board(int rows, int columns, int bombs) {
        // Check for input validity (no upper bound for rows/columns right now)
        if (rows <= 0)
            throw new IllegalArgumentException(TOO_FEW_ROWS);
        if (columns <= 0)
            throw new IllegalArgumentException(TOO_FEW_COLUMNS);
        if (bombs <= 0)
            throw new IllegalArgumentException(TOO_FEW_BOMBS);
        if (bombs > rows * columns)
            throw new IllegalArgumentException(TOO_MUCH_BOMBS);

        // Generate bomb locations in the board randomly and populate accordingly
        this.bombLocations = Random.nUniqueIntsInRange(bombs, 0, rows * columns);
        this.cells = new Cell[rows][columns];
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < columns; ++j)
                this.cells[i][j] = new Cell(this.bombLocations.contains(i * columns + j));

        // Initialize board state
        this.rows = rows;
        this.columns = columns;
        this.gameOver = false;
        this.gameWon = false;
        flaggedLocations = new HashSet<>();
    }

    /**
     * pending!
     */
    private boolean validPosition(int row, int column) {
        return row >= 0 && column >= 0 && row < this.cells.length && column < this.cells[row].length;
    }

    /**
     * pending!
     */
    private int countAdjacentBombs(int row, int column) {
        // Iterate throught all of the valid possibilities
        int counter = 0;
        for (int i = 0; i < NUMBER_OF_DISPLACEMENTS; ++i) {
            int tmpRow = row + ROW_DISPLACEMENTS[i];
            int tmpColumn = column + COLUMN_DISPLACEMENTS[i];
            if (validPosition(tmpRow, tmpColumn) && this.cells[tmpRow][tmpColumn].hasBomb())
                ++counter;
        }

        // Return the final count
        return counter;
    }

    /**
     * pending!
     */
    private void uncoverAllBombs() {
        for (int bomb1D : this.bombLocations)
            this.cells[bomb1D / this.columns][bomb1D % this.columns].uncover();
    }

    /**
     * pending!
     */
    public void uncoverCell(int row, int column) throws InvalidActivityException {
        // Check if game isn't already finished
        if (this.gameOver)
            throw new InvalidActivityException(GAME_OVER);

        // Check if the input cell is valid
        if (!validPosition(row, column))
            throw new IllegalArgumentException(INVALID_CELL);

        // If cell is already uncovered, do nothing
        Cell cell = this.cells[row][column];
        if (!cell.isCovered())
            return;

        // Uncover the cell
        cell.uncover();

        // If the game is now lost, uncover all the bombs and quit
        if (cell.hasBomb()) {
            this.gameOver = true;
            this.gameWon = false;
            uncoverAllBombs();
            return;
        }

        // Count the number of adjacent bombs
        int bombCounter = countAdjacentBombs(row, column);
        cell.setAdjacentCounter(bombCounter);

        // If no adjacent bombs, recursively uncover the adjacent cells
        if (bombCounter == 0)
            for (int i = 0; i < NUMBER_OF_DISPLACEMENTS; ++i) {
                int tmpRow = row + ROW_DISPLACEMENTS[i];
                int tmpColumn = column + COLUMN_DISPLACEMENTS[i];
                if (validPosition(tmpRow, tmpColumn))
                    uncoverCell(tmpRow, tmpColumn);
            }
    }

    /**
     * pending!
     */
    public void toggleCellFlag(int row, int column) throws InvalidActivityException {
        // Check if game isn't already finished
        if (this.gameOver)
            throw new InvalidActivityException(GAME_OVER);

        // Check if the input cell is valid
        if (!validPosition(row, column))
            throw new IllegalArgumentException(INVALID_CELL);

        // Toggle the flag
        Cell cell = this.cells[row][column];
        int location = row * this.columns + column;
        cell.toggleFlag();
        if (cell.isFlagged())
            this.flaggedLocations.add(location);
        else
            this.flaggedLocations.remove(location);

        // If the game is now won, uncover all the bombs and quit
        if (this.flaggedLocations.equals(this.bombLocations)) {
            this.gameOver = true;
            this.gameWon = true;
            uncoverAllBombs();
            return;
        }
    }

    @Override
    public String toString() {
        // Initialize string with an initial capacity for improved efficiency
        StringBuilder sb = new StringBuilder(2 * (this.rows + 1) * (this.columns + 2));

        // Add a row indicating the column number
        sb.append("   ");
        for (int j = 0; j < this.columns; ++j)
            sb.append(String.format("%-2d", j + 1));
        sb.append('\n');

        // Print the actual board, including an extra coumn indicating the row number
        for (int i = 0; i < this.rows; ++i) {
            // Row number
            sb.append(String.format("%-2d ", i + 1));

            for (int j = 0; j < this.columns; ++j) {
                // Get the cell information and print accordingly
                Cell cell = this.cells[i][j];
                if (cell.isCovered())
                    sb.append(cell.isFlagged() ? 'P' : '.');
                else {
                    if (cell.hasBomb())
                        sb.append(cell.isFlagged() ? 'P' : '*');
                    else {
                        int count = cell.adjacentCount();
                        if (count == 0)
                            sb.append('-');
                        else
                            sb.append(count);
                    }
                }

                // Separate the cells using spaces
                sb.append(j == this.rows - 1 ? '\n' : ' ');
            }
        }

        // Finally build the string
        return sb.toString();
    }

    /**
     * pending!
     */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * pending!
     */
    public boolean isGameWon() {
        return this.gameWon;
    }
}
