package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.servertoclient.PlayerWinner;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.Map;

public class EndState implements State{
    private final Game game;
    private final Map<String, ClientHandler> players;

    /**
     * Class Constructor
     * @param game the match
     * @param players the players
     */

    public EndState(Game game, Map<String, ClientHandler> players) {
        this.game = game;
        this.players = players;
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
        return false;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void chooseCloud() {

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

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void endGame() {
        String winner = "", reason = "";
        int lessTowers = 10;
        boolean parity = false;
        for (Player player : this.game.getPlayers()) {
            if (player.getTowers() < lessTowers) {
                winner = player.getNickname();
                reason = "Ha costruito più torri di tutti";
                lessTowers = player.getTowers();
                parity = false;
            } else if (player.getTowers() == lessTowers) {
                parity = true;
            }
        }
        for (Player player : this.game.getPlayers()) {
            if (player.getTowers() == 0) {
                winner = player.getNickname();
                reason = "Ha costruito tutte le torri";
                break;
            }
        }
        if (parity) {
            int professorsControlled = 0;
            for (Player player : this.game.getPlayers()) {
                if (player.getSchoolBoard().getProfessors().size() > professorsControlled) {
                    winner = player.getNickname();
                    reason = "Ha il controllo su più professori";
                    professorsControlled = player.getSchoolBoard().getProfessors().size();
                }
            }
        }
        PlayerWinner playerWinnerMessage = new PlayerWinner(winner, reason);
        for (ClientHandler client : this.players.values())
            client.sendObjectMessage(playerWinnerMessage);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void resumeState() {

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public State changeState() {
        return null;
    }
}
