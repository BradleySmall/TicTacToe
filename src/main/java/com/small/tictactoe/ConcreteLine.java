package com.small.tictactoe;

public class ConcreteLine implements Line {
    private final BoardReader board;
    private final LineType lineType;
    private final int index; // Row number, column number, or diagonal type (0 for main, 1 for anti)

    public enum LineType {
        ROW, COLUMN, MAIN_DIAGONAL, ANTI_DIAGONAL
    }

    public ConcreteLine(BoardReader board, LineType lineType, int index) {
        this.board = board;
        this.lineType = lineType;
        this.index = index;
    }

    @Override
    public TileValue[] getTiles() {
        TileValue[] tiles = new TileValue[BoardConfig.BOARD_SIZE];
        for (int i = 0; i < BoardConfig.BOARD_SIZE; i++) {
            int[] coords = toBoardCoordinates(i);
            tiles[i] = board.getTileValue(coords[0], coords[1]);
        }
        return tiles;
    }

    @Override
    public int[] toBoardCoordinates(int index) {
        return switch (lineType) {
            case ROW -> new int[]{this.index, index};
            case COLUMN -> new int[]{index, this.index};
            case MAIN_DIAGONAL -> new int[]{index, index};
            case ANTI_DIAGONAL -> new int[]{index, BoardConfig.BOARD_SIZE - 1 - index};
        };
    }
}
