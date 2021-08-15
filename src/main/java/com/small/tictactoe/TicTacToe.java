package com.small.tictactoe;

import java.util.Arrays;

public class TicTacToe {
    public static final String PLAYER_S_WINS = "Player %s wins.";
    private final Character [][] board = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
    private String score = "";

    public void newGame() {
        for (int row = 0; row < 3; ++row) {
            for (int column=0; column < 3 ; ++column){
                board[row][column] = ' ';
            }
        }

        score = "";
    }
    private void placePiece(char xOrO, int row, int column) {
        board[row][column] = xOrO;
    }
    public String getScore() {
        for (int i = 0; i < 3; ++i) {
            if (winByRow(i)) return score;
        }
        for (int i = 0; i < 3; ++i){
            if (winByColumn(i)) return score;
        }
        if (winByCrisCross()) {
            return score;
        }

        score = "Tie Game";
        return score;
    }

    private boolean allSame(Character... row) {
        return Arrays.stream(row).allMatch(e->e=='O') || Arrays.stream(row).allMatch(e->e=='X');
    }

    public void play(int[][] plays) {
        newGame();
        for (int row = 0; row < 3; ++row){
            for (int col = 0; col < 3; ++col){
                placePiece(plays[row][col] == 0 ? 'O' : 'X', row, col);
            }
        }
    }

    private boolean winByCrisCross() {
        if (allSame(board[0][0], board[1][1], board[2][2]) ||
            allSame(board[0][2], board[1][1], board[2][0])) {
            score = String.format(PLAYER_S_WINS, board[1][1]);
            return true;
        }
        return false;
    }

    private boolean winByColumn(int column) {
        if (allSame(board[0][column], board[1][column], board[2][column])) {
            score = String.format(PLAYER_S_WINS, board[0][column]);
            return true;
        }
        return false;
    }

    private boolean winByRow(int row) {
        if (allSame(board[row])) {
            score = String.format(PLAYER_S_WINS, board[row][0]);
            return true;
        }
        return false;
    }
}
