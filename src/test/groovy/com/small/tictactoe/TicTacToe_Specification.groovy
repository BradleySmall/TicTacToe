package com.small.tictactoe

import spock.lang.Specification

class TicTacToe_Specification extends Specification {
    def "should show empty "() {
        expect:
        "Tie Game" == new TicTacToe().getScore()
    }

    def "should show tie "() {
        TicTacToe game = new TicTacToe();
        int[][] plays = [[0, 1, 0],
                         [1, 0, 1],
                         [1, 0, 1]];
        game.play(plays);
        expect:
        "Tie Game" == game.getScore()
    }
    def "should show player O win "() {
        TicTacToe game = new TicTacToe();
        int[][] plays = [[0, 0, 0],
                         [1, 1, 0],
                         [1, 0, 1]];
        game.play(plays);
        expect:
        "Player O wins." == game.getScore()
    }
    def "should show player X win "() {
        TicTacToe game = new TicTacToe();
        int[][] plays = [[0, 1, 0],
                         [1, 1, 1],
                         [0, 0, 1]];
        game.play(plays);
        expect:
        "Player X wins." == game.getScore()
    }
    def "should show player X win vertically"() {
        TicTacToe game = new TicTacToe();
        int[][] plays = [[1, 1, 0],
                         [1, 0, 1],
                         [1, 0, 0]];
        game.play(plays);
        expect:
        "Player X wins." == game.getScore()
    }
    def "should show player O  win vertically"() {
        TicTacToe game = new TicTacToe();
        int[][] plays = [[1, 0, 0],
                         [0, 0, 1],
                         [1, 0, 1]];
        game.play(plays);
        expect:
        "Player O wins." == game.getScore()
    }
    def "should show player O win criss-cross"() {
        TicTacToe game = new TicTacToe();
        int[][] plays = [[0, 0, 1],
                         [1, 0, 1],
                         [1, 0, 0]];
        game.play(plays);
        expect:
        "Player O wins." == game.getScore()
    }
    def "should show player X win criss-cross"() {
        TicTacToe game = new TicTacToe();
        int[][] plays = [[0, 0, 1],
                         [0, 1, 0],
                         [1, 0, 1]];
        game.play(plays);
        expect:
        "Player X wins." == game.getScore()
    }
}