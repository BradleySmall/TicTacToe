package com.small.tictactoe;

public interface TicTacToeGamePlayer {
    String getScore() ;

    Character playSquare(int row, int column);

    void newGame();
}
