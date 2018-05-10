package com.grnn.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.grnn.chess.Actors.IActor;
import com.grnn.chess.Actors.Player;


import java.sql.*;
import java.util.ArrayList;


/**
 * Created by hakon on 21.03.2018.
 */
public class PlayerData {
	private FileHandle userHandle;
	public ArrayList<Player> accounts;
	private Connection conn;
	private Boolean offline;



    /**
     * Constructor for PlayerData. Will try to connect to our MySql server
     * If there is a trouble with the connection, an offline PlayerData will be made.
     */
	public PlayerData(){
		accounts = new ArrayList<Player>();
		boolean successfullConnection = connectToDatabase();
		if(successfullConnection) {
			getAccountsFromDB();
			offline = false;
		}else{
			//Offline
			offline = true;
			addOfflinePlayers();
		}
	}

    /**
     * Tells if the PlayerData is offline
     * @return True if the Database did not connect, otherwise false.
     */
	public boolean isOffline(){
		return offline;
	}

    /**
     * Adds two offline Players
     */
	private void addOfflinePlayers(){
		Player player1 = new Player("Player1","", true);
		Player player2 = new Player("Player2","", false);
		accounts.add(player1);
		accounts.add(player2);
	}

    /**
     * Get all the accounts from the DataBase into accounts
     */
	private void getAccountsFromDB() {
		Statement stmt = null;
		String query = "SELECT * FROM Users";
		try{
			stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				String name = res.getString("Name");
				String password = res.getString("Password");
				int wins = res.getInt("Wins");
				int losses = res.getInt("Losses");
				int draw = res.getInt("Draws");
				int rating = res.getInt("Rating");
				Player player = new Player(name,password,wins,losses,draw,rating);
				if(player!=null) {
					accounts.add(player);
				}
			}
		}catch (SQLException e){
			System.out.println("Did not Connect");
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			}catch(SQLException e){
				System.out.println("Kunne ikke koble fra databasen");
			}
		}

	}

    /**
     * Connects to the DataBase
     * @return True if the connection was successfull, otherwise false
     */
	private boolean connectToDatabase(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.setLoginTimeout(1);
			conn = DriverManager.getConnection("jdbc:mysql://grnn.cj7trxamf8oy.us-east-2.rds.amazonaws.com:3306/Sjakk", "grnn", "grnnsjakk");
			offline = false;
			return true;
		}catch(Exception e){
			System.out.println("not connected");
			System.out.println(e);
			offline = true;
			return false;
		}
	}

    /**
     * Get one player from the database
     * @param playerName name off the player
     * @return the player if the connection works, otherwise null
     */
	public Player getPlayerFromDatabase(String playerName){
		if(!offline){
			try{
				String query = "SELECT * FROM Users WHERE Name = '"+playerName+"';";
				Statement stmt = conn.createStatement();
				ResultSet res = stmt.executeQuery(query);
				return new Player(res.getString("Name"), res.getString("Password"), res.getInt("Wins"), res.getInt("Losses"), res.getInt("Rating"),res.getInt("Rating"));
			} catch (SQLException e){
				return null;
			}
		}else {
			return null;
		}
	}

	/**
	 * gets a player by playerName
	 * @param playerName
	 * @return the Player named playerName, else null
	 */
	public Player getPlayer(String playerName) {

		for(Player account : accounts) {
			if(account.getName().equals(playerName)) {
				return account;
			}
		}

		return null;
	}


    /**
     * Save all players in accounts to the database
     */
	public void saveAccountsToDatabase(){
		try{
			for(Player account : accounts) {
				//SQL
				String query = "UPDATE Users SET Wins = "+account.getNoOfWins()+", Losses = "+account.getNoOfLose()+", Draws = "+account.getNoOfDraws()+", Rating = "+account.getRating()+" WHERE Name = '"+account.name+"';";
				Statement stmt = conn.createStatement();
				int res = stmt.executeUpdate(query);
			}
		}catch(SQLException e){

			}
	}

    /**
     * Add a new player to the database
     * @param player The player that should be added
     */
	public void addAccountToDatabase(Player player){
	    if(!nameExists(player.name)) {
	        accounts.add(player);
            try {
                String query = "INSERT INTO Users VALUES ('" + player.name + "', '" + player.getPassword() + "', " + player.getNoOfWins() + ", " + player.getNoOfLose() + ", " + player.getNoOfDraws() + ", " + player.getRating() + ");";
                Statement stmt = conn.createStatement();
                int res = stmt.executeUpdate(query);
            } catch (SQLException e) {

            }
        }else
            throw new IllegalArgumentException();
	}


	/**
	 * Checks if a name is taken
	 * @param name
	 * @return true if the name is taken, else false
	 */
	public boolean nameExists(String name) {
		for(Player a : accounts) {
			if(a.getName().equals(name) || a.getName().toLowerCase().equals(name.toLowerCase()))
				return true;
		}

		return false;
	}

    /**
     * Asks the Database for the top 10 players
     * @return a list of top 10 players
     */
	public ArrayList<Player> getTopTenPlayers(){
        ArrayList<Player> topTenPlayers = new ArrayList<Player>();
	    try{
            String query = "SELECT * FROM Users ORDER BY Rating DESC LIMIT 10";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                topTenPlayers.add(new Player(res.getString("Name"), res.getString("Password"), res.getInt("Wins"), res.getInt("Losses"), res.getInt("Rating"), res.getInt("Rating")));
            }
            return topTenPlayers;
	    }catch (SQLException e){
	        return null;
        }
    }


	/**
     * method that updates the score for two playors
     * @param player1 an AI or a Player
     * @param player2 an AI or a Player
     */
    public void updatePlayers(IActor player1, IActor player2){
	    if(player1 instanceof Player){
	        updatePlayer((Player) player1);
        }
        if(player2 instanceof Player){
	        updatePlayer((Player) player2);
        }
    }

    /**
     * Update one player in the database.
     * @param player The player that should be added to the database
     */
    public void updatePlayer(Player player){
        try{
            String query = "UPDATE Users SET Wins = "+player.getNoOfWins()+", Losses = "+player.getNoOfLose()+", Draws = "+player.getNoOfDraws()+", Rating = "+player.getRating()+" WHERE Name = '"+player.name+"';";
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
        }catch (SQLException e){

        }
    }

    public Connection getConnection(){
    	return conn;
	}

}
