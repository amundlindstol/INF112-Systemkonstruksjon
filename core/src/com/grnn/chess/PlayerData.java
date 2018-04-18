package com.grnn.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
	 * Object for getting userdata from the database.
	 * Initialises a file handle.
	 * Adds test data if none is present.
	 */
	public PlayerData() {
		userHandle = Gdx.files.local("data/players.csv");

		accounts = getAccounts();

		if(accounts.size() == 0) {
			addTestAccounts();
			accounts = getAccounts();
		}
	}
	public PlayerData(int i){
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

	public boolean isOffline(){
		return offline;
	}

	private void addOfflinePlayers(){
		Player player1 = new Player("Player1","", true);
		Player player2 = new Player("Player2","", false);
		accounts.add(player1);
		accounts.add(player2);
	}

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
	 * Adds test players Simon and Håkon
	 */
	public void addTestAccounts() {
		Player test1 = new Player("Håkon", "123456", true);
		Player test2 = new Player("Simon", "123456", false);
		addAccount(test1);
		addAccount(test2);
	}

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
			return getPlayer(playerName);
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
	 * Gets all accounts in the database
	 * @return
	 */
	public ArrayList<Player> getAccounts() {
		ArrayList<Player> players = new ArrayList<Player>();
		String text = userHandle.readString();
		if(text.equals("")) return players;
		String accountStrings[] = text.split("\\n");
		for(String csv : accountStrings) {
			String[] userData = csv.split(";");
			players.add(new Player(
					userData[0],
					userData[1],
					Integer.parseInt(userData[2]),
					Integer.parseInt(userData[3]),
					Integer.parseInt(userData[4]),
					Integer.parseInt(userData[5])
			));
		}

		return players;
	}

	/**
	 * Saves accounts
	 */
	public void saveAccounts() {
		String out = "";
		for(Player account : accounts) {
			out += account.getName() + ";" +
					account.getPassword() + ";" +
					account.getNoOfWins() + ";" +
					account.getNoOfDraws() +  ";" +
					account.getNoOfLose() + ";" +
					account.getRating();
			out += "\n";
		}

		userHandle.writeString(out, false);
	}

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

	public void addAccountToDatabase(Player player){
		try{
			String query = "INSERT INTO Users VALUES ('"+player.name+"', '"+player.getPassword()+"', "+player.getNoOfWins()+", "+player.getNoOfLose()+", "+player.getNoOfDraws()+", "+player.getRating()+");";
			Statement stmt = conn.createStatement();
			int res = stmt.executeUpdate(query);
		}catch (SQLException e){

		}
	}
	/**
	 * adds an account
	 * @param account the account to be added
	 */
	public void addAccount(Player account) {
		if(!nameExists(account.name)) {
			accounts.add(account);
			saveAccounts();
		} else {
			throw new IllegalArgumentException();
		}
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

}
