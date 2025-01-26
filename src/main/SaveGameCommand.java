package main;

public class SaveGameCommand implements Command{
    private GamePanel gamePanel;

    public SaveGameCommand(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void execute() {
        gamePanel.saveGame();
    }
}
