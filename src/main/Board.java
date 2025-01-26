package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Board {

    public static final int MAX_COL = 5;
    public static final int MAX_ROW = 8;
    public static final int SQUARE_SIZE = 90;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;


    public void draw(Graphics2D g2) {

        for (int row = 0; row < MAX_ROW; row++) {

            for (int col = 0; col < MAX_COL; col++) {

                if ((row + col) % 2 == 0) {
                    g2.setColor(Color.gray);
                } else {
                    g2.setColor(Color.white);
                }
                g2.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

            }
        }

    }
}
