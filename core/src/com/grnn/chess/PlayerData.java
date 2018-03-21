package com.grnn.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

/**
 * Created by hakon on 21.03.2018.
 */
public class PlayerData {
	FileHandle userHandle;
	ArrayList<Player> accounts;

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

	/**
	 * Adds test players Simon and Håkon
	 */
	public void addTestAccounts() {
		Player test1 = new Player("Håkon", "123456");
		Player test2 = new Player("Simon", "123456");
		addAccount(test1);
		addAccount(test2);
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
			if(a.getName().equals(name))
				return true;
		}

		return false;
	}

}
