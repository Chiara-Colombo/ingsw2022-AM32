package it.polimi.ingsw.client.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ActionPhaseEntranceHandler implements EventHandler<MouseEvent> {
    private final TileImage pawn;
    private final MoveStudentsManager manager;

    public ActionPhaseEntranceHandler(TileImage pawn, MoveStudentsManager manager) {
        this.pawn = pawn;
        this.manager = manager;
    }

    @Override
    public void handle(MouseEvent event) {
        manager.setStudentIndex(this.pawn.getIndex());
    }
}
