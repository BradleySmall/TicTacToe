/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicTacToeGame implements TicTacToeGamePlayer, Serializable {
    private static final String PLAYER_S_WINS = "Player %s wins.";
    private TileValue[][] board;
    private TileValue nextCharacter = TileValue.CROSS;
    private TileValue winPiece = TileValue.EMPTY;
    private WinDirection winDirection = WinDirection.NONE;
    private boolean isTied = false;
    private int winRow = -1;
    private int winColumn = -1;

    public TicTacToeGame() {
        board = new TileValue[BoardConfig.BOARD_SIZE][BoardConfig.BOARD_SIZE];
        newGame();
    }

    private void resetBoardValues() {
        for (int row = 0; row < BoardConfig.BOARD_SIZE; ++row) {
            for (int column = 0; column < BoardConfig.BOARD_SIZE; ++column) {
                board[row][column] = TileValue.EMPTY;
            }
        }
    }

    private void resetGameState() {
        nextCharacter = TileValue.CROSS;
        winPiece = TileValue.EMPTY;
        winDirection = WinDirection.NONE;
        winRow = -1;
        winColumn = -1;
        isTied = false;
    }

    /**
     * @return if the game is in a state that is over like tie or win
     */
    @Override
    public String getScore() {
        String result;
        if (!isGameOver()) {
            result = "";
        } else if (isTied) {
            result = "Tie Game";
        } else {
            result = String.format(PLAYER_S_WINS, winPiece.name());
        }
        return result;
    }

    @Override
    public TileValue getCurrentPlayer() {
        return nextCharacter;
    }

    @Override
    public boolean isGameOver() {
        return winPiece != TileValue.EMPTY || isTied;
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

    private void updateGameState() {
        if (checkWinConditions()) {
            return;
        }
        isTied = noneLeft();
    }

    @Override
    public void newGame() {
        resetBoardValues();
        resetGameState();
    }

    @Override
    /**
     * places the next piece and updates the game state returning the winning piece if in a win state
     */
    public Optional<TileValue> makeMove(int row, int column) {
        if ( row < 0 || row >= BoardConfig.BOARD_SIZE || column < 0 || column >= BoardConfig.BOARD_SIZE) {
            throw new IllegalArgumentException("Row and column must be between 0 and 2");
        }
        if (winPiece == TileValue.EMPTY  && board[row][column] == TileValue.EMPTY) {
            board[row][column] = getNextPiece();
            updateGameState();
            return Optional.of(board[row][column]);
        }
        return Optional.empty();
    }

    public TileValue getNextPiece() {
        TileValue currentCharacter = nextCharacter;
        nextCharacter = nextCharacter == TileValue.CROSS ? TileValue.NOUGHT : TileValue.CROSS;
        return currentCharacter;
    }

    private boolean allSame(TileValue... row) {
        return Arrays.stream(row).collect(Collectors.toSet()).size() == 1 && row[0] != TileValue.EMPTY;
    }

    private boolean noneLeft() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .noneMatch(e -> e == TileValue.EMPTY);
    }
    private boolean checkWinConditions() {
        // Check rows
        for (int row = 0; row < BoardConfig.BOARD_SIZE; row++) {
            if (allSame(board[row][0], board[row][1], board[row][2])) {
                winDirection = WinDirection.ROW;
                winRow = row;
                winColumn = 0;
                winPiece = board[row][0];
                return true;
            }
        }
        // Check columns
        for (int col = 0; col < BoardConfig.BOARD_SIZE; col++) {
            if (allSame(board[0][col], board[1][col], board[2][col])) {
                winDirection = WinDirection.COLUMN;
                winRow = 0;
                winColumn = col;
                winPiece = board[0][col];
                return true;
            }
        }
        // Check diagonals
        if (allSame(board[0][0], board[1][1], board[2][2])) {
            winDirection = WinDirection.DIAGONAL;
            winRow = 0;
            winColumn = 0;
            winPiece = board[0][0];
            return true;
        }
        if (allSame(board[0][2], board[1][1], board[2][0])) {
            winDirection = WinDirection.DIAGONAL;
            winRow = 0;
            winColumn = 2;
            winPiece = board[0][2];
            return true;
        }
        return false;
    }

    public void setBoard(TileValue[][] board) {
        if (board == null || board.length != BoardConfig.BOARD_SIZE || Arrays.stream(board).anyMatch(row -> row == null || row.length != BoardConfig.BOARD_SIZE)) {
            throw new IllegalArgumentException("Board must be a square array of size " + BoardConfig.BOARD_SIZE);
        }
        this.board = Arrays.stream(board).map(TileValue[]::clone).toArray(TileValue[][]::new);
        updateGameState();
    }
}