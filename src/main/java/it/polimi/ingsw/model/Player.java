package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IFarmerHandled;
import it.polimi.ingsw.model.Handled.IKnightHandled;
import it.polimi.ingsw.model.Handled.ISpoiledPrincessHandled;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Player implements IKnightHandled, ISpoiledPrincessHandled, IFarmerHandled {

    private final String nickname;
    private final ArrayList<Tower> towers;
    private TowersColors color;
    private Optional<Wizards> wizard;
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
        this.towers = new ArrayList<>();
        this.wizard = Optional.empty();
        for (int i = 0; i<towers; i++) {
            this.towers.add(new Tower(TowersColors.NONE));
        }
    }

    /**
     * Set the tower color for the player
     * @param color the colors available
     */
    public void setColor(TowersColors color) {
        this.color = color;
        this.towers.forEach(tower -> {
            tower.setColor(color);
        });
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
    public Optional<Wizards> getWizard() {
        return this.wizard;
    }

    /**
     * Method that sets Wizard for the selected Player
     * @param wizard the enumeration of wizards
     */
    public void setWizard(Wizards wizard) {
        this.wizard = Optional.of(wizard);
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
    public Pawn removeStudent(int studentIndex){
        return this.schoolBoard.removeStudent(studentIndex);
    }


    /**
     * Method which add a professor pawn
     * @param professor the index of the professor pawn to be added
     */
    public void addProfessor(Pawn professor){
        this.schoolBoard.addProfessor(professor);
    }

    public Pawn removeProfessor(int professorIndex){
        return this.schoolBoard.removeProfessor(professorIndex);
    }
    /**
     * Getter method of coins
     * @return the int value of coins that a player has
     */
    public int getCoins() {
        return this.coins;
    }

    /**
     * Method that decrease the coins of a player when he uses a Character Card
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
    /**
     * {@inheritDoc}
     */
    @Override
    public void setExtraInfluence(int extraInfluence) {
        this.extraInfluence = extraInfluence;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetExtraInfluence(){
        this.extraInfluence = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExtraInfluence(){
        return this.extraInfluence;
    }

    /**Methods based of the SpoiledPrincess Character Card/**/
    /**
     * {@inheritDoc}
     */
    @Override
    public void addStudentInDiningRoom(Pawn student) {
        this.schoolBoard.addStudentToDiningRoom(student);
    }

    /**Methods based on the Farmer Character Card**/
    /**
     * {@inheritDoc}
     */
    @Override
    public void setExtraStudent(int extraStudent) {
        this.extraStudent = extraStudent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExtraStudent() {
        return this.extraStudent;
    }

    public int getTowers() {
        return this.towers.size();
    }

    public Tower removeTower() {
        return this.towers.remove(0);
    }

    public void addTower(Tower tower) {
        this.towers.add(tower);
    }
}
