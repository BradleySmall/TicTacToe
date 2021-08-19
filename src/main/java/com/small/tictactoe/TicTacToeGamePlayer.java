/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

public interface TicTacToeGamePlayer {
    String getScore();

    Character getWinDirection();

    int getWinRow();

    int getWinColumn();

    Character playSquare(int row, int column);

    void newGame();
}
