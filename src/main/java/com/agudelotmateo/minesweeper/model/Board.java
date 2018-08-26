package com.agudelotmateo.minesweeper.model;

import java.util.HashSet;
import java.util.Set;

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
    private static final String TOO_MANY_ROWS = "rows must be less than 100";
    private static final String TOO_MANY_COLUMNS = "columns must be less than 100";
    private static final String TOO_MANY_BOMBS = "bombs cannot be greater than the total number of cells";
    private static final String INVALID_CELL = "invalid cell coordinates";
    private static final String GAME_OVER = "game already finished";

    // Define the valid displacements
    private static final int NUMBER_OF_DISPLACEMENTS = 8;
    private static final int[] ROW_DISPLACEMENTS = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] COLUMN_DISPLACEMENTS = { -1, 0, 1, -1, 1, -1, 0, 1 };

    // Board state
    private Cell[][] cells;
    private int rows;
    private int columns;
    private boolean gameOver;
    private boolean gameWon;
    private Set<Integer> bombLocations;
    private Set<Integer> flaggedLocations;

    /**
     * Creates a new game board given the exact specifications.
     * 
     * @param rows    board's height. Must be positive, and no upper limit is
     *                enforced
     * @param columns board's width. Must be positive, and no upper limit is
     *                enforced
     * @param bombs   amount of bombs to randomly place on the board. Must be
     *                positive and fit in the board
     * @throws IllegalArgumentException if the parameter's restrictions are not
     *                                  followed
     */
    public Board(int rows, int columns, int bombs) {
        // Check for input validity (no upper bound for rows/columns right now)
        if (rows <= 0)
            throw new IllegalArgumentException(TOO_FEW_ROWS);
        if (columns <= 0)
            throw new IllegalArgumentException(TOO_FEW_COLUMNS);
        if (bombs <= 0)
            throw new IllegalArgumentException(TOO_FEW_BOMBS);
        if (rows >= 100)
            throw new IllegalArgumentException(TOO_MANY_ROWS);
        if (columns >= 100)
            throw new IllegalArgumentException(TOO_MANY_COLUMNS);
        if (bombs > rows * columns)
            throw new IllegalArgumentException(TOO_MANY_BOMBS);

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
        this.flaggedLocations = new HashSet<>();
    }

    /**
     * Check whether a coordinate maps to a valid cell inside the board.
     * 
     * @param row    cell's row (y-value)
     * @param column cells's column (x-value)
     * @return whether the given position is valid
     */
    private boolean validPosition(int row, int column) {
        return row >= 0 && column >= 0 && row < this.cells.length && column < this.cells[row].length;
    }

    /**
     * Counts the number of bombs adjacent to the cell at the position specified by
     * the given coordinates (row, column).
     * 
     * @param row    cell's row (y-value)
     * @param column cells's column (x-value)
     * @return the amount of adjacent cells that contain bombs.
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
     * Marks all the cells in the board that contain bombs as uncovered, effectively
     * uncovering all bombs in the board.
     */
    private void uncoverAllBombs() {
        for (int bomb1D : this.bombLocations)
            this.cells[bomb1D / this.columns][bomb1D % this.columns].uncover();
    }

    /**
     * Uncovers the cell at the position specified by the given coordinates (row,
     * column). If the now uncovered cell does not have any adjacent bombs, the
     * adjacent cells are recursively uncovered automatically.
     * 
     * @param row    cell's row (y-value)
     * @param column cells's column (x-value
     * @throws InvalidActivityException if the game already ended
     * @throws IllegalArgumentException if the specified position is invalid
     */
    public void uncoverCell(int row, int column) throws InvalidActivityException {
        // Check if game is not already finished
        if (this.gameOver)
            throw new InvalidActivityException(GAME_OVER);

        // Check if the input cell is valid
        if (!validPosition(row, column))
            throw new IllegalArgumentException(INVALID_CELL);

        // Only uncovered and non-flagged cells can be uncovered
        Cell cell = this.cells[row][column];
        if (!cell.isCovered() || cell.isFlagged())
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
     * Toggles the flagged state of the cell at the position specified by the given
     * coordinates (row, column). That is, if the cell is not flagged before calling
     * this method, it will be flagged after it runs, and viceversa.
     * 
     * @param row    cell's row (y-value)
     * @param column cells's column (x-value
     * @throws InvalidActivityException if the game already ended
     * @throws IllegalArgumentException if the specified position is invalid
     */
    public void toggleCellFlag(int row, int column) throws InvalidActivityException {
        // Check if game is not already finished
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
        }
    }

    /**
     * String representation of the gameboard.
     * 
     * @return String representation of the gameboard
     */
    @Override
    public String toString() {
        // Initialize string with an initial capacity for improved efficiency
        StringBuilder sb = new StringBuilder(2 * (this.rows + 1) * (this.columns + 2));

        // Add a new line for asthetic purposes
        sb.append('\n');

        // Add a row indicating the column number
        sb.append("    ");
        for (int j = 0; j < this.columns; ++j)
            sb.append(String.format("%-2d", j + 1));
        sb.append('\n');

        // Add an empty row separating the column indicator from the actual board
        sb.append("     ");
        for (int j = 0; j < this.columns; ++j)
            sb.append("  ");
        sb.append('\n');

        // Print the actual board, including an extra column indicating the row number
        for (int i = 0; i < this.rows; ++i) {
            // Row number and separator
            sb.append(String.format("%-2d  ", i + 1));

            // Print the cells separated by spaces
            for (int j = 0; j < this.columns; ++j) {
                sb.append(this.cells[i][j]);
                sb.append(j == this.rows - 1 ? '\n' : ' ');
            }
        }

        // Finally build the string
        return sb.toString();
    }

    /**
     * Checks if the game has already finished.
     * 
     * @return whether the game has already finished
     */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * Checks if the user game has already won the game.
     * 
     * @return whether the user game has already won the game
     */
    public boolean isGameWon() {
        return this.gameWon;
    }
}
