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
        return null;
    }

    private int[] findBlockingMove() {
        int[] blockMove = findStrategicMove(TileValue.CROSS);
        if (blockMove.length != 0) {
            logMove("Blocking", blockMove);
            return blockMove;
        }
        return null;
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
//        System.out.println("AIPlayer.executeMove: Attempting to place move at (" + row + ", " + col + ")");
//        Optional<TileValue> tileValue = game.placeTile(row, col);
//        if (tileValue.isPresent()) {
//            System.out.println("AIPlayer.executeMove: Successfully placed " + tileValue.get() + " at (" + row + ", " + col + ")");
//            listener.onMoveMade(row, col, true);
//        } else {
//            System.out.println("AI: Move failed at (" + row + ", " + col + ")");
//        }
//    }
    public void executeMove() {
        // Iterate to find a valid empty tile
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                System.out.println("AIPlayer.executeMove: Attempting to place move at (" + row + ", " + column + ")");
                Optional<TileValue> result = game.placeTile(row, column);
                if (result.isPresent()) {
                    if (listener != null) {
                        listener.onMoveMade(row, column, true);
                    }
                    return;
                }
                System.out.println("AI: Move failed at (" + row + ", " + column + ")");
            }
        }
        System.out.println("AIPlayer.executeMove: No valid moves available");
    }
    private void logEmptyTiles(List<int[]> emptyTiles) {
        System.out.println("AI: Empty tiles count = " + emptyTiles.size());
        for (int[] tile : emptyTiles) {
            System.out.println("AI: Empty tile at (" + tile[0] + ", " + tile[1] + ")");
        }
    }

    private void logMove(String type, int[] move) {
        System.out.println("AI: " + type + " move at (" + move[0] + ", " + move[1] + ")");
    }
}