package it.polimi.ingsw.client.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DiningRoomHandler implements EventHandler<MouseEvent> {
    private final MoveStudentsManager manager;

    /**
     * Class Constructor
     */

    public DiningRoomHandler(MoveStudentsManager manager) {
        this.manager = manager;
    }

    /**
     * Method that handles the mouse event on Dining Room
     * @param event a mouse event
     */

    @Override
    public void handle(MouseEvent event) {
        if (event.getY() >= 100 && event.getY() <= 400)
            this.manager.setMoveOnSchoolBoard();
    }
}
