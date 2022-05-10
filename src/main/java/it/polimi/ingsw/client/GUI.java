package it.polimi.ingsw.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EnumMap;

public class GUI extends Application implements View{

    private Stage stage;
    private EnumMap<EnumScenes, Scene> scenes;

    public GUI() {
        this.scenes = new EnumMap<>(EnumScenes.class);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        try {
            this.scenes.put(EnumScenes.MAIN_SCENE, FXMLLoader.load(getClass().getResource("/fxml/MainScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(this.scenes.get(EnumScenes.MAIN_SCENE));
        primaryStage.setTitle("Eriantys");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }

    @Override
    public void showRequestNumOfPlayers() {

    }

    @Override
    public void showRequestExpertMode() {

    }

    @Override
    public void showRequestUsername() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void showWaitingView() {

    }

    @Override
    public void showGameStartingView() {

    }

    @Override
    public void showUsernameCorrectlyAssigned() {

    }
}
