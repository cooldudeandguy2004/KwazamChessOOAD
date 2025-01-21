package main;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        
        JFrame window = new JFrame("Kwazam Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        //Create main menu
        MainMenu menu = new MainMenu(window);
        window.add(menu);

        window.setSize(800,600);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        /*Add GamePanel to the window
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        window.setSize(GamePanel.WIDTH, GamePanel.HEIGHT);

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.launchGame();*/
    }

}
