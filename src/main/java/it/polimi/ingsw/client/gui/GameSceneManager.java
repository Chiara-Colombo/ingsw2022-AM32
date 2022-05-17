package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.BoardUpdateContent;
import javafx.scene.layout.Pane;

public class GameSceneManager {
    private final GameScene gameScene;

    public GameSceneManager(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    public void showBoardUpdate(BoardUpdateContent boardUpdateContent) {
        GameScene.showBoard((Pane) this.gameScene.getRoot(), boardUpdateContent);
    }
}
