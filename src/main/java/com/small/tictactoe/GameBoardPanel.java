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

import static com.small.tictactoe.TileValue.*;

/**
 *
 */
public class GameBoardPanel extends JPanel {
    private static final int HALF_LINE_THICKNESS = 10;
    private static final int VERTICAL_OFFSET = 20;
    private static final int LINE_THICKNESS = 20;
    private static final int VERTICAL_OFFSET_DOUBLED = 40;
    private static final int HORIZONTAL_OFFSET_DOUBLED = 40;
    private static final int THIRD_FACTOR = 3;
    public static final int CONFIRMATION_POSITIVE = 0;
    public static final int EXIT_STATUS = 0;
    private static final int HORIZONTAL_OFFSET = 20;
    public static final int SIXTH_FACTOR = 6;
    private static final int CROSS_LATERAL_OFFSET = 10;
    private static final int CROSS_BAR_WIDTH = 30;
    private static final int CROSS_VERTICAL_OFFSET = 10;
    public static final int NUMBER_CROSS_BAR_POINTS = 4;

    private final GameTile[][] gameTiles = new GameTile[3][3];
    private final transient TicTacToeGamePlayer player;
    private final JFrame jframe;

    private final JPanel glassPanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawWinLine(g);
        }

        private void drawWinLine(Graphics g) {
            WinDirection winDirection = player.getWinDirection();
            int winRow = player.getWinRow();
            int winColumn = player.getWinColumn();
            if (winDirection == WinDirection.NONE)
                return;

            g.setColor(Color.CYAN);
            if (winDirection == WinDirection.ROW) {
                switch (winRow) {
                    case 0 -> g.fillRect(HORIZONTAL_OFFSET, getHeight() / SIXTH_FACTOR - HALF_LINE_THICKNESS, getWidth() - HORIZONTAL_OFFSET_DOUBLED, LINE_THICKNESS);
                    case 1 -> g.fillRect(HORIZONTAL_OFFSET, getHeight() / SIXTH_FACTOR * 3 - HALF_LINE_THICKNESS, getWidth() - HORIZONTAL_OFFSET_DOUBLED, LINE_THICKNESS);
                    case 2 -> g.fillRect(HORIZONTAL_OFFSET, getHeight() / SIXTH_FACTOR * 5 - HALF_LINE_THICKNESS, getWidth() - HORIZONTAL_OFFSET_DOUBLED, LINE_THICKNESS);
                    default -> throw new IllegalStateException("Unexpected row value: " + winRow);
                }
            }
            if (winDirection == WinDirection.COLUMN) {
                switch (winColumn) {
                    case 0 -> g.fillRect(getWidth() / SIXTH_FACTOR - HALF_LINE_THICKNESS, VERTICAL_OFFSET, LINE_THICKNESS, getHeight() - VERTICAL_OFFSET_DOUBLED);
                    case 1 -> g.fillRect(getWidth() / SIXTH_FACTOR * 3 - HALF_LINE_THICKNESS, VERTICAL_OFFSET, LINE_THICKNESS, getHeight() - VERTICAL_OFFSET_DOUBLED);
                    case 2 -> g.fillRect(getWidth() / SIXTH_FACTOR * 5 - HALF_LINE_THICKNESS, VERTICAL_OFFSET, LINE_THICKNESS, getHeight() - VERTICAL_OFFSET_DOUBLED);
                    default -> throw new IllegalStateException("Unexpected column value: " + winColumn);
                }
            }
            if (winDirection == WinDirection.DIAGONAL) {
                if (winColumn == 0) {
                    int[] x = {CROSS_LATERAL_OFFSET, CROSS_BAR_WIDTH, getWidth() - CROSS_LATERAL_OFFSET, getWidth() - CROSS_BAR_WIDTH};
                    int[] y = {CROSS_BAR_WIDTH, CROSS_VERTICAL_OFFSET, getHeight() - CROSS_BAR_WIDTH, getHeight() - CROSS_VERTICAL_OFFSET};
                    g.fillPolygon(new Polygon(x, y, NUMBER_CROSS_BAR_POINTS));
                }
                if (winColumn == 2) {
                    int[] x1 = {CROSS_LATERAL_OFFSET, CROSS_BAR_WIDTH, getWidth() - CROSS_LATERAL_OFFSET, getWidth() - CROSS_BAR_WIDTH};
                    int[] y1 = {getHeight() - CROSS_BAR_WIDTH, getHeight() - CROSS_VERTICAL_OFFSET, CROSS_BAR_WIDTH, CROSS_VERTICAL_OFFSET};
                    g.fillPolygon(new Polygon(x1, y1, NUMBER_CROSS_BAR_POINTS));
                }
            }
        }

    };

    /**
     * @param player interface for handling game logic
     * @param jframe parent frame to hold glassPane for win-lines
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
                gameTiles[row][column].setCurrentValue(BLANK);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawCrossHatch(g);
    }

    private void drawCrossHatch(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(getWidth() / THIRD_FACTOR - HALF_LINE_THICKNESS, VERTICAL_OFFSET, LINE_THICKNESS, getHeight() - VERTICAL_OFFSET_DOUBLED);
        g.fillRect(getWidth() / THIRD_FACTOR * 2 - HALF_LINE_THICKNESS, VERTICAL_OFFSET, LINE_THICKNESS, getHeight() - VERTICAL_OFFSET_DOUBLED);

        g.fillRect(HORIZONTAL_OFFSET, getHeight() / THIRD_FACTOR - HALF_LINE_THICKNESS, getWidth() - HORIZONTAL_OFFSET_DOUBLED, LINE_THICKNESS);
        g.fillRect(HORIZONTAL_OFFSET, getHeight() / THIRD_FACTOR * 2 - HALF_LINE_THICKNESS, getWidth() - HORIZONTAL_OFFSET_DOUBLED, LINE_THICKNESS);
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
        TileValue xOrO = player.playSquare(row, column);
        if (xOrO == UNKNOWN) {
            return;
        }
        gameTiles[row][column].setCurrentValue(xOrO);
        repaint();

        String score = player.getScore();
        if (!score.isEmpty()) {
            displayWin(score);
        }
    }

    private void displayWin(String score) {
        glassPanel.setVisible(true);
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Care to try again?",
                score,
                JOptionPane.YES_NO_OPTION);

        glassPanel.setVisible(false);
        if (confirmation == CONFIRMATION_POSITIVE) {
            newGame();
        } else {
            System.exit(EXIT_STATUS);
        }
    }
}
