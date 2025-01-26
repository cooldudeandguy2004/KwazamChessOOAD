package main;

public class Biz extends Piece {

    public Biz(int color, int col, int row) {
        super(color, col, row, "BIZ");
    }

    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) {
            if (Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 2) {
                if (isValidSquare(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }
}
