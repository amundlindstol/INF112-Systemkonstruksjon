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

    int GameID;
    Connection conn;
    /**
     * Constructur of multiplayer, Should either connect to a Peer of create a new peer
     */

    public MultiPlayer(boolean peerOne){{
        if(peerOne){
            //createPeer();
        }else{
            //createPeer();
        }
    }

    }

    /**
     * Return a list of gameId, Player1Id and Player2Id from db
     * @return
     */
    public ArrayList<ArrayList<String>> getGames() {
        ArrayList<String> availableGames = new ArrayList<String>();
        try{
            String query = "SELECT * FROM Games WHERE Available='"+true+"';";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                availableGames.add(res.getString("GameId"));
            }
            return null;//return availableGames;
        }catch(SQLException e){
            return null;
        }
    }


    /**
     * Create a new Game in the database, adds player1
     * @return true if the connection was successful, otherwise false.
     */
    public boolean createGame(Player player1){
        //TODO create peer
        try {
            //String query = "INSERT INTO Games (IPAdress, Avalible) VALUES ('"+getIP()+"', '"+"');";
            Statement stmt = conn.createStatement();
            //int res = stmt.executeUpdate(query);
        }catch(SQLException e){
            return false;
        }
        return true;
    }


    /**
     * Connect to peer
     */
    public boolean joinGame(Player player2, String gameId ){
        //String Ip = getPeerIP(nr);
        return false;
    }

    /**
     * Make a move in the game with this.gameid
     * @param move
     * @return true if succesfull, otherwise false
     */
    public boolean makeMove(Move move){
        return false;
    }

    /**
     * Waiting for player 2 to join your game
     * @return true when player 2 joins, never false
     */
    public boolean player2Connected(){
        while(true){

        }
    }

    /**
     * Waiting for opponents move
     * @return the opponents move
     */
    public Move waitForMove(){
        while (true) {
            //check if the move changed
        }
    }

    /**
     * Set GameActive to False,
     */
    public void endGame(){

    }

}
