package com.service;
import com.frame.Canvas;
import com.model.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import static com.util.Constants.*;

public class TetrisService extends JFrame {



    Canvas canvas = new Canvas();
    Figure figure = new Figure();
    public static int[][] mine = new int[FIELD_HEIGHT + 1][FIELD_WIDTH];
    public static boolean gameOver = false;
    private int gameScore=0;

    public TetrisService() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, FIELD_WIDTH * BLOCK_SIZE + FIELD_DX, FIELD_HEIGHT * BLOCK_SIZE + FIELD_DY);
        setResizable(false);
        canvas.setBackground(Color.black); // define the background color
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    if (e.getKeyCode() == DOWN) figure.drop();
                    if (e.getKeyCode() == UP) figure.rotate();
                    if (e.getKeyCode() == LEFT || e.getKeyCode() == RIGHT) figure.move(e.getKeyCode());
                }
                canvas.repaint();
            }
        });
        add(BorderLayout.CENTER, canvas);
        setVisible(true);
        Arrays.fill(mine[FIELD_HEIGHT], 1); // create a ground for mines
    }


    public void go() { // main loop of game
        while (!gameOver) {
            try {
                Thread.sleep(SHOW_DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            canvas.repaint();
            if (figure.isTouchGround()) {
                figure.leaveOnTheGround();
                figure = new Figure();
                gameOver = figure.isCrossGround();
            } else
                figure.stepDown();
        }
    }



}
