package com.small.tictactoe

import spock.lang.Specification

class TicTacToeGame_Specification extends Specification {

    TicTacToeGame game

    void setup() {
        game = new TicTacToeGame()
    }

    def "should show empty string when game is ongoing"() {
        expect:
        game.getResult() == GameResult.ONGOING
        game.getResult().getDisplayText() == ""
    }

    def "should reset to ongoing state when new game is started"() {
        given:
        game.placeTile(0, 0)
        game.placeTile(0, 1)
        game.placeTile(0, 2)
        game.newGame()
        expect:
        game.getResult() == GameResult.ONGOING
        game.getResult().getDisplayText() == ""
    }

    def "should show player X wins for row win"() {
        given:
        game.placeTile(0, 0) // X
        game.placeTile(2, 0) // O
        game.placeTile(0, 1) // X
        game.placeTile(2, 1) // O
        game.placeTile(0, 2) // X
        expect:
        game.getResult() == GameResult.WIN_CROSS
        game.getResult().getDisplayText() == "Player X Wins!"
    }

    def "should alternate players correctly"() {
        expect:
        game.getCurrentPlayer() == TileValue.CROSS
        game.placeTile(0, 0)
        game.getCurrentPlayer() == TileValue.NOUGHT
        game.placeTile(0, 1)
        game.getCurrentPlayer() == TileValue.CROSS
        game.placeTile(0, 2)
        game.getCurrentPlayer() == TileValue.NOUGHT
    }

    def "should show player X wins when importing a winning board"() {
        given:
        TileValue[][] board = [
                [TileValue.CROSS, TileValue.CROSS, TileValue.CROSS],
                [TileValue.EMPTY, TileValue.EMPTY, TileValue.EMPTY],
                [TileValue.EMPTY, TileValue.EMPTY, TileValue.EMPTY]
        ]
        game.setBoard(board)
        expect:
        game.getResult() == GameResult.WIN_CROSS
        game.getResult().getDisplayText() == "Player X Wins!"
    }

    def "should show tie game when no squares available and no winner"() {
        given:
        TileValue[][] board = [
                [TileValue.CROSS, TileValue.NOUGHT, TileValue.CROSS],
                [TileValue.NOUGHT, TileValue.CROSS, TileValue.NOUGHT],
                [TileValue.NOUGHT, TileValue.CROSS, TileValue.NOUGHT]
        ]
        game.setBoard(board)
        expect:
        game.getResult() == GameResult.TIE
        game.getResult().getDisplayText() == "Tie!"
    }

    def "should throw exception for invalid row or column"() {
        when:
        game.placeTile(-1, 0)
        then:
        thrown(IllegalArgumentException)
        when:
        game.placeTile(0, 3)
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
        game.placeTile(0, 0)
        expect:
        game.placeTile(0, 0) == Optional.empty()
    }

    def "should alternate players correctly after valid moves"() {
        expect:
        game.getCurrentPlayer() == TileValue.CROSS
        game.placeTile(0, 0)
        game.getCurrentPlayer() == TileValue.NOUGHT
        game.placeTile(0, 1)
        game.getCurrentPlayer() == TileValue.CROSS
    }

    def "should detect game over for win"() {
        given:
        game.placeTile(0, 0) // X
        game.placeTile(2, 0) // O
        game.placeTile(0, 1) // X
        game.placeTile(2, 1) // O
        game.placeTile(0, 2) // X
        expect:
        game.isGameOver()
        game.getResult() == GameResult.WIN_CROSS
        game.getResult().getDisplayText() == "Player X Wins!"
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
        game.getResult() == GameResult.TIE
        game.getResult().getDisplayText() == "Tie!"
    }

    def "should detect a row win with correct direction and position"() {
        given: "a game with three crosses in the first row"
        game.placeTile(0, 0) // Cross
        game.placeTile(1, 0) // Nought
        game.placeTile(0, 1) // Cross
        game.placeTile(1, 1) // Nought
        game.placeTile(0, 2) // Cross
        expect: "the game is over with a row win for Cross"
        game.isGameOver()
        game.getResult() == GameResult.WIN_CROSS
        game.getWinDirection() == WinDirection.ROW
        game.getWinRow() == 0
        game.getWinColumn() == 0
    }

    def "should detect a column win with correct direction and position"() {
        given: "a game with three noughts in the first column"
        game.placeTile(1, 1) // Cross
        game.placeTile(0, 0) // Nought
        game.placeTile(1, 2) // Cross
        game.placeTile(1, 0) // Nought
        game.placeTile(2, 2) // Cross
        game.placeTile(2, 0) // Nought
        expect: "the game is over with a column win for Nought"
        game.isGameOver()
        game.getResult() == GameResult.WIN_NOUGHT
        game.getWinDirection() == WinDirection.COLUMN
        game.getWinRow() == 0
        game.getWinColumn() == 0
    }

    def "should detect a main diagonal win with correct direction and position"() {
        given: "a game with three crosses from top-left to bottom-right"
        game.placeTile(0, 0) // Cross
        game.placeTile(0, 1) // Nought
        game.placeTile(1, 1) // Cross
        game.placeTile(0, 2) // Nought
        game.placeTile(2, 2) // Cross
        expect: "the game is over with a diagonal win for Cross"
        game.isGameOver()
        game.getResult() == GameResult.WIN_CROSS
        game.getWinDirection() == WinDirection.DIAGONAL
        game.getWinRow() == 0
        game.getWinColumn() == 0
    }

    def "should detect a reverse diagonal win with correct direction and position"() {
        given: "a game with three crosses from top-right to bottom-left"
        game.placeTile(0, 2) // Cross
        game.placeTile(0, 1) // Nought
        game.placeTile(1, 1) // Cross
        game.placeTile(0, 0) // Nought
        game.placeTile(2, 0) // Cross
        expect: "the game is over with a diagonal win for Cross"
        game.isGameOver()
        game.getResult() == GameResult.WIN_CROSS
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
        game.getResult() == GameResult.TIE
        game.getWinDirection() == WinDirection.NONE
        game.getWinRow() == -1
        game.getWinColumn() == -1
    }
}