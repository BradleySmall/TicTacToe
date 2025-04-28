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
    private final GameBoardPanel boardPanel; // Still needed for UI updates
    private final Random random;
    private final LineChecker lineChecker;

    public AIPlayer(TicTacToeGamePlayer game, GameBoardPanel boardPanel) {
        this.game = game;
        this.boardPanel = boardPanel;
        this.random = new Random();
        this.lineChecker = new LineChecker((TicTacToeGame) game);
    }

    /**
     * Makes a strategic move for Player O (win, block, or random).
     */
    public void makeMove() {
        List<int[]> emptyTiles = findEmptyTiles();
        logEmptyTiles(emptyTiles);

        int[] winMove = findStrategicMove(TileValue.NOUGHT);
        if (winMove.length != 0) {
            logMove("Winning", winMove);
            executeMove(winMove[0], winMove[1]);
            return;
        }

        int[] blockMove = findStrategicMove(TileValue.CROSS);
        if (blockMove.length != 0) {
            logMove("Blocking", blockMove);
            executeMove(blockMove[0], blockMove[1]);
            return;
        }

        if (!emptyTiles.isEmpty()) {
            int[] move = emptyTiles.get(random.nextInt(emptyTiles.size()));
            logMove("Random", move);
            executeMove(move[0], move[1]);
        } else {
            System.out.println("AI: No empty tiles available");
        }
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

    private void executeMove(int row, int col) {
        Optional<TileValue> tileValue = game.placeTile(row, col);
        if (tileValue.isPresent()) {
            boardPanel.setTileValue(row, col, tileValue.get());
            boardPanel.repaint();
            boardPanel.getListener().updateStatus();
            GameResult result = game.getResult();
            if (result != GameResult.ONGOING) {
                boardPanel.displayWin(result.getDisplayText());
            }
            System.out.println("AI: Move successful, placed " + tileValue.get() + " at (" + row + ", " + col + ")");
        } else {
            System.out.println("AI: Move failed at (" + row + ", " + col + ")");
        }
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