package com.model;

import java.awt.*;

import static com.util.Constants.ARC_RADIUS;
import static com.util.Constants.BLOCK_SIZE;

public class Block {
    private int x,y;

    public Block(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    void paint(Graphics g, int color) {
        g.setColor(new Color(color));
        g.drawRoundRect(x * BLOCK_SIZE + 1, y * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2, ARC_RADIUS, ARC_RADIUS);
    }

}
