package com.grnn.chess.test;

import com.grnn.chess.Board;
import com.grnn.chess.objects.King;
import com.grnn.chess.objects.Queen;
import com.grnn.chess.objects.Rook;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EvaluateBoardTest {

    @Test
    public void evaluateBoard() {
        Board board = new Board();

        King whiteKing = new King(true, true);
        Queen whiteQueen = new Queen(true);
        Rook firstWhiteRook = new Rook(true, false);
        Rook secondWhiteRook = new Rook(true, false);

        King blackKing = new King(false, true);

        board.setPiece(whiteKing, 4, 0);
        board.setPiece(whiteQueen, 3, 0);

        board.setPiece(firstWhiteRook, 0, 0);
        board.setPiece(secondWhiteRook, 7, 0);

        board.setPiece(blackKing, 4, 7);

        int whiteValue = whiteKing.getValue() + whiteQueen.getValue() + firstWhiteRook.getValue() + secondWhiteRook.getValue();

        int blackValue = blackKing.getValue();

        assertTrue(whiteValue > blackValue);
        assertEquals(whiteValue - blackValue, board.calculateBoardForActor(true));
    }

}
