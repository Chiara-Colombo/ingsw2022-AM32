package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static it.polimi.ingsw.utils.Utils.*;

public class Server {
    private final ServerSocket serverSocket;
    private final MatchManager matchManager;

    /**
     * Allocates a new Server object and initialize serverSocket
     * @throws IOException if cannot initialize the ServerSocket
     */

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(DEFAULT_SERVER_PORT);
        this.matchManager = new MatchManager();
    }

    /**
     * method that starts connection with clients
     * @throws IOException if cannot initialize connection
     */

    public void start() throws IOException {
        while (true) {
            Socket clientSocket = this.serverSocket.accept();
            this.matchManager.newClient(clientSocket);
        }
    }
}