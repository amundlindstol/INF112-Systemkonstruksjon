package com.grnn.chess.multiPlayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MultiPlayer {

    Connection conn;
    /**
     * Constructur of multiplayer, Should either connect to a Peer of create a new peer
     */
    public MultiPlayer(boolean peerOne){{
        if(peerOne){
            createPeer();
        }else{
            createPeer();
        }
    }

    }

    /**
     * Return a list of open peers
     * @return
     */
    public ArrayList<String> getGames() {
        ArrayList<String> availableGames = new ArrayList<String>();
        try{
            String query = "SELECT * FROM Games WHERE Available='"+true+"';";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                availableGames.add(res.getString("GameId"));
            }
            return availableGames;
        }catch(SQLException e){
            return null;
        }
    }


    /**
     * Create a new Peer, should upload ip-adress to database and open a socket.
     * @return true if the connection was successful, otherwise false.
     */
    public boolean createPeer(){
        //TODO create peer
        try {
            String query = "INSERT INTO Games (IPAdress, Avalible) VALUES ('"+getIP()+"', '"+"');";
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
        }catch(SQLException e){
            return false;
        }
        return true;
    }


    /**
     * Connect to peer
     */
    public boolean connectToPeer(int nr){
        String Ip = getPeerIP(nr);

    }


    /**
     * Close the connection and remove the game from the database
     */
    public void closeConnection(int nr){
        //TODO close socket
        //Remove the game from the database
        try {
            String sql = "DELETE FROM Games WHERE GameID='"+nr+"';";
            Statement stmt = conn.createStatement();
            boolean res = stmt.execute(sql);
        }catch(SQLException e){

        }
    }

    /**
     * Returns the ip of this machine
     */
    public String getIP(){
        //TODO
        return "localhost";
    }
    /**
     *
     */
    public String getPeerIP(int nr){
        try{
            String query = "SELECT IPAdress FROM Games WHERE GameID='"+nr+"';";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            String ip = res.getString("IPAdress");
            return ip;
        }catch(SQLException e) {
            return null;
        }
    }
}
