package com.agudelotmateo.minesweeper.model;

/**
 * pending!
 */
class Cell {
    private boolean hasBomb;
    private boolean isFlagged;
    private boolean isCovered;
    private int adjacentCounter;

    /**
     * pending!
     */
    public Cell(boolean hasBomb) {
        this.hasBomb = hasBomb;
        this.isFlagged = false;
        this.isCovered = true;
        this.adjacentCounter = 0;
    }

    /**
     * pending!
     */
    public boolean hasBomb() {
        return hasBomb;
    }

    /**
     * pending!
     */
    public boolean isFlagged() {
        return isFlagged;
    }

    /**
     * pending!
     */
    public boolean isCovered() {
        return isCovered;
    }

    /**
     * pending!
     */
    public int adjacentCount() {
        return this.adjacentCounter;
    }

    /**
     * pending!
     */
    public void toggleFlag() {
        this.isFlagged = !this.isFlagged;
    }

    /**
     * pending!
     */
    public void uncover() {
        this.isCovered = false;
    }

    /**
     * pending!
     */
    public void setAdjacentCounter(int adjacentCounter) {
        this.adjacentCounter = adjacentCounter;
    }
}
