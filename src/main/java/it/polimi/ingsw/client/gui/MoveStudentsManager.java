package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.MovePawnResponse;

public class MoveStudentsManager {
    private ClientController controller;
    private final ScenesManager scenesManager;
    private int studentIndex, islandIndex;
    private boolean moveOnSchoolBoard;

    public MoveStudentsManager(ScenesManager scenesManager) {
        this.scenesManager = scenesManager;
        this.studentIndex = -1;
        this.islandIndex = -1;
        this.moveOnSchoolBoard = true;
    }

    public void setStudentIndex(int studentIndex) {
        if (studentIndex >= 0)
            this.studentIndex = studentIndex;
    }

    public void setIslandIndex(int islandIndex) {
        if (islandIndex >= 0) {
            this.islandIndex = islandIndex;
            this.moveOnSchoolBoard = false;
            if (this.studentIndex >= 0) {
                this.sendResponse();
            }
        }
    }

    public void setMoveOnSchoolBoard() {
        this.moveOnSchoolBoard = true;
        if (this.studentIndex >= 0) {
            this.sendResponse();
        }
    }

    private void sendResponse() {
        MovePawnResponse response = new MovePawnResponse(this.studentIndex, this.islandIndex, this.moveOnSchoolBoard);
        this.controller.sendObjectMessage(response);
        this.scenesManager.removeEventHandlers();
        this.studentIndex = -1;
        this.islandIndex = -1;
        this.moveOnSchoolBoard = true;
    }

    void setController(ClientController controller) {
        this.controller = controller;
    }
}
