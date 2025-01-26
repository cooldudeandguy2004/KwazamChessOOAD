package main;

//Ammar
//Ram Pieces class
public class Ram extends Piece {

    //Ammar
    public Ram(int color, int col, int row) {
        super(color, col, row, "RAM");

        if (color == GamePanel.BLUE) {
            image = getImage("res\\Piece\\BRAM.png");
        } else {
            image = getImage("res\\Piece\\RRAM.png");
        }
    }

    // Change direction once piece reach the end
    //Adlan
    int moveFactor = 1;

    private int changeDirection() {
        if (preRow == 0 || preRow == 7) {
            rotateImage();
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
            } else {
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

    // Rotate image 180 degrees after reaching end
    private void rotateImage() {
        image = rotateImage180(image);
    }
}
