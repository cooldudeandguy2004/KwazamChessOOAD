package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 900;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();

    //Pieces
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();

    // Color
    public static final int RED = 1;
    public static final int BLUE = 0;
    int currentColor = BLUE;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setPieces();
        copyPieces(pieces, simPieces);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setPieces() {
        //Blue Team
        pieces.add(new Ram(BLUE, 0, 6));
        pieces.add(new Ram(BLUE, 1, 6));
        pieces.add(new Ram(BLUE, 2, 6));
        pieces.add(new Ram(BLUE, 3, 6));
        pieces.add(new Ram(BLUE, 4, 6));
        pieces.add(new Xor(BLUE, 0, 7));
        pieces.add(new Biz(BLUE, 1, 7));
        pieces.add(new Sau(BLUE, 2, 7));
        pieces.add(new Biz(BLUE, 3, 7));
        pieces.add(new Tor(BLUE, 4, 7));

        //Red Team
        pieces.add(new Ram(RED, 0, 1));
        pieces.add(new Ram(RED, 1, 1));
        pieces.add(new Ram(RED, 2, 1));
        pieces.add(new Ram(RED, 3, 1));
        pieces.add(new Ram(RED, 4, 1));
        pieces.add(new Xor(RED, 0, 0));
        pieces.add(new Biz(RED, 1, 0));
        pieces.add(new Sau(RED, 2, 0));
        pieces.add(new Biz(RED, 3, 0));
        pieces.add(new Tor(RED, 4, 0));
    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        for (int i = 0; i < source.size(); i++) {
            target.add(source.get(i));
        }
    } 

    @Override
    public void run() {
        // Game loop
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }

    }

    private void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        //Board
        board.draw(g2);
        //Pieces
        for (Piece p : simPieces) {
            p.draw(g2);
        }
    }

}
