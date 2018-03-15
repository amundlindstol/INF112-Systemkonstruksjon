package com.grnn.chess;

import com.grnn.chess.objects.AbstractChessPiece;

public class Move {

    private Position toPos;
    private Position fromPos;
    private AbstractChessPiece piece;

    public Move(Position toPos, Position fromPos, AbstractChessPiece piece) {
        toPos = toPos;
        fromPos = fromPos;
        piece = piece;
    }

}
