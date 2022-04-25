package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AssistantCardsManager {

   /**
    private ArrayList<AssistantCard> assistantCards;
    */
    private ArrayList<AssistantCard> assistantCards;
    private Map<Wizards,Integer> currentCards;

    /**
     * Constructor of AssistantCardManager Class
     */

    public AssistantCardsManager(String jsonCards){
        if (Objects.isNull(jsonCards)) {
            System.out.println("Error");
            return;
        }
        Gson gson = new Gson();
        this.assistantCards = gson.fromJson(jsonCards, new TypeToken<ArrayList<AssistantCard>>(){}.getType());
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
