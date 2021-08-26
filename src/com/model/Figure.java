package com.model;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static com.service.TetrisService.mine;
import static com.util.Constants.*;

public class Figure {
    Random random = new Random();
    private ArrayList<Block> figure = new ArrayList();
    private int[][] shape = new int[4][4];
    private int type, size, color;
    private int x = 3, y = 0; // starting left up corner

    public Figure() {
        type = random.nextInt(SHAPES.length);
        size = SHAPES[type][4][0];
        color = SHAPES[type][4][1];
        if (size == 4) y = -1;
        for (int i = 0; i < size; i++)
            System.arraycopy(SHAPES[type][i], 0, shape[i], 0, SHAPES[type][i].length);
        createFromShape();
    }


    void createFromShape() {
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                if (shape[y][x] == 1) figure.add(new Block(x + this.x, y + this.y));
    }



    void rotateShape(int direction) {
        for (int i = 0; i < size / 2; i++)
            for (int j = i; j < size - 1 - i; j++)
                if (direction == RIGHT) { // clockwise
                    int tmp = shape[size - 1 - j][i];
                    shape[size - 1 - j][i] = shape[size - 1 - i][size - 1 - j];
                    shape[size - 1 - i][size - 1 - j] = shape[j][size - 1 - i];
                    shape[j][size - 1 - i] = shape[i][j];
                    shape[i][j] = tmp;
                } else { // counterclockwise
                    int tmp = shape[i][j];
                    shape[i][j] = shape[j][size - 1 - i];
                    shape[j][size - 1 - i] = shape[size - 1 - i][size - 1 - j];
                    shape[size - 1 - i][size - 1 - j] = shape[size - 1 - j][i];
                    shape[size - 1 - j][i] = tmp;
                }
    }
    boolean isWrongPosition() {
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                if (shape[y][x] == 1) {
                    if (y + this.y < 0) return true;
                    if (x + this.x < 0 || x + this.x > FIELD_WIDTH - 1) return true;
                    if (mine[y + this.y][x + this.x] > 0) return true;
                }
        return false;
    }
    public void rotate() {
        rotateShape(RIGHT);
        if (!isWrongPosition()) {
            figure.clear();
            createFromShape();
        } else
            rotateShape(LEFT);
    }



    public boolean isTouchGround() {
        for (Block block : figure) if (mine[block.getY() + 1][block.getX()] > 0) return true;
        return false;
    }

    public void leaveOnTheGround() {
        for (Block block : figure) mine[block.getY()][block.getX()] = color;
    }

    public boolean isCrossGround() {
        for (Block block : figure) if (mine[block.getY()][block.getX()] > 0) return true;
        return false;    }

    public void stepDown() {
        for (Block block : figure) {
            block.setY(block.getY() + 1);
            y++;
        }
    }
    boolean isTouchWall(int direction) {
        for (Block block : figure) {
            if (direction == LEFT && (block.getX() == 0 || mine[block.getY()][block.getX() - 1] > 0)) return true;
            if (direction == RIGHT && (block.getX() == FIELD_WIDTH - 1 || mine[block.getY()][block.getX() + 1] > 0))
                return true;
        }
        return false;
    }
    public void move(int direction) {
        if (!isTouchWall(direction)) {
            int dx = direction - 38; // LEFT = -1, RIGHT = 1
            for (Block block : figure) block.setX(block.getX() + dx);
            x += dx;
        }
    }
    public void drop() {
        while (!isTouchGround()) stepDown();
    }

    public void paint(Graphics g) {
        for (Block block : figure) block.paint(g, color);
    }
}
