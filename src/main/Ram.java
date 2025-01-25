package main;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Ram extends Piece {

    public Ram(int color, int col, int row) {
        super(color, col, row);

        if (color == GamePanel.BLUE) {
            image = getImage("res\\Piece\\BRAM.png");
        } else {
            image = getImage("res\\Piece\\RRAM.png");
        }
    }

    // Change direction once piece reach the end
    int moveFactor = 1;
    private int changeDirection() {
        if (preRow == 0 || preRow == 7) {
            moveFactor = moveFactor * -1;
        }
        return moveFactor;
    }

    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
            // Different color, different direction
            int moveValue;
            if (color == GamePanel.BLUE) {
                moveValue = -1;
            }
            else {
                moveValue = 1;
            }
            // Ram can only move 1 step forward
            if (targetCol == preCol && targetRow == preRow + (moveValue * changeDirection())) {
                if (isValidSquare(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics2D g2) {
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.scale(Board.SQUARE_SIZE / (double) image.getWidth(null), Board.SQUARE_SIZE / (double) image.getHeight(null));

        // Rotate 180 degrees if moveFactor is -1
        if (moveFactor == -1) {
            at.rotate(Math.toRadians(180), image.getWidth(null) / 2.0, image.getHeight(null) / 2.0);
        }
        g2.drawImage(image, at, null);
    }
}
