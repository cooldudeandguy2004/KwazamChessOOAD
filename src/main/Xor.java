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
}
