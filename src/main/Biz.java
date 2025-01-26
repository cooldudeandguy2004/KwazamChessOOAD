package main;

//Biz piece class, moves in an L-shaped pattern
//Inherits from Piece class
//Ammar
public class Biz extends Piece {

    //Constructors, color is color of the piece, col is column placement on the board and row is the row placement
    //Part of Factory design pattern
    //Ammar
    public Biz(int color, int col, int row) {
        super(color, col, row, "BIZ");
    }


    //Check if the piece can move to the target position
    //Faiz
    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) { //Check if target is within board boundaries
            if (Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 2) { //L-shaped movement
                if (isValidSquare(targetCol, targetRow)) { //Check if square is valid
                    return true;
                }
            }
        }
        return false;
    }
}
