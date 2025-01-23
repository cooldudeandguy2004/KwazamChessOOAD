package main;

public class Tor extends Piece {

    public Tor(int color, int col, int row) {
        super(color, col, row);

        if (color == GamePanel.BLUE) {
            image = getImage("res\\Piece\\BTOR.png");
        } else {
            image = getImage("res\\Piece\\RTOR.png");
        }
    }
    public boolean canMove(int targetCol,int targetRow) {
        if(isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false ) {
            if(targetCol == preCol || targetRow == preRow) {
                if(isValidSquare(targetCol, targetRow) && pieceIsonStraightLine(targetCol,targetRow) == false) {
                    return true;
                }
            }
        }
        return false;
    }
}
