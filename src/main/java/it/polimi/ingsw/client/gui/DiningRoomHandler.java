package it.polimi.ingsw.client.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DiningRoomHandler implements EventHandler<MouseEvent> {
    private final MoveStudentsManager manager;

    public DiningRoomHandler(MoveStudentsManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getY() >= 100 && event.getY() <= 400)
            this.manager.setMoveOnSchoolBoard();
    }
}
