package main;

public class Ram extends Piece {

    public Ram(int color, int col, int row) {
        super(color, col, row);

        if (color == GamePanel.BLUE) {
            image = getImage("res\\Piece\\BRAM.png");
        } else {
            image = getImage("res\\Piece\\RRAM.png");
        }
    }
}
