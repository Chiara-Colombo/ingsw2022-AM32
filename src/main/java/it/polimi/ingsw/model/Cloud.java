package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Cloud implements ICloud  {

    private boolean empty;
    public ArrayList<Pawn> students;

    /**
     * Constructor of Cloud Class
     */
    public Cloud(){
        this.empty = true;
        this.students = new ArrayList<>();
    }

    /**
     * Method that removes a student
     * @param index  of the students pawn that has to be removed
     * @return the (student pawn) selected
     */
    public Pawn removeStudent(int index){
         return this.students.remove(index);
    }

    /**
     * Method for adding a student pawn to the cloud
     * @param student the student pawn that needs to be added
     */
    public void addStudent(Pawn student){
        this.students.add(student);
    }

    public int studentsSize(){
        return this.students.size();
    }

    /**
     * Method that checks if the cloud has no student pawns
     * @return that the cloud is empty
     */
    @Override
    public boolean isEmpty() {
         return this.students.isEmpty();
    }

    /**
     * Method which returns a student iterator
     * @return student iterator
     */
    @Override
    public Iterator<Pawn> getStudents() {
        return this.students.iterator();
    }
}
