package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

import static it.polimi.ingsw.utils.Utils.*;

public class GUI extends Application implements View{

    private static Stage stage;
    private static ClientController controller;
    private static GamesSettingsManager gamesSettingsManager;
    private static EnumMap<EnumScenes, Scene> scenes;

    @Override
    public void start(Stage primaryStage) {
        scenes = new EnumMap<>(EnumScenes.class);
        controller = new ClientController(DEFAULT_SERVER_PORT, DEFAULT_SERVER_ADDRESS, this);
        stage = primaryStage;
        GameSettingsScene gameSettingsScene = GameSettingsScene.getInstance();
        gamesSettingsManager = new GamesSettingsManager(gameSettingsScene);
        scenes.put(EnumScenes.MAIN_SCENE, MainScene.getInstance());
        scenes.put(EnumScenes.GAME_SETTINGS_SCENE, gameSettingsScene);
        scenes.put(EnumScenes.USERNAME_SCENE, UsernameScene.getInstance());
        scenes.put(EnumScenes.WAITING_SCENE, WaitingScene.getInstance());
        scenes.put(EnumScenes.GAME_STARTING_SCENE, GameSetupScene.getInstance());
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

    }

    @Override
    public void showWaitingView() {
        stage.setScene(scenes.get(EnumScenes.WAITING_SCENE));
    }

    @Override
    public void showGameStartingView() {
        stage.setScene(scenes.get(EnumScenes.GAME_STARTING_SCENE));
    }

    @Override
    public void showWizardCardRequest(String nickname) {

    }

    @Override
    public void showPlayerChoosingWizard(String nicknae) {

    }

    @Override
    public void showActionPhaseTurn(String nickname) {

    }

    @Override
    public void showAssistantCardChosen() {

    }

    @Override
    public void showAssistantsCardUpdate() {

    }

    @Override
    public void showBoardUpdate() {

    }

    @Override
    public void showChosenWizardCard() {

    }

    @Override
    public void showCloudRequest() {

    }

    @Override
    public void showCoinsUpdate() {

    }

    @Override
    public void showMNPositionUpdate() {

    }

    @Override
    public void showMoveMNRequest(int movements) {

    }

    @Override
    public void showMovePawnRequest() {

    }

    @Override
    public void showPlanningPhaseTurn(String nickname) {

    }

    @Override
    public void showPlayerChoosingWizard() {

    }

    @Override
    public void showSchoolBoardUpdate() {

    }

    @Override
    public void showSelectPawnRequest() {

    }

    @Override
    public void showWizardCardRequest() {

    }

    @Override
    public void showYourActionPhaseTurnEnds() {

    }

    @Override
    public void showYourPlanningPhaseTurnEnds() {

    }

    @Override
    public void showAssistantCardRequest() {

    }

    @Override
    public void showErrorMotherNaturePosition() {

    }

    @Override
    public void showNotEnoughCoins() {

    }


}
