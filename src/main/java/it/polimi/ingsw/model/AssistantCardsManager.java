package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class AssistantCardsManager {

   /**
    private ArrayList<AssistantCard> assistantCards;
    */
    private ArrayList<AssistantCard> assistantCards;
    private Map<Wizards,Integer> currentCards;

    /**
     * Constructor of AssistantCardManager Class
     */

    /*needs to be modified*/
    public AssistantCardsManager(){

    }


    /**
     *  Method that gets the collection of AssistantCards based of the AssistantCardsManager
     * @return collection of AssistantCard
     */

    public ArrayList<AssistantCard> getAssistantCards() {
        return this.assistantCards;
    }

    /**
     * Method for getting the availableCards for
     * @param player
     * @return
     */
/**
    public ArrayList<AssistantCard> getAvailableCardsForPlayer(Wizards player){return assistantCards.get();}
*/
    /**
     * Method that gets the current AssistantCard for a certain player
     * @param player the player
     * @return
     */
    /*
    public AssistantCard getCurrentCardForPlayer(Wizards player){
        return assistantCards;
    }
*/
    /**
     *
     * @param player
     * @param card
     */
    public void setDiscardedCardForPlayer(Wizards player,int card) {
        this.assistantCards.get(card).getDiscarded().put(player, true);
    }

}
