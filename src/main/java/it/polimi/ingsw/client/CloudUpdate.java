package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PawnsColors;

import java.io.Serializable;
import java.util.ArrayList;

public class CloudUpdate implements Serializable {
    private final boolean empty;
    private final ArrayList<PawnsColors> students;

    /**
     * Class Constructor
     * @param empty true if a cloud is empty
     * @param students students pawns
     */

    public CloudUpdate(boolean empty, ArrayList<PawnsColors> students) {
        this.empty = empty;
        this.students = students;
    }

    /**
     * Method that says if a cloud is empty
     * @return true if the cloud is empty
     *          false if the cloud has students
     */

    public boolean isEmpty() {
        return this.empty;
    }

    /**
     * Getter for students
     * @return list of students
     */

    public ArrayList<PawnsColors> getStudents() {
        return this.students;
    }
}
