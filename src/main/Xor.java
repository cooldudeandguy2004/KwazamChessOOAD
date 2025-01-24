package main;

public class Xor extends Piece {

    public Xor(int color, int col, int row) {
        super(color, col, row);

        if (color == GamePanel.BLUE) {
            image = getImage("res\\Piece\\BXOR.png");
        } else {
            image = getImage("res\\Piece\\RXOR.png");
        }
    }
    public boolean canMove(int targetCol, int targetRow) {
        
        if(isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol,targetRow) == false) {

            if(Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
                if(isValidSquare(targetCol, targetRow) && pieceIsOnDiagonalLine(targetCol, targetRow) == false) {
                    return true;

                }
            }
        }
        return false;

    }
}
