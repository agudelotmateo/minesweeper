package com.agudelotmateo.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Random utility class.
 */
public class CellTest {

    /**
     * Check wheter the state of a newly created cell containing a bomb is correct.
     */
    @Test
    public void constructorTrue() {
        Cell cell = new Cell(true);

        assertEquals(true, cell.hasBomb());
        assertEquals(false, cell.isFlagged());
        assertEquals(true, cell.isCovered());
        assertEquals(0, cell.adjacentCount());
    }

    /**
     * Check wheter the state of a newly created cell not containing a bomb is
     * correct.
     */
    @Test
    public void constructorFalse() {
        Cell cell = new Cell(false);

        assertEquals(false, cell.hasBomb());
        assertEquals(false, cell.isFlagged());
        assertEquals(true, cell.isCovered());
        assertEquals(0, cell.adjacentCount());
    }
}
