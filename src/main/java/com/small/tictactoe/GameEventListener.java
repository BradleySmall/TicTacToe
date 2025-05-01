package com.small.tictactoe;

public interface GameEventListener {
    void onNewGame();
    void onExit();
    void updateStatus();
    void onMoveMade(int row, int column, boolean isAIMove);
}
