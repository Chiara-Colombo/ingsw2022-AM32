package it.polimi.ingsw.model;

import java.util.*;

public class SchoolBoard implements ISchoolBoard {

    private final EnumMap<PawnsColors, ArrayList<Pawn>> diningRoom;
    private final ArrayList<Pawn> entrance;
    private final ArrayList<Pawn> professorsTable;

    /**
     * Constructor of SchoolBoard class
     */

    public SchoolBoard(){
        this.entrance = new ArrayList<>(9);
        this.professorsTable = new ArrayList<>();
        this.diningRoom = new EnumMap<>(PawnsColors.class);
        for(PawnsColors color:PawnsColors.values())
        this.diningRoom.put(color,new ArrayList<Pawn>());
    }

    /**
     * Method that adds a student pawn to the entrance of schoolboard
     * @param student the student pawn that needs to be added
     */

    public void addStudent(Pawn student){

        /*if getStudentsInEntrance() - 7=0 --->you can't app other students in entrance
        else*/
        entrance.add(student) ;
    }

    /**
     * Method which removes a student Pawn from the entrance of schoolBoard
     * @param studentIndex the index which refers to the position of the student in the entrance
     * @return the removed student Pawn
     */

    Pawn removeStudent(int studentIndex) {
        if (studentIndex >= 0 && studentIndex < this.entrance.size()) {
            Pawn student = this.entrance.get(studentIndex);
            this.entrance.remove(studentIndex);
            return student;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Method that add a student Pawn to the diningRoom
     * @param student the student that needs to be added
     * @return
     */

    public int addStudentToDiningRoom(Pawn student){
        PawnsColors color = student.getColor();
        this.diningRoom.get(color).add(student);
        return this.diningRoom.get(color).size();
    }

    /**
     * Method that adds a professor pawn to the professorsTable
     * @param professor pawn that needs to be added
     */

    public void addProfessor(Pawn professor){
        professorsTable.add(professor);
    }

    /**
     * Method that remove a professor pawn to the professorsTable
     * @param professorIndex pawn that represent the position of the pawn
     * @return the professor pawn removed
     */

    Pawn removeProfessor(int professorIndex){
        return this.professorsTable.remove(professorIndex);
    }

    /**
     * Method that gets the dining room (an enumerator for Pawns Colors and an array list for Pawns)
     * @return dining room
     */

    public EnumMap<PawnsColors, ArrayList<Pawn>> getDiningRoom() {
        return diningRoom;
    }

    /**
     * Method that gets an iterator of Professor table
     * @return professor table
     */

    @Override
    public ArrayList<Pawn> getProfessors() {
        return this.professorsTable;
    }

    /**
     * Method that gets the iterator of the students pawn based of a particular selected color
     * @param color the selected color
     * @return iterator of students pawns
     */

    @Override
    public ArrayList<Pawn> getStudentsOfColor(PawnsColors color) {
        return this.diningRoom.get(color);
    }

    /**
     * Method that returns the iterator of students pawns that are in entrance
     * @return iterator of students pawn
     */

    @Override
    public ArrayList<Pawn> getStudentsInEntrance() {
        return this.entrance;
    }

    /**
     * Method that remove a student from the entrance
     * @param pawn student that has to been removed
     */

    void removeStudentInEntrance(Pawn pawn){
        this.entrance.remove(pawn);
   }

}
