package com.agudelotmateo.minesweeper;

import com.agudelotmateo.minesweeper.controller.Driver;

/**
 * Minesweeper's entry point.
 * 
 * @author Mateo Agudelo Toro
 */
public class App {
    public static void main(String[] args) {
        Driver game = new Driver();
        game.welcomeUser();
        game.createBoardFromStandardInput();
        game.playGame();
        game.thankUser();
    }
}
