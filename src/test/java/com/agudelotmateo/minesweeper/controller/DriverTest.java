package com.agudelotmateo.minesweeper.controller;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import com.agudelotmateo.minesweeper.model.Board;

import org.junit.jupiter.api.RepeatedTest;

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

        // Check if it succeeds
        Driver game = new Driver();
        assertTimeout(Duration.ofSeconds(2), () -> game.createBoardFromStandardInput());
    }
}
