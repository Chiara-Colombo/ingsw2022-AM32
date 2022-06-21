package it.polimi.ingsw.client.gui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import static it.polimi.ingsw.utils.Utils.*;

public class WaitingScene extends Scene {
    private final AnchorPane MAIN_PANE;
    public WaitingScene(AnchorPane MAIN_PANE) {
        super(MAIN_PANE, GUI_WIDTH, GUI_HEIGHT);
        this.MAIN_PANE = MAIN_PANE;
        this.initialize();
    }

    private void initialize() {
        VBox loadingPane = new VBox();
        loadingPane.setSpacing(80.0);
        loadingPane.setPrefWidth(GUI_WIDTH);
        loadingPane.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(loadingPane, 180.0);
        Label title = new Label("In attesa di altri giocatori");
        title.setFont(Font.font("Berlin Sans FB", 30));
        title.setAlignment(Pos.CENTER);
        ProgressIndicator progressIndicator = new ProgressIndicator(-1.0);
        loadingPane.getChildren().addAll(title, progressIndicator);
        this.MAIN_PANE.getChildren().add(loadingPane);
    }
}
