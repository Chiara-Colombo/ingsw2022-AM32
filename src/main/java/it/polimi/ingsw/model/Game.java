package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMooshroomManHandled;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Game implements IMooshroomManHandled {

    private final ArrayList<Player> players;
    private int currentPlayer;
    private final Board gameBoard;
    private final int numOfPlayers;
    private final boolean expertMode;
    private final AssistantCardsManager cardsManager;
    private final ArrayList<Characters> validCharacters;
    private final EnumMap<Characters,Integer> charactersValue;
    private final EnumMap<PawnsColors,Integer> colorsInfluenceMultiplier;
    private Collection<Pawn> monkStudents;
    private Collection<Pawn> spoiledPrincessStudents;
    private EffectHandler activeCharacter;
    private GamePhase gamePhase;
    private int grandmaHerbsNoEntryTiles;
    private final List<String> playersCopyList;
    private int playerOrderIndex;

    /**
     * Game class Constructor
     * @param numOfPlayers  number of Players that play the match
     * @param expertMode sets the mode of the match
     * @param jsonCards file that sets the character cards
     */
    public Game(int numOfPlayers, boolean expertMode, String jsonCards){
        this.gamePhase = GamePhase.NO_PHASE;
        this.expertMode = expertMode;
        this.gameBoard = new Board(numOfPlayers);
        this.numOfPlayers = numOfPlayers;
        this.players = new ArrayList<>();
        this.playersCopyList = new ArrayList<>();
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
            this.playerOrderIndex = 0;
            for(Player p : players){
                playersCopyList.add(p.getNickname());
            }
            final ArrayList<Characters> characters = new ArrayList<>(Arrays.asList(Characters.values()));
            for (int i = 0; i<3; i++) {
                this.validCharacters.add(characters.remove((int) Math.floor(Math.random() * characters.size())));
                this.charactersValue.put(this.validCharacters.get(i), this.validCharacters.get(i).getCoinValue());
            }
            this.setupCharacters();
            this.setupPlayers();
        } else {
            System.out.println("Cannot start a new game");
        }
    }

    /**
     * Method for Changing order of play based on the card value selected
     * @param order New order
     */
    public void ChangePlayersOrder(List<String> order){
        this.playerOrderIndex = 0;
        for (int i = 0; i < order.size(); i++){
            for (int j = 0; j < playersCopyList.size(); j++){
                if (order.get(i).equals(playersCopyList.get(j))){
                    String playerR = playersCopyList.get(j);
                    String playerS = playersCopyList.get(i);
                    playersCopyList.set(i,playerR);
                    playersCopyList.set(j,playerS);
                }
            }
        }
        int j = 0;
        for(Player player : players){
            if(player.getNickname().equals(playersCopyList.get(playerOrderIndex))){
                this.currentPlayer = j;
            }
            j ++;
        }
    }

    public List<String> getOrderOfPlay() {
        return playersCopyList;
    }

    /* only for testing*/
    public List<String> getPlayersCopyList() {
        return playersCopyList;
    }

    /**
     * Method that sets up Characters
     */
    private void setupCharacters() {
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
    }

    private void setupPlayers() {
        this.players.forEach(player -> {
            int students = this.numOfPlayers == 2 ? 7 : 9;
            for (int i = 0; i < students; i++) {
                this.gameBoard.drawFromBag().ifPresent(player::addStudentInEntrance);
            }
            if (this.expertMode) player.earnCoin();
        });
    }

    /**
     * Method that calculate the next player
     */
    public boolean nextPlayer() {
        if (this.playerOrderIndex + 1 >= this.playersCopyList.size()){
            this.playerOrderIndex = 0;
            String playerNick = playersCopyList.get(playerOrderIndex);
            int j= 0;
            for(Player player : players){
                if(player.getNickname() == playersCopyList.get(playerOrderIndex)){
                    this.currentPlayer = j;
                }
                j ++;
            }
            return false;
        }
        this.playerOrderIndex ++;
        String playerNick = playersCopyList.get(playerOrderIndex);
        int i = 0;
        for(Player player : players){
            if(player.getNickname() == playersCopyList.get(playerOrderIndex)){
                this.currentPlayer = i;
            }
            i ++;
        }
        return true;
    }

    public int getPlayerOrderIndex() {
        return playerOrderIndex;
    }

    /* only for testing*/
    public int getcurrentplayerindex(){
        return currentPlayer;
    }

    /**
     * Getter for the Phase of the game
     * @return the phase of the game
     */
    public GamePhase getGamePhase() {
        return this.gamePhase;
    }

    /**
     * Setter for the Phase of the game
     * @return the phase of the game
     */
    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }


    /**
     * Get method that returns the current player
     * @return the index of the current player
     */
    public Player getCurrentPlayer(){
        return this.players.get(this.currentPlayer); }

    /**
     * Method that sets the current player index
     * @param currentPlayer
     */
    public void setCurrentPlayer(int currentPlayer) {
        if(currentPlayer >= 0 && currentPlayer < players.size())
            this.currentPlayer = currentPlayer;
        else System.out.println("Error");
    }


    /**
     * Getter for the Players
     * @return the players
     */
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
    public AssistantCardsManager getCardsManager() { return this.cardsManager; }

    /**
     * Method endGame that ends the Game when the conditions are satisfied
     */
    private void endGame() {
    }

    /**
     * Method used for calling the effects of Characters cards
     * @param character
     */
    private void useCharacterEffect(EffectHandler character){
        this.activeCharacter = character;
        this.activeCharacter.applyEffect();
    }

    /**
     * Method that removes the Character card effect
     */
    private void removeCharacterEffect(){
        this.activeCharacter.removeEffect();
    }



    /**
     * Method that get valid characters
     */
    public ArrayList<Characters> getValidCharacters(){ return this.validCharacters; }




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
 */
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

    /**
     * Method that get the influence for a pawn color
     * @param color is the color of the pawns of which we calculate the influence
     * @return
     */
    @Override
    public int getInfluenceForColor(PawnsColors color) {
        return this.colorsInfluenceMultiplier.get(color);
    }

    /**
     * Method that tell if the match is in Expert Mode
     * @return expertMode variable
     */
    public boolean isExpertMode() {
        return expertMode;
    }
}