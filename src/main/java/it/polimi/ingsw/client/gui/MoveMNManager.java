package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.MoveMNResponse;

import java.util.ArrayList;

public class MoveMNManager {
    private int currentMNPosition, maxMovements;
    private ArrayList<Integer> validIslands;
    private final ClientController controller;
    private final ScenesManager scenesManager;

    public MoveMNManager(ClientController controller, ScenesManager scenesManager) {
        this.controller = controller;
        this.validIslands = new ArrayList<>();
        this.currentMNPosition = -1;
        this.maxMovements = -1;
        this.scenesManager = scenesManager;
    }

    public void setCurrentMNPosition(int currentMNPosition) {
        this.currentMNPosition = currentMNPosition;
    }

    public void setValidIslands(ArrayList<Integer> validIslands) {
        this.validIslands = validIslands;
    }

    public ArrayList<Integer> getValidIslands() {
        return this.validIslands;
    }

    public void setMaxMovements(int maxMovements) {
        this.maxMovements = maxMovements;
    }

    public void chooseIsland(int index) {
        if (this.validIslands.contains(index)) {
            MoveMNResponse response = new MoveMNResponse(index);
            this.controller.sendObjectMessage(response);
            this.scenesManager.removeEventHandlers();
        }
    }
}
