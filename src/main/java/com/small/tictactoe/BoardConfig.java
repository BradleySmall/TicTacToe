package com.small.tictactoe;

import java.awt.Color;

public final class BoardConfig {
    public static final int LINE_THICKNESS = 20;
    public static final int HALF_LINE_THICKNESS = LINE_THICKNESS / 2;
    public static final int VERTICAL_OFFSET = 20;
    public static final int HORIZONTAL_OFFSET = 20;
    public static final int VERTICAL_OFFSET_DOUBLED = VERTICAL_OFFSET * 2;
    public static final int HORIZONTAL_OFFSET_DOUBLED = HORIZONTAL_OFFSET * 2;

    public static final int TOP_INSET = 30;
    public static final int LEFT_INSET = 30;
    public static final int BOTTOM_INSET = 30;
    public static final int RIGHT_INSET = 30;

    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;
    public static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final Color GRID_COLOR = Color.BLUE;
    public static final Color WIN_LINE_COLOR = Color.CYAN;
    public static final int BOARD_SIZE = 3;

    static {
        if (WINDOW_WIDTH <= 0 || WINDOW_HEIGHT <= 0) {
            throw new IllegalStateException("Window dimensions must be positive");
        }
        if (BOARD_SIZE <= 0) {
            throw new IllegalStateException("Board size must be positive");
        }
        if (LINE_THICKNESS <= 0 || VERTICAL_OFFSET <= 0 || HORIZONTAL_OFFSET <= 0) {
            throw new IllegalStateException("Line thickness and offsets must be positive");
        }
        if (TOP_INSET < 0 || LEFT_INSET < 0 || BOTTOM_INSET < 0 || RIGHT_INSET < 0) {
            throw new IllegalStateException("Insets must be non-negative");
        }
    }

}
