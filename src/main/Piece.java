package main;

import java.awt.Image;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Piece {

    public Image image;
    public int x, y;
    public int col, row, preCol, preRow;
    public int color;
    public Piece hittingP;


    public Piece(int color, int col, int row) {
        this.color = color;
        this.col = col;
        this.row = row;
        x = getX(col);
        y = getY(row);
        preCol = col;
        preRow = row;
    }

    public Image getImage(String imagePath) {
        Image img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    
    public int getX(int col) {
        return col * Board.SQUARE_SIZE;
    }

    public int getY(int row) {
        return row * Board.SQUARE_SIZE;
    }
    public int getCol (int x) {
        return (x + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }
    public int getRow (int x) {
        return (y + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }
    public int getIndex() {
        for(int index = 0; index < GamePanel.simPieces.size(); index++ ) {
            if(GamePanel.simPieces.get(index) == this) {
                return index;
            }
        }
        return 0;

    }
    public void updatePosition() {
        x = getX(col);
        y = getY(row);
        preCol = getCol(x);
        preRow = getRow(y);
    }
    public void resetPosition() {
        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
    }
    public boolean canMove(int targetCol, int targetRow){
        return false;
    }
    public boolean isWithinBoard(int targetCol, int targetRow) {
        if(targetCol >= 0 && targetCol <= 7 && targetRow >= 0 && targetRow <= 7) {
            return true;
        }
        return false;
    }
    public boolean isSameSquare(int targetCol,int targetRow) {
        if(targetCol == preCol && targetRow == preRow) {
            return true;
        }
        return false;
    }
    public Piece getHittingP(int targetCol, int targetRow) {
        for(Piece piece : GamePanel.simPieces) {
            if(piece.col == targetCol && piece.row == targetRow && piece != this) {
                return piece;
            }
        }
        return null;
    }
    public boolean isValidSquare(int targetCol, int targetRow) {

        hittingP = getHittingP(targetCol, targetRow);

        if(hittingP == null) {
            return true;
        }
        else {
            if(hittingP.color != this.color) {
                return true;
            }
            else {
                hittingP = null;
            }
        }
        return false;
    }

    //Blocks movement if there is a piece on the line
    public boolean pieceIsonStraightLine(int targetCol, int targetRow) {

        //Check left
        for(int c = preCol-1; c > targetCol; c--) {
            for(Piece piece : GamePanel.simPieces) {
                if(piece.col == c && piece.row == targetRow) { 
                    hittingP= piece;
                    return true;
                }
            }
        }
        //Check right
        for(int c = preCol+1; c < targetCol; c++) {
            for(Piece piece : GamePanel.simPieces) {
                if(piece.col == c && piece.row == targetRow) {
                    hittingP= piece;
                    return true;
                }
            }
        }
        //Check up
        for(int r = preRow-1; r > targetRow; r--) {
            for(Piece piece : GamePanel.simPieces) {
                if(piece.col == targetCol && piece.row == r) {
                    hittingP= piece;
                    return true;
                }
            }
        }
        //Check down
        for(int r = preRow+1; r < targetRow; r++) {
            for(Piece piece : GamePanel.simPieces) {
                if(piece.col == targetCol && piece.row == r) {
                    hittingP= piece;
                    return true;
                }
            }
        }
        return false;
    }

    //Blocks movement if there is a piece on diagonal line
    public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow) {
        if(targetRow < preRow) {
            //Check up left
            for(int c = preCol-1; c > targetCol; c-- ) {
                int diff = Math.abs(c - preCol);
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.col == c && piece.row == preRow - diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }

            //Check up right
            for(int c = preCol+1; c < targetCol; c++ ) {
                int diff = Math.abs(c - preCol);
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.col == c && piece.row == preRow - diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }

        }

        if(targetRow > preRow) {
            //Check down left
            for(int c = preCol-1; c > targetCol; c-- ) {
                int diff = Math.abs(c - preCol);
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.col == c && piece.row == preRow + diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }

            //Check down right 
            for(int c = preCol+1; c < targetCol; c++ ) {
                int diff = Math.abs(c - preCol);
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.col == c && piece.row == preRow + diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }

        }
        return false;
    }

    //Draw piece on board
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

}
