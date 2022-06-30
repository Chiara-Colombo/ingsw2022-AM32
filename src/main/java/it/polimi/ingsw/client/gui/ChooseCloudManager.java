package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.CloudResponse;

import java.util.ArrayList;

public class ChooseCloudManager {
    private ClientController controller;
    private final ScenesManager scenesManager;
    private ArrayList<Integer> validClouds;

    /**
     * Class Constructor
     */

    public ChooseCloudManager(ScenesManager scenesManager) {
        this.scenesManager = scenesManager;
        this.validClouds = new ArrayList<>();
    }

    /**
     * Method that sets valid Clouds
     * @param validClouds Clouds with students on top
     */

    public void setValidClouds(ArrayList<Integer> validClouds) {
        this.validClouds = validClouds;
    }

    /**
     * Method that allows to choose a Cloud
     * @param index index of the chosen cloud
     */

    public void chooseCloud(int index) {
        if (index >= 0 && !this.validClouds.isEmpty()) {
            if (this.validClouds.contains(index)) {
                CloudResponse response = new CloudResponse(index);
                this.controller.sendObjectMessage(response);
                this.scenesManager.removeEventHandlers();
            }
        }
    }

    /**
     * Setter for the controller
     * @param controller
     */

    void setController(ClientController controller) {
        this.controller = controller;
    }
}
