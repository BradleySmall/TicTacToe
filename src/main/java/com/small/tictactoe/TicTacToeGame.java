package com.small.tictactoe;

import java.util.Arrays;
import java.util.Optional;

public class TicTacToeGame implements TicTacToeGamePlayer {
    private final TileValue[][] board;
    private TileValue currentPlayer;
    private GameResult result;
    private WinDirection winDirection;
    private int winRow;
    private int winColumn;
    private final LineChecker lineChecker;

    public TicTacToeGame() {
        this.board = new TileValue[BoardConfig.BOARD_SIZE][BoardConfig.BOARD_SIZE];
        this.lineChecker = new LineChecker(this);
        newGame();
    }

    @Override
    public Optional<TileValue> placeTile(int row, int column) {
        if (isValidMove(row, column)) {
            TileValue placedPlayer = currentPlayer; // Store player before switch
            board[row][column] = placedPlayer;
            updateGameState();
            System.out.println("TicTacToeGame.placeTile: Placed " + placedPlayer + " at (" + row + ", " + column + "), result=" + result);
            return Optional.of(currentPlayer);
        }
        return Optional.empty();
    }

    private boolean isValidMove(int row, int column) {
        boolean isValid = row >= 0 && row < 3 &&
                column >= 0 && column < 3 &&
                board[row][column] == TileValue.EMPTY &&
                result == GameResult.ONGOING;
        if (!isValid) {
            System.out.println("TicTacToeGame.isValidMove: Invalid move at (" + row + ", " + column + "), " +
                    "rowValid=" + (row >= 0 && row < 3) + ", " +
                    "colValid=" + (column >= 0 && column < 3) + ", " +
                    "tileEmpty=" + (board[row][column] == TileValue.EMPTY) + ", " +
                    "gameOngoing=" + (result == GameResult.ONGOING));
        }
        return isValid;
    }

    // public Optional<TileValue> placeTile(int row, int column) {
    //     validateCoordinates(row, column);
    //     if (result != GameResult.ONGOING || board[row][column] != TileValue.EMPTY) {
    //         return Optional.empty();
    //     }
    //     TileValue placedValue = currentPlayer;
    //     board[row][column] = placedValue;
    //     updateGameState();
    //     return Optional.of(placedValue);
    // }

    @Override
    public void newGame() {
        resetBoard();
        winDirection = WinDirection.NONE;
        winRow = -1;
        winColumn = -1;
    }

    public TileValue getTileValue(int row, int column) {
        validateCoordinates(row, column);
        return board[row][column];
    }

    @Override
    public GameResult getResult() {
        return result;
    }

    @Override
    public TileValue getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public boolean isGameOver() {
        return result != GameResult.ONGOING;
    }

    @Override
    public WinDirection getWinDirection() {
        return winDirection;
    }

    @Override
    public int getWinRow() {
        return winRow;
    }

    @Override
    public int getWinColumn() {
        return winColumn;
    }

    public void setBoard(TileValue[][] newBoard) {
        validateBoard(newBoard);
        for (int row = 0; row < BoardConfig.BOARD_SIZE; row++) {
            System.arraycopy(newBoard[row], 0, board[row], 0, BoardConfig.BOARD_SIZE);
        }
        updateGameState();
    }

    private void resetBoard() {
        for (TileValue[] row : board) {
            Arrays.fill(row, TileValue.EMPTY);
        }
        currentPlayer = TileValue.CROSS;
        result = GameResult.ONGOING;
    }

    private void validateCoordinates(int row, int column) {
        if (row < 0 || row >= BoardConfig.BOARD_SIZE || column < 0 || column >= BoardConfig.BOARD_SIZE) {
            throw new IllegalArgumentException("Row and column must be between 0 and " + (BoardConfig.BOARD_SIZE - 1));
        }
    }

    private void validateBoard(TileValue[][] newBoard) {
        if (newBoard == null || newBoard.length != BoardConfig.BOARD_SIZE) {
            throw new IllegalArgumentException("Board must be a " + BoardConfig.BOARD_SIZE + "x" + BoardConfig.BOARD_SIZE + " array");
        }
        for (TileValue[] row : newBoard) {
            if (row == null || row.length != BoardConfig.BOARD_SIZE) {
                throw new IllegalArgumentException("Each row must have " + BoardConfig.BOARD_SIZE + " columns");
            }
            for (TileValue value : row) {
                if (value == null) {
                    throw new IllegalArgumentException("Board tiles cannot be null");
                }
            }
        }
    }

    private void updateGameState() {
        checkWinConditions();
        if (result == GameResult.ONGOING && isBoardFull()) {
            result = GameResult.TIE;
            winDirection = WinDirection.NONE;
            winRow = -1;
            winColumn = -1;
        }
        if (result == GameResult.ONGOING) {
            currentPlayer = (currentPlayer == TileValue.CROSS) ? TileValue.NOUGHT : TileValue.CROSS;
            System.out.println("Tic Tac Toe Game.updateGameState: Switched to player=" + currentPlayer);
        }
    }

    private void checkWinConditions() {
        for (int i = 0; i < lineChecker.getLines().size(); i++) {
            Line line = lineChecker.getLines().get(i);
            TileValue[] tiles = line.getTiles();
            if (tiles[0] != TileValue.EMPTY && tiles[0] == tiles[1] && tiles[1] == tiles[2]) {
                TileValue winner = tiles[0];
                result = (winner == TileValue.CROSS) ? GameResult.WIN_CROSS : GameResult.WIN_NOUGHT;
                setWinDetails(i);
                return;
            }
        }
    }

    private void setWinDetails(int lineIndex) {
        if (lineIndex < 3) { // Rows
            winDirection = WinDirection.ROW;
            winRow = lineIndex;
            winColumn = 0;
        } else if (lineIndex < 6) { // Columns
            winDirection = WinDirection.COLUMN;
            winRow = 0;
            winColumn = lineIndex - 3;
        } else { // Diagonals
            winDirection = WinDirection.DIAGONAL;
            winRow = (lineIndex == 6) ? 0 : 0;
            winColumn = (lineIndex == 6) ? 0 : 2;
        }
    }

    private boolean isBoardFull() {
        for (int row = 0; row < BoardConfig.BOARD_SIZE; row++) {
            for (int col = 0; col < BoardConfig.BOARD_SIZE; col++) {
                if (board[row][col] == TileValue.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}