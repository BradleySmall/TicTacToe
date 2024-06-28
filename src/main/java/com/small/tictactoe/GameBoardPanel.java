/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 */
public class GameBoardPanel extends JPanel {
    private final GameTile[][] gameTiles = new GameTile[3][3];
    private final transient TicTacToeGamePlayer player;
    private final JFrame jframe;

    private final JPanel glassPanel = new JPanel(){
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawWinLine(g);
        }
    };
    /**
     * @param player interface for handling game logic
     */
    GameBoardPanel(TicTacToeGamePlayer player, JFrame jframe) {
        this.jframe = jframe;
        this.player = player;

        initGUI();
        newGame();
    }

    /**
     *
     */
    public void newGame() {
        player.newGame();
        clearTable();
        repaint();
    }

    private void clearTable() {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                gameTiles[row][column].setCurrentValue(' ');
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawCrossHatch(g);
    }

    private void drawWinLine(Graphics g) {
        Character winDirection = player.getWinDirection();
        int winRow = player.getWinRow();
        int winColumn = player.getWinColumn();
        if (winDirection == ' ')
            return;

        g.setColor(Color.CYAN);
        if (winDirection == 'r') {
            switch (winRow) {
                case 0 -> g.fillRect(20, getHeight() / 6 - 10, getWidth() - 40, 20);
                case 1 -> g.fillRect(20, (getHeight() / 6) * 3 - 10, getWidth() - 40, 20);
                case 2 -> g.fillRect(20, getHeight() / 6 * 5 - 10, getWidth() - 40, 20);
                default -> throw new IllegalStateException("Unexpected row value: " + winRow);
            }
        }
        if (winDirection == 'c') {
            switch (winColumn) {
                case 0 -> g.fillRect(getWidth() / 6 - 10, 20, 20, getHeight() - 40);
                case 1 -> g.fillRect(getWidth() / 6 * 3 - 10, 20, 20, getHeight() - 40);
                case 2 -> g.fillRect(getWidth() / 6 * 5 - 10, 20, 20, getHeight() - 40);
                default -> throw new IllegalStateException("Unexpected column value: " + winColumn);
            }
        }
        if (winDirection == 'd') {
            if (winColumn == 0) {
                int[] x = {10, 30, getWidth() - 10, getWidth() - 30};
                int[] y = {30, 10, getHeight() - 30, getHeight() - 10};
                g.fillPolygon(new Polygon(x, y, 4));
            }
            if (winColumn == 2) {
                int[] x1 = {10, 30, getWidth() - 10, getWidth() - 30};
                int[] y1 = {getHeight() - 30, getHeight() - 10, 30, 10};
                g.fillPolygon(new Polygon(x1, y1, 4));
            }
        }
    }

    private void drawCrossHatch(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((getWidth()) / 3 - 10, 20, 20, getHeight() - 40);
        g.fillRect((getWidth()) / 3 * 2 - 10, 20, 20, getHeight() - 40);

        g.fillRect(20, (getHeight()) / 3 - 10, getWidth() - 40, 20);
        g.fillRect(20, (getHeight()) / 3 * 2 - 10, getWidth() - 40, 20);
    }

    private void initGUI() {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(30, 30, 30, 30);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = gridBagConstraints.weighty = 1.0;
        initializeTiles(gridBagConstraints);

        glassPanel.setForeground(Color.cyan);
        glassPanel.setOpaque(false);
        glassPanel.setVisible(false);
        jframe.setGlassPane(glassPanel);
    }

    private void initializeTiles(GridBagConstraints gridBagConstraints) {
        for (int column = 0; column < 3; ++column) {
            for (int row = 0; row < 3; ++row) {
                gridBagConstraints.gridx = column;
                gridBagConstraints.gridy = row;
                gameTiles[row][column] = new GameTile();
                gameTiles[row][column].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == 1) {
                            GameTile tile = (GameTile) e.getSource();
                            playTilesSquare(tile);
                        }
                    }
                });
                add(gameTiles[row][column], gridBagConstraints);
            }
        }
    }

    private void playTilesSquare(GameTile tile) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                if (gameTiles[row][column] == tile) {
                    playSquare(row, column);
                }
            }
        }
    }

    private void playSquare(int row, int column) {
        Character xOrO = player.playSquare(row, column);
        if (xOrO == null) {
            return;
        }
        if (xOrO == 'x' || xOrO == 'o' || xOrO == ' ') {
            gameTiles[row][column].setCurrentValue(xOrO);
            repaint();

            String score = player.getScore();
            if (!score.isEmpty()) {
                displayWin(score);
            }
        }
    }

    private void displayWin(String score) {
        glassPanel.setVisible(true);
        int n = JOptionPane.showConfirmDialog(this,
                "Care to try again?",
                score,
                JOptionPane.YES_NO_OPTION);

        glassPanel.setVisible(false);
        if (n == 0) {
            newGame();
        } else {
            System.exit(0);
        }
    }
}
