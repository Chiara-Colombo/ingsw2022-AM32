package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.servertoclient.PlayerChoosingWizard;
import it.polimi.ingsw.messages.servertoclient.WizardCardRequest;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;

public class StartState implements  State {

    Game game;
    ArrayList<ClientHandler> players;

    public StartState(Game game, ArrayList<ClientHandler> players) {
        this.game = game;
        this.players = players;
    }

    @Override
    public synchronized void StartTurn() {
     /**   for(ClientHandler player : players) {
            if (player.getNickname() == game.getCurrentPlayer().getNickname()) {
                WizardCardRequest wizardCardRequest = new WizardCardRequest(player.getNickname());
                player.sendObjectMessage(wizardCardRequest);
            }
        }
      */
    }

    @Override
    public void chooseCloud() {

    }

    @Override
    public void drawAssistantCard() {

    }

    @Override
    public void fillClouds() {

    }

    @Override
    public void moveMN() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public State changeState() {
        return null;
    }
}