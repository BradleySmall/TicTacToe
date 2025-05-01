/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

/**
 *
 */
public class GameBoardPanel extends JPanel {
    private final GameTile[][] gameTiles = new GameTile[BoardConfig.BOARD_SIZE][BoardConfig.BOARD_SIZE];
    private final transient TicTacToeGamePlayer player;
    private final JLayeredPane layeredPane;
    private final JPanel tilePanel;
    private final JPanel winLinePanel;
    private  transient GameEventListener listener;

    /**
     * @param player interface for handling game logic
     */
    GameBoardPanel(TicTacToeGamePlayer player, GameEventListener listener) {
        this.player = player;
        this.listener = listener;
        this.layeredPane = new JLayeredPane();
        this.tilePanel = new JPanel();
        this.winLinePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawWinLine(g);
            }
        };

        initGUI();
    }

    /**
     * initializes a new game
     */
    public void newGame() {
        player.newGame();
        clearTable();
        winLinePanel.setVisible(false);
        repaint();
        if (listener != null) {
            listener.updateStatus();
        }
    }

    private void clearTable() {
        for (int row = 0; row < BoardConfig.BOARD_SIZE; ++row) {
            for (int column = 0; column < BoardConfig.BOARD_SIZE; ++column) {
                gameTiles[row][column].setCurrentValue(TileValue.EMPTY);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCrossHatch(g);
    }

    private void drawWinLine(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(BoardConfig.WIN_LINE_COLOR);
        WinDirection winDirection = player.getWinDirection();
        int winRow = player.getWinRow();
        int winColumn = player.getWinColumn();
        if (winDirection == WinDirection.NONE) return;

        if (winDirection == WinDirection.ROW) {
            drawRowWinLine(winRow, g2d);
        } else if (winDirection == WinDirection.COLUMN) {
            drawColumnWinLine(winColumn, g2d);
        } else if (winDirection == WinDirection.DIAGONAL) {
            drawDiagonalWinLine(winColumn, g2d);
        }
    }

    private void drawDiagonalWinLine(int winColumn, Graphics2D g2d) {
        int[] xPoints = {TileConfig.CROSS_LATERAL_OFFSET, TileConfig.CROSS_BAR_WIDTH,
                getWidth() - TileConfig.CROSS_LATERAL_OFFSET, getWidth() - TileConfig.CROSS_BAR_WIDTH};
        int[] yPoints = winColumn == 0 ?
                new int[]{TileConfig.CROSS_BAR_WIDTH, TileConfig.CROSS_VERTICAL_OFFSET,
                        getHeight() - TileConfig.CROSS_BAR_WIDTH, getHeight() - TileConfig.CROSS_VERTICAL_OFFSET} :
                new int[]{getHeight() - TileConfig.CROSS_BAR_WIDTH, getHeight() - TileConfig.CROSS_VERTICAL_OFFSET,
                        TileConfig.CROSS_BAR_WIDTH, TileConfig.CROSS_VERTICAL_OFFSET};
        g2d.fillPolygon(new Polygon(xPoints, yPoints, TileConfig.NUMBER_CROSS_BAR_POINTS));
    }

    private void drawColumnWinLine(int winColumn, Graphics2D g2d) {
        int x = getWidth() / 6 * (2 * winColumn + 1) - BoardConfig.HALF_LINE_THICKNESS;
        g2d.fillRect(x, BoardConfig.VERTICAL_OFFSET,
                BoardConfig.LINE_THICKNESS, getHeight() - BoardConfig.VERTICAL_OFFSET_DOUBLED);
    }

    private void drawRowWinLine(int winRow, Graphics2D g2d) {
        int y = getHeight() / 6 * (2 * winRow + 1) - BoardConfig.HALF_LINE_THICKNESS;
        g2d.fillRect(BoardConfig.HORIZONTAL_OFFSET, y,
                getWidth() - BoardConfig.HORIZONTAL_OFFSET_DOUBLED, BoardConfig.LINE_THICKNESS);
    }


    private void drawCrossHatch(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(BoardConfig.GRID_COLOR);
        g2d.fillRect(getWidth() / 3 - BoardConfig.HALF_LINE_THICKNESS, BoardConfig.VERTICAL_OFFSET,
                BoardConfig.LINE_THICKNESS, getHeight() - BoardConfig.VERTICAL_OFFSET_DOUBLED);
        g2d.fillRect(getWidth() / 3 * 2 - BoardConfig.HALF_LINE_THICKNESS, BoardConfig.VERTICAL_OFFSET,
                BoardConfig.LINE_THICKNESS, getHeight() - BoardConfig.VERTICAL_OFFSET_DOUBLED);
        g2d.fillRect(BoardConfig.HORIZONTAL_OFFSET, getHeight() / 3 - BoardConfig.HALF_LINE_THICKNESS,
                getWidth() - BoardConfig.HORIZONTAL_OFFSET_DOUBLED, BoardConfig.LINE_THICKNESS);
        g2d.fillRect(BoardConfig.HORIZONTAL_OFFSET, getHeight() / 3 * 2 - BoardConfig.HALF_LINE_THICKNESS,
                getWidth() - BoardConfig.HORIZONTAL_OFFSET_DOUBLED, BoardConfig.LINE_THICKNESS);
    }

    private void initGUI() {
        setBackground(BoardConfig.BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);

        tilePanel.setLayout(new GridBagLayout());
        tilePanel.setOpaque(false);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(BoardConfig.TOP_INSET, BoardConfig.LEFT_INSET, BoardConfig.BOTTOM_INSET, BoardConfig.RIGHT_INSET);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = gridBagConstraints.weighty = 1.0;
        initializeTiles(gridBagConstraints);

        winLinePanel.setOpaque(false);
        winLinePanel.setVisible(false);
        winLinePanel.setEnabled(false);

        layeredPane.add(tilePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(winLinePanel, JLayeredPane.POPUP_LAYER);

        winLinePanel.setBounds(0, 0, getWidth(), getHeight());

        // Resize panels on window resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                tilePanel.setBounds(0, 0, getWidth(), getHeight());
                winLinePanel.setBounds(0, 0, getWidth(), getHeight());
            }
        });
    }

    private void initializeTiles(GridBagConstraints gridBagConstraints) {
        for (int column = 0; column < BoardConfig.BOARD_SIZE; ++column) {
            for (int row = 0; row < BoardConfig.BOARD_SIZE; ++row) {
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
                tilePanel.add(gameTiles[row][column], gridBagConstraints);
            }
        }
    }

    private void playTilesSquare(GameTile tile) {
        for (int row = 0; row < BoardConfig.BOARD_SIZE; ++row) {
            for (int column = 0; column < BoardConfig.BOARD_SIZE; ++column) {
                if (gameTiles[row][column] == tile) {
                    playSquare(row, column);
                }
            }
        }
    }

    void playSquare(int row, int column) {
        Logger.debug("GameBoardPanel.playSquare: Processing move at (" + row + ", " + column + ")");
        Optional<TileValue> tileValue = player.placeTile(row, column);
        if (tileValue.isEmpty()) {
            Logger.debug("GameBoardPanel.playSquare: Move invalid at (" + row + ", " + column + ")");
            return;
        }
        TileValue placedValue = tileValue.get();
        GameResult result = player.getResult();
        // Use displayValue for non-winning moves, placedValue for winning moves
        TileValue displayValue = (result == GameResult.ONGOING && placedValue == TileValue.CROSS) ? TileValue.NOUGHT :
                (result == GameResult.ONGOING && placedValue == TileValue.NOUGHT) ? TileValue.CROSS : placedValue;
        Logger.debug("GameBoardPanel.playSquare: Placed " + displayValue + " at (" + row + ", " + column + ")");
        gameTiles[row][column].setCurrentValue(displayValue);
        repaint();
        if (listener != null) {
            listener.updateStatus();
            listener.onMoveMade(row, column, false);
        }
        if (result != GameResult.ONGOING) {
            displayWin(result.getDisplayText());
        }
    }


    public void displayWin(String score) {
        if (score.isEmpty()) {
            return;
        }
        winLinePanel.setVisible(true);
        int choice = JOptionPane.showConfirmDialog(this,
                "Care to try again?",
                score,
                JOptionPane.YES_NO_OPTION);
        winLinePanel.setVisible(false);
        if (choice == JOptionPane.YES_OPTION) {
            listener.onNewGame();
        } else {
            listener.onExit();
        }
    }


    public void setListener(GameEventListener listener) {
        this.listener = listener;
    }
    public void setTileForAI(int row, int column, TileValue tileValue) {
        gameTiles[row][column].setCurrentValue(tileValue);
        repaint();
    }
}
