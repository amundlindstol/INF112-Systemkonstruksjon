package com.grnn.chess;

import com.grnn.chess.Actors.Player;

public class EloRatingSystem {
    private Player player;
    private int rating;

    public EloRatingSystem(Player player) {
        this.player = player;
        this.rating = player.getRating();
    }

    private double getExpectedScore(int opponentRating){
        return 1.0 / (1.0 + Math.pow(10.0, ((double) (opponentRating - rating) / 400.0)));
    }

    public int getNewRating(Result result, int opponentRating){
        switch (result){
            case WIN:
                return getNewRating(1.0, opponentRating);
            case DRAW:
                return getNewRating(0.5, opponentRating);
            case LOSS:
                return getNewRating(0.0, opponentRating);
        }
        return -1;
    }

    private int getNewRating(double result, int opponentRating){
        int kFactor = getK();
        double expectedResult = getExpectedScore(opponentRating);
        return calculateNewRating(result, expectedResult, kFactor);

    }

    private int calculateNewRating(double result, double expectedResult, int kFactor){
        return rating + (int)(kFactor * (result - expectedResult));
    }

    private int getK(){
        int k;
        if(player.gamesPlayed.size() < 30){
            k = 40;
        }else if(rating < 2400){
            k = 20;
        }else {
            k = 10;
        }
        return k;
    }
}
