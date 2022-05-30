package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.CloudResponse;

import java.util.ArrayList;

public class ChooseCloudManager {
    private final ClientController controller;
    private final GUI gui;
    private ArrayList<Integer> validClouds;

    public ChooseCloudManager(ClientController controller, GUI gui) {
        this.controller = controller;
        this.gui = gui;
        this.validClouds = new ArrayList<>();
    }

    public void setValidClouds(ArrayList<Integer> validClouds) {
        this.validClouds = validClouds;
    }

    public void chooseCloud(int index) {
        if (index >= 0 && !this.validClouds.isEmpty()) {
            if (this.validClouds.contains(index)) {
                CloudResponse response = new CloudResponse(index);
                this.controller.sendObjectMessage(response);
                this.gui.removeEventHandlers();
            }
        }
    }
}
