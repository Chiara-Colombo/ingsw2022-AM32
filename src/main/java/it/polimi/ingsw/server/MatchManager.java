package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.servertoclient.MatchRequest;
import it.polimi.ingsw.messages.servertoclient.NoMatchAvailable;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatchManager {
    private final ExecutorService executor;
    private final ArrayList<ServerController> matches;
    private final ArrayList<ServerController> endedMatches;
    private final ArrayList<ClientHandler> newClients;

    public MatchManager() {
        this.executor = Executors.newCachedThreadPool();
        this.matches = new ArrayList<>();
        this.endedMatches = new ArrayList<>();
        this.newClients = new ArrayList<>();
    }

    public void newClient(Socket client) throws IOException {
        ClientHandler handler = new ClientHandler(this, client);
        this.executor.submit(handler);
        this.newClients.add(handler);
        MatchRequest request = new MatchRequest();
        handler.sendObjectMessage(request);
    }

    public void removeClient(ClientHandler client) {
        if (this.newClients.remove(client))
            client.close();
    }

    public void matchResponse(boolean isNewMatch, ClientHandler client) throws IOException {
        if (isNewMatch) {
            if (this.newClients.remove(client)) {
                ServerController newMatch;
                if (this.endedMatches.size() > 0) {
                    newMatch = this.endedMatches.remove(0);
                } else {
                    newMatch = new ServerController(this);
                }
                newMatch.addPlayer(client);
                this.matches.add(newMatch);
            }
        } else {
            boolean notAvailable = true;
            for (ServerController match : this.matches) {
                if (!match.isFull()) {
                    notAvailable = false;
                    match.addPlayer(client);
                    break;
                }
            }
            if (notAvailable) {
                NoMatchAvailable response = new NoMatchAvailable();
                client.sendObjectMessage(response);
            }
        }
    }

    void matchEnded(ServerController match) {
        if (this.matches.remove(match)) {
            this.endedMatches.add(match);
        }
    }
}
