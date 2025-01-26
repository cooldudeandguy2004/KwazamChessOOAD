package main;

public class Sau extends Piece {

    public Sau(int color, int col, int row) {
        super(color, col, row, "SAU");

        if (color == GamePanel.BLUE) {
            image = getImage("res\\Piece\\BSAU.png");
        } else {
            image = getImage("res\\Piece\\RSAU.png");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {

        if (isWithinBoard(targetCol, targetRow)) {
            if (Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 ||
                    Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1) {
                if (isValidSquare(targetCol, targetRow)) {
                    return true;
                }

            }
        }
        return false;
    }
}
