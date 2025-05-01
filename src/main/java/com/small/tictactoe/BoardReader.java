package com.small.tictactoe;

/**
 * Exposes read-only access to board values for AI and other read-only components.
 */
public interface BoardReader {
    TileValue getTileValue(int row, int column);
}