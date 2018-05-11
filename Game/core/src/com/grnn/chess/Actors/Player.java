package com.grnn.chess.Actors;

import com.grnn.chess.Game;
import com.grnn.chess.Result;

import java.util.ArrayList;

public class Player implements IActor{

    public String level;
    private boolean isWhite;
    public String name;

    public int getNoOfWins() {
        return noOfWins;
    }

    public int getNoOfLose() {
        return noOfLose;
    }

    public int getNoOfDraws() {
        return noOfDraws;
    }

    private String password;
    public int rating;
    private int noOfWins, noOfLose, noOfDraws;
    public ArrayList<Game> gamesPlayed;

    public Player(String name, String password, boolean isWhite){
        this.name = name;
        this.password = password;
        gamesPlayed = new ArrayList<Game>();
        noOfWins = 0;
        noOfLose = 0;
        noOfDraws = 0;
        rating = 1200;
        this.isWhite = isWhite;
    }

    public Player(String name, String password, int wins, int losses, int draws, int rating){
        this.name = name;
        this.password = password;
        gamesPlayed = new ArrayList<Game>();
        noOfWins = wins;
        noOfLose = losses;
        noOfDraws = draws;
        this.rating = rating;
    }

    /**
     * Method to registrer the result of a game, updatePlayers must be called after this.
     */
    public void registrerResult(Result res){
        if (res == Result.WIN){
            noOfWins++;
        }else if(res == Result.DRAW){
            noOfDraws++;
        }else if(res == Result.LOSS){
            noOfLose++;
        }
    }
    public String getLevel(){
        return level;
    }

    public int getRating() { return rating;}

    public String getName(){
        return name;
    }

    public String getPassword() { return password; }

    public boolean checkPassword(String password) { return this.password.equals(password); }

    public void setRating(int newRating){
        rating = newRating;
    }


    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }
}