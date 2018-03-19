package com.grnn.chess.AI;

import com.grnn.chess.Game;

import java.util.List;

public class Node {

    Game game;
    List<Node> children;
    int score;

    public Node(Game game) {
        game = game;
    }

    public int getScore() {
        return score;
    }
}
