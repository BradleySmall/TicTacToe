/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

public interface TicTacToeGamePlayer {
    String getScore();

    WinDirection getWinDirection();

    int getWinRow();

    int getWinColumn();

    TileValue playSquare(int row, int column);

    void newGame();
}
