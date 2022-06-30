package it.polimi.ingsw.client.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ActionPhaseEntranceHandler implements EventHandler<MouseEvent> {
    private final TileImage pawn;
    private final MoveStudentsManager manager;

    /**
     * Class Constructor
     */

    public ActionPhaseEntranceHandler(TileImage pawn, MoveStudentsManager manager) {
        this.pawn = pawn;
        this.manager = manager;
    }


    /**
     * Method that handles Mouse events
     * @param event a Mouse event
     */

    @Override
    public void handle(MouseEvent event) {
        manager.setStudentIndex(this.pawn.getIndex());
    }
}
