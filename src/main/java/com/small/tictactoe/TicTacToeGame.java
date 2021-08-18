package com.small.tictactoe;

import java.util.Arrays;

public class TicTacToeGame implements TicTacToeGamePlayer {
    public static final String PLAYER_S_WINS = "Player %s wins.";
    private Character[][] board;
    private Character nextCharacter = 'x';
    private Character winPiece = ' ';
    private Character winDirection = ' ';
    private int winRow = -1;
    private int winColumn = -1;

    public TicTacToeGame() {
        board = new Character[3][3];
        newGame();
    }

    private void resetBoardValues() {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                board[row][column] = ' ';
            }
        }
    }

    private void resetGameState() {
        nextCharacter = 'x';
        winPiece = ' ';
        winDirection = ' ';
        winRow = -1;
        winColumn = -1;
    }

    public String getScore() {
        if (winPiece != ' ') {
            return String.format(PLAYER_S_WINS, winPiece);
        }
        if (noneLeft()) {
            return "Tie Game";
        }
        return "";
    }

    private void updateGameState() {
        for (int i = 0; i < 3; ++i) {
            if (winByRow(i)) {
                return;
            }
        }
        for (int i = 0; i < 3; ++i) {
            if (winByColumn(i)) {
                return;
            }
        }
        winByCrisCross();
    }

    @Override
    public void newGame() {
        resetBoardValues();
        resetGameState();
    }

    @Override
    public Character playSquare(int row, int column) {
        if (winPiece == ' '  && board[row][column] == ' ') {
            board[row][column] = getNextPiece();
            updateGameState();
            return board[row][column];
        }
        return null;
    }

    @Override
    public Character getNextPiece() {
        Character currentCharacter = nextCharacter;
        nextCharacter = nextCharacter == 'x' ? 'o' : 'x';
        return currentCharacter;
    }

    private boolean allSame(Character... row) {
        return Arrays.stream(row).allMatch(e -> e == 'o') || Arrays.stream(row).allMatch(e -> e == 'x');
    }
    private boolean noneLeft() {
        return Arrays.stream(board[0]).noneMatch(e -> e == ' ') &&
                Arrays.stream(board[1]).noneMatch(e -> e == ' ') &&
                Arrays.stream(board[2]).noneMatch(e -> e == ' ');

    }
    private boolean winByCrisCross() {
        boolean returnValue = false;
        if (allSame(board[0][0], board[1][1], board[2][2])) {
            winColumn = 0;
            returnValue = true;
        } else if (allSame(board[0][2], board[1][1], board[2][0])) {
            winColumn = 2;
            returnValue = true;
        }
        if (returnValue) {
            winDirection = 'd';
            winRow = 0;
            winPiece = board[winRow][winColumn];
        }
        return returnValue;
    }

    private boolean winByColumn(int column) {
        if (allSame(board[0][column], board[1][column], board[2][column])) {
            winDirection = 'c';
            winRow = 0;
            winColumn = column;
            winPiece = board[winRow][winColumn];
            return true;
        }
        return false;
    }

    private boolean winByRow(int row) {
        if (allSame(board[row])) {
            winDirection = 'r';
            winRow = row;
            winColumn = 0;
            winPiece = board[winRow][winColumn];
            return true;
        }
        return false;
    }

    public void setBoard(Character[][] board) {
        this.board = board;
        updateGameState();
    }
}