package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.servertoclient.WizardCardRequest;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StartState implements  State {

    private final Game game;
    private final Map<String, ClientHandler> players;

    public StartState(Game game, Map<String, ClientHandler> players) {
        this.game = game;
        this.players = players;
    }

    @Override
    public synchronized void chooseWizard() {
        ArrayList<Wizards> availableWizards = new ArrayList<>(List.of(Wizards.values()));
        this.game.getPlayers().forEach(player -> {
            player.getWizard().ifPresent(availableWizards::remove);
        });
        WizardCardRequest wizardCardRequest = new WizardCardRequest(availableWizards);
        this.players.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(wizardCardRequest);
    }

    @Override
    public boolean moveStudent(boolean error) {
        return false;
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
        return new PlanningState(this.game, this.players);
    }
}