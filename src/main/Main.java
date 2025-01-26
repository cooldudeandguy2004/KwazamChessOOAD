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
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
    }

}
