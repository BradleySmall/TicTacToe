/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import javax.swing.*;
import java.awt.*;

public class GameTile extends JPanel {
    private TileValue currentValue = TileValue.EMPTY;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(TileConfig.BACKGROUND_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (currentValue == TileValue.NOUGHT) {
            drawNaught(g2d);
        } else if (currentValue == TileValue.CROSS) {
            drawCross(g2d);
        }
    }

    private void drawCross(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(TileConfig.CROSS_COLOR);
        g2d.setStroke(new BasicStroke(TileConfig.CROSS_BAR_WIDTH / 2f));
        g2d.drawLine(TileConfig.CROSS_LATERAL_OFFSET, TileConfig.CROSS_VERTICAL_OFFSET,
                getWidth() - TileConfig.CROSS_LATERAL_OFFSET, getHeight() - TileConfig.CROSS_VERTICAL_OFFSET);
        g2d.drawLine(getWidth() - TileConfig.CROSS_LATERAL_OFFSET, TileConfig.CROSS_VERTICAL_OFFSET,
                TileConfig.CROSS_LATERAL_OFFSET, getHeight() - TileConfig.CROSS_VERTICAL_OFFSET);
    }

    private void drawNaught(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(TileConfig.NOUGHT_COLOR);
        g2d.fillOval(0, 0, getWidth(), getHeight());
        g2d.setColor(TileConfig.BACKGROUND_COLOR);
        g2d.fillOval(TileConfig.NOUGHT_INTERIOR_OFFSET_X, TileConfig.NOUGHT_INTERIOR_OFFSET_Y,
                getWidth() - BoardConfig.HORIZONTAL_OFFSET_DOUBLED,
                getHeight() - BoardConfig.VERTICAL_OFFSET_DOUBLED);
    }
    /**
     * Returns the current value of the tile.
     * @return the tile's value (CROSS, NOUGHT, or EMPTY)
     */
    public TileValue getCurrentValue() {
        return currentValue;
    }

    /**
     * setter for currentValue checks the validity of the parameter
     * @param currentValue the value to set
     */
    public void setCurrentValue(TileValue currentValue) {
        if (currentValue == null) {
            throw new IllegalArgumentException("Cannot set tile to null");
        }
        this.currentValue = currentValue;
    }


}
