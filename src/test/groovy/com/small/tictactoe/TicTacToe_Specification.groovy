package com.small.tictactoe

import spock.lang.Specification

class TicTacToe_Specification extends Specification {
    def game = new TicTacToe()
    void setup() {
        game = new TicTacToe()
    }

    def "should show tie "() {
        expect:
        "Tie Game" == game.getScore()
    }

    def "should show player O win horizontally"() {
        game.placePiece('O' as char, 0, 0)
        game.placePiece('O' as char, 0, 1)
        game.placePiece('O' as char, 0, 2)
        expect:
        "Player O wins." == game.getScore()
    }

    def "should show player X win vertically"() {
        game.placePiece('X' as char, 0, 0)
        game.placePiece('X' as char, 1, 0)
        game.placePiece('X' as char, 2, 0)
        expect:
        "Player X wins." == game.getScore()
    }

    def "should show player O win criss-cross"() {
        game.placePiece('O' as char, 0, 0)
        game.placePiece('O' as char, 1, 1)
        game.placePiece('O' as char, 2, 2)
        expect:
        "Player O wins." == game.getScore()
    }

    def "should play the game"() {
        game.newGame()
        game.showBoard()
        game.placePiece('X' as char, 0,0)
        game.showBoard()
        game.placePiece('O' as char, 1,1)
        game.showBoard()
        game.placePiece('X' as char, 0,2)
        game.placePiece('O' as char, 0,1)
        game.placePiece('X' as char, 2,1)
        game.placePiece('O' as char, 2,2)
        game.showBoard()
        println(game.getScore())
        expect:
        true
    }
}