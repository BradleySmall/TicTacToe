package com.small.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Handles AI logic for Tic-Tac-Toe, making strategic moves for Player O.
 */
public class AIPlayer {
    private final TicTacToeGamePlayer game;
    private final GameEventListener listener;
    private final Random random;
    private final LineChecker lineChecker;
    private final AIDifficulty difficulty;

    public AIPlayer(BoardReader board, TicTacToeGamePlayer game, GameEventListener listener, AIDifficulty difficulty) {
        this.game = game;
        this.listener = listener;
        this.random = new Random();
        this.difficulty = difficulty;
        this.lineChecker = new LineChecker(board);
    }

    private List<int[]> findEmptyTiles() {
        List<int[]> emptyTiles = new ArrayList<>();
        for (int row = 0; row < BoardConfig.BOARD_SIZE; row++) {
            for (int col = 0; col < BoardConfig.BOARD_SIZE; col++) {
                if (((TicTacToeGame) game).getTileValue(row, col) == TileValue.EMPTY) {
                    emptyTiles.add(new int[]{row, col});
                }
            }
        }
        return emptyTiles;
    }

    private int[] findStrategicMove(TileValue tileValue) {
        return lineChecker.findPatternMove(tileValue);
    }

    public void executeMove() {
        int[] move = null;

        // Try to win
        if (difficulty == AIDifficulty.HARD || difficulty == AIDifficulty.MEDIUM) {
            move = findStrategicMove(TileValue.NOUGHT);
        }

        // Try to block
        if ((move == null || move.length == 0) && difficulty != AIDifficulty.EASY) {
            move = findStrategicMove(TileValue.CROSS);
        }

        // Fallback to random
        if (move == null || move.length == 0) {
            move = chooseRandomMove();
        }

        if (move != null && move.length == 2) {
            Logger.debug("AI (" + difficulty + ") executing move at (" + move[0] + ", " + move[1] + ")");
            Optional<TileValue> result = game.placeTile(move[0], move[1]);
            if (result.isPresent() && listener != null) {
                listener.onMoveMade(move[0], move[1], true);
            }
        } else {
            Logger.warn("AI could not find a valid move.");
        }
    }
    private int[] chooseRandomMove() {
        List<int[]> emptyTiles = findEmptyTiles();
        return emptyTiles.isEmpty() ? null : emptyTiles.get(random.nextInt(emptyTiles.size()));
    }
}