package com.grnn.chess.multiPlayer;

import com.badlogic.gdx.Gdx;
import com.grnn.chess.Actors.IActor;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.Move;
import com.grnn.chess.states.WaitForPlayerState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MultiPlayer implements IActor, Runnable {

    int gameId;
    Player thisIsThePlayerAtThisComputer;
    boolean thisIsTheColorOfThePlayerAtThisComputer;
    Connection conn;
    Move lastMove;
    private ConnectionListener listener;
    private String p2Name;

    /**
     * Constructur of multiplayer, Should either connect to a Peer of create a new peer
     */
    public MultiPlayer(Connection conn) {{
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
            String query = "SELECT * FROM GameManager WHERE Player2ID IS NULL AND GameActive= 1;";
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
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the gameId of the game the player has created or joined
     * @return the gameid
     */
    public int getGameID(Player player1){
        try{
            String query = "SELECT GameID FROM GameManager WHERE Player1ID='"+player1.name+"';";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                gameId = res.getInt("GameID");
            }
            return gameId;
        }catch(SQLException e){
        }
        return -1;
    }

    /**
     * Create a new Game in the database, adds player1
     * @return true if the connection was successful, otherwise false.
     */
    public boolean createGame(Player player1){
        try {
            String query = "INSERT INTO GameManager (Player1ID, GameActive, Turn) VALUES ('"+player1.name+"', 1, 1);";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            gameId = getGameID(player1);
            thisIsThePlayerAtThisComputer = player1;
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
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Waiting for player 2 to join your game
     * @return name of player2 when player 2 joins
     */
    public void run(){
        try {
            String query = "SELECT * FROM GameManager WHERE GameId='"+gameId+"' AND Player2ID IS NOT NULL;";
            Statement stmt = conn.createStatement();
            while(true) {
                ResultSet res = stmt.executeQuery(query);
                if(res.next()){
                   // return res.getString("Player2ID");
                    p2Name = res.getString("Player2ID");
                    break;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                   // e.printStackTrace();
                    return;
                }
            }
        }catch(SQLException e){
           return;
        }
        notifyListener();
    }

    /**
     * Waiting for opponents move
     * @return the opponents move
     */
    public Move waitForMove(){
        String from = "";
        String to = "";
        try {
            String query = "SELECT FromMove, ToMove FROM GameManager WHERE GameId='"+gameId+"' AND GameActive = 1;";
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

    public Move nextMove() {
        String from = "";
        String to = "";
        try {
            String query = "SELECT FromMove, ToMove FROM GameManager WHERE GameId='"+gameId+"' AND GameActive = 1 AND FromMove IS NOT NULL AND ToMove IS NOT NULL;";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if(res.next()){
                from = res.getString("FromMove");
                to = res.getString("ToMove");
                Move curMove = new Move(from, to);
                if(curMove.equals(lastMove)) {
                    return null;
                }
                return curMove;
            }
        }catch(SQLException e){
            return null;
        }
        return null;
    }


    /**
     * Set GameActive to False,
     */
    public void endGame(){
        try {
            String query = "UPDATE GameManager SET GameActive=0 WHERE GameId='"+gameId+"';";
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
        }catch(SQLException e){
        }
    }

    public boolean gameIsActive() {
        try {
            String query = "SELECT GameActive FROM GameManager WHERE GameId = '" + gameId + "';";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                String gameActive = res.getString("GameActive");
                return gameActive.equals("1");
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
            while(res.next()) {
                String name = res.getString(opponent);
                return name;
            }
        }catch(SQLException e){
            return null;
        }
        return null;
    }

    /**
     * Returns true if the color of the player at this computer is white, otherwise false
     * @return
     */
    @Override
    public boolean isWhite() {
        return thisIsTheColorOfThePlayerAtThisComputer;
    }

    private void notifyListener() {
        listener.workDone(this);
    }

    public void registerWorkerListener(ConnectionListener listener) {
        this.listener = listener;
    }

    public String getP2Name() {
        return p2Name;
    }
}
