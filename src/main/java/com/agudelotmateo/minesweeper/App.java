package com.agudelotmateo.minesweeper;

import java.io.IOException;

import com.agudelotmateo.minesweeper.controller.Driver;

/**
 * Minesweeper's entry point.
 * 
 * @author Mateo Agudelo Toro
 */
public class App {
    public static void main(String[] args) throws IOException {
        Driver game = new Driver();
        game.welcomeUser();
        game.createBoardFromStandardInput();
        game.playGame();
        game.thankUser();
    }
}
