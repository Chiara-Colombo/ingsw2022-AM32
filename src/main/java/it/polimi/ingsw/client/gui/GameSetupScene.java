package it.polimi.ingsw.client.gui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

import static it.polimi.ingsw.utils.Utils.*;

public class GameSetupScene extends Scene {
    private GameSetupScene(Parent root) {
        super(root, GUI_WIDTH, GUI_HEIGHT);
    }

    public static GameSetupScene getInstance() {
        Pane root = new Pane();
        initialize(root);
        return new GameSetupScene(root);
    }

    private static void initialize(Pane pane) {
        Label title = new Label("La partita sta iniziando");
        title.setLayoutX(GUI_WIDTH / 2.0);
        title.setLayoutY(GUI_HEIGHT / 2.0);
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(title);
    }
}
