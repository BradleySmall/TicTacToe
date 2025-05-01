package com.small.tictactoe;

import java.util.Arrays;
import java.util.List;

/**
 * Checks Tic-Tac-Toe board lines for strategic move patterns.
 */
public class LineChecker {
    private final BoardReader board;
    private final List<Line> lines;

    public LineChecker(BoardReader board) {
        this.board = board;
        this.lines = initializeLines();
    }

    /**
     * Finds a strategic move for the given tile value (win or block).
     * @param tileValue NOUGHT for win, CROSS for block
     * @return [row, col] of the empty tile, or null if none
     */
    public int[] findPatternMove(TileValue tileValue) {
        for (Line line : lines) {
            int[] move = checkLinePattern(line.getTiles(), tileValue);
            if (move != null) {
                return line.toBoardCoordinates(move[0]);
            }
        }
        return new int[0];
    }

    private List<Line> initializeLines() {
        return Arrays.asList(
                // Rows
                new ConcreteLine(board, ConcreteLine.LineType.ROW, 0),
                new ConcreteLine(board, ConcreteLine.LineType.ROW, 1),
                new ConcreteLine(board, ConcreteLine.LineType.ROW, 2),
                // Columns
                new ConcreteLine(board, ConcreteLine.LineType.COLUMN, 0),
                new ConcreteLine(board, ConcreteLine.LineType.COLUMN, 1),
                new ConcreteLine(board, ConcreteLine.LineType.COLUMN, 2),
                // Diagonals
                new ConcreteLine(board, ConcreteLine.LineType.MAIN_DIAGONAL, 0),
                new ConcreteLine(board, ConcreteLine.LineType.ANTI_DIAGONAL, 1)
        );
    }

    private int[] checkLinePattern(TileValue[] line, TileValue tileValue) {
        int tileCount = 0;
        int emptyIndex = -1;
        for (int i = 0; i < line.length; i++) {
            if (line[i] == tileValue) {
                tileCount++;
            } else if (line[i] == TileValue.EMPTY) {
                emptyIndex = i;
            }
        }
        return (tileCount == 2 && emptyIndex != -1) ? new int[]{emptyIndex} : null;
    }

    public List<Line> getLines() {
        return lines;
    }
}