package com.grnn.chess.test;

import com.grnn.chess.Position;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by hakon on 19.03.2018.
 */
public class PositionTest {
	@Test
	public void positionEqualsTest() {
		Position pos1 = new Position(4, 0);
		Position pos2 = new Position(4, 0);

		assertThat(pos1, is(pos2));
	}

	public void positionNorthTest() {
		Position pos1 = new Position(4, 1);
		Position pos2 = new Position(4, 0).north(1);

		assertThat(pos1, is(pos2));
	}
}
