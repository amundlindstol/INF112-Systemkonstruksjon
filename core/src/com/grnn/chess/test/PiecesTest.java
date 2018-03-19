package com.grnn.chess.test;

import com.grnn.chess.Board;
import com.grnn.chess.Position;
import com.grnn.chess.objects.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by hakon on 05.03.2018.
 */
public class PiecesTest {
	Board board;

	@Before
	public void setup() {
		board = new Board();
		board.addPieces();
	}

	@Test
	public void setupBoardTest() {
		AbstractChessPiece piece = board.getPieceAt(new Position(1, 0));

		assertTrue(piece instanceof Knight);
	}

	//TODO: check that it only accepts valid moves
	@Test
	public void moveRookTest() {
		Position startPosition = new Position(0, 0);
		Position endPosition = new Position(3, 2);
		movePieceTest(startPosition, endPosition);
	}

	public void movePieceTest(Position startPosition, Position endPosition) {

		AbstractChessPiece piece = board.getPieceAt(startPosition);

		board.movePiece(startPosition, endPosition);


		assertThat(board.getPieceAt(endPosition), is(piece));
	}

	@Test
	public void rookValidMoveTest() {
		Board board = new Board();
		Rook r = new Rook(true);
		board.setPiece(r, 0, 0);

		assertTrue(r.getValidMoves(board).contains(new Position(0, 3)));
		assertTrue(r.getValidMoves(board).contains(new Position(3, 0)));
	}

	@Test
	public void rookInvalidMoveTest() {
		Board board = new Board();
		Rook r = new Rook(true);
		board.setPiece(r, 1, 1);

		assertFalse(r.getValidMoves(board).contains(new Position(0, 0)));
		assertFalse(r.getValidMoves(board).contains(new Position(3, 1)));
	}

	@Test
	public void bishopValidMoveTest() {
		Board board = new Board();
		Bishop r = new Bishop(true);
		board.setPiece(r, 1, 1);

		assertTrue(r.getValidMoves(board).contains(new Position(3, 3)));
		assertTrue(r.getValidMoves(board).contains(new Position(2, 0)));
	}

	@Test
	public void bishopInvalidMoveTest() {
		Board board = new Board();
		Bishop r = new Bishop(true);
		board.setPiece(r, 1, 1);

		assertFalse(r.getValidMoves(board).contains(new Position(2, 1)));
		assertFalse(r.getValidMoves(board).contains(new Position(3, 1)));
	}

	@Test
	public void kingValidMoveTest() {
		Board board = new Board();
		Bishop r = new Bishop(true);
		board.setPiece(r, 4, 0);

		assertTrue(r.getValidMoves(board).contains(new Position(4, 1)));
		assertTrue(r.getValidMoves(board).contains(new Position(3, 0)));
		assertTrue(r.getValidMoves(board).contains(new Position(5, 0)));

	}

	@Test
	public void kingInvalidMoveTest() {
		Board board = new Board();
		King r = new King(true);
		board.setPiece(r, 4, 0);

		assertFalse(r.getValidMoves(board).contains(new Position(4, 4)));
		assertFalse(r.getValidMoves(board).contains(new Position(7, 0)));
	}

	@Test
	public void kingCanCastleWhenNotMoved() {
		Board board = new Board();
		Bishop r = new Bishop(true);
		board.setPiece(r, 4, 0);

		assertTrue(r.getValidMoves(board).contains(new Position(6, 0)));
		assertTrue(r.getValidMoves(board).contains(new Position(1, 0)));

	}

	@Test
	public void kingCantCastleWhenMoved() {
		Board board = new Board();
		King r = new King(true);
		board.setPiece(r, 4, 0);

		board.movePiece(new Position(4, 0), new Position(4, 1));

		assertFalse(r.getValidMoves(board).contains(new Position(6, 0)));
		assertFalse(r.getValidMoves(board).contains(new Position(6, 1)));

		board.movePiece(new Position(4, 1), new Position(4, 0));

		assertFalse(r.getValidMoves(board).contains(new Position(6, 0)));
		assertFalse(r.getValidMoves(board).contains(new Position(1, 0)));
	}
}
