package main;

public class Biz extends Piece {

    public Biz(int color, int col, int row) {
        super(color, col, row);

        if (color == GamePanel.BLUE) {
            image = getImage("res\\Piece\\BBIZ.png");
        } else {
            image = getImage("res\\Piece\\RBIZ.png");
        }
    }
}
