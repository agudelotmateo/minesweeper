package com.agudelotmateo.minesweeper;

import java.util.HashSet;
import com.agudelotmateo.minesweeper.util.Random;

/**
 * Represents the gameboard.
 * 
 * @author Mateo Agudelo Toro
 */
class Board {
    // IllegalArgumentException messages
    private static final String TOO_FEW_ROWS = "rows must be positive";
    private static final String TOO_FEW_COLUMNS = "columns must be positive";
    private static final String TOO_FEW_BOMBS = "bombs must be positive";
    private static final String TOO_MUCH_BOMBS = "bombs cannot be greater than the total number of cells";

    private Cell cells[][];

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
        HashSet<Integer> bombLocations = Random.nUniqueIntsInRange(bombs, 0, rows * columns);
        cells = new Cell[rows][columns];
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < columns; ++j)
                cells[i][j] = new Cell(bombLocations.contains(i * columns + j));
    }

    @Override
    public String toString() {
        // pending
        return "test";
    }
}
