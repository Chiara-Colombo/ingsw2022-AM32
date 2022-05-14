package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.SetUsername;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import static it.polimi.ingsw.utils.Utils.*;

public class UsernameScene extends Scene {
    private UsernameScene(Parent root) {
        super(root, GUI_WIDTH, GUI_HEIGHT);
    }

    public static UsernameScene getInstance(){
        AnchorPane root = new AnchorPane();
        initialize(root);
        return new UsernameScene(root);
    }

    private static void initialize(AnchorPane anchorPane) {
        Button continueBtn = new Button("Continua");
        continueBtn.setFont(Font.font("Berlin Sans FB", 20));
        continueBtn.setCursor(Cursor.HAND);
        continueBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        AnchorPane.setBottomAnchor(continueBtn, 55.0);
        AnchorPane.setRightAnchor(continueBtn, 80.0);
        anchorPane.setBackground(Background.fill(Paint.valueOf("#dedede")));
        VBox usernamePane = new VBox();
        usernamePane.setAlignment(Pos.CENTER);
        usernamePane.setFillWidth(true);
        usernamePane.setSpacing(100.0);
        usernamePane.setPrefWidth(GUI_WIDTH);
        usernamePane.setPrefHeight(300);
        Label title = new Label("Inserisci il tuo username");
        title.setFont(Font.font("Berlin Sans FB", 40));
        title.setAlignment(Pos.CENTER);
        TextField usernameInput = new TextField("Username");
        usernameInput.setMaxWidth(GUI_WIDTH / 3.0);
        usernameInput.setFont(Font.font("Berlin Sans FB", 20));
        usernameInput.setPadding(new Insets(10, 15, 10, 15));
        usernamePane.getChildren().addAll(title, usernameInput);
        AnchorPane.setTopAnchor(usernamePane, 80.0);
        anchorPane.getChildren().addAll(usernamePane, continueBtn);
        continueBtn.addEventHandler(ActionEvent.ACTION, event -> {
            String username = usernameInput.getText();
            SetUsername setUsername = new SetUsername(username);
            GUI.getController().sendObjectMessage(setUsername);
        });
    }
}
