package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PawnsColors;

import java.io.Serializable;
import java.util.ArrayList;

public class CloudUpdate implements Serializable {
    private final boolean empty;
    private final ArrayList<PawnsColors> students;

    public CloudUpdate(boolean empty, ArrayList<PawnsColors> students) {
        this.empty = empty;
        this.students = students;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public ArrayList<PawnsColors> getStudents() {
        return this.students;
    }
}
