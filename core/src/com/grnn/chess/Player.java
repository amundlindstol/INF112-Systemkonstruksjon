package com.grnn.chess;

import com.grnn.chess.Game;

import java.util.ArrayList;

public class Player {

    public String name;
    public String level;
    public int rating;
    private int noOfWins, noOfLose, noOfDraws;
    public ArrayList<Game> gamesPlayed;

    public Player(String name){
        this.name = name;
        gamesPlayed = new ArrayList<Game>();
        noOfWins = 0;
        noOfLose = 0;
        noOfDraws = 0;

    }

    public String getLevel(){
        return level;
    }

    public int getRating() { return rating;}

    public String getName(){
        return name;
    }

    public void updateStatictics(Player otherPlayer, int score){
        int k;
        if(gamesPlayed.size() < 30){
            k = 40;
        }else if(rating < 2400){
            k = 20;
        }else {
            k = 40;
        }
        rating = rating+k;
    }

}