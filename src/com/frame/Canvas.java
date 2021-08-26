package com.frame;


import com.model.Figure;
import com.service.TetrisService;

import javax.swing.*;
import java.awt.*;

import static com.service.TetrisService.gameOver;
import static com.service.TetrisService.mine;
import static com.util.Constants.*;


public class Canvas extends JPanel {
    Figure figure=new Figure();
   @Override
    public void paint(Graphics g){
       super.paint(g);
       for (int x = 0; x < FIELD_WIDTH; x++)
           for (int y = 0; y < FIELD_HEIGHT; y++) {
               if (x < FIELD_WIDTH - 1 && y < FIELD_HEIGHT - 1) {
                   g.setColor(Color.lightGray);
                   g.drawLine((x + 1) * BLOCK_SIZE - 2, (y + 1) * BLOCK_SIZE, (x + 1) * BLOCK_SIZE + 2, (y + 1) * BLOCK_SIZE);
                   g.drawLine((x + 1) * BLOCK_SIZE, (y + 1) * BLOCK_SIZE - 2, (x + 1) * BLOCK_SIZE, (y + 1) * BLOCK_SIZE + 2);
               }
               if (mine[y][x] > 0) {
                   g.setColor(new Color(mine[y][x]));
                   g.fill3DRect(x * BLOCK_SIZE + 1, y * BLOCK_SIZE + 1, BLOCK_SIZE - 1, BLOCK_SIZE - 1, true);
               }
           }
       if (gameOver) {
           g.setColor(Color.white);
           for (int y = 0; y < GAME_OVER_MSG.length; y++)
               for (int x = 0; x < GAME_OVER_MSG[y].length; x++)
                   if (GAME_OVER_MSG[y][x] == 1) g.fill3DRect(x * 11 + 18, y * 11 + 160, 10, 10, true);
       } else
           figure.paint(g);
   }
}
