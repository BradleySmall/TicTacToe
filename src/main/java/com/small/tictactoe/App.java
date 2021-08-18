package com.small.tictactoe;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private transient TicTacToeGamePlayer player = new TicTacToeGame();
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
        JPanel buttonPanel = new JPanel();
        JButton buttonNewGame = new JButton("New Game");
        buttonNewGame.addActionListener(e -> newGame());
        buttonPanel.add(buttonNewGame);

        add(buttonPanel);
        Dimension d = new Dimension(600, 100);
        textScore.setSize(d);
        textScore.setMaximumSize(d);
        textScore.setMinimumSize(d);
        add(textScore);
        pack();
        setSize(600, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void newGame() {
        gameBoardPanel.newGame();
        textScore.setText("");
    }

    public void getScore() {
        TicTacToeGame game = (TicTacToeGame) player;
        textScore.setText(game.getScore());
    }
}
