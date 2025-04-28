package com.small.tictactoe;

/**
 * Tracks and formats game scores for X, O, and ties.
 */
public class ScoreTracker {
    private int xWins;
    private int oWins;
    private int ties;

    /**
     * Updates scores based on the game outcome.
     * @param result the game result
     */
    public void updateScore(GameResult result) {
        switch (result) {
            case WIN_CROSS -> xWins++;
            case WIN_NOUGHT -> oWins++;
            case TIE -> ties++;
            case ONGOING -> {} // No action
        }
    }

    /**
     * Resets all scores to zero.
     */
    public void reset() {
        xWins = 0;
        oWins = 0;
        ties = 0;
    }

    /**
     * Returns the formatted score display.
     */
    public String getScoreDisplay() {
        return String.format("X Wins: %d | O Wins: %d | Ties: %d", xWins, oWins, ties);
    }
}