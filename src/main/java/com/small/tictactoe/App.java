/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import javax.swing.*;

public class App extends JFrame {
    private final TicTacToeGamePlayer player = new TicTacToeGame();
    private final GameBoardPanel gameBoardPanel = new GameBoardPanel(player);
    private final JTextArea textScore = new JTextArea();

    App() {
        initGUI();
    }

    public static void main(String[] args) {
        new App();
    }

    private void initGUI() {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        add(gameBoardPanel);

        pack();
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void getScore() {
        TicTacToeGame game = (TicTacToeGame) player;
        textScore.setText(game.getScore());
    }
}
