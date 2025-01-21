package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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

    //Save button
    private JPanel sidePanel;
    private JButton saveGameButton;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setPieces();
        copyPieces(pieces, simPieces);

        //Side panel and save game button
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(Color.WHITE);

        saveGameButton = new JButton("Save Game");
        saveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });
        sidePanel.add(saveGameButton);

        this.setLayout(new BorderLayout());
        this.add(sidePanel, BorderLayout.EAST);
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

    //Save game method
    public void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();


            //Check if file does not have .txt and add it
            if (!selectedFile.getName().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".txt");
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                writer.write("CurrentColor: " + currentColor + "\n");
                for (Piece piece : pieces) {
                    writer.write(piece.getClass().getSimpleName() + "," + piece.color + "," + piece.col + "," + piece.row + "\n");
                }
                JOptionPane.showMessageDialog(this, "Game saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to save game.");
            }
        }    
    }

    //Helper method to create piece based on type
    private Piece createPiece(String pieceType, int color, int col, int row) {
        switch (pieceType) {
            case "Ram":
                return new Ram(color, col, row);
            case "Xor":
                return new Xor(color, col, row);
            case "Biz":
                return new Biz(color, col, row);
            case "Sau":
                return new Sau(color, col, row);
            case "Tor":
                return new Tor(color, col, row);
            default:
                return null;
        }
    }

    public void loadGame(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            pieces.clear(); //Clear current game pieces
            String line = reader.readLine();
            if (line != null && line.startsWith("CurrentColor: ")) {
                currentColor = Integer.parseInt(line.split(":")[1].trim());
            }
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String pieceType = parts[0];
                int color = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);
                int row = Integer.parseInt(parts[3]);
                Piece piece = createPiece(pieceType, color, col, row);
                if (piece != null) {
                    pieces.add(piece);
                }
            }

            copyPieces(pieces, simPieces);
            JOptionPane.showMessageDialog(this, "Game loaded successfully.");
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load game.");
        }
    }

    

}
