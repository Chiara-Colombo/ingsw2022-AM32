package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.EnumScenes;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.servertoclient.BoardUpdate;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Wizards;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.EnumMap;

public class ScenesManager {
    private final EnumMap<EnumScenes, Scene> scenes;
    private final GameSettingsScene gameSettingsScene;
    private final GameSetupScene gameSetupScene;
    private final GameScene gameScene;
    private final UsernameScene usernameScene;
    private final MoveStudentsManager moveStudentsManager;
    private final MoveMNManager moveMNManager;
    private final ChooseCloudManager chooseCloudManager;
    private final GUI gui;

    public ScenesManager(GUI gui) {
        this.scenes = new EnumMap<>(EnumScenes.class);
        this.gameSetupScene = new GameSetupScene(new Pane());
        this.usernameScene = new UsernameScene(new AnchorPane());
        this.gameSettingsScene = new GameSettingsScene(new AnchorPane());
        this.gameScene = new GameScene(new Pane());
        this.moveStudentsManager = new MoveStudentsManager(this);
        this.moveMNManager = new MoveMNManager(this);
        this.chooseCloudManager = new ChooseCloudManager(this);
        this.gui = gui;
        this.initialize();
    }

    private void initialize() {
        scenes.put(EnumScenes.MAIN_SCENE, new MainScene(new GridPane(), this));
        scenes.put(EnumScenes.GAME_SETTINGS_SCENE, this.gameSettingsScene);
        scenes.put(EnumScenes.CONNECTION_SCENE, new ServerScene(new AnchorPane(), this));
        scenes.put(EnumScenes.USERNAME_SCENE, this.usernameScene);
        scenes.put(EnumScenes.WAITING_SCENE, new WaitingScene(new AnchorPane()));
        scenes.put(EnumScenes.GAME_STARTING_SCENE, this.gameSetupScene);
        scenes.put(EnumScenes.GAME_SCENE, this.gameScene);
    }

    void connect(String address, int port) {
        this.gui.startGame(address, port);
    }

    public void setController() {
        this.gameSettingsScene.setController(this.gui.getController());
        this.gameScene.setController(this.gui.getController());
        this.moveStudentsManager.setController(this.gui.getController());
        this.moveMNManager.setController(this.gui.getController());
        this.chooseCloudManager.setController(this.gui.getController());
        this.usernameScene.setController(this.gui.getController());
    }

    void playOnline() {
        this.gui.showServerScene();
    }

    public Scene getScene(EnumScenes scene) {
        return this.scenes.get(scene);
    }

    void removeEventHandlers() {
        this.gameScene.removeHandlers();
    }

    public void showNumOfPlayersOptions() {
        this.gameSettingsScene.showNumOfPlayersOptions();
    }

    public void showExpertModeOptions() {
        this.gameSettingsScene.showExpertModeOptions();
    }

    public void showPlayerChoosingWizard() {
        this.gameSetupScene.showPlayerChoosingWizard();
    }

    public void showWizardCards(ArrayList<Wizards> validWizards, GUI gui) {
        this.gameSetupScene.showWizardCards(validWizards, gui);
    }

    public void showTurn(String nickname, boolean playerTurn) {
        if (playerTurn) {
            this.gameScene.showTurnMessage("È il tuo turno");
            this.gameScene.showGamePhaseMessage("");
        } else {
            this.gameScene.showGamePhaseMessage(nickname + " è nella fase azione");
            this.gameScene.showTurnMessage("È il turno di " + nickname);
        }
    }

    public void showBoardUpdate(BoardUpdate boardUpdate) {
        this.gameScene.updateBoard(boardUpdate.getBoardUpdateContent(), boardUpdate.getGameUpdate().isExpertMode());
        this.gameScene.updatePlayers(boardUpdate.getPlayersUpdate(), boardUpdate.getGameUpdate().isExpertMode());
        this.gameScene.updateGame(boardUpdate.getGameUpdate());
    }

    public void showCloudRequest(ArrayList<Integer> validClouds) {
        this.gameScene.showGamePhaseMessage("Scegli una nuvola da cui prendere gli studenti");
        this.chooseCloudManager.setValidClouds(validClouds);
        this.gameScene.addChooseCloudHandler(this.chooseCloudManager);
    }

    public void showMoveMNRequest(int movements, ArrayList<Integer> validIndexes) {
        this.gameScene.showGamePhaseMessage("Sposta madre natura. Puoi spostarla di un massimo di " + movements + " isole in senso orario");
        this.moveMNManager.setMaxMovements(movements);
        this.moveMNManager.setValidIslands(validIndexes);
        this.gameScene.addMoveMNHandlers(this.moveMNManager);
    }

    public void showMovePawnRequest(int numOfPawns) {
        this.gameScene.showGamePhaseMessage("Sposta " + numOfPawns + " studenti dall'ingresso");
        this.gameScene.addMoveEntranceStudentsHandlers(this.moveStudentsManager);
    }

    public void showPlanningPhaseTurn(String nickname, boolean playerTurn) {
        if (playerTurn) {
            this.gameScene.showGamePhaseMessage("Scegli la carta assistente");
            this.gameScene.showTurnMessage("È il tuo turno");
        } else {
            this.gameScene.showGamePhaseMessage(nickname + " sta scegliendo la carta assistente");
            this.gameScene.showTurnMessage("È il turno di " + nickname);
        }
    }

    public void showGameBoard() {
        this.gameScene.showBoard();
    }

    public void showAssistantCardRequest(ArrayList<AssistantCard> availableCards) {
        this.gameScene.showAssistantCardsPane(availableCards);
    }
}
