package com.grnn.chess.multiPlayer;

import com.grnn.chess.Actors.IActor;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.Move;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MultiPlayer implements IActor {

    int gameId;
    Player thisIsThePlayerAtThisComputer;
    boolean thisIsTheColorOfThePlayerAtThisComputer;
    Connection conn;

    /**
     * Constructur of multiplayer, Should either connect to a Peer of create a new peer
     */
    public MultiPlayer(Connection conn){{
        this.conn = conn;
    }

    }

    /**
     * Return a list of gameId, Player1Id and Player2Id from db
     * @return
     */
    public ArrayList<ArrayList<String>> getGames() {
        ArrayList<ArrayList<String>> availableGames = new ArrayList<ArrayList<String>>();
        try{
            String query = "SELECT * FROM GameManager WHERE Player2ID IS NULL;";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                ArrayList<String> game = new ArrayList<String>();
                Integer gameId = res.getInt("GameID");
                String player1 = res.getString("Player1ID");
                game.add(Integer.toString(gameId));
                game.add(player1);
                availableGames.add(game);
            }
            return availableGames;
        }catch(SQLException e){
            return null;
        }
    }


    /**
     * Create a new Game in the database, adds player1
     * @return true if the connection was successful, otherwise false.
     */
    public boolean createGame(Player player1){
        try {
            String query = "INSERT INTO GameManager (Player1ID, GameActive, Turn) VALUES ('"+player1.name+"', 'true', 'true');";
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
            thisIsThePlayerAtThisComputer = player1.name;
            thisIsTheColorOfThePlayerAtThisComputer = true;
        }catch(SQLException e){
            return false;
        }
        return true;
    }


    /**
     * Connect to game
     * @param player2 the player that wants to join
     * @param gameId the id of the game the player wants to join, this will be the gameId of this Multiplayer Object
     */
    public boolean joinGame(Player player2, String gameId ){
        try {
            String query = "UPDATE GameManager SET Player2ID='"+player2.name+"' WHERE GameId='"+gameId+"';";
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
            thisIsThePlayerAtThisComputer = player2;
            thisIsTheColorOfThePlayerAtThisComputer = false;
        }catch(SQLException e){
            return false;
        }
        this.gameId = Integer.parseInt(gameId);
        return true;
    }

    /**
     * Make a move in the game with this.gameid
     * @param move
     * @return true if succesfull, otherwise false
     */
    public boolean makeMove(Move move){
        String from = move.getFromPosInDatabaseFormat(); //Maybe create a better way,
        String to = move.getToPosInDatabaseFormat();
        try {
            String query = "UPDATE GameManager SET FromMove='"+from+"', ToMove='"+to+"' WHERE GameId='"+gameId+"';";
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
        }catch(SQLException e){
            return false;
        }
        return true;
    }

    /**
     * Waiting for player 2 to join your game
     * @return name of player2 when player 2 joins
     */
    public String player2Connected(){
        try {
            String query = "SELECT * FROM GameManager WHERE GameId='"+gameId+"' AND Player2ID IS NOT NULL;";
            Statement stmt = conn.createStatement();
            while(true) {
                ResultSet res = stmt.executeQuery(query);
                if(res.next()){
                    return res.getString("Player2ID");
                }
            }
        }catch(SQLException e){
            return null;
        }
    }

    /**
     * Waiting for opponents move
     * @return the opponents move
     */
    public Move waitForMove(){
        String from = "";
        String to = "";
        try {
            String query = "SELECT FromMove, ToMove FROM GameManager WHERE GameId='"+gameId+"' AND GameActive = 'true';";
            Statement stmt = conn.createStatement();
            while(true) {
                ResultSet res = stmt.executeQuery(query);
                if(res.next()){
                     from = res.getString("FromMove");
                     to = res.getString("ToMove");
                     return new Move(from, to);
                }
            }
        }catch(SQLException e){
            return null;
        }
    }

    /**
     * Set GameActive to False,
     */
    public void endGame(){
        try {
            String query = "UPDATE GameManager SET GameActive='false' WHERE GameId='"+gameId+"';";
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
        }catch(SQLException e){

        }
    }

    /**
     * Get the name of the opponent of the game
     */
    public String getOpponent(String player){
        String opponent = thisIsTheColorOfThePlayerAtThisComputer ? "Player2ID" : "Player1ID";
        try{
            String query = "SELECT "+opponent+" FROM GameManager WHERE GameId='"+gameId+"';";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            return res.getString(opponent);
        }catch(SQLException e){
            return null;
        }
    }

    /**
     * Returns true if the color of the player at this computer is white, otherwise false
     * @return
     */
    @Override
    public boolean isWhite() {
        return thisIsTheColorOfThePlayerAtThisComputer;
    }
}
