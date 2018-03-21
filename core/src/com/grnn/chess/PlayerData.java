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
	public PlayerData() {
		userHandle = Gdx.files.local("data/players.csv");

		accounts = getAccounts();

		if(accounts.size() == 0) {
			addTestAccounts();
			accounts = getAccounts();
		}
	}

	public void addTestAccounts() {
		Player test1 = new Player("Håkon", "123456");
		Player test2 = new Player("Simon", "123456");
		addAccount(test1);
		addAccount(test2);
	}

	public Player getPlayer(String playerName) {
		for(Player account : accounts) {
			if(account.getName().equals(playerName)) {
				return account;
			}
		}

		return null;
	}


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

	public void addAccount(Player account) {
		if(!nameExists(account.name)) {
			accounts.add(account);
			saveAccounts();
		} else {
			throw new IllegalArgumentException();
		}
	}

	public boolean nameExists(String name) {
		for(Player a : accounts) {
			if(a.getName().equals(name))
				return true;
		}

		return false;
	}

}
