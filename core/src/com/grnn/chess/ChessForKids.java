package com.grnn.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.grnn.chess.objects.AbstractChessPiece;

public class ChessForKids extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bg;
	Board board;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		bg = new Texture("core/assets/sjakk2.png");

		board = new Board();
		board.addPieces();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(bg, 0, 0);
        batch.draw(new Texture(board.getPieceAt(new Position(3,0)).getImage()),3*(600/9),10);
        batch.draw(new Texture(board.getPieceAt(new Position(3,1)).getImage()), 3*(600/9), 2*(600/9));
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
