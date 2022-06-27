package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.messages.clienttoserver.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import static it.polimi.ingsw.utils.Utils.GUI_HEIGHT;
import static it.polimi.ingsw.utils.Utils.GUI_WIDTH;

public class MatchSettingScene extends Scene {
    private final AnchorPane MAIN_PANE;
    private ClientController controller;
    public MatchSettingScene(AnchorPane MAIN_PANE) {
        super(MAIN_PANE, GUI_WIDTH, GUI_HEIGHT);
        this.MAIN_PANE = MAIN_PANE;
        this.initialize();
    }
    private void initialize() {
        this.MAIN_PANE.getChildren().clear();
        Button continueBtn = new Button("Continua");
        continueBtn.setFont(Font.font("Berlin Sans FB", 20));
        continueBtn.setCursor(Cursor.HAND);
        continueBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        AnchorPane.setBottomAnchor(continueBtn, 55.0);
        AnchorPane.setRightAnchor(continueBtn, 80.0);
        this.MAIN_PANE.setBackground(Background.fill(Paint.valueOf("#dedede")));
        this.MAIN_PANE.getChildren().add(continueBtn);
        VBox matchSettingPane = new VBox();
        matchSettingPane.setAlignment(Pos.CENTER);
        matchSettingPane.setFillWidth(true);
        matchSettingPane.setSpacing(50.0);
        matchSettingPane.setPrefWidth(GUI_WIDTH);
        matchSettingPane.setPrefHeight(300);
        Label title = new Label("Vuoi creare una nuova partita o\npartecipare ad una già esistente?");
        title.setFont(Font.font("Berlin Sans FB", 40));
        title.setAlignment(Pos.CENTER);
        AnchorPane matchSettingOptions = new AnchorPane();
        matchSettingOptions.setMaxWidth(300);
        matchSettingOptions.setPrefHeight(150);
        matchSettingOptions.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        ToggleGroup options = new ToggleGroup();
        RadioButton createMatch = new RadioButton("Crea");
        createMatch.setFont(Font.font("Berlin Sans FB", 20));
        createMatch.setAlignment(Pos.CENTER);
        createMatch.setCursor(Cursor.HAND);
        createMatch.setToggleGroup(options);
        AnchorPane.setRightAnchor(createMatch, 50.0);
        AnchorPane.setLeftAnchor(createMatch, 50.0);
        AnchorPane.setTopAnchor(createMatch, 30.0);
        RadioButton partecipateMatch = new RadioButton("Partecipa");
        partecipateMatch.setFont(Font.font("Berlin Sans FB", 20));
        partecipateMatch.setAlignment(Pos.CENTER);
        partecipateMatch.setCursor(Cursor.HAND);
        partecipateMatch.setToggleGroup(options);
        AnchorPane.setRightAnchor(partecipateMatch, 50.0);
        AnchorPane.setLeftAnchor(partecipateMatch, 50.0);
        AnchorPane.setBottomAnchor(partecipateMatch, 30.0);
        createMatch.setSelected(true);
        matchSettingOptions.getChildren().addAll(createMatch, partecipateMatch);
        matchSettingPane.getChildren().addAll(title, matchSettingOptions);
        AnchorPane.setTopAnchor(matchSettingPane, 150.0);
        continueBtn.setOnAction(event -> {
            MatchResponse response = new MatchResponse(createMatch.isSelected());
            this.controller.sendObjectMessage(response);
        });
        this.MAIN_PANE.getChildren().add(matchSettingPane);
    }

    void setController(ClientController controller) {
        this.controller = controller;
    }
}
