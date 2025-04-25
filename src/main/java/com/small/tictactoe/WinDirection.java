package com.small.tictactoe;

/**
 * Enum representing the direction of a winning line in Tic-Tac-Toe.
 */
public enum WinDirection {
    ROW, // this indicates a horizontal win
    COLUMN, // this indicates a vertical win
    DIAGONAL, // this represents a diagonal win
    NONE
}
