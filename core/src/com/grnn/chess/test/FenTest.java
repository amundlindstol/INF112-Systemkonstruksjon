package com.grnn.chess.test;

import com.grnn.chess.Board;
import com.grnn.chess.Game;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.Position;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FenTest {
    @Test
    public void testStartPosition() {
        String defaultBoardFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

        Game defaultGame =
                new Game(0, new Player("hakon", "123456", true),
                    new Player("hakon", "123456", false));

        assertThat(defaultGame.toFen(), is(defaultBoardFEN));
    }

    @Test
    public void testMovedPawn() {
        String defaultBoardFEN = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";

        Game defaultGame =
                new Game(0, new Player("hakon", "123456", true),
                        new Player("hakon", "123456", false));

        //defaultGame.getBoard().movePiece(new Position(4, 1), new Position(4, 3));
        defaultGame.selectFirstPiece(new Position(4, 1));
        defaultGame.moveFirstSelectedPieceTo(new Position(4, 3));

        assertThat(defaultGame.toFen(), is(defaultBoardFEN));
    }
}
