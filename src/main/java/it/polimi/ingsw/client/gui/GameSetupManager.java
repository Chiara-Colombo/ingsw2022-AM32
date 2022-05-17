package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.model.Wizards;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GameSetupManager {
    private final GameSetupScene gameSetupScene;

    public GameSetupManager(GameSetupScene gameSetupScene) {
        this.gameSetupScene = gameSetupScene;
    }

    public void showWizardCards(ArrayList<Wizards> availableWizards, GUI gui) {
        GameSetupScene.showWizardCards(availableWizards, (Pane) this.gameSetupScene.getRoot(), gui);
    }

    public void showPlayerChoosingWizard() {
        GameSetupScene.showPlayerChoosingWizard((Pane) this.gameSetupScene.getRoot());
    }
}
