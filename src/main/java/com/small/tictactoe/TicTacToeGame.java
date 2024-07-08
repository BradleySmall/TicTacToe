/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import java.io.Serializable;
import java.util.Arrays;

import static com.small.tictactoe.TileValue.*;

public class TicTacToeGame implements TicTacToeGamePlayer, Serializable {
    public static final String PLAYER_S_WINS = "Player %s wins.";
    private TileValue[][] board;
    private TileValue nextCharacter = CROSS;
    private TileValue winPiece = BLANK;
    private WinDirection winDirection = WinDirection.NONE;
    private boolean isTied = false;
    private int winRow = -1;
    private int winColumn = -1;

    public TicTacToeGame() {
        board = new TileValue[3][3];
        newGame();
    }

    private void resetBoardValues() {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                board[row][column] = BLANK;
            }
        }
    }

    private void resetGameState() {
        nextCharacter = CROSS;
        winPiece = BLANK;
        winDirection = WinDirection.NONE;
        winRow = -1;
        winColumn = -1;
        isTied = false;
    }

    @Override
    public String getScore() {
        if (winPiece != BLANK) {
            return String.format(PLAYER_S_WINS, winPiece);
        }
        if (isTied) {
            return "Tie Game";
        }
        return "";
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
        for (int i = 0; i < 3; ++i) {
            if (winByRow(i)) {
                return;
            }
            if (winByColumn(i)) {
                return;
            }
        }
        if (winByCrisCross()) {
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
    public TileValue playSquare(int row, int column) {
        if (winPiece == BLANK && board[row][column] == BLANK) {
            board[row][column] = getNextPiece();
            updateGameState();
            return board[row][column];
        }
        return UNKNOWN;
    }

    public TileValue getNextPiece() {
        TileValue currentCharacter = nextCharacter;
        nextCharacter = nextCharacter == CROSS ? NOUGHT : CROSS;
        return currentCharacter;
    }

    private boolean allSame(TileValue... row) {
        return Arrays.stream(row).allMatch(e -> e == NOUGHT) || Arrays.stream(row).allMatch(e -> e == CROSS);
    }

    private boolean noneLeft() {
        return Arrays.stream(board[0]).noneMatch(e -> e == BLANK) &&
                Arrays.stream(board[1]).noneMatch(e -> e == BLANK) &&
                Arrays.stream(board[2]).noneMatch(e -> e == BLANK);

    }

    private boolean winByCrisCross() {
        boolean returnValue;
        if (allSame(board[0][0], board[1][1], board[2][2])) {
            winColumn = 0;
            returnValue = true;
        } else if (allSame(board[0][2], board[1][1], board[2][0])) {
            winColumn = 2;
            returnValue = true;
        } else {
            returnValue = false;
        }
        if (returnValue) {
            winDirection = WinDirection.DIAGONAL;
            winRow = 0;
            winPiece = board[winRow][winColumn];
        }
        return returnValue;
    }

    private boolean winByColumn(int column) {
        if (allSame(board[0][column], board[1][column], board[2][column])) {
            winDirection = WinDirection.COLUMN;
            winRow = 0;
            winColumn = column;
            winPiece = board[winRow][winColumn];
            return true;
        }
        return false;
    }

    private boolean winByRow(int row) {
        if (allSame(board[row])) {
            winDirection = WinDirection.ROW;
            winRow = row;
            winColumn = 0;
            winPiece = board[winRow][winColumn];
            return true;
        }
        return false;
    }

    public void setBoard(TileValue[][] board) {
        this.board = board;
        updateGameState();
    }
}