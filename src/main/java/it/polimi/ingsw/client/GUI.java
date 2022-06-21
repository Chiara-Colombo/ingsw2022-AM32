package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.*;
import it.polimi.ingsw.messages.servertoclient.AssistantsCardUpdate;
import it.polimi.ingsw.messages.servertoclient.BoardUpdate;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Wizards;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

import static it.polimi.ingsw.utils.Utils.*;

public class GUI extends Application implements View{

    private static Stage stage;
    private static ClientController controller;
    private ScenesManager scenesManager;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        this.scenesManager = new ScenesManager(this);
        primaryStage.setScene(this.scenesManager.getScene(EnumScenes.MAIN_SCENE));
        primaryStage.setTitle("Eriantys");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }

    public static ClientController getController() {
        return controller;
    }

    public static void exit() {
        stage.close();
    }

    public void startGame(String address, int port) {
        controller = new ClientController(port, address, this, true);
        try {
            controller.connect();
        } catch (RuntimeException e) {
            this.showErrorMessage("Impossibile connettersi al server");
            stage.setScene(this.scenesManager.getScene(EnumScenes.MAIN_SCENE));
            return;
        }
        new Thread(controller).start();
    }

    public void showServerScene() {
        stage.setScene(this.scenesManager.getScene(EnumScenes.CONNECTION_SCENE));
    }

    @Override
    public void showActionPhaseTurn(String nickname) {
        this.scenesManager.showTurn(nickname, controller.getUsername().equals(nickname));
    }

    @Override
    public void showAssistantCardChosen() {}

    @Override
    public void showAssistantCardRequest(ArrayList<AssistantCard> availableCards) {
        this.scenesManager.showAssistantCardRequest(availableCards);
    }

    @Override
    public void showAssistantsCardUpdate(AssistantsCardUpdate assistantsCardUpdate) {}

    @Override
    public void showBoardUpdate(BoardUpdate boardUpdate) {
        stage.setScene(this.scenesManager.getScene(EnumScenes.GAME_SCENE));
        this.scenesManager.showBoardUpdate(boardUpdate);
    }

    @Override
    public void showChosenWizardCard() {}

    @Override
    public void showCloudRequest(ArrayList<Integer> validClouds) {
        this.scenesManager.showCloudRequest(validClouds);
    }

    @Override
    public void showCoinsUpdate() {}

    @Override
    public void showErrorMessage(String message) {
        Platform.runLater(() -> {
            final double WIDTH = 500, HEIGHT = 220;
            Stage errorStage = new Stage();
            Pane errorPane = new Pane();
            Scene errorScene = new Scene(errorPane, WIDTH, HEIGHT);
            errorStage.setTitle("Errore!");
            Label errorText = new Label(message);
            errorText.setFont(Font.font("Berlin Sans FB", 20));
            errorText.setAlignment(Pos.CENTER);
            errorText.setTextAlignment(TextAlignment.CENTER);
            errorText.setPrefWidth(WIDTH);
            errorText.setLayoutY(HEIGHT / 2.0 - 35);
            Button errorBtn = new Button("Chiudi");
            errorBtn.setLayoutX(WIDTH / 2.0 - 40);
            errorBtn.setLayoutY(HEIGHT - 80);
            errorBtn.setMinHeight(50.0);
            errorBtn.setMaxHeight(50.0);
            errorBtn.setMinWidth(80.0);
            errorBtn.setFont(Font.font("Berlin Sans FB", 18));
            errorBtn.setCursor(Cursor.HAND);
            errorBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
            errorPane.setBackground(Background.fill(Paint.valueOf("#dedede")));
            errorPane.getChildren().addAll(errorBtn, errorText);
            errorStage.setScene(errorScene);
            errorStage.show();
            errorBtn.setOnAction((event) -> {
                errorStage.close();
            });
        });
    }

    @Override
    public void showErrorOnPawnPosition() {

    }

    @Override
    public void showGameStartingView() {
        stage.setScene(this.scenesManager.getScene(EnumScenes.GAME_STARTING_SCENE));
        this.scenesManager.showPlayerChoosingWizard();
    }

    @Override
    public void showMNPositionUpdate() {}

    @Override
    public void showMoveMNRequest(int movements, ArrayList<Integer> validIndexes) {
        this.scenesManager.showMoveMNRequest(movements, validIndexes);
    }

    @Override
    public void showMovePawnRequest(int numOfPawns) {
        this.scenesManager.showMovePawnRequest(numOfPawns);
    }

    @Override
    public void showNotEnoughCoins(){}

    @Override
    public void showPlanningPhaseTurn(String nickname) {
        this.scenesManager.showPlanningPhaseTurn(nickname, controller.getUsername().equals(nickname));
    }

    @Override
    public void showPlayerChoosingWizard() {
        this.scenesManager.showPlayerChoosingWizard();
    }

    @Override
    public void showRequestExpertMode() {
        this.scenesManager.showExpertModeOptions();
    }

    @Override
    public void showRequestNumOfPlayers() {
        stage.setScene(this.scenesManager.getScene(EnumScenes.GAME_SETTINGS_SCENE));
        this.scenesManager.showNumOfPlayersOptions();
    }

    @Override
    public void showRequestUsername() {
        stage.setScene(this.scenesManager.getScene(EnumScenes.USERNAME_SCENE));
    }

    @Override
    public void showSchoolBoardUpdate() {}

    @Override
    public void showSelectPawnRequest() {}

    @Override
    public void showWaitingView() {
        stage.setScene(this.scenesManager.getScene(EnumScenes.WAITING_SCENE));
    }

    @Override
    public void showWinnerMessage(String winner, String reason) {
        Platform.runLater(() -> {
            final double WIDTH = 500, HEIGHT = 220;
            Stage winnerStage = new Stage();
            Pane winnerPane = new Pane();
            Scene winnerScene = new Scene(winnerPane, WIDTH, HEIGHT);
            winnerStage.setTitle("Vittoria!");
            Label winnerText = new Label(winner + " vince la partita: " + reason + "!");
            Button winnerBtn = new Button("Chiudi");
            winnerText.setFont(Font.font("Berlin Sans FB", 20));
            winnerText.setAlignment(Pos.CENTER);
            winnerText.setTextAlignment(TextAlignment.CENTER);
            winnerText.setPrefWidth(WIDTH);
            winnerText.setLayoutY(HEIGHT / 2.0 - 35);
            winnerBtn.setLayoutX(WIDTH / 2.0 - 40);
            winnerBtn.setLayoutY(HEIGHT - 80);
            winnerBtn.setMaxHeight(50.0);
            winnerBtn.setMinHeight(50.0);
            winnerBtn.setMinWidth(80.0);
            winnerBtn.setFont(Font.font("Berlin Sans FB", 18));
            winnerBtn.setCursor(Cursor.HAND);
            winnerBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
            winnerPane.getChildren().addAll(winnerBtn, winnerText);
            winnerPane.setBackground(Background.fill(Paint.valueOf("#dedede")));
            winnerStage.setScene(winnerScene);
            winnerStage.show();
            winnerBtn.setOnAction((event) -> {
                stage.setScene(this.scenesManager.getScene(EnumScenes.MAIN_SCENE));
                winnerStage.close();
            });
        });
    }

    @Override
    public void showWizardCardRequest(ArrayList<Wizards> validWizards) {
        this.scenesManager.showWizardCards(validWizards, this);
    }

    @Override
    public void showYourActionPhaseTurnEnds() {}

    @Override
    public void showYourPlanningPhaseTurnEnds() {
        this.scenesManager.showGameBoard();
    }
}
