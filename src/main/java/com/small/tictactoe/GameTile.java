/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import javax.swing.*;
import java.awt.*;

import static com.small.tictactoe.TileValue.BLANK;

public class GameTile extends JPanel {
    private static final int UPPER_LEFT_X = 0;
    private static final int UPPER_LEFT_Y = 0;
    private static final int NOUGHT_INTERIOR_OFFSET_X = 20;
    private static final int NOUGHT_INTERIOR_OFFSET_Y = 20;
    private static final int CROSS_LATERAL_OFFSET = 10;
    private static final int CROSS_BAR_WIDTH = 30;
    private static final int CROSS_VERTICAL_OFFSET = 10;
    public static final int NUMBER_CROSS_BAR_POINTS = 4;
    private TileValue currentValue = BLANK;

    @Override
    protected void paintComponent(Graphics g) {
        switch (getCurrentValue()) {
            case NOUGHT -> drawNought(g);
            case CROSS -> drawCross(g);
            default -> clearBackground(g);
        }
    }

    private void clearBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(UPPER_LEFT_X, UPPER_LEFT_Y, getWidth(), getHeight());
        repaint();
    }

    private void drawCross(Graphics g) {
        g.setColor(Color.RED);
        int[] x = {
                CROSS_LATERAL_OFFSET,
                CROSS_BAR_WIDTH,
                getWidth() - CROSS_LATERAL_OFFSET,
                getWidth() - CROSS_BAR_WIDTH
        };
        int[] y = {
                CROSS_BAR_WIDTH,
                CROSS_VERTICAL_OFFSET,
                getHeight() - CROSS_BAR_WIDTH,
                getHeight() - CROSS_VERTICAL_OFFSET
        };
        g.fillPolygon(new Polygon(x, y, NUMBER_CROSS_BAR_POINTS));
        int[] x1 = {
                CROSS_LATERAL_OFFSET,
                CROSS_BAR_WIDTH,
                getWidth() - CROSS_LATERAL_OFFSET,
                getWidth() - CROSS_BAR_WIDTH
        };
        int[] y1 = {
                getHeight() - CROSS_BAR_WIDTH,
                getHeight() - CROSS_VERTICAL_OFFSET,
                CROSS_BAR_WIDTH,
                CROSS_VERTICAL_OFFSET
        };
        g.fillPolygon(new Polygon(x1, y1, NUMBER_CROSS_BAR_POINTS));
    }

    private void drawNought(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(UPPER_LEFT_X, UPPER_LEFT_Y, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.fillOval(
                NOUGHT_INTERIOR_OFFSET_X,
                NOUGHT_INTERIOR_OFFSET_Y,
                getWidth() - (NOUGHT_INTERIOR_OFFSET_X * 2),
                getHeight() -(NOUGHT_INTERIOR_OFFSET_Y * 2));
    }

    public TileValue getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(TileValue currentValue) {
        this.currentValue = currentValue;
    }

}
