package main;

//Ammar
//Load game command method
public class LoadGameCommand implements Command{
    private GamePanel gamePanel;

    public LoadGameCommand(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void execute() {
        gamePanel.loadGame(null);
    }
}
