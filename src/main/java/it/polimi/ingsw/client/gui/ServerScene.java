package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.ClientMessage;
import it.polimi.ingsw.messages.clienttoserver.GameModeResponse;
import it.polimi.ingsw.messages.clienttoserver.NumOfPlayersResponse;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import static it.polimi.ingsw.utils.Utils.*;

public class ServerScene extends Scene {
    private final AnchorPane MAIN_PANE;
    private final TextField serverAddress, serverPort;
    private final Label wrongPortLabel;
    private final Button continueBtn, defaultAddressBtn, defaultPortBtn;
    private final ScenesManager scenesManager;

    public ServerScene(AnchorPane MAIN_PANE, ScenesManager scenesManager) {
        super(MAIN_PANE, GUI_WIDTH, GUI_HEIGHT);
        this.MAIN_PANE = MAIN_PANE;
        this.continueBtn = new Button();
        this.serverAddress = new TextField();
        this.serverPort = new TextField();
        this.wrongPortLabel = new Label();
        this.defaultAddressBtn = new Button();
        this.defaultPortBtn = new Button();
        this.scenesManager = scenesManager;
        this.initialize();
    }

    private void initialize() {
        VBox addressBox = new VBox();
        addressBox.setAlignment(Pos.CENTER);
        addressBox.setFillWidth(true);
        addressBox.setSpacing(20.0);
        addressBox.setPrefWidth(GUI_WIDTH);
        addressBox.setPrefHeight(300);
        Label addressTitle = new Label("Inserisci l'indirizzo del Server:");
        addressTitle.setFont(Font.font("Berlin Sans FB", 40));
        addressTitle.setAlignment(Pos.CENTER);
        Button defaultAddress = new Button("Default");
        defaultAddress.setFont(Font.font("Berlin Sans FB", 20));
        defaultAddress.setCursor(Cursor.HAND);
        defaultAddress.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        AnchorPane addressInputs = new AnchorPane();
        addressInputs.setMaxWidth(500);
        addressInputs.setPrefHeight(50);
        this.serverAddress.setMaxWidth(350.0);
        this.serverAddress.setMinWidth(350.0);
        AnchorPane.setTopAnchor(this.serverAddress, 55.0);
        AnchorPane.setBottomAnchor(this.serverAddress, 55.0);
        AnchorPane.setLeftAnchor(this.serverAddress, 20.0);
        AnchorPane.setTopAnchor(defaultAddress, 55.0);
        AnchorPane.setBottomAnchor(defaultAddress, 55.0);
        AnchorPane.setRightAnchor(defaultAddress, 20.0);
        addressInputs.getChildren().addAll(this.serverAddress, defaultAddress);
        addressBox.getChildren().addAll(addressTitle, addressInputs);
        AnchorPane.setTopAnchor(addressBox, 0.0);
        this.MAIN_PANE.getChildren().add(addressBox);
        VBox portBox = new VBox();
        portBox.setAlignment(Pos.CENTER);
        portBox.setFillWidth(true);
        portBox.setSpacing(20.0);
        portBox.setPrefWidth(GUI_WIDTH);
        portBox.setPrefHeight(300);
        Label portTitle = new Label("Inserisci la porta del Server");
        portTitle.setFont(Font.font("Berlin Sans FB", 40));
        portTitle.setAlignment(Pos.CENTER);
        Button defaultPort = new Button("Default");
        defaultPort.setFont(Font.font("Berlin Sans FB", 20));
        defaultPort.setCursor(Cursor.HAND);
        defaultPort.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        AnchorPane portInputs = new AnchorPane();
        portInputs.setMaxWidth(500);
        portInputs.setPrefHeight(50);
        this.serverPort.setMaxWidth(350.0);
        this.serverPort.setMinWidth(350.0);
        AnchorPane.setTopAnchor(this.serverPort, 55.0);
        AnchorPane.setBottomAnchor(this.serverPort, 55.0);
        AnchorPane.setLeftAnchor(this.serverPort, 20.0);
        AnchorPane.setTopAnchor(defaultPort, 55.0);
        AnchorPane.setBottomAnchor(defaultPort, 55.0);
        AnchorPane.setRightAnchor(defaultPort, 20.0);
        this.wrongPortLabel.setFont(Font.font("Berlin Sans FB", 15));
        this.wrongPortLabel.setTextFill(Color.DARKRED);
        portInputs.getChildren().addAll(this.serverPort, defaultPort);
        portBox.getChildren().addAll(portTitle, portInputs, this.wrongPortLabel);
        AnchorPane.setBottomAnchor(portBox, 100.0);
        this.MAIN_PANE.getChildren().add(portBox);
        this.continueBtn.setText("Continua");
        this.continueBtn.setFont(Font.font("Berlin Sans FB", 20));
        this.continueBtn.setCursor(Cursor.HAND);
        this.continueBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        this.continueBtn.addEventHandler(ActionEvent.ACTION, event -> {
            String address = this.serverAddress.getText();
            int port;
            try {
                port = Integer.parseInt(this.serverPort.getText());
                if (port < 1024 || port > 65535) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                this.wrongPortLabel.setText("Deve essere un numero! (1024 - 65535)");
                return;
            }
            this.serverPort.clear();
            this.serverAddress.clear();
            this.scenesManager.connect(address, port);
        });
        defaultAddress.setOnAction(event -> {
            this.serverAddress.setText(DEFAULT_SERVER_ADDRESS);
        });
        defaultPort.setOnAction(event -> {
            this.serverPort.setText(DEFAULT_SERVER_PORT + "");
        });
        AnchorPane.setBottomAnchor(this.continueBtn, 55.0);
        AnchorPane.setRightAnchor(this.continueBtn, 80.0);
        this.MAIN_PANE.setBackground(Background.fill(Paint.valueOf("#dedede")));
        this.MAIN_PANE.getChildren().add(this.continueBtn);
    }
}
