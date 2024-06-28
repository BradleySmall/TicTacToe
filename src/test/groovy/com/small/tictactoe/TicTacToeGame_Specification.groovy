/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe

import spock.lang.Specification

class TicTacToeGame_Specification extends Specification {

    TicTacToeGame game = new TicTacToeGame()

    void setup() {
        game = new TicTacToeGame()
    }

    def "should show blank string game when no play has happened"() {
        expect:
        "" == game.getScore()
    }

    def "should show blank string when new game"() {
        game.playSquare(0, 0)
        game.playSquare(0, 1)
        game.playSquare(0, 2)
        game.newGame()
        expect:
        game.getScore() == ""
    }

    def "should show player x wins "() {
        game.playSquare(0, 0)
        game.playSquare(2, 0)
        game.playSquare(0, 1)
        game.playSquare(2, 1)
        game.playSquare(0, 2)
        expect:
        game.getScore() == "Player CROSS wins."
    }

    def "should show that play begins with x and swaps to o each call"() {
        expect:
        TileValue.CROSS == game.getNextPiece()
        TileValue.NAUGHT == game.getNextPiece()
        TileValue.CROSS == game.getNextPiece()
        TileValue.NAUGHT == game.getNextPiece()
        TileValue.CROSS == game.getNextPiece()
        TileValue.NAUGHT == game.getNextPiece()
    }

    def "should show player x wins when importing a winning board"() {
        TileValue[][] board
        board = [[TileValue.CROSS, TileValue.CROSS, TileValue.CROSS],
                 [TileValue.BLANK, TileValue.BLANK, TileValue.BLANK],
                 [TileValue.BLANK, TileValue.BLANK, TileValue.BLANK]]
        game.setBoard(board)
        expect:
        "Player CROSS wins." == game.getScore()
    }

    def "should show tie game when no squares available and no winner"() {
        TileValue[][] board
        board = [[TileValue.CROSS, TileValue.NAUGHT, TileValue.CROSS],
                 [TileValue.NAUGHT, TileValue.CROSS, TileValue.NAUGHT],
                 [TileValue.NAUGHT, TileValue.CROSS, TileValue.NAUGHT]]
        game.setBoard(board)
        expect:
        "Tie Game" == game.getScore()
    }
}
