package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.servertoclient.MoveMNRequest;
import it.polimi.ingsw.messages.servertoclient.MovePawnRequest;
import it.polimi.ingsw.model.Game;

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
        if (this.studentsMoved >= 3) {
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
        return null;
    }
}
