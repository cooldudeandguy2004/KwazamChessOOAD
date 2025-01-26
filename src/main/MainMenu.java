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

    //Sets the mainmenu window
    public MainMenu(JFrame window) {
        this.window = window;
        setLayout(null);//setLayout(new GridLayout(3, 1));

        //Load background image
        try {
            backgroundImage = ImageIO.read(new File("res\\chessback.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create the title on the main menu
        JLabel title = new JLabel("Kwazam Chess", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setOpaque(false);
        title.setForeground(Color.WHITE);
        title.setBounds(100, 50, 600, 50);
        add(title);


        //Create the new game button
        JButton newGameButton = createButton("New Game", 0, 400);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        add(newGameButton);

        
        //Create the load game button
        JButton loadGameButton = createButton("Load Game", 0, 500);
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });
        add(loadGameButton);

        revalidate();
        repaint();
        
    }

    //Create button
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Helvetica", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setBounds(x, y, 150, 40);

        //Hovering mouse effect
        button.addMouseListener(new MouseAdapter() {
            private final Font defaultFont = button.getFont();
            private final Font hoverFont = defaultFont.deriveFont(defaultFont.getStyle(), defaultFont.getSize() + 2);

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(hoverFont);
                button.setForeground(Color.LIGHT_GRAY);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setFont(defaultFont);
                button.setForeground(Color.WHITE);
                button.setBorder(null);
            }
        });
        return button;
    }

    //Paints the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    //Method for when clicking new game
    private void startNewGame() {
        window.getContentPane().removeAll();
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();
        window.setSize(GamePanel.WIDTH, GamePanel.HEIGHT);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.launchGame();
    }

    //Method for when clicking load game
    private void loadGame() {

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            GamePanel gp = new GamePanel();
            window.getContentPane().removeAll();
            window.add(gp);
            window.setResizable(true);
            window.pack();
            window.setSize(GamePanel.WIDTH, GamePanel.HEIGHT);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            gp.loadGame(selectedFile.getPath());
        }
    }


}
