package it.polimi.ingsw.client.gui;

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
    private static RadioButton twoPlayersOption;
    private static RadioButton threePlayersOption;
    private static RadioButton expertModeOption;
    private static Button continueBtn;
    private static VBox numOfPlayersPane;
    private static boolean isNumOfPlayersOptions;

    private GameSettingsScene(Parent root) {
        super(root, GUI_WIDTH, GUI_HEIGHT);
    }

    public static GameSettingsScene getInstance() {
        AnchorPane root = new AnchorPane();
        initialize(root);
        return new GameSettingsScene(root);
    }

    private static void initialize(AnchorPane anchorPane) {
        continueBtn = new Button("Continua");
        continueBtn.setFont(Font.font("Berlin Sans FB", 20));
        continueBtn.setCursor(Cursor.HAND);
        continueBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        continueBtn.addEventHandler(ActionEvent.ACTION, event -> {
            if (isNumOfPlayersOptions) {
                int numOfPlayers = twoPlayersOption.isSelected() ? 2 : threePlayersOption.isSelected() ? 3 : 0;
                ClientMessage numOfPlayersMessage = new NumOfPlayersResponse(numOfPlayers);
                GUI.getController().sendObjectMessage(numOfPlayersMessage);
            } else {
                boolean expertMode = expertModeOption.isSelected();
                ClientMessage expertModeMessage = new GameModeResponse(expertMode);
                GUI.getController().sendObjectMessage(expertModeMessage);
            }
        });
        isNumOfPlayersOptions = true;
        AnchorPane.setBottomAnchor(continueBtn, 55.0);
        AnchorPane.setRightAnchor(continueBtn, 80.0);
        anchorPane.setBackground(Background.fill(Paint.valueOf("#dedede")));
        anchorPane.getChildren().add(continueBtn);
    }

    public static void showNumOfPlayersOptions(AnchorPane anchorPane) {
        if (Objects.nonNull(anchorPane)) {
            numOfPlayersPane = new VBox();
            numOfPlayersPane.setAlignment(Pos.CENTER);
            numOfPlayersPane.setFillWidth(true);
            numOfPlayersPane.setSpacing(50.0);
            numOfPlayersPane.setPrefWidth(GUI_WIDTH);
            numOfPlayersPane.setPrefHeight(300);
            Label title = new Label("Seleziona il numero di giocatori");
            title.setFont(Font.font("Berlin Sans FB", 40));
            title.setAlignment(Pos.CENTER);
            AnchorPane numOfPlayersOptions = new AnchorPane();
            numOfPlayersOptions.setMaxWidth(250);
            numOfPlayersOptions.setPrefHeight(150);
            numOfPlayersOptions.setBackground(Background.fill(Paint.valueOf("#fafafa")));
            ToggleGroup options = new ToggleGroup();
            twoPlayersOption = new RadioButton("2 giocatori");
            twoPlayersOption.setFont(Font.font("Berlin Sans FB", 20));
            twoPlayersOption.setAlignment(Pos.CENTER);
            twoPlayersOption.setCursor(Cursor.HAND);
            twoPlayersOption.setToggleGroup(options);
            AnchorPane.setRightAnchor(twoPlayersOption, 50.0);
            AnchorPane.setLeftAnchor(twoPlayersOption, 50.0);
            AnchorPane.setTopAnchor(twoPlayersOption, 30.0);
            threePlayersOption = new RadioButton("3 giocatori");
            threePlayersOption.setFont(Font.font("Berlin Sans FB", 20));
            threePlayersOption.setAlignment(Pos.CENTER);
            threePlayersOption.setCursor(Cursor.HAND);
            threePlayersOption.setToggleGroup(options);
            AnchorPane.setRightAnchor(threePlayersOption, 50.0);
            AnchorPane.setLeftAnchor(threePlayersOption, 50.0);
            AnchorPane.setBottomAnchor(threePlayersOption, 30.0);
            twoPlayersOption.setSelected(true);
            numOfPlayersOptions.getChildren().addAll(twoPlayersOption, threePlayersOption);
            numOfPlayersPane.getChildren().addAll(title, numOfPlayersOptions);
            AnchorPane.setTopAnchor(numOfPlayersPane, 20.0);
            anchorPane.getChildren().add(numOfPlayersPane);
        }
    }

    public static void showExpertModeOptions(AnchorPane anchorPane) {
        if (Objects.nonNull(anchorPane)) {
            isNumOfPlayersOptions = false;
            numOfPlayersPane.setDisable(true);
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
            expertModeOption = new RadioButton("modalità esperti");
            expertModeOption.setFont(Font.font("Berlin Sans FB", 20));
            expertModeOption.setAlignment(Pos.CENTER);
            expertModeOption.setCursor(Cursor.HAND);
            expertModeOption.setToggleGroup(options);
            AnchorPane.setRightAnchor(expertModeOption, 50.0);
            AnchorPane.setLeftAnchor(expertModeOption, 50.0);
            AnchorPane.setTopAnchor(expertModeOption, 30.0);
            RadioButton regularModeOption = new RadioButton("modalità normale");
            regularModeOption.setFont(Font.font("Berlin Sans FB", 20));
            regularModeOption.setAlignment(Pos.CENTER);
            regularModeOption.setCursor(Cursor.HAND);
            regularModeOption.setToggleGroup(options);
            AnchorPane.setRightAnchor(regularModeOption, 50.0);
            AnchorPane.setLeftAnchor(regularModeOption, 50.0);
            AnchorPane.setBottomAnchor(regularModeOption, 30.0);
            regularModeOption.setSelected(true);
            expertModeOptions.getChildren().addAll(expertModeOption, regularModeOption);
            expertModePane.getChildren().addAll(title, expertModeOptions);
            AnchorPane.setBottomAnchor(expertModePane, 100.0);
            anchorPane.getChildren().add(expertModePane);
        }
    }
}
