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

        /*if getStudentsInEntrance() - 7=0 --->non puoi aggiungere altri studenti in ingresso
        else*/
        entrance.add(student) ;
    }

    /**
     * Method which removes a student Pawn from the entrance of schoolBoard
     * @param studentIndex the index which refers to the position of the student in the entrance
     * @return the removed student Pawn
     */

    public Pawn removeStudent(int studentIndex) {
        return entrance.get(studentIndex);
    }

    /**
     * Method that add a student Pawn to the diningRoom
     * @param student the student that needs to be added
     * @return
     */
    public int addStudentToDiningRoom(Pawn student){
        PawnsColors color = student.getColor();
        this.diningRoom.get(color).add(student);
        return this.diningRoom.size();
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
    public Pawn removeProfessor(int professorIndex){
        return this.professorsTable.remove(professorIndex);
    }


    public EnumMap<PawnsColors, ArrayList<Pawn>> getDiningRoom() {
        return diningRoom;
    }

    /**
     * Method that gets an iterator of Professor table
     * @return
     */


    @Override
    public Iterator<Pawn> getProfessors() {
        return professorsTable.iterator();
    }


    /**
     * Method that gets the iterator of the students pawn based of a particular selected color
     * @param color the selected color
     * @return iterator of students pawns
     */
    @Override
    public Iterator<Pawn> getStudentsOfColor(PawnsColors color) {
        return this.diningRoom.get(color).iterator();
    }

    /**
     * Method that returns the iterator of students pawns that are in entrance
     * @return iterator of students pawn
     */

    @Override
    public Iterator<Pawn> getStudentsInEntrance() {
        return entrance.iterator();
    }
    /*public int getStudentsInEntrance(){
    return entrance.sizeof()
     */
}
