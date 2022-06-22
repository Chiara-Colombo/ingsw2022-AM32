package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.*;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.model.Wizards;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GUI extends Application implements View{

    private static Stage stage;
    private ClientController controller;
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

    public ClientController getController() {
        return this.controller;
    }

    public static void exit() {
        stage.close();
    }

    public void startGame(String address, int port) {
        try {
            this.controller = new ClientController(port, address, this, true);
        } catch (IOException e) {
            this.showErrorMessage("Impossibile connettersi al server");
            stage.setScene(this.scenesManager.getScene(EnumScenes.MAIN_SCENE));
            return;
        }
        new Thread(this.controller).start();
        this.scenesManager.setController();
    }

    private void showDialogBox(String title, String message, EventHandler<ActionEvent> callback) {
        Platform.runLater(() -> {
            final double WIDTH = 500, HEIGHT = 220;
            Stage dialogStage = new Stage();
            Pane dialogPane = new Pane();
            Scene dialogScene = new Scene(dialogPane, WIDTH, HEIGHT);
            dialogStage.setTitle(title);
            Label dialogText = new Label(message);
            dialogText.setFont(Font.font("Berlin Sans FB", 20));
            dialogText.setAlignment(Pos.CENTER);
            dialogText.setTextAlignment(TextAlignment.CENTER);
            dialogText.setPrefWidth(WIDTH);
            dialogText.setLayoutY(HEIGHT / 2.0 - 35);
            Button dialogBtn = new Button("Chiudi");
            dialogBtn.setLayoutX(WIDTH / 2.0 - 40);
            dialogBtn.setLayoutY(HEIGHT - 80);
            dialogBtn.setMinHeight(50.0);
            dialogBtn.setMaxHeight(50.0);
            dialogBtn.setMinWidth(80.0);
            dialogBtn.setFont(Font.font("Berlin Sans FB", 18));
            dialogBtn.setCursor(Cursor.HAND);
            dialogBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
            dialogPane.setBackground(Background.fill(Paint.valueOf("#dedede")));
            dialogPane.getChildren().addAll(dialogBtn, dialogText);
            dialogStage.setScene(dialogScene);
            dialogStage.show();
            dialogBtn.setOnAction(actionEvent -> {
                callback.handle(actionEvent);
                dialogStage.close();
            });
        });
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
    public void showCharacterCardUsed(Characters character, String username) {
        this.scenesManager.showCharacterUsed(character, username);
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
    public void showConnectionLost() {
        this.showDialogBox("Errore!", "Connessione con il server persa!", (event) -> {
            stage.setScene(this.scenesManager.getScene(EnumScenes.MAIN_SCENE));
        });
    }

    @Override
    public void showErrorMessage(String message) {
        this.showDialogBox("Errore", message, (event) -> {});
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
    public void showNotEnoughCoins(){
        this.showDialogBox("Attenzione!", "Non hai abbastanza monete per usare questo personaggio!", (event) -> {});
    }

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
    public void showSchoolBoardUpdate() {
    }

    @Override
    public void showSelectColorRequest(SelectColorRequest selectColorRequest) {
        this.scenesManager.showColorRequest(selectColorRequest);
    }

    @Override
    public void showSelectIslandRequest(SelectIslandRequest selectIslandRequest) {
        this.scenesManager.showIslandRequest(selectIslandRequest);
    }

    @Override
    public void showSelectPawnRequest(SelectPawnRequest selectPawnRequest) {
        this.scenesManager.showPawnRequest(selectPawnRequest);
    }

    @Override
    public void showWaitingView() {
        stage.setScene(this.scenesManager.getScene(EnumScenes.WAITING_SCENE));
    }

    @Override
    public void showWinnerMessage(String winner, String reason) {
        this.showDialogBox("Vittoria!", winner + " vince la partita: " + reason + "!", (event) -> {
            this.scenesManager.resetScenes();
            stage.setScene(this.scenesManager.getScene(EnumScenes.MAIN_SCENE));
        });
        this.controller.close();
    }

    @Override
    public void showWizardCardRequest(ArrayList<Wizards> validWizards) {
        this.scenesManager.showWizardCards(validWizards, this);
    }

    @Override
    public void showYourActionPhaseTurnEnds() {
        this.scenesManager.actionTurnEnds();
    }

    @Override
    public void showYourPlanningPhaseTurnEnds() {
        this.scenesManager.showGameBoard();
    }
}
