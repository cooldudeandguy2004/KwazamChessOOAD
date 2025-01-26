package main;

import java.awt.Color;
import java.awt.Graphics2D;

//Represents the game board
//Ammar
public class Board {

    //Board dimensions and layout constants
    public static final int MAX_COL = 5; //Columns
    public static final int MAX_ROW = 8; //Rows
    public static final int SQUARE_SIZE = 90; //Pixel size of each square
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2; //Half size for positioning calculations


    //Draws the board by rendering alternating colored squares.
    public void draw(Graphics2D g2) {

        // Iterate through all rows and columns to draw squares
        for (int row = 0; row < MAX_ROW; row++) {

            for (int col = 0; col < MAX_COL; col++) {
                // Alternate colors based on (row + col) parity for a checkerboard pattern
                if ((row + col) % 2 == 0) {
                    g2.setColor(Color.gray);
                } else {
                    g2.setColor(Color.white);
                }
                // Draw the square at the calculated position
                g2.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

            }
        }

    }
}
