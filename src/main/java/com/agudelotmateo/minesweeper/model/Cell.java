package com.agudelotmateo.minesweeper.model;

/**
 * Represents a cell of the gameboard.
 * 
 * @author Mateo Agudelo Toro
 */
class Cell {
    private boolean hasBomb;
    private boolean isFlagged;
    private boolean isCovered;
    private int adjacentCounter;

    /**
     * Creates a new cell.
     * 
     * @param hasBomb whether this cell contains a bomb
     */
    public Cell(boolean hasBomb) {
        this.hasBomb = hasBomb;
        this.isFlagged = false;
        this.isCovered = true;
        this.adjacentCounter = 0;
    }

    /**
     * Checks if the cell has a bomb.
     * 
     * @return whether this cell contains a bomb
     */
    public boolean hasBomb() {
        return this.hasBomb;
    }

    /**
     * Checks if the cell has been flagged by the user.
     * 
     * @return whether this cell is flagged
     */
    public boolean isFlagged() {
        return this.isFlagged;
    }

    /**
     * Checks if the cell is covered.
     * 
     * @return whether this cell is covered
     */
    public boolean isCovered() {
        return this.isCovered;
    }

    /**
     * Gets the number of bombs adjacent to the cell.
     * 
     * @return the number of bombs adjacent to the cell
     */
    public int adjacentCount() {
        return this.adjacentCounter;
    }

    /**
     * Toggles the flagged state of the cell. That is, if the cell is not flagged
     * before calling this method, it will be flagged after it runs, and viceversa.
     */
    public void toggleFlag() {
        this.isFlagged = !this.isFlagged;
    }

    /**
     * Sets the state of the cell to uncovered.
     */
    public void uncover() {
        this.isCovered = false;
    }

    /**
     * Sets the number of bombs adjacent to the cell.
     */
    public void setAdjacentCounter(int adjacentCounter) {
        this.adjacentCounter = adjacentCounter;
    }
}
