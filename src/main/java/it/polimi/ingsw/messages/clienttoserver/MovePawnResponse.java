package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Message that returns the pawn that the player want to move
 */

public class MovePawnResponse extends ClientMessage{
    private final int studentIndex, islandIndex;
    private final boolean moveOnSchoolBoard;

    public MovePawnResponse(int studentIndex, int islandIndex, boolean moveOnSchoolBoard) {
        this.studentIndex = studentIndex;
        this.islandIndex = islandIndex;
        this.moveOnSchoolBoard = moveOnSchoolBoard;
    }

    @Override
    public String TypeOfMessage() {
        return "MovePawnResponse";
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public int getStudentIndex() {
        return studentIndex;
    }

    public int getIslandIndex() {
        return islandIndex;
    }

    public boolean isMoveOnSchoolBoard() {
        return moveOnSchoolBoard;
    }
}
