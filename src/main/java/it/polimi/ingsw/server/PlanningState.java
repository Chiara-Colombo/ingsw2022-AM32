package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.servertoclient.AssistantCardRequest;
import it.polimi.ingsw.messages.servertoclient.PlanningPhaseTurn;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.Map;

public class PlanningState implements State{

    private final Game game;
    private final Map<String, ClientHandler> players;

    public PlanningState(Game game, Map<String, ClientHandler> players) {
        this.game = game;
        this.players = players;
    }

    @Override
    public void chooseWizard() {

    }

    @Override
    public boolean moveStudent() {
        return false;
    }

    @Override
    public void chooseCloud() {

    }

    @Override
    public void drawAssistantCard() {
        System.out.println("DRAW ASSISTANT CARD FOR " + this.game.getCurrentPlayer().getNickname());
        this.game.getCurrentPlayer().getWizard().ifPresent(wizard -> {
            PlanningPhaseTurn phaseTurn = new PlanningPhaseTurn(this.game.getCurrentPlayer().getNickname());
            this.players.forEach((nickname, clientHandler) -> clientHandler.sendObjectMessage(phaseTurn));
            ArrayList<AssistantCard> availableCards = this.game.getCardsManager().getAvailableCardsForPlayer(wizard);
            AssistantCardRequest cardRequest = new AssistantCardRequest(availableCards);
            this.players.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(cardRequest);
        });
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
        return new ActionState(game, players);
    }
}
