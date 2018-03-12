package com.grnn.chess;

public class Position {
    int posX;
    int posY;

    public Position(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public int getX(){
        return posX;
    }

    public int getY(){
        return posY;
    }

    public void setX(int x){
        posX = x;
    }

    public void setY(int y){
        posY = y;
    }
}
