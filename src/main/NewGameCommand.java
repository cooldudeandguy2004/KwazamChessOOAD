package main;

public class NewGameCommand implements Command {
    private GamePanel gamePanel;

    public NewGameCommand(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void execute() {
        gamePanel.newGame();
    }
}
