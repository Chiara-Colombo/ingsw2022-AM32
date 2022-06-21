package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.ClientMessage;
import it.polimi.ingsw.messages.clienttoserver.GameModeResponse;
import it.polimi.ingsw.messages.clienttoserver.NumOfPlayersResponse;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.Objects;

import static it.polimi.ingsw.utils.Utils.*;

public class GameSettingsScene extends Scene {
    private final AnchorPane MAIN_PANE;
    private final RadioButton twoPlayersOption;
    private final RadioButton threePlayersOption;
    private final RadioButton expertModeOption;
    private final Button continueBtn;
    private final VBox numOfPlayersPane;
    private ClientController controller;
    private boolean isNumOfPlayersOptions;

    public GameSettingsScene(AnchorPane MAIN_PANE) {
        super(MAIN_PANE, GUI_WIDTH, GUI_HEIGHT);
        this.MAIN_PANE = MAIN_PANE;
        this.continueBtn = new Button();
        this.twoPlayersOption = new RadioButton();
        this.threePlayersOption = new RadioButton();
        this.expertModeOption = new RadioButton();
        this.numOfPlayersPane = new VBox();
        this.isNumOfPlayersOptions = false;
        this.initialize();
    }

    private void initialize() {
        this.continueBtn.setText("Continua");
        this.continueBtn.setFont(Font.font("Berlin Sans FB", 20));
        this.continueBtn.setCursor(Cursor.HAND);
        this.continueBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        this.continueBtn.addEventHandler(ActionEvent.ACTION, event -> {
            if (this.isNumOfPlayersOptions) {
                int numOfPlayers = this.twoPlayersOption.isSelected() ? 2 : this.threePlayersOption.isSelected() ? 3 : 0;
                ClientMessage numOfPlayersMessage = new NumOfPlayersResponse(numOfPlayers);
                this.controller.sendObjectMessage(numOfPlayersMessage);
            } else {
                boolean expertMode = this.expertModeOption.isSelected();
                ClientMessage expertModeMessage = new GameModeResponse(expertMode);
                this.controller.sendObjectMessage(expertModeMessage);
            }
        });
        this.isNumOfPlayersOptions = true;
        AnchorPane.setBottomAnchor(this.continueBtn, 55.0);
        AnchorPane.setRightAnchor(this.continueBtn, 80.0);
        this.MAIN_PANE.setBackground(Background.fill(Paint.valueOf("#dedede")));
        this.MAIN_PANE.getChildren().add(this.continueBtn);
    }

    public void showNumOfPlayersOptions() {
        this.numOfPlayersPane.setAlignment(Pos.CENTER);
        this.numOfPlayersPane.setFillWidth(true);
        this.numOfPlayersPane.setSpacing(50.0);
        this.numOfPlayersPane.setPrefWidth(GUI_WIDTH);
        this.numOfPlayersPane.setPrefHeight(300);
        Label title = new Label("Seleziona il numero di giocatori");
        title.setFont(Font.font("Berlin Sans FB", 40));
        title.setAlignment(Pos.CENTER);
        AnchorPane numOfPlayersOptions = new AnchorPane();
        numOfPlayersOptions.setMaxWidth(250);
        numOfPlayersOptions.setPrefHeight(150);
        numOfPlayersOptions.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        ToggleGroup options = new ToggleGroup();
        this.twoPlayersOption.setText("2 giocatori");
        this.twoPlayersOption.setFont(Font.font("Berlin Sans FB", 20));
        this.twoPlayersOption.setAlignment(Pos.CENTER);
        this.twoPlayersOption.setCursor(Cursor.HAND);
        this.twoPlayersOption.setToggleGroup(options);
        AnchorPane.setRightAnchor(this.twoPlayersOption, 50.0);
        AnchorPane.setLeftAnchor(this.twoPlayersOption, 50.0);
        AnchorPane.setTopAnchor(this.twoPlayersOption, 30.0);
        this.threePlayersOption.setText("3 giocatori");
        this.threePlayersOption.setFont(Font.font("Berlin Sans FB", 20));
        this.threePlayersOption.setAlignment(Pos.CENTER);
        this.threePlayersOption.setCursor(Cursor.HAND);
        this.threePlayersOption.setToggleGroup(options);
        AnchorPane.setRightAnchor(this.threePlayersOption, 50.0);
        AnchorPane.setLeftAnchor(this.threePlayersOption, 50.0);
        AnchorPane.setBottomAnchor(this.threePlayersOption, 30.0);
        this.twoPlayersOption.setSelected(true);
        numOfPlayersOptions.getChildren().addAll(this.twoPlayersOption, this.threePlayersOption);
        this.numOfPlayersPane.getChildren().addAll(title, numOfPlayersOptions);
        AnchorPane.setTopAnchor(this.numOfPlayersPane, 20.0);
        this.MAIN_PANE.getChildren().add(this.numOfPlayersPane);
    }

    public void showExpertModeOptions() {
        this.isNumOfPlayersOptions = false;
        this.numOfPlayersPane.setDisable(true);
        VBox expertModePane = new VBox();
        expertModePane.setAlignment(Pos.CENTER);
        expertModePane.setFillWidth(true);
        expertModePane.setSpacing(50.0);
        expertModePane.setPrefWidth(GUI_WIDTH);
        expertModePane.setPrefHeight(300);
        Label title = new Label("Seleziona la modalità di gioco");
        title.setFont(Font.font("Berlin Sans FB", 40));
        title.setAlignment(Pos.CENTER);
        AnchorPane expertModeOptions = new AnchorPane();
        expertModeOptions.setMaxWidth(300);
        expertModeOptions.setPrefHeight(150);
        expertModeOptions.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        ToggleGroup options = new ToggleGroup();
        this.expertModeOption.setText("modalità esperti");
        this.expertModeOption.setFont(Font.font("Berlin Sans FB", 20));
        this.expertModeOption.setAlignment(Pos.CENTER);
        this.expertModeOption.setCursor(Cursor.HAND);
        this.expertModeOption.setToggleGroup(options);
        AnchorPane.setRightAnchor(this.expertModeOption, 50.0);
        AnchorPane.setLeftAnchor(this.expertModeOption, 50.0);
        AnchorPane.setTopAnchor(this.expertModeOption, 30.0);
        RadioButton regularModeOption = new RadioButton("modalità normale");
        regularModeOption.setFont(Font.font("Berlin Sans FB", 20));
        regularModeOption.setAlignment(Pos.CENTER);
        regularModeOption.setCursor(Cursor.HAND);
        regularModeOption.setToggleGroup(options);
        AnchorPane.setRightAnchor(regularModeOption, 50.0);
        AnchorPane.setLeftAnchor(regularModeOption, 50.0);
        AnchorPane.setBottomAnchor(regularModeOption, 30.0);
        this.expertModeOption.setSelected(true);
        expertModeOptions.getChildren().addAll(this.expertModeOption, regularModeOption);
        expertModePane.getChildren().addAll(title, expertModeOptions);
        AnchorPane.setBottomAnchor(expertModePane, 100.0);
        this.MAIN_PANE.getChildren().add(expertModePane);
    }

    void setController(ClientController controller) {
        this.controller = controller;
    }
}
