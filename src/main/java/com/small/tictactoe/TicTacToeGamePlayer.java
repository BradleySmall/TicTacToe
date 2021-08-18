package com.small.tictactoe;

public interface TicTacToeGamePlayer {
    Character getNextPiece();

    Character playSquare(int row, int column);

    void newGame();
}
