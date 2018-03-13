package com.grnn.chess;

public class Game {

    //private Board board;
    private Player player1;
    private Player player2;
    private int gameId;

    static int currid = 1;

    public Game(){
        player1 = new Player("Spiller1");
        player2 = new Player("Spiller2");
        gameId = currid++;


    }

    private void startGame() {
        return;
    }

    private void endGame(int res) {
        return;
    }

    private Player announceWinner() {
        return null;
    }


}
