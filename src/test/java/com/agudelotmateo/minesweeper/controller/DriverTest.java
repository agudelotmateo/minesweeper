package com.agudelotmateo.minesweeper.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

import com.agudelotmateo.minesweeper.model.Board;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for the game Driver.
 */
public class DriverTest {

    /**
     * Check if a valid input is read accordignly.
     */
    @RepeatedTest(10)
    public void createBoardFromStandardInputValid() {
        // Generate random valid input
        int rows = ThreadLocalRandom.current().nextInt(1, Board.MAX_ROWS_EXCLUSIVE);
        int columns = ThreadLocalRandom.current().nextInt(1, Board.MAX_COLUMNS_EXCLUSIVE);
        int bombs = Math.max(1, rows * columns / 2);
        System.setIn(new ByteArrayInputStream(String.format("%d %d %d\n", rows, columns, bombs).getBytes()));

        // Check if it succeed
        assertTimeout(Duration.ofSeconds(2), () -> new Driver().createBoardFromStandardInput());
    }

    /**
     * Check if input with too many rows is detected as invalid.
     */
    @RepeatedTest(10)
    public void createBoardFromStandardInputTooManyRows() {
        // Generate random invalid input with too many rows
        int rows = Board.MAX_ROWS_EXCLUSIVE;
        int columns = ThreadLocalRandom.current().nextInt(1, Board.MAX_COLUMNS_EXCLUSIVE);
        System.setIn(new ByteArrayInputStream(String.format("%d %d 1\n", rows, columns).getBytes()));

        // Check if it fails by asking for input again
        assertThrows(NoSuchElementException.class, () -> new Driver().createBoardFromStandardInput());
    }

    /**
     * Check if input with too few rows is detected as invalid.
     */
    @Test
    public void createBoardFromStandardInputTooFewRows() {
        // Generate random invalid input with too few rows
        int columns = ThreadLocalRandom.current().nextInt(1, Board.MAX_COLUMNS_EXCLUSIVE);
        System.setIn(new ByteArrayInputStream(String.format("0 %d 1\n", columns).getBytes()));

        // Check if it fails by asking for input again
        assertThrows(NoSuchElementException.class, () -> new Driver().createBoardFromStandardInput());
    }

    /**
     * Check if input with too many columns is detected as invalid.
     */
    @RepeatedTest(10)
    public void createBoardFromStandardInputTooManyColunns() {
        // Generate random invalid input with too many columns
        int rows = ThreadLocalRandom.current().nextInt(1, Board.MAX_ROWS_EXCLUSIVE);
        int columns = Board.MAX_COLUMNS_EXCLUSIVE;
        System.setIn(new ByteArrayInputStream(String.format("%d %d 1\n", rows, columns).getBytes()));

        // Check if it fails by asking for input again
        assertThrows(NoSuchElementException.class, () -> new Driver().createBoardFromStandardInput());
    }

    /**
     * Check if input with too few columns is detected as invalid.
     */
    @Test
    public void createBoardFromStandardInputTooFewColumns() {
        // Generate random invalid input with too few columns
        int rows = ThreadLocalRandom.current().nextInt(1, Board.MAX_ROWS_EXCLUSIVE);
        System.setIn(new ByteArrayInputStream(String.format("%d 0 1\n", rows).getBytes()));

        // Check if it fails by asking for input again
        assertThrows(NoSuchElementException.class, () -> new Driver().createBoardFromStandardInput());
    }

    /**
     * Check if input with too many bombs is detected as invalid.
     */
    @RepeatedTest(10)
    public void createBoardFromStandardInputTooManyBombs() {
        // Generate random invalid input with too many bombs
        int rows = ThreadLocalRandom.current().nextInt(1, Board.MAX_ROWS_EXCLUSIVE);
        int columns = ThreadLocalRandom.current().nextInt(1, Board.MAX_COLUMNS_EXCLUSIVE);
        int bombs = rows * columns + 1;
        System.setIn(new ByteArrayInputStream(String.format("%d %d %d\n", rows, columns, bombs).getBytes()));

        // Check if it fails by asking for input again
        assertThrows(NoSuchElementException.class, () -> new Driver().createBoardFromStandardInput());
    }

    /**
     * Check if input with too few bombs is detected as invalid.
     */
    @Test
    public void createBoardFromStandardInputTooFewBombs() {
        // Generate random invalid input with too few bombs
        int rows = ThreadLocalRandom.current().nextInt(1, Board.MAX_ROWS_EXCLUSIVE);
        int columns = ThreadLocalRandom.current().nextInt(1, Board.MAX_COLUMNS_EXCLUSIVE);
        System.setIn(new ByteArrayInputStream(String.format("%d %d 0\n", rows, columns).getBytes()));

        // Check if it fails by asking for input again
        assertThrows(NoSuchElementException.class, () -> new Driver().createBoardFromStandardInput());
    }
}
