package com.small.tictactoe;

/**
 * Represents a line (row, column, or diagonal) in the Tic-Tac-Toe board.
 */
public interface Line {
    /**
     * Gets the tile values in this line.
     * @return array of tile values
     */
    TileValue[] getTiles();

    /**
     * Converts the index in the line to board coordinates.
     * @param index the index in the line
     * @return [row, col] coordinates
     */
    int[] toBoardCoordinates(int index);
}