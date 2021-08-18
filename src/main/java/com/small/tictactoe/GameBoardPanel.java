package com.small.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameBoardPanel extends JPanel implements MouseListener {
    private final GameTile[][] gameTiles = new GameTile[3][3];
     private  final transient TicTacToeGamePlayer player;

    GameBoardPanel(TicTacToeGamePlayer player) {
        this.player = player;
        initGUI();
        newGame();
    }

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

    private void drawCrossHatch(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((getWidth()) / 3 - 10, 0 + 20, 20, getHeight() - 40);
        g.fillRect((getWidth()) / 3 * 2 - 10, 0 + 20, 20, getHeight() - 40);

        g.fillRect(0 + 20, (getHeight()) / 3 - 10, getWidth() - 40, 20);
        g.fillRect(0 + 20, (getHeight()) / 3 * 2 - 10, getWidth() - 40, 20);
    }

    private void initGUI() {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
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

    private void playSquare(int row, int column) {
        Character xOrO = player.playSquare(row, column);
        if (xOrO == null) {
            return;
        }
        if (xOrO == 'x' || xOrO == 'o' || xOrO == ' ') {
            gameTiles[row][column].setCurrentValue(xOrO);
            repaint();

            App app = (App)getRootPane().getParent();

            app.getScore();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            GameTile tile = (GameTile) e.getSource();
            for (int row = 0; row < 3; ++row) {
                for (int column = 0; column < 3; ++column) {
                    if (gameTiles[row][column] == tile) {
                        playSquare(row, column);
                    }
                }
            }
        }
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
