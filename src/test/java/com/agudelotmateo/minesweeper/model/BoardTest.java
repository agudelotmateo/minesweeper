package com.agudelotmateo.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTest {

    /**
     * Check trying to create a board with too few rows is forbidden.
     */
    public void constructorTooFewRows() {
        assertThrows(IllegalArgumentException.class, () -> new Board(0, Board.MAX_COLUMNS_EXCLUSIVE - 1, 1));
    }

    /**
     * Check trying to create a board with too many rows is forbidden.
     */
    public void constructorTooManyRows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Board(Board.MAX_ROWS_EXCLUSIVE, Board.MAX_COLUMNS_EXCLUSIVE - 1, 1));
    }

    /**
     * Check trying to create a board with too few columns is forbidden.
     */
    public void constructorTooFewColumns() {
        assertThrows(IllegalArgumentException.class, () -> new Board(Board.MAX_ROWS_EXCLUSIVE - 1, 0, 1));
    }

    /**
     * Check trying to create a board with too many columns is forbidden.
     */
    public void constructorTooManyColumns() {
        assertThrows(IllegalArgumentException.class,
                () -> new Board(Board.MAX_ROWS_EXCLUSIVE - 1, Board.MAX_COLUMNS_EXCLUSIVE, 1));
    }

    /**
     * Check trying to create a board with too few bombs is forbidden.
     */
    public void constructorTooFewBombs() {
        assertThrows(IllegalArgumentException.class,
                () -> new Board(Board.MAX_ROWS_EXCLUSIVE - 1, Board.MAX_COLUMNS_EXCLUSIVE - 1, 0));
    }

    /**
     * Check trying to create a board with too many bombs is forbidden.
     */
    public void constructorTooManyBombs() {
        assertThrows(IllegalArgumentException.class, () -> new Board(Board.MAX_ROWS_EXCLUSIVE - 1,
                Board.MAX_COLUMNS_EXCLUSIVE - 1, Board.MAX_ROWS_EXCLUSIVE * Board.MAX_COLUMNS_EXCLUSIVE));
    }

}
