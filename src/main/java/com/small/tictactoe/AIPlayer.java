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

    public AIPlayer(BoardReader board, TicTacToeGamePlayer game, GameEventListener listener) {
        this.game = game;
        this.listener = listener;
        this.random = new Random();
        this.lineChecker = new LineChecker(board);
    }

    private int[] findWinningMove() {
        int[] winMove = findStrategicMove(TileValue.NOUGHT);
        if (winMove.length != 0) {
            logMove("Winning", winMove);
            return winMove;
        }
        return new int[0];
    }

    private int[] findBlockingMove() {
        int[] blockMove = findStrategicMove(TileValue.CROSS);
        if (blockMove.length != 0) {
            logMove("Blocking", blockMove);
            return blockMove;
        }
        return new int[0];
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

//    void executeMove(int row, int col) {
//        Logger.debug("AIPlayer.executeMove: Attempting to place move at (" + row + ", " + col + ")");
//        Optional<TileValue> tileValue = game.placeTile(row, col);
//        if (tileValue.isPresent()) {
//            Logger.debug("AIPlayer.executeMove: Successfully placed " + tileValue.get() + " at (" + row + ", " + col + ")");
//            listener.onMoveMade(row, col, true);
//        } else {
//            Logger.debug("AI: Move failed at (" + row + ", " + col + ")");
//        }
//    }
    public void executeMove() {
        // Iterate to find a valid empty tile
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                Logger.debug("AIPlayer.executeMove: Attempting to place move at (" + row + ", " + column + ")");
                Optional<TileValue> result = game.placeTile(row, column);
                if (result.isPresent()) {
                    if (listener != null) {
                        listener.onMoveMade(row, column, true);
                    }
                    return;
                }
                Logger.debug("AI: Move failed at (" + row + ", " + column + ")");
            }
        }
        Logger.debug("AIPlayer.executeMove: No valid moves available");
    }
    private void logEmptyTiles(List<int[]> emptyTiles) {
        Logger.debug("AI: Empty tiles count = " + emptyTiles.size());
        for (int[] tile : emptyTiles) {
            Logger.debug("AI: Empty tile at (" + tile[0] + ", " + tile[1] + ")");
        }
    }

    private void logMove(String type, int[] move) {
        Logger.debug("AI: " + type + " move at (" + move[0] + ", " + move[1] + ")");
    }
}