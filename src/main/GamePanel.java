package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.RenderingHints;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 750;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    // Pieces
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    ArrayList<Piece> transformedPieces = new ArrayList<>();
    Piece activeP;

    // Color
    public static final int RED = 1;
    public static final int BLUE = 0;
    int currentColor = BLUE;

    // Booleans
    boolean canMove;
    boolean validSquare;

    // Side panel to put save, load, and new game button
    private JPanel sidePanel;
    private JButton saveGameButton;
    private JButton newGameButton;
    private JButton loadGameButton;

    // Game over
    private boolean isGameOver = false;

    //Command design pattern fields
    private Command saveGameCommand;
    private Command newGameCommand;
    private Command loadGameCommand;

    private boolean flipBoard = false;
    

    // Game panel attributes and methods
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
        setPieces();
        copyPieces(pieces, simPieces);

        // Side panel
        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(142, HEIGHT));
        sidePanel.setBackground(Color.WHITE);

        //Initialize commands
        saveGameCommand = new SaveGameCommand(this);
        loadGameCommand = new LoadGameCommand(this);
        newGameCommand = new NewGameCommand(this);

        // Save button
        saveGameButton = new JButton("Save Game");
        saveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGameCommand.execute();
            }
        });
        sidePanel.add(saveGameButton);

        // New game button
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGameCommand.execute();
            }
        });
        sidePanel.add(newGameButton);

        // Load game button
        loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGameCommand.execute();
            }
        });
        sidePanel.add(loadGameButton);

    
        this.add(sidePanel, BorderLayout.EAST);

    }

    // Launch game method
    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Sets the pieces on the board
    public void setPieces() {
        // Blue Team
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

        // Red Team
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

    // Game loop
    @Override
    public void run() {
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
        if (isGameOver) {
            return; // Stop updates when the game is over
        }

        // Mouse button pressed
        if (mouse.pressed) {
            if (activeP == null) {

            // Get raw mouse coordinates
            int mouseX = mouse.x;
            int mouseY = mouse.y;

            // Flip coordinates if the board is rotated
            if (flipBoard) {
                mouseX = (Board.MAX_COL * Board.SQUARE_SIZE) - mouseX - 1;
                mouseY = (Board.MAX_ROW * Board.SQUARE_SIZE) - mouseY - 1;
            }

            int mouseCol = mouseX / Board.SQUARE_SIZE;
            int mouseRow = mouseY / Board.SQUARE_SIZE; 

            for (Piece piece : simPieces) {
                if (piece.color == currentColor && piece.col == mouseCol && piece.row == mouseRow) {
                    activeP = piece;
                    break;
                }
            }
        } else {
            simulate();
            }
        }

        // Mouse button released
        if (!mouse.pressed && activeP != null) {
            if (validSquare) {
                copyPieces(simPieces, pieces);
                activeP.updatePosition();
                changePlayer();
                checkForSauCapture(); // Check if Sau has been captured or not
            } else {
                copyPieces(pieces, simPieces);
                activeP.resetPosition();
                activeP = null;
            }
        }
    }

    private void simulate() {
        if (isGameOver) {
            return; // Stop piece simulation when the game is over
        }

        canMove = false;
        validSquare = false;

        copyPieces(pieces, simPieces);

        // Get raw mouse coordinates
        int mouseX = mouse.x;
        int mouseY = mouse.y;

        // Flip coordinates if the board is visually rotated
        if (flipBoard) {
        mouseX = (Board.MAX_COL * Board.SQUARE_SIZE) - mouseX - 1;
        mouseY = (Board.MAX_ROW * Board.SQUARE_SIZE) - mouseY - 1;
        }

        // Update active piece position
        activeP.x = mouseX - Board.HALF_SQUARE_SIZE;
        activeP.y = mouseY - Board.HALF_SQUARE_SIZE;

        // Calculate logical column/row
        activeP.col = activeP.getCol(activeP.x);
        activeP.row = activeP.getRow(activeP.y);
    
        if (activeP.canMove(activeP.col, activeP.row)) {
            canMove = true;
            if (activeP.hittingP != null) {
                simPieces.remove(activeP.hittingP.getIndex());
            }
            validSquare = true;
        }
    }

    // Turn counter variable
    private int turnCounter = 0;

    private void changePlayer() {
        if (currentColor == BLUE) {
            currentColor = RED;
        } else {
            currentColor = BLUE;
            turnCounter++; // Increment after both Blue and Red move

            if (turnCounter % 2 == 0) {
                transformPieces();
            }
        }
        flipBoard = !flipBoard;
        activeP = null;
    }

    // Transform method for Xor and Tor
    private void transformPieces() {
        ArrayList<Piece> transformedPieces = new ArrayList<>();

        for (Piece piece : pieces) {
            if (piece instanceof Tor) {
                transformedPieces.add(new Xor(piece.color, piece.col, piece.row));
            } else if (piece instanceof Xor) {
                transformedPieces.add(new Tor(piece.color, piece.col, piece.row));
            } else {
                transformedPieces.add(piece); // Keep other pieces unchanged
            }
        }

        pieces = transformedPieces;
        copyPieces(pieces, simPieces);
    }

    // Paints the necessary components onto the frame
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        AffineTransform originalTransform = g2.getTransform();

        if (flipBoard) {
            int centerX = (Board.MAX_COL * Board.SQUARE_SIZE) / 2;
            int centerY = (Board.MAX_ROW * Board.SQUARE_SIZE) / 2;
            g2.rotate(Math.PI, centerX, centerY);
        }
        // Board
        board.draw(g2);
        // Pieces
        for (Piece p : simPieces) {
            p.draw(g2);
        }

        if (activeP != null) {
            if (canMove) {
                g2.setColor(Color.blue);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7F));
                g2.fillRect(activeP.col * Board.SQUARE_SIZE, activeP.row * Board.SQUARE_SIZE,
                    Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            activeP.draw(g2);
        }

        g2.setTransform(originalTransform);

        // status
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Book Antiqua", Font.PLAIN, 25));
        g2.setColor(Color.white);

        if (currentColor == BLUE) {
            g2.drawString("Blue's turn", 530, 550);

        } else {
            g2.drawString("Red's turn", 530, 250);
        }
    }

    // Helper method to create piece based on type
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

    // Save game method
    public void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Check if file does not have .txt and add it
            if (!selectedFile.getName().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".txt");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                writer.write("CurrentColor: " + currentColor + "\n");
                for (Piece piece : pieces) {
                    writer.write(piece.getClass().getSimpleName() + "," + piece.color + "," + piece.col + ","
                            + piece.row + "\n");
                }
                JOptionPane.showMessageDialog(this, "Game saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to save game.");
            }
        }
    }

    // Load game method
    public void loadGame(String filePath) {
        if (filePath == null) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                filePath = fileChooser.getSelectedFile().getAbsolutePath();
            } else {
                return;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            pieces.clear(); // Clear current game pieces
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
            launchGame();
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load game.");
        }
    }

    // New game method
    public void newGame() {
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to start a new game? All progress will be lost.", "New Game Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            pieces.clear(); // Clear pieces and reset game state
            simPieces.clear();
            transformedPieces.clear(); // Reset any transformed pieces
            setPieces(); // Reput the pieces
            currentColor = BLUE;
            flipBoard = false; // Reset current player to blue
            activeP = null;
            canMove = false; // Reset movement flag
            validSquare = false;
            turnCounter = 0; // Reset turn couner
            isGameOver = false;
            copyPieces(pieces, simPieces); // Reinitialize pieces to new game state
            launchGame();
            repaint();
        }
    }

    private void checkForSauCapture() {
        int blueSauCount = 0;
        int redSauCount = 0;

        // Count the number of Sau pieces remaining on the board
        for (Piece piece : pieces) {
            if (piece instanceof Sau) {
                if (piece.color == BLUE) {
                    blueSauCount++;
                } else {
                    redSauCount++;
                }
            }
        }

        if (blueSauCount == 0) {
            JOptionPane.showMessageDialog(this, "Congrats! Red has won!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            isGameOver = true;
        } else if (redSauCount == 0) {
            JOptionPane.showMessageDialog(this, "Congrats! Blue has won!", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            isGameOver = true;
        }
    }

}
