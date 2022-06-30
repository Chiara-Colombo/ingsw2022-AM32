package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.MoveMNResponse;

import java.util.ArrayList;

public class MoveMNManager {
    private int currentMNPosition, maxMovements;
    private ArrayList<Integer> validIslands;
    private ClientController controller;
    private final ScenesManager scenesManager;

    /**
     * Class Constructor
     */

    public MoveMNManager(ScenesManager scenesManager) {
        this.validIslands = new ArrayList<>();
        this.currentMNPosition = -1;
        this.maxMovements = -1;
        this.scenesManager = scenesManager;
    }

    /**
     * Setter for Mother Nature Position
     * @param currentMNPosition the current position of Mother Nature
     */

    public void setCurrentMNPosition(int currentMNPosition) {
        this.currentMNPosition = currentMNPosition;
    }

    /**
     * Setter for valid Islands
     */

    public void setValidIslands(ArrayList<Integer> validIslands) {
        this.validIslands = validIslands;
    }

    /**
     * Getter for valid Island
     */

    public ArrayList<Integer> getValidIslands() {
        return this.validIslands;
    }

    /**
     * Method that sets the Max number of movements that mother Nature can do
     * according to the Assistant Card Chosen by the player
     * @param maxMovements Max number of steps that Mother Nature can do
     */

    public void setMaxMovements(int maxMovements) {
        this.maxMovements = maxMovements;
    }

    /**
     * Method that allows to choose the island where the player want to put Mother Nature
     * @param index index of the islands chosen
     */

    public void chooseIsland(int index) {
        if (this.validIslands.contains(index)) {
            MoveMNResponse response = new MoveMNResponse(index);
            this.controller.sendObjectMessage(response);
            this.scenesManager.removeEventHandlers();
        }
    }

    /**
     * Setter for the Controller
     */

    void setController(ClientController controller) {
        this.controller = controller;
    }
}
