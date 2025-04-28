package com.small.tictactoe;

/**
 * Represents the result of a Tic-Tac-Toe game.
 */
public enum GameResult {
    WIN_CROSS("Player X Wins!"),
    WIN_NOUGHT("Player O Wins!"),
    TIE("Tie!"),
    ONGOING("");

    private final String displayText;

    GameResult(String displayText) {
        this.displayText = displayText;
    }

    /**
     * Gets the display text for the game result.
     * @return the display text
     */
    public String getDisplayText() {
        return displayText;
    }
}