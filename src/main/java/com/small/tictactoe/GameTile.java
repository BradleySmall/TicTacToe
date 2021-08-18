/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import javax.swing.*;
import java.awt.*;

public class GameTile extends JPanel {
    private char currentValue = ' ';

    @Override
    protected void paintComponent(Graphics g) {
        if (getCurrentValue() == 'o') {
            drawNaught(g);
        } else if (getCurrentValue() == 'x') {
            drawCross(g);
        } else {
            clearBackground(g);
        }
    }

    private void clearBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        repaint();
    }

    private void drawCross(Graphics g) {
        g.setColor(Color.RED);
        int[] x = {10, 30, getWidth() - 10, getWidth() - 30};
        int[] y = {30, 10, getHeight() - 30, getHeight() - 10};
        g.fillPolygon(new Polygon(x, y, 4));
        int[] x1 = {10, 30, getWidth() - 10, getWidth() - 30};
        int[] y1 = {getHeight() - 30, getHeight() - 10, 30, 10};
        g.fillPolygon(new Polygon(x1, y1, 4));
    }

    private void drawNaught(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.fillOval(20, 20, getWidth() - 40, getHeight() - 40);
    }

    public char getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(char currentValue) {
        this.currentValue = currentValue;
    }

}
