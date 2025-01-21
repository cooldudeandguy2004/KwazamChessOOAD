package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {

    private JFrame window;
    private Image backgroundImage;

    public MainMenu(JFrame window) {
        this.window = window;
        setLayout(new GridLayout(3, 1));

        //Load background image
        try {
            backgroundImage = ImageIO.read(new File("res\\chessbackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel title = new JLabel("Kwazam Chess", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setOpaque(false);
        add(title);

        JButton newGameButton = new JButton("New Game");
        
        newGameButton.setOpaque(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setBorderPainted(false);
        newGameButton.setForeground(Color.BLACK);
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 18));
        newGameButton.setFocusPainted(false);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        newGameButton.addMouseListener(new MouseAdapter() {
            private final Font defaultFont = newGameButton.getFont();
            private final Font hoverFont = defaultFont.deriveFont(defaultFont.getStyle(), defaultFont.getSize() + 2);

            @Override
            public void mouseEntered(MouseEvent e) {
                newGameButton.setFont(hoverFont);
                newGameButton.setForeground(Color.DARK_GRAY);
                newGameButton.setBorder(BorderFactory.createRaisedBevelBorder());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                newGameButton.setFont(defaultFont);
                newGameButton.setForeground(Color.BLACK);
                newGameButton.setBorder(null);
            }

        });
        add(newGameButton);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setOpaque(false);
        loadGameButton.setContentAreaFilled(false);
        loadGameButton.setBorderPainted(false);
        loadGameButton.setForeground(Color.BLACK);
        loadGameButton.setFont(new Font("Arial", Font.PLAIN, 18));
        loadGameButton.setFocusPainted(false);
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });

        loadGameButton.addMouseListener(new MouseAdapter() {
            private final Font defaultFont = loadGameButton.getFont();
            private final Font hoverFont = defaultFont.deriveFont(defaultFont.getStyle(), defaultFont.getSize() + 2);

            @Override
            public void mouseEntered(MouseEvent e) {
                loadGameButton.setFont(hoverFont);
                loadGameButton.setForeground(Color.DARK_GRAY);
                loadGameButton.setBorder(BorderFactory.createRaisedBevelBorder());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loadGameButton.setFont(defaultFont);
                loadGameButton.setForeground(Color.BLACK);
                loadGameButton.setBorder(null);
            }
        });
    
        add(loadGameButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void startNewGame() {
        window.getContentPane().removeAll();
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();
        window.setSize(GamePanel.WIDTH, GamePanel.HEIGHT);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.launchGame();
    }

    private void loadGame() {
        // Implement your load game logic here
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            GamePanel gp = new GamePanel();
            window.getContentPane().removeAll();
            window.add(gp);
            window.pack();
            window.setSize(GamePanel.WIDTH, GamePanel.HEIGHT);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            gp.loadGame(selectedFile.getPath());
            /*try {
                //Read file and parse game state
                java.util.List<String> lines = java.nio.file.Files.readAllLines(selectedFile.toPath());
                loadGameFromFile(lines);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(window, "Error loading game: " + e.getMessage());
            }*/
        }
    }


}
