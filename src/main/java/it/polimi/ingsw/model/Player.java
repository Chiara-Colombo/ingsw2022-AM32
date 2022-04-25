package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IFarmerHandled;
import it.polimi.ingsw.model.Handled.IKnightHandled;
import it.polimi.ingsw.model.Handled.ISpoiledPrincessHandled;

import java.util.ArrayList;
import java.util.Collection;

public class Player implements IKnightHandled, ISpoiledPrincessHandled, IFarmerHandled {

    public  String nickname;
    private final ArrayList<Tower> towers;
    private TowersColors color;
    private Wizards wizard;
    private final SchoolBoard schoolBoard;
    private int coins, extraInfluence, extraStudent;

    /**
     * Player Class Constructor
     * @param nickname The nickname chosen by the player
     * @param towers the number of towers that the player has
     */
    public Player(String nickname, int towers){
        this.nickname = nickname;
        this.schoolBoard = new SchoolBoard();
        this.towers = new ArrayList<>(towers);
    }

    /**
     * Set the tower color for the player
     * @param colors the colors available
     */
    public void setColor(TowersColors colors) {
        this.color = colors;
    }

    /**
     * Method that gets the tower color of the player
     * @return the tower color
     */

    public TowersColors getColor() {
        return color;
    }

    /**
     * Method that gets the Wizard of the player, which determines the AssistantCardsManager
     * @return Wizard number
     */

    public Wizards getWizard() {
        return this.wizard;
    }

    /**
     * Method that sets Wizard for the selected Player
     * @param wizard the enumeration of wizards
     */

    public void setWizard(Wizards wizard) {
        this.wizard = wizard;
    }

    /**
     *Method which put a student tile in the schoolBoard entrance of the selected player
     * @param student the student Pawn that needs to be put
     */

    public void addStudentInEntrance(Pawn student){
        schoolBoard.addStudent(student);
    }

/**
    public boolean moveStudentInDiningRoom(Pawn student){
        return;
    }
 */
    /**
     * Method which removes a student pawn
     * @param studentIndex the index of the student pawn to be removed
     */
    public void removeStudent(int studentIndex){
        this.schoolBoard.removeStudent(studentIndex);
    }


    /**
     * Method which add a professor pawn
     * @param professor the index of the professor pawn to be added
     */
    public void addProfessor(Pawn professor){
        this.schoolBoard.addProfessor(professor);
    }
/**
    public Pawn loseProfessor(PawnsColors professorColor){
        return;
    }
*/
    /**
     * Getter method of coins
     * @return the int value of coins that a player has
     */
    public int getCoins() {
        return this.coins;
    }

    /**
     *
     */
    public boolean payCoins(int value){
        if (this.coins - value >= 0) {
            this.coins -= value;
            return true;
        }
        return false;
    }

    /**
     * Method which increase the coins of the player
     */

    public void earnCoin(){
        this.coins++;
    }



    /**
     * Method that gets the schoolBoard for the player
     * @return a schoolBoard object
     */
    public ISchoolBoard getSchoolBoard() {
        return this.schoolBoard;
    }

    /**
     * Getter method that returns the player nickname
     * @return the string value of nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**Methods based on the Knight Character Card/**/
    @Override
    public void setExtraInfluence(int extraInfluence) {
        this.extraInfluence = extraInfluence;
    }

    @Override
    public void resetExtraInfluence(){
        this.extraInfluence = 0;
    }

    @Override
    public int getExtraInfluence(){
        return this.extraInfluence;
    }

    /**Methods based of the SpoiledPrincess Character Card/**/
    @Override
    public void addStudentInDiningRoom(Pawn student) {
        this.schoolBoard.addStudentToDiningRoom(student);
    }

    /**Methods based on the Farmer Character Card**/
    @Override
    public void setExtraStudent(int extraStudent) {
        this.extraStudent = extraStudent;
    }

    @Override
    public int getExtraStudent() {
        return this.extraStudent;
    }
}
