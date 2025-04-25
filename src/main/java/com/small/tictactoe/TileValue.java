package com.small.tictactoe;

/**
 * Enumeration to represent tile values in a Tic-Tac-Toe game.
 */
public enum TileValue {
    NOUGHT('o'), // this is the circle traditionally referred to as NOUGHT or O
    CROSS('x'),  // this is the cross traditionally referred to as CROSS or X
    EMPTY(' ');  // This is a yet unplayed position

    private final char displayCharacter;

    TileValue(char displayCharacter) {
        this.displayCharacter = displayCharacter;
    }

    char getDisplayCharacter() {
        return this.displayCharacter;
    }
}
