package com.grnn.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

/**
 * Created by hakon on 21.03.2018.
 */
public class GameData {
	FileHandle userHandle;
	ArrayList<SerialGame> games;
	public GameData() {
		userHandle = Gdx.files.local("data/games.csv");

		games = getGames();

		if(games.size() == 0) {
			addTestGames();
			games = getGames();
		}
	}

	private void addTestGames() {
		SerialGame test1 = new SerialGame("Håkon", "Simon", 1, 1);
		SerialGame test2 = new SerialGame("Simon", "Håkon", 1, 1);
		addGame(test1);
		addGame(test2);
	}

	public SerialGame getGame(int gameId) {
		for(SerialGame game : games) {
			if(game.getId() == (gameId)) {
				return game;
			}
		}

		return null;
	}


	public ArrayList<SerialGame> getGames() {
		ArrayList<SerialGame> games = new ArrayList<SerialGame>();
		String text = userHandle.readString();

		if(text.equals("")) return games;
		int lastGameId = 0;

		String gameStrings[] = text.split("\\n");
		for(String csv : gameStrings) {
			String[] gameData = csv.split(";");
			lastGameId = Integer.parseInt(gameData[2]);
			games.add(new SerialGame(
					gameData[0],
					gameData[1],
					lastGameId,
					Integer.parseInt(gameData[3])
			));
		}

		Game.setCurrid(lastGameId);
		return games;
	}

	private void saveGames() {
		String out = "";
		for(SerialGame game : games) {
			out += game.getWhiteName() + ";" +
					game.getBlackName() + ";" +
					game.getId() + ";" +
					game.getResult();

			out += "\n";
		}

		userHandle.writeString(out, false);
	}

	public void addGame(SerialGame game) {
		games.add(game);
		saveGames();
	}
}

