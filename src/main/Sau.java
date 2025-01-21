package main;

public class Sau extends Piece {

    public Sau(int color, int col, int row) {
        super(color, col, row);

        if (color == GamePanel.BLUE) {
            image = getImage("res\\Piece\\BSAU.png");
        } else {
            image = getImage("res\\Piece\\RSAU.png");
        }
    }
}
