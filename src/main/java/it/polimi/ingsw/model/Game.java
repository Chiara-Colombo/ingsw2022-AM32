package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMooshroomManHandled;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Objects;

public class Game implements IMooshroomManHandled {

   private final ArrayList<Player> players;
   /* private Player[] players;*/
    private int currentPlayer;
    private final Board gameBoard;
    private final int numOfPlayers;
    private boolean expertMode;
    private final AssistantCardsManager cardsManager;
    private Characters[] validCharacters;
    private EnumMap<Characters,Integer> charactersValue;
    private EnumMap<PawnsColors,Integer> colorsInfluenceMultiplier;
    private Collection<Pawn> monkStudents;
    private Collection<Pawn> spoiledPrincessStudents;
    private Characters activeCharacters;
    private int grandmaHerbsNoEntryTiles;

    /**
     * Game class Constructor
     * @param numOfPlayers  number of Players that play the match
     */
    public Game(int numOfPlayers, String jsonCards){
        this.gameBoard = new Board(numOfPlayers);
        this.numOfPlayers = numOfPlayers;
        this.players = new ArrayList<>(numOfPlayers);
        this.cardsManager = new AssistantCardsManager(jsonCards);
        this.colorsInfluenceMultiplier=new EnumMap<PawnsColors, Integer>(PawnsColors.class);
    }

    /**
     * Method for start the game
     */
    public void startGame(){
        return;
    }

    /**
     * Get method that returns the current player
     * @return the index of the current player
     */
    public Player getCurrentPlayer(){
        return this.players.get(currentPlayer); }

    /**
     * Method that sets the current player index
     * @param currentPlayer
     */
    public void setCurrentPlayer(int currentPlayer) {
        if(currentPlayer >= 0 && currentPlayer < players.size())
            this.currentPlayer = currentPlayer;
        else System.out.println("Error");
    }



    public ArrayList<Player>getPlayers() {
        return this.players;
    }

    /**
     * Method that gets the gameBoard of the game
     * @return gameboard variable
     */
    public Board getGameBoard() { return this.gameBoard; }

    /**
     * Method AssistantCardsManager that returns the cardsManager variable
     * @return
     */
    private AssistantCardsManager getCardsManager() { return this.cardsManager; }

    /**
     * Method endGame that ends the Game when the conditions are satisfied
     */
    private void endGame() {
    }

    /**
     * Method used for calling the effects of Characters cards
     * @param characters
     */
    private void useCharacterEffect(Characters characters){
    }

    /**
     * Method that removes the Character card effect
     */
    private void removeCharacterEffect(){ return; }




    private Characters[] getValidCharacters(){ return this.validCharacters; }




    private int getGrandmaHerbsNoEntryTiles(){
        return this.grandmaHerbsNoEntryTiles;
    }




    /**
     * Method that gets the Coinvalue of the Character card selected
     * @param characters the character card selected
     * @return  coinvalue of character card
     */

   public int getCharacterCost(Characters characters){
       return characters.getCoinValue();
   }



/*
    METODI AGGIUNTIVI:
    /**
     * getter for returning the number of players which was selected
     * @return numoplayers value
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }






    /**
     * Method that adds a selected player to the game
     * @param player the player that needs to be added
     */
    public void addPlayer(Player player){
        if(Objects.nonNull(player)) {
            this.players.add(player);
        }
        else System.out.println("Player has not been added");
 }


 /**Override methods of MushroomMan Character Card*/
    @Override
    public void setInfluenceForColor(PawnsColors color, int influence) {
        if((Objects.nonNull(this.colorsInfluenceMultiplier.get(color)))) {
            this.colorsInfluenceMultiplier.replace(color, influence);
        }
        else
            this.colorsInfluenceMultiplier.put(color,influence);
    }

    @Override
    public int getInfluenceForColor(PawnsColors color) {
        return this.colorsInfluenceMultiplier.get(color);
    }
}