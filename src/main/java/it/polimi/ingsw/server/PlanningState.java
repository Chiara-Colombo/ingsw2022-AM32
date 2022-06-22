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
        this.game.getCardsManager().resetCurrentCards();
    }

    @Override
    public void chooseWizard() {

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
        for (int i = 0; i < this.game.getGameBoard().getClouds().size(); i++) {
            if (this.game.getGameBoard().getClouds().get(i).isEmpty()) {
                int students = this.game.getNumOfPlayers() == 2 ? 3 : 4;
                for (int j = 0; j < students; j++) {
                    int temp_i = i;
                    this.game.getGameBoard().drawFromBag().ifPresent(student -> {
                        this.game.getGameBoard().setStudentOnCloud(student, temp_i);
                    });
                }
            }
        }
    }

    @Override
    public void moveMN() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public void resumeState() {

    }

    @Override
    public State changeState() {
        return new ActionState(game, players);
    }
}
