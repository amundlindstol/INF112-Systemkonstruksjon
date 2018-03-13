package test;

import com.grnn.chess.Board;
import com.grnn.chess.Position;
import com.grnn.chess.objects.AbstractChessPiece;
import com.grnn.chess.objects.Knight;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by hakon on 05.03.2018.
 */
public class ChessForKidsTest {
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

	@Test
	public void moveRookTest() {
		Position startPosition = new Position(0, 0);
		Position endPosition = new Position(3, 2);
		movePieceTest(startPosition, endPosition);
	}

	@Test
	public void movePieceTest(Position startPosition, Position endPosition) {

		AbstractChessPiece piece = board.getPieceAt(startPosition);

		board.movePiece(startPosition, endPosition);
		assertThat(board.getPieceAt(endPosition), is(piece));
	}

}
