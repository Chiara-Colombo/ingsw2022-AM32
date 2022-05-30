package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.servertoclient.CloudRequest;
import it.polimi.ingsw.messages.servertoclient.MoveMNRequest;
import it.polimi.ingsw.messages.servertoclient.MovePawnRequest;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.Map;

public class ActionState implements State{
    private int studentsMoved;
    private final Game game;
    private final Map<String, ClientHandler> players;

    public ActionState(Game game, Map<String, ClientHandler> players) {
        this.game = game;
        this.players = players;
        this.studentsMoved = 0;
    }

    @Override
    public void chooseWizard() {

    }

    @Override
    public boolean moveStudent() {
        if ((this.game.getNumOfPlayers() == 2 && this.studentsMoved >= 3) ||
                (this.game.getNumOfPlayers() == 3 && this.studentsMoved >= 4)) {
            this.studentsMoved = 0;
            return false;
        }
        MovePawnRequest request = new MovePawnRequest();
        this.players.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(request);
        this.studentsMoved++;
        return true;
    }

    @Override
    public void chooseCloud() {
        ArrayList<Integer> validClouds = new ArrayList<>();
        for (int i = 0; i < this.game.getGameBoard().getClouds().size(); i++) {
            if (!this.game.getGameBoard().getClouds().get(i).isEmpty()) {
                validClouds.add(i);
            }
        }
        CloudRequest request = new CloudRequest(validClouds);
        this.players.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(request);
    }

    @Override
    public void drawAssistantCard() {

    }

    @Override
    public void fillClouds() {

    }

    @Override
    public void moveMN() {
        this.game.getCurrentPlayer().getWizard().flatMap(wizard -> this.game.getCardsManager().getCurrentCardForPlayer(wizard)).ifPresent(card -> {
            MoveMNRequest request = new MoveMNRequest(card.getMotherNatureMovements() + card.getExtraMotherNatureMovements());
            this.players.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(request);
        });
    }

    @Override
    public void endGame() {

    }

    @Override
    public State changeState() {
        return new PlanningState(game, players);
    }
}
