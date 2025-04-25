/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import java.util.Optional;

/**
 * Interface for Tic-Tac-Toe game logic, managing moves, scores. and win condition.
 */
public interface TicTacToeGamePlayer {
    /**
     * Returns the current score or empty string if ongoing
     * @return score message
     */
    String getScore();

    /**
     *
     * @return The next players tile value
     */
    TileValue getCurrentPlayer();

    /**
     *
     * @return true if win/lose or tie
     */
    boolean isGameOver();

    /**
     * Returns the direction of the winning line.
     *
     * @return WinDirection (ROW, COLUMN, DIAGONAL, OR NONE
     */
    WinDirection getWinDirection();

    /**
     * returns winning row
     * @return row number
     */
    int getWinRow();

    /**
     * returns winning column
     * @return column number
     */
    int getWinColumn();

    /**
     * returns the value stored on the square
     *
     * @param row    row number
     * @param column column number
     * @return value on square
     */
    Optional<TileValue> makeMove(int row, int column);

    /**
     * begins a new game
     */
    void newGame();
}
