package com.small.tictactoe;

import javax.swing.*;

public class App extends JFrame {
    private final GameBoardPanel gameBoardPanel = new GameBoardPanel();
    App() {
        initGUI();
    }

    private void initGUI() {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(gameBoardPanel);
        JPanel buttonPanel = new JPanel();
        JButton buttonNewGame = new JButton("New Game");
        buttonNewGame.addActionListener(e -> newGame());
        JButton buttonGetScore = new JButton ("Get Score");
        buttonGetScore.addActionListener(e -> getScore());
        buttonPanel.add(buttonNewGame);
        buttonPanel.add(buttonGetScore);
        add(buttonPanel);
        pack();
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void newGame() {
        gameBoardPanel.newGame();
    }
    private void getScore() {
        TicTacToeGame game = new TicTacToeGame();
        game.setBoard(gameBoardPanel.getBoard());
        System.out.println(game.getScore());

    }
    public static void main(String[] args) {
        new App();
    }

}
