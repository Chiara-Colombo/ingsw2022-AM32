package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.BoardUpdateContent;
import it.polimi.ingsw.client.GameUpdate;
import it.polimi.ingsw.client.PlayerUpdate;
import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Message that sends to all players the Board Update
 */
public class BoardUpdate extends ServerMessage{
    private final ArrayList<PlayerUpdate> playersUpdate;
    private final BoardUpdateContent boardUpdateContent;
    private final GameUpdate gameUpdate;

    public BoardUpdate(ArrayList<PlayerUpdate> playersUpdate, BoardUpdateContent boardUpdateContent, GameUpdate gameUpdate) {
        this.playersUpdate = playersUpdate;
        this.boardUpdateContent = boardUpdateContent;
        this.gameUpdate = gameUpdate;
    }

    @Override
    public String TypeOfMessage() {
        return "BoardUpdate";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public ArrayList<PlayerUpdate> getPlayersUpdate() {
        return playersUpdate;
    }

    public BoardUpdateContent getBoardUpdateContent() {
        return boardUpdateContent;
    }

    public GameUpdate getGameUpdate() {
        return gameUpdate;
    }
}