package com.small.tictactoe;

import java.util.Stack;

public class MoveHistory {
    private final Stack<Move> history = new Stack<>();

    public void addMove(int row, int col, TileValue player) {
        history.push(new Move(row, col, player));
    }

    public Move undoLastMove() {
        return history.isEmpty() ? null : history.pop();
    }

    public static class Move {
        public final int row;
        public final int col;
        public final TileValue player;

        public Move(int row, int col, TileValue player) {
            this.row = row;
            this.col = col;
            this.player = player;
        }
    }
}