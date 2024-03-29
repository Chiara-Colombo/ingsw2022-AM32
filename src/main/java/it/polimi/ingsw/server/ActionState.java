package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.servertoclient.CloudRequest;
import it.polimi.ingsw.messages.servertoclient.MoveMNRequest;
import it.polimi.ingsw.messages.servertoclient.MovePawnRequest;
import it.polimi.ingsw.messages.servertoclient.ServerMessage;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActionState implements State{
    private final Game game;
    private final Map<String, ClientHandler> players;
    private final HashMap<String, IPerformAction> lastMessageAction;
    private int studentsMoved;
    private String lastAction;

    /**
     * Class constructor
     * @param game the match
     * @param players the players
     */

    public ActionState(Game game, Map<String, ClientHandler> players) {
        this.game = game;
        this.players = players;
        this.studentsMoved = 0;
        this.lastMessageAction = new HashMap<>(Map.ofEntries(
                Map.entry("chooseCloud", this::chooseCloud),
                Map.entry("moveMN", this::moveMN),
                Map.entry("moveStudent", () -> this.moveStudent(true))
        ));
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void chooseWizard() {

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean moveStudent(boolean error) {
        if (error) this.studentsMoved--;
        if ((this.game.getNumOfPlayers() == 2 && this.studentsMoved >= 3) ||
                (this.game.getNumOfPlayers() == 3 && this.studentsMoved >= 4)) {
            this.studentsMoved = 0;
            return false;
        }
        MovePawnRequest request = new MovePawnRequest(this.game.getNumOfPlayers() == 2 ? 3 : 4);
        this.lastAction = "moveStudent";
        this.players.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(request);
        this.studentsMoved++;
        return true;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void chooseCloud() {
        ArrayList<Integer> validClouds = new ArrayList<>();
        for (int i = 0; i < this.game.getGameBoard().getClouds().size(); i++) {
            if (!this.game.getGameBoard().getClouds().get(i).isEmpty()) {
                validClouds.add(i);
            }
        }
        CloudRequest request = new CloudRequest(validClouds);
        this.lastAction = "chooseCloud";
        this.players.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(request);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void drawAssistantCard() {

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void fillClouds() {

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void moveMN() {
        this.game.getCurrentPlayer().getWizard().flatMap(wizard -> this.game.getCardsManager().getCurrentCardForPlayer(wizard)).ifPresent(card -> {
            ArrayList<Integer> validIndexes = new ArrayList<>();
            final int mnPosition = this.game.getGameBoard().getMotherNature(),
                    movements = card.getMotherNatureMovements() + card.getExtraMotherNatureMovements(),
                    islands = this.game.getGameBoard().getIslandsManager().getIslandsSize();
            int moved = 0;
            for (int i = 0; i < islands; i++) {
                if (moved <= movements) {
                    int index = (i + mnPosition) % islands;
                    if (!validIndexes.contains(index)) {
                        validIndexes.addAll(this.game.getGameBoard().getIslandsManager().getIslandsGroupIndexes(index));
                        moved++;
                    }
                } else break;
            }
            MoveMNRequest request = new MoveMNRequest(movements, validIndexes);
            this.lastAction = "moveMN";
            this.players.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(request);
        });
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void endGame() {

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void resumeState() {
        if (!Objects.isNull(this.lastAction)) {
            this.lastMessageAction.get(this.lastAction).performAction();
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public State changeState() {
        return new PlanningState(game, players);
    }
}

@FunctionalInterface
interface IPerformAction {
    void performAction();
}