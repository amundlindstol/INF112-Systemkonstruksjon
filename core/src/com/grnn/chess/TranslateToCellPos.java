package com.grnn.chess;

import com.badlogic.gdx.Gdx;


/**
 * class to translate mouse x,y to cellPosition x,y
 * @author Amund Lindstøl, 21.03.18
 */
public class TranslateToCellPos {
    private int BOARD_PADDING = 40;
    private int CELL_DIM = 65;

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
        return new Position(x, y);
    }

    public int[] toPixels(int gridX, int gridY){
        int[] pos = new int[2];
        pos[0] = BOARD_PADDING+CELL_DIM*gridX;
        pos[1] = BOARD_PADDING+CELL_DIM*gridY;
    return pos;
    }


    private int translateY(int y) {
        return Math.abs(y-Gdx.graphics.getHeight()+BOARD_PADDING);
    }
}
