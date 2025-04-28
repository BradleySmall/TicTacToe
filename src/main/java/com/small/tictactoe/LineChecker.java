package com.small.tictactoe;

import java.util.Arrays;
import java.util.List;

/**
 * Checks Tic-Tac-Toe board lines for strategic move patterns.
 */
public class LineChecker {
    private final TicTacToeGame game;
    private final List<Line> lines;

    public LineChecker(TicTacToeGame game) {
        this.game = game;
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
                new Line() {
                    @Override
                    public TileValue[] getTiles() {
                        return new TileValue[]{
                                game.getTileValue(0, 0), game.getTileValue(0, 1), game.getTileValue(0, 2)
                        };
                    }
                    @Override
                    public int[] toBoardCoordinates(int index) {
                        return new int[]{0, index};
                    }
                },
                new Line() {
                    @Override
                    public TileValue[] getTiles() {
                        return new TileValue[]{
                                game.getTileValue(1, 0), game.getTileValue(1, 1), game.getTileValue(1, 2)
                        };
                    }
                    @Override
                    public int[] toBoardCoordinates(int index) {
                        return new int[]{1, index};
                    }
                },
                new Line() {
                    @Override
                    public TileValue[] getTiles() {
                        return new TileValue[]{
                                game.getTileValue(2, 0), game.getTileValue(2, 1), game.getTileValue(2, 2)
                        };
                    }
                    @Override
                    public int[] toBoardCoordinates(int index) {
                        return new int[]{2, index};
                    }
                },
                // Columns
                new Line() {
                    @Override
                    public TileValue[] getTiles() {
                        return new TileValue[]{
                                game.getTileValue(0, 0), game.getTileValue(1, 0), game.getTileValue(2, 0)
                        };
                    }
                    @Override
                    public int[] toBoardCoordinates(int index) {
                        return new int[]{index, 0};
                    }
                },
                new Line() {
                    @Override
                    public TileValue[] getTiles() {
                        return new TileValue[]{
                                game.getTileValue(0, 1), game.getTileValue(1, 1), game.getTileValue(2, 1)
                        };
                    }
                    @Override
                    public int[] toBoardCoordinates(int index) {
                        return new int[]{index, 1};
                    }
                },
                new Line() {
                    @Override
                    public TileValue[] getTiles() {
                        return new TileValue[]{
                                game.getTileValue(0, 2), game.getTileValue(1, 2), game.getTileValue(2, 2)
                        };
                    }
                    @Override
                    public int[] toBoardCoordinates(int index) {
                        return new int[]{index, 2};
                    }
                },
                // Main diagonal (0,0 -> 2,2)
                new Line() {
                    @Override
                    public TileValue[] getTiles() {
                        return new TileValue[]{
                                game.getTileValue(0, 0), game.getTileValue(1, 1), game.getTileValue(2, 2)
                        };
                    }
                    @Override
                    public int[] toBoardCoordinates(int index) {
                        return new int[]{index, index};
                    }
                },
                // Anti-diagonal (0,2 -> 2,0)
                new Line() {
                    @Override
                    public TileValue[] getTiles() {
                        return new TileValue[]{
                                game.getTileValue(0, 2), game.getTileValue(1, 1), game.getTileValue(2, 0)
                        };
                    }
                    @Override
                    public int[] toBoardCoordinates(int index) {
                        return new int[]{index, 2 - index};
                    }
                }
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