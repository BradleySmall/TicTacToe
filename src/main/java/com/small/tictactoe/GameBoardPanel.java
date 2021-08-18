package com.small.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameBoardPanel extends JPanel implements MouseListener {
    Character [][] board;
    private final GameTile[][] gameTiles = new GameTile[3][3];
    private char nextPiece = 'x';

    GameBoardPanel() {
        initGUI();
    }
    Character[][] getBoard() {
        Character [][]  board = new Character[3][3];
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                board[row][column] = gameTiles[row][column].getCurrentValue();
            }
        }
        return board;
    }
    public void placePiece(char xOrO, int row, int col) {
        gameTiles[row][col].setCurrentValue(xOrO);
    }

    public void newGame() {
        nextPiece = 'x';

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                placePiece(' ', row, column);
            }
        }
        repaint();
    }

    public char getNextPiece() {
        char returnValue = nextPiece;

        if (nextPiece == 'x') {
            nextPiece = 'o';
        } else if (nextPiece == 'o') {
            nextPiece = 'x';
        }
        return returnValue;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawCrossHatch(g);
    }

    private void drawCrossHatch(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((getWidth()) / 3 - 10, 0 + 20, 20, getHeight() - 40);
        g.fillRect((getWidth()) / 3 * 2 - 10, 0 + 20, 20, getHeight() - 40);

        g.fillRect(0 + 20, (getHeight()) / 3 - 10, getWidth() - 40, 20);
        g.fillRect(0 + 20, (getHeight()) / 3 * 2 - 10, getWidth() - 40, 20);
    }

    void initGUI() {
        GridBagLayout gbl = new GridBagLayout();
        setBackground(Color.BLACK);
        setLayout(gbl);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(30, 30, 30, 30);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = gridBagConstraints.weighty = 1.0;
        for (int column = 0; column < 3; ++column) {
            for (int row = 0; row < 3; ++row) {
                gridBagConstraints.gridx = column;
                gridBagConstraints.gridy = row;
                gameTiles[row][column] = new GameTile();
                gameTiles[row][column].addMouseListener(this);
                add(gameTiles[row][column], gridBagConstraints);

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GameTile tile = (GameTile) e.getSource();

        if (e.getButton() == 1) {
            if (tile.getCurrentValue() == ' ') {
                tile.setCurrentValue(getNextPiece());
            }
        } else {
            tile.setCurrentValue(' ');
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // ignoring this action
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // ignoring this action
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // ignoring this action
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // ignoring this action
    }
}
