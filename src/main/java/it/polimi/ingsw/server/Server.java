package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.utils.Utils.*;

public class Server {
    private ServerSocket serverSocket;
    private ServerController match;

    /**
     * Allocates a new Server object and initialize serverSocket
     * @throws IOException if cannot initialize the ServerSocket
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(DEFAULT_SERVER_PORT);
        this.match = new ServerController();
    }

    /**
     * method that starts connection with clients
     * @throws IOException if cannot initialize connection
     */
    public void start() throws IOException {
        System.out.println("Server started...");
        //ExecutorService executor = Executors.newCachedThreadPool();
        while (true) {
            Socket clientSocket = this.serverSocket.accept();
            System.out.println("Client connected");
            this.match.addPlayer(clientSocket);
            //executor.submit(new ClientHandler(clientSocket, match));
        }
        //executor.shutdown();
    }
}
