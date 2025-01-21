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
}
