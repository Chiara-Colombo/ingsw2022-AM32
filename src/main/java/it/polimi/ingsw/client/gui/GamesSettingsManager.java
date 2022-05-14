package it.polimi.ingsw.client.gui;

import javafx.scene.layout.AnchorPane;

public class GamesSettingsManager {
    private final GameSettingsScene gameSettingsScene;

    public GamesSettingsManager(GameSettingsScene gameSettingsScene) {
        this.gameSettingsScene = gameSettingsScene;
    }

    public void showNumOfPlayersOptions() {
        GameSettingsScene.showNumOfPlayersOptions((AnchorPane) this.gameSettingsScene.getRoot());
    }

    public void showExpertModeOptions() {
        GameSettingsScene.showExpertModeOptions((AnchorPane) this.gameSettingsScene.getRoot());
    }
}
