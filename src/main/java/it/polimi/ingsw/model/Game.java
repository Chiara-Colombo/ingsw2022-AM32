package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMooshroomManHandled;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Game implements IMooshroomManHandled {

   private final ArrayList<Player> players;
   /* private Player[] players;*/
    private int currentPlayer;
    private final Board gameBoard;
    private final int numOfPlayers;
    private boolean expertMode;
    private final AssistantCardsManager cardsManager;
    private final ArrayList<Characters> validCharacters;
    private final EnumMap<Characters,Integer> charactersValue;
    private final EnumMap<PawnsColors,Integer> colorsInfluenceMultiplier;
    private Collection<Pawn> monkStudents;
    private Collection<Pawn> spoiledPrincessStudents;
    private Characters activeCharacters;
    private GamePhase gamePhase;
    private int grandmaHerbsNoEntryTiles;

    /**
     * Game class Constructor
     * @param numOfPlayers  number of Players that play the match
     */
    public Game(int numOfPlayers, boolean expertMode, String jsonCards){
        this.gamePhase = GamePhase.NO_PHASE;
        this.expertMode = expertMode;
        this.gameBoard = new Board(numOfPlayers);
        this.numOfPlayers = numOfPlayers;
        this.players = new ArrayList<>(numOfPlayers);
        this.cardsManager = new AssistantCardsManager(jsonCards);
        this.validCharacters = new ArrayList<>();
        this.charactersValue = new EnumMap<>(Characters.class);
        this.colorsInfluenceMultiplier = new EnumMap<>(PawnsColors.class);
    }

    /**
     * Method for start the game
     */
    public void startGame(){
        if (this.numOfPlayers == this.players.size()) {
            this.gamePhase = GamePhase.START_PHASE;
            this.currentPlayer = 0;
            final ArrayList<Characters> characters = new ArrayList<>(Arrays.asList(Characters.values()));
            for (int i = 0; i<3; i++) {
                this.validCharacters.add(characters.remove((int) Math.floor(Math.random() * characters.size())));
                this.charactersValue.put(this.validCharacters.get(i), this.validCharacters.get(i).getCoinValue());
            }
            if (this.validCharacters.contains(Characters.MONK)) {
                this.monkStudents = new ArrayList<>();
                for (int i = 0; i<4; i++) {
                    this.gameBoard.drawFromBag().ifPresent(pawn -> {
                        this.monkStudents.add(pawn);
                    });
                }
            }
            if (this.validCharacters.contains(Characters.SPOILED_PRINCESS)) {
                this.spoiledPrincessStudents = new ArrayList<>();
                for (int i = 0; i<4; i++) {
                    this.gameBoard.drawFromBag().ifPresent(pawn -> {
                        this.spoiledPrincessStudents.add(pawn);
                    });
                }
            }
            if (this.validCharacters.contains(Characters.GRANDMA_HERBS)) {
                this.grandmaHerbsNoEntryTiles = 4;
            }
        } else {
            System.out.println("Cannot start a new game");
        }
    }

    public boolean nextPlayer() {
        if (this.currentPlayer + 1 >= this.players.size()){
            this.currentPlayer = 0;
            return false;
        }
        this.currentPlayer = this.currentPlayer + 1;
        return true;
    }



    public GamePhase getGamePhase() {
        return this.gamePhase;
    }

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
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




    private ArrayList<Characters> getValidCharacters(){ return this.validCharacters; }




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