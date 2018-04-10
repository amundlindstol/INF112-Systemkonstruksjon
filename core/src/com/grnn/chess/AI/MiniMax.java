package com.grnn.chess.AI;

import com.grnn.chess.Game;
import com.grnn.chess.Move;

import java.util.List;

public class MiniMax {
    Node root;


    public void constructTree() {
        Game game = new Game(0,null,null);
        root = new Node(game);
        List<Move> listOfPossibleMoves ;
    }

}
