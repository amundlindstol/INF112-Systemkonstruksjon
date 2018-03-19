package com.grnn.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TranslateToCellPos {
    private int BOARD_PADDING = 40;
    private int CELL_DIM = 65;

//    public TranslateToCellPos() {}
//    /**
//     * @board used to set optional cell dim
//     * @param board
//     */
//    public TranslateToCellPos(Texture board) {
//        if (board != null)
//            CELL_DIM = (board.getWidth() - BOARD_PADDING*2)/8;
//    }

    /**
     * @param mouseX position
     * @param mouseY position
     * @return Position of cell, where (0,0) is lower left
     */
    public Position toCellPos(int mouseX, int mouseY) {
        int x = mouseX - BOARD_PADDING;
        int y = translateY(mouseY);

        if (x > CELL_DIM*8 || y > CELL_DIM*8 || x < 0 || y < 0) //not on board
            return null;
        x /= CELL_DIM;
        y /= CELL_DIM;

        if (x > 7) x = 7; //avoid pixel perfect bug
        if (y > 7) y = 7;
        System.out.println(x + ", " + y);
        return new Position(x, y);
    }

    private int translateY(int y) {
        return Math.abs(y-Gdx.graphics.getHeight()+BOARD_PADDING);
    }
}
