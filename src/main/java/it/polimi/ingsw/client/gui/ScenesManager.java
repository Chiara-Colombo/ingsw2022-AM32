package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.EnumScenes;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.servertoclient.BoardUpdate;
import it.polimi.ingsw.messages.servertoclient.SelectColorRequest;
import it.polimi.ingsw.messages.servertoclient.SelectIslandRequest;
import it.polimi.ingsw.messages.servertoclient.SelectPawnRequest;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.model.Wizards;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.EnumMap;

import static it.polimi.ingsw.utils.Utils.*;

public class ScenesManager {
    private final EnumMap<EnumScenes, Scene> scenes;
    private final MatchSettingScene matchSettingScene;
    private final GameSettingsScene gameSettingsScene;
    private final GameSetupScene gameSetupScene;
    private final GameScene gameScene;
    private final UsernameScene usernameScene;
    private final MoveStudentsManager moveStudentsManager;
    private final MoveMNManager moveMNManager;
    private final ChooseCloudManager chooseCloudManager;
    private final GUI gui;
    private boolean isExpertMode;

    /**
     * Manager of the different scenes
     * @param gui GUI
     */

    public ScenesManager(GUI gui) {
        this.scenes = new EnumMap<>(EnumScenes.class);
        this.gameSetupScene = new GameSetupScene(new Pane());
        this.usernameScene = new UsernameScene(new AnchorPane());
        this.matchSettingScene = new MatchSettingScene(new AnchorPane());
        this.gameSettingsScene = new GameSettingsScene(new AnchorPane());
        this.gameScene = new GameScene(new Pane());
        this.moveStudentsManager = new MoveStudentsManager(this);
        this.moveMNManager = new MoveMNManager(this);
        this.chooseCloudManager = new ChooseCloudManager(this);
        this.gui = gui;
        this.isExpertMode = false;
        this.initialize();
    }

    /**
     * Method that initialize the different scenes
     */

    private void initialize() {
        scenes.put(EnumScenes.MAIN_SCENE, new MainScene(new GridPane(), this));
        scenes.put(EnumScenes.GAME_SETTINGS_SCENE, this.gameSettingsScene);
        scenes.put(EnumScenes.CONNECTION_SCENE, new ServerScene(new AnchorPane(), this));
        scenes.put(EnumScenes.USERNAME_SCENE, this.usernameScene);
        scenes.put(EnumScenes.WAITING_SCENE, new WaitingScene(new AnchorPane()));
        scenes.put(EnumScenes.GAME_STARTING_SCENE, this.gameSetupScene);
        scenes.put(EnumScenes.GAME_SCENE, this.gameScene);
        scenes.put(EnumScenes.MATCH_SETTING_SCENE, this.matchSettingScene);
    }

    /**
     * Method that reset
     */

    public void resetScenes() {
        this.gameSettingsScene.reset();
    }

    /**
     * Method that connect the GUI Client to the Server
     * @param address IP address of the Server
     * @param port number of the port used for communicate to the server
     */

    void connect(String address, int port) {
        this.gui.startGame(address, port);
    }

    /**
     * Setter for the Controller
     */

    public void setController() {
        this.gameSettingsScene.setController(this.gui.getController());
        this.gameScene.setController(this.gui.getController());
        this.moveStudentsManager.setController(this.gui.getController());
        this.moveMNManager.setController(this.gui.getController());
        this.chooseCloudManager.setController(this.gui.getController());
        this.usernameScene.setController(this.gui.getController());
        this.matchSettingScene.setController(this.gui.getController());
    }

    /**
     * Method that shows to the user the scene where he has to put the server parameters
     */

    void playOnline() {
        this.gui.showServerScene();
    }

    /**
     * Getter for the Scene
     */

    public Scene getScene(EnumScenes scene) {
        return this.scenes.get(scene);
    }

    /**
     * Method that remove the event handler
     */

    void removeEventHandlers() {
        this.gameScene.removeHandlers();
    }

    /**
     * Method that shows the possibility to choose between a 2 players match or a 3 players match
     */

    public void showNumOfPlayersOptions() {
        this.gameSettingsScene.showNumOfPlayersOptions();
    }

    /**
     * Method that shows the possibility to choose the expert mode
     */
    public void showExpertModeOptions() {
        this.gameSettingsScene.showExpertModeOptions();
    }

    /**
     * Method that shows to a player that he has to choose a Wizard Card
     */

    public void showPlayerChoosingWizard() {
        this.gameSetupScene.showPlayerChoosingWizard();
    }

    /**
     * Method that shows the available Wizard Cards
     * @param validWizards available Cards that a player can choose
     * @param gui
     */

    public void showWizardCards(ArrayList<Wizards> validWizards, GUI gui) {
        this.gameSetupScene.showWizardCards(validWizards, gui);
    }

    /**
     * Method that removes the Character Handler from tha board of a player
     * when his action phase turn ends
     */
    public void actionTurnEnds() {
        if (this.isExpertMode)
            this.gameScene.removeCharactersHandlers();
    }

    /**
     * Method that shows the fact that a player has to choose a Color
     * @param selectColorRequest message that asks to choose a color
     */

