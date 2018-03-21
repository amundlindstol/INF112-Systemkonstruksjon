package com.grnn.chess;

/**
 * Created by hakon on 21.03.2018.
 */
public class SerialGame {
	int id, result;
	String blackName, whiteName;

	public int getId() {
		return id;
	}

	public int getResult() {
		return result;
	}

	public String getBlackName() {
		return blackName;
	}

	public String getWhiteName() {
		return whiteName;
	}

	/**
	 * constructs a object that is used to serialize game data.
	 * @param whiteName
	 * @param blackName
	 * @param id
	 * @param result
	 */
	SerialGame(String whiteName, String blackName, int id, int result) {
		this.whiteName = whiteName;
		this.blackName = blackName;
		this.id = id;
		this.result = result;
	}
}
