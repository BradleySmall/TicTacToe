/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe

import spock.lang.Specification

class TicTacToeGame_Specification extends Specification {

    TicTacToeGame game

    void setup() {
        game = new TicTacToeGame()
    }
    def "should show blank string game when no play has happened"() {
        expect:
        "" == game.getScore()
    }

    def "should show blank string when new game"() {
        game.makeMove(0, 0)
        game.makeMove(0, 1)
        game.makeMove(0, 2)
        game.newGame()
        expect:
        game.getScore() == ""
    }

    def "should show player x wins "() {
        game.makeMove(0, 0)
        game.makeMove(2, 0)
        game.makeMove(0, 1)
        game.makeMove(2, 1)
        game.makeMove(0, 2)
        expect:
        game.getScore() == "Player CROSS wins."
    }

    def "should show that play begins with x and swaps to o each call"() {
        expect:
        TileValue.CROSS == game.getNextPiece()
        TileValue.NOUGHT == game.getNextPiece()
        TileValue.CROSS == game.getNextPiece()
        TileValue.NOUGHT == game.getNextPiece()
        TileValue.CROSS == game.getNextPiece()
        TileValue.NOUGHT == game.getNextPiece()
    }

    def "should show player CROSS wins when importing a winning board"() {
        TileValue [][] board
        board =[[TileValue.CROSS,TileValue.CROSS, TileValue.CROSS],
                [TileValue.EMPTY,TileValue.EMPTY, TileValue.EMPTY],
                [TileValue.EMPTY,TileValue.EMPTY, TileValue.EMPTY]]
        game.setBoard(board)
        expect:
        game.getScore() == "Player CROSS wins."
    }

    def "should show tie game when no squares available and no winner"() {
        TileValue [][] board
        board =[[TileValue.CROSS,TileValue.NOUGHT, TileValue.CROSS],
                [TileValue.NOUGHT,TileValue.CROSS, TileValue.NOUGHT],
                [TileValue.NOUGHT,TileValue.CROSS, TileValue.NOUGHT]]
        game.setBoard(board)
        expect:
        game.getScore() == "Tie Game"
    }
    def "should throw exception for invalid row or column"() {
        when:
        game.makeMove(-1, 0)
        then:
        thrown(IllegalArgumentException)
        when:
        game.makeMove(0, 3)
        then:
        thrown(IllegalArgumentException)
    }
    def "should throw exception for invalid board size"() {
        when:
        game.setBoard(new TileValue[2][3])
        then:
        thrown(IllegalArgumentException)
    }
    def "should not allow playing on filled square"() {
        given:
        game.makeMove(0, 0)
        expect:
        game.makeMove(0, 0) == Optional.empty()
    }
    def "should alternate players correctly"() {
        expect:
        game.getCurrentPlayer() == TileValue.CROSS
        game.makeMove(0, 0)
        game.getCurrentPlayer() == TileValue.NOUGHT
        game.makeMove(0, 1)
        game.getCurrentPlayer() == TileValue.CROSS
    }

    def "should detect game over for win"() {
        given:
        game.makeMove(0, 0) // X
        game.makeMove(2, 0) // O
        game.makeMove(0, 1) // X
        game.makeMove(2, 1) // O
        game.makeMove(0, 2) // X
        expect:
        game.isGameOver()
        game.getScore() == "Player CROSS wins."
    }

    def "should detect game over for tie"() {
        given:
        TileValue[][] board = [
                [TileValue.CROSS, TileValue.NOUGHT, TileValue.CROSS],
                [TileValue.NOUGHT, TileValue.CROSS, TileValue.NOUGHT],
                [TileValue.NOUGHT, TileValue.CROSS, TileValue.NOUGHT]
        ]
        game.setBoard(board)
        expect:
        game.isGameOver()
        game.getScore() == "Tie Game"
    }
    def "should detect a row win with correct direction and position"() {
        given: "a game with three crosses in the first row"
        game.makeMove(0, 0) // Cross
        game.makeMove(1, 0) // Nought
        game.makeMove(0, 1) // Cross
        game.makeMove(1, 1) // Nought
        game.makeMove(0, 2) // Cross

        expect: "the game is over with a row win for Cross"
        game.isGameOver()
        game.getScore() == "Player CROSS wins."
        game.getWinDirection() == WinDirection.ROW
        game.getWinRow() == 0
        game.getWinColumn() == 0
    }

    def "should detect a column win with correct direction and position"() {
        given: "a game with three noughts in the first column"
        game.makeMove(1, 1) // Cross
        game.makeMove(0, 0) // Nought
        game.makeMove(1, 2) // Cross
        game.makeMove(1, 0) // Nought
        game.makeMove(2, 2) // Cross
        game.makeMove(2, 0) // Nought

        expect: "the game is over with a column win for Nought"
        game.isGameOver()
        game.getScore() == "Player NOUGHT wins."
        game.getWinDirection() == WinDirection.COLUMN
        game.getWinRow() == 0
        game.getWinColumn() == 0
    }

    def "should detect a main diagonal win with correct direction and position"() {
        given: "a game with three crosses from top-left to bottom-right"
        game.makeMove(0, 0) // Cross
        game.makeMove(0, 1) // Nought
        game.makeMove(1, 1) // Cross
        game.makeMove(0, 2) // Nought
        game.makeMove(2, 2) // Cross

        expect: "the game is over with a diagonal win for Cross"
        game.isGameOver()
        game.getScore() == "Player CROSS wins."
        game.getWinDirection() == WinDirection.DIAGONAL
        game.getWinRow() == 0
        game.getWinColumn() == 0
    }

    def "should detect a reverse diagonal win with correct direction and position"() {
        given: "a game with three crosses from top-right to bottom-left"
        game.makeMove(0, 2) // Cross
        game.makeMove(0, 1) // Nought
        game.makeMove(1, 1) // Cross
        game.makeMove(0, 0) // Nought
        game.makeMove(2, 0) // Cross

        expect: "the game is over with a diagonal win for Cross"
        game.isGameOver()
        game.getScore() == "Player CROSS wins."
        game.getWinDirection() == WinDirection.DIAGONAL
        game.getWinRow() == 0
        game.getWinColumn() == 2
    }

    def "should report no win direction and invalid position for tie game"() {
        given: "a game with all tiles filled and no winner"
        TileValue[][] board = [
                [TileValue.CROSS, TileValue.NOUGHT, TileValue.CROSS],
                [TileValue.NOUGHT, TileValue.CROSS, TileValue.NOUGHT],
                [TileValue.NOUGHT, TileValue.CROSS, TileValue.NOUGHT]
        ]
        game.setBoard(board)

        expect: "the game is over with a tie and no win direction"
        game.isGameOver()
        game.getScore() == "Tie Game"
        game.getWinDirection() == WinDirection.NONE
        game.getWinRow() == -1
        game.getWinColumn() == -1
    }
}