    public void showColorRequest(SelectColorRequest selectColorRequest) {
        this.gameScene.addRequestColorHandler(selectColorRequest);
    }

    /**
     *  Method that shows to the other player that someone has used a specific Character Card
     * @param character Character card that has been used
      * @param username nickname of the player who used a Character Card
     */

    public void showCharacterUsed(Characters character, String username) {
        String message = "Ha usato il personaggio: " + CHARACTERS_NAME_MAP.get(character);
        if (this.gui.getController().getUsername().equals(username))
            message = "Hai usato il personaggio: " + CHARACTERS_NAME_MAP.get(character);
        this.gameScene.showCharacterUsedMessage(message);
    }

    /**
     * Method that shows the request tho choose an Island
     * @param selectIslandRequest message that ask to choose an island
     */
    public void showIslandRequest(SelectIslandRequest selectIslandRequest) {
        this.gameScene.addRequestIslandHandler(selectIslandRequest);
    }

    /**
     * Method that shows a request to select a pawn
     * @param selectPawnRequest message that ask to select a pawn
     */

    public void showPawnRequest(SelectPawnRequest selectPawnRequest) {
        this.gameScene.addRequestPawnHandler(selectPawnRequest);
    }

    /**
     * Method that shows which player is playing
     * @param nickname Name of the user
     * @param playerTurn if true the user is the one who's playing
     *                   if false is another user that is playing
     */

    public void showTurn(String nickname, boolean playerTurn) {
        if (playerTurn) {
            this.gameScene.showTurnMessage("È il tuo turno");
            this.gameScene.showGamePhaseMessage("");
            if (this.isExpertMode)
                this.gameScene.addCharactersHandlers();
        } else {
            this.gameScene.showGamePhaseMessage(nickname + " è nella fase azione");
            this.gameScene.showTurnMessage("È il turno di " + nickname);
        }
    }

    /**
     * Message that shows the updates that happen on the board
     * @param boardUpdate message that sends all the board update
     */

    public void showBoardUpdate(BoardUpdate boardUpdate) {
        this.isExpertMode = boardUpdate.getGameUpdate().isExpertMode();
        this.gameScene.updateBoard(boardUpdate.getBoardUpdateContent(), boardUpdate.getGameUpdate().isExpertMode());
        this.gameScene.updatePlayers(boardUpdate.getPlayersUpdate(), boardUpdate.getGameUpdate().isExpertMode());
        this.gameScene.updateGame(boardUpdate.getGameUpdate());
    }

    /**
     * Method that shows to the player that he has to choose a Cloud
     * @param validClouds Clouds with pawns on them
     */

    public void showCloudRequest(ArrayList<Integer> validClouds) {
        this.gameScene.showGamePhaseMessage("Scegli una nuvola da cui prendere gli studenti");
        this.chooseCloudManager.setValidClouds(validClouds);
        this.gameScene.addChooseCloudHandler(this.chooseCloudManager);
    }

    /**
     * Method that shows to the player that he has to move Mother Nature
     * @param movements is the maximum number of movements that Mother Nature can do
     *                  (according to the Assistant Card Chosen)
     * @param validIndexes indexes of island where Mother Nature can be put
     */

    public void showMoveMNRequest(int movements, ArrayList<Integer> validIndexes) {
        this.gameScene.showGamePhaseMessage("Sposta madre natura. Puoi spostarla di un massimo di " + movements + " isole in senso orario");
        this.moveMNManager.setMaxMovements(movements);
        this.moveMNManager.setValidIslands(validIndexes);
        this.gameScene.addMoveMNHandlers(this.moveMNManager);
    }

    /**
     * Method that asks the player to move some pawns
     * @param numOfPawns number of pawns that the player has to move
     */

    public void showMovePawnRequest(int numOfPawns) {
        this.gameScene.showGamePhaseMessage("Sposta " + numOfPawns + " studenti dall'ingresso");
        this.gameScene.addMoveEntranceStudentsHandlers(this.moveStudentsManager);
    }

    /**
     * Method that shows that is the player turn in the Planning Phase and he has to choose an Assistant Card
     * @param nickname the name of the user
     * @param playerTurn boolean that if is false is another player that is choosing an Assistant Card
     *                                 if is true the player has to choose an Assistant Card
     */

    public void showPlanningPhaseTurn(String nickname, boolean playerTurn) {
        if (playerTurn) {
            this.gameScene.showGamePhaseMessage("Scegli la carta assistente");
            this.gameScene.showTurnMessage("È il tuo turno");
        } else {
            this.gameScene.showGamePhaseMessage(nickname + " sta scegliendo la carta assistente");
            this.gameScene.showTurnMessage("È il turno di " + nickname);
        }
    }

    /**
     * Method that shows the Board of the game
     */

    public void showGameBoard() {
        this.gameScene.showBoard();
    }

    /**
     * Method that shows a request that allows to choose an Assistant Card
     * @param availableCards Assistant Cards that can be used
     */

    public void showAssistantCardRequest(ArrayList<AssistantCard> availableCards) {
        this.gameScene.showAssistantCardsPane(availableCards);
    }
}
