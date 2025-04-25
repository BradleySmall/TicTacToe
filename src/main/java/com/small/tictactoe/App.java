/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import javax.swing.*;
import java.awt.*;

/**
 * Main application class for the Tic-Tac-Toe game, initializing the GUI.
 */
public class App extends JFrame implements GameEventListener {
    private final TicTacToeGamePlayer player;
    private final GameBoardPanel gameBoardPanel;
//    private final GameEventListener gameEventListener;
    /**
     * Constructs the application and initializes the GUI.
     */
    App(TicTacToeGamePlayer player) {
        this.player = player;
        this.gameBoardPanel = new GameBoardPanel(this.player, this);
        initGUI();
    }

    /**
     * Initializes the GUI with game board panel.
     */
    private void initGUI() {
        if (BoardConfig.WINDOW_WIDTH <= 0 || BoardConfig.WINDOW_HEIGHT <= 0) {
            throw new IllegalStateException("Window dimensions must be positive");
        }
        if (BoardConfig.BOARD_SIZE <= 0) {
            throw new IllegalStateException("Board size must be positive");
        }

        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(gameBoardPanel, BorderLayout.CENTER);
        pack();
        setSize(BoardConfig.WINDOW_WIDTH, BoardConfig.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Creates a TicTacToeGamePlayer instance.
     * @return a new game player
     */
    private static TicTacToeGamePlayer createGamePlayer() {
        return new TicTacToeGame();
    }

    /**
     * Entry point for the application.
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App(createGamePlayer()));
    }

    @Override
    public void onNewGame() {
        gameBoardPanel.newGame();
    }

    @Override
    public void onExit() {
        System.exit(0);
    }
}
