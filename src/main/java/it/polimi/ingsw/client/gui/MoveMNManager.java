package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.MoveMNResponse;

import java.util.ArrayList;

public class MoveMNManager {
    private int currentMNPosition, maxMovements;
    private ArrayList<Integer> islands;
    private final ClientController controller;
    private final GUI gui;

    public MoveMNManager(ClientController controller, GUI gui) {
        this.controller = controller;
        this.islands = new ArrayList<>();
        this.currentMNPosition = -1;
        this.maxMovements = -1;
        this.gui = gui;
    }

    public void setCurrentMNPosition(int currentMNPosition) {
        this.currentMNPosition = currentMNPosition;
    }

    public void setIslands(ArrayList<Integer> islands) {
        this.islands = islands;
    }

    public void setMaxMovements(int maxMovements) {
        this.maxMovements = maxMovements;
    }

    public void chooseIsland(int index) {
        if (index >= 0 && this.maxMovements >= 0 && this.currentMNPosition >= 0 && this.islands.size() > 0) {
            int normalMNPosition = this.currentMNPosition, normalIndex = index;
            if (index >= Math.ceil(this.islands.size() / 2)) normalIndex -= this.islands.size();
            if (this.currentMNPosition >= Math.ceil(this.islands.size() / 2)) normalMNPosition -= this.islands.size();
            if (Math.abs(normalIndex - normalMNPosition) <= this.maxMovements) {
                MoveMNResponse response = new MoveMNResponse(normalIndex - normalMNPosition);
                this.controller.sendObjectMessage(response);
                this.gui.removeEventHandlers();
            } else {
                System.out.println("Ko");
            }
        }
    }
}
