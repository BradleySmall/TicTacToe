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
        game.getScore() == "Player x wins."
    }

    def "should show that play begins with x and swaps to o each call"() {
        expect:
        (char)'x' == game.getNextPiece()
        (char)'o' == game.getNextPiece()
        (char)'x' == game.getNextPiece()
        (char)'o' == game.getNextPiece()
        (char)'x' == game.getNextPiece()
        (char)'o' == game.getNextPiece()
    }

    def "should show player x wins when importing a winning board"() {
        Character [][] board
        board =[['x' as char,'x' as char, 'x' as char],
                [' ' as char,' ' as char, ' ' as char],
                [' ' as char,' ' as char, ' ' as char]]
        game.setBoard(board)
        expect:
        "Player x wins." == game.getScore()
    }

    def "should show tie game when no squares available and no winner"() {
        Character [][] board
        board =[['x' as char,'o' as char, 'x' as char],
                ['o' as char,'x' as char, 'o' as char],
                ['o' as char,'x' as char, 'o' as char]]
        game.setBoard(board)
        expect:
        "Tie Game" == game.getScore()
    }
}
