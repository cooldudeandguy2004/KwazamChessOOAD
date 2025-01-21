package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {

    private JFrame window;

    public MainMenu(JFrame window) {
        this.window = window;
        setLayout(new GridLayout(3, 1));

        JLabel title = new JLabel("Kwazam Chess", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        add(newGameButton);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });
        add(loadGameButton);
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
        JOptionPane.showMessageDialog(window, "Load Game feature is not implemented yet.");
    }
}
