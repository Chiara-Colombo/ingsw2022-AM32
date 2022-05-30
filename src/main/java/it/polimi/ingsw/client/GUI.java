package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.*;
import it.polimi.ingsw.messages.servertoclient.BoardUpdate;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Wizards;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

import static it.polimi.ingsw.utils.Utils.*;

public class GUI extends Application implements View{

    private static Stage stage;
    private static ClientController controller;
    private static GamesSettingsManager gamesSettingsManager;
    private static GameSetupManager gameSetupManager;
    private static GameScene gameScene;
    private static EnumMap<EnumScenes, Scene> scenes;
    private static MoveStudentsManager moveStudentsManager;
    private static MoveMNManager moveMNManager;
    private static ChooseCloudManager chooseCloudManager;

    @Override
    public void start(Stage primaryStage) {
        scenes = new EnumMap<>(EnumScenes.class);
        controller = new ClientController(DEFAULT_SERVER_PORT, DEFAULT_SERVER_ADDRESS, this, true);
        stage = primaryStage;
        GameSettingsScene gameSettingsScene = GameSettingsScene.getInstance();
        GameSetupScene gameSetupScene = GameSetupScene.getInstance();
        moveStudentsManager = new MoveStudentsManager(controller, this);
        moveMNManager = new MoveMNManager(controller, this);
        chooseCloudManager = new ChooseCloudManager(controller, this);
        gameScene = new GameScene(new Pane());
        gamesSettingsManager = new GamesSettingsManager(gameSettingsScene);
        gameSetupManager = new GameSetupManager(gameSetupScene);
        scenes.put(EnumScenes.MAIN_SCENE, MainScene.getInstance());
        scenes.put(EnumScenes.GAME_SETTINGS_SCENE, gameSettingsScene);
        scenes.put(EnumScenes.USERNAME_SCENE, UsernameScene.getInstance());
        scenes.put(EnumScenes.WAITING_SCENE, WaitingScene.getInstance());
        scenes.put(EnumScenes.GAME_STARTING_SCENE, gameSetupScene);
        scenes.put(EnumScenes.GAME_SCENE, gameScene);
        primaryStage.setScene(scenes.get(EnumScenes.MAIN_SCENE));
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

    public static void startGame() {
        try {
            controller.connect();
        } catch (IOException e) {
            System.out.println("Can't start controller");
            return;
        }
        new Thread(controller).start();
    }

    public void removeEventHandlers() {
        gameScene.removeHandlers();
    }

    @Override
    public void showRequestNumOfPlayers() {
        stage.setScene(scenes.get(EnumScenes.GAME_SETTINGS_SCENE));
        gamesSettingsManager.showNumOfPlayersOptions();
    }

    @Override
    public void showRequestExpertMode() {
        gamesSettingsManager.showExpertModeOptions();
    }

    @Override
    public void showRequestUsername() {
        stage.setScene(scenes.get(EnumScenes.USERNAME_SCENE));
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showWaitingView() {
        stage.setScene(scenes.get(EnumScenes.WAITING_SCENE));
    }

    @Override
    public void showGameStartingView() {
        stage.setScene(scenes.get(EnumScenes.GAME_STARTING_SCENE));
        gameSetupManager.showPlayerChoosingWizard();
    }

    @Override
    public void showWizardCardRequest(ArrayList<Wizards> validWizards) {
        gameSetupManager.showWizardCards(validWizards, this);
    }

    @Override
    public void showPlayerChoosingWizard() {
        gameSetupManager.showPlayerChoosingWizard();
    }

    @Override
    public void showActionPhaseTurn(String nickname) {
        if (controller.getUsername().equals(nickname)) {
            gameScene.showTurnMessage("È il tuo turno");
            gameScene.showGamePhaseMessage("");
        } else {
            gameScene.showGamePhaseMessage(nickname + " è nella fase azione");
            gameScene.showTurnMessage("È il turno di " + nickname);
        }
    }

    @Override
    public void showAssistantCardChosen() {}

    @Override
    public void showAssistantsCardUpdate() {}

    @Override
    public void showBoardUpdate(BoardUpdate boardUpdate) {
        stage.setScene(scenes.get(EnumScenes.GAME_SCENE));
        gameScene.updateBoard(boardUpdate.getBoardUpdateContent());
        gameScene.showPlayers(boardUpdate.getPlayersUpdate());
        gameScene.showGameUpdate(boardUpdate.getGameUpdate());
    }

    @Override
    public void showChosenWizardCard() {}

    @Override
    public void showCloudRequest(ArrayList<Integer> validClouds) {
        gameScene.showGamePhaseMessage("Scegli una nuvola da cui prendere gli studenti");
        chooseCloudManager.setValidClouds(validClouds);
        gameScene.addChooseCloudHandler(chooseCloudManager);
    }

    @Override
    public void showCoinsUpdate() {}

    @Override
    public void showMNPositionUpdate() {}

    @Override
    public void showMoveMNRequest(int movements) {
        gameScene.showGamePhaseMessage("Sposta madre natura. Puoi spostarla di un massimo di " + movements + " isole in senso orario");
        moveMNManager.setMaxMovements(movements);
        gameScene.addMoveMNHandlers(moveMNManager);
    }

    @Override
    public void showMovePawnRequest() {
        gameScene.showGamePhaseMessage("Sposta tre studenti dall'ingresso");
        gameScene.addMoveEntranceStudentsHandlers(moveStudentsManager);
    }

    @Override
    public void showPlanningPhaseTurn(String nickname) {
        if (controller.getUsername().equals(nickname)) {
            gameScene.showGamePhaseMessage("Scegli la carta assistente");
            gameScene.showTurnMessage("È il tuo turno");
        } else {
            gameScene.showGamePhaseMessage(nickname + " sta scegliendo la carta assistente");
            gameScene.showTurnMessage("È il turno di " + nickname);
        }
    }

    @Override
    public void showSchoolBoardUpdate() {}

    @Override
    public void showSelectPawnRequest() {}

    @Override
    public void showYourActionPhaseTurnEnds() {}

    @Override
    public void showYourPlanningPhaseTurnEnds() {
        gameScene.showBoard();
    }

    @Override
    public void showAssistantCardRequest(ArrayList<AssistantCard> availableCards) {
        gameScene.showAssistantCardsPane(availableCards);
    }

    @Override
    public void showErrorMotherNaturePosition(){}

    @Override
    public void showNotEnoughCoins(){}

    @Override
    public void showErrorOnPawnPosition() {

    }
}
