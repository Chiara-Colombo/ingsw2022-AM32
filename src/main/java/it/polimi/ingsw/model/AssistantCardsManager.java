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
    private EnumMap<Wizards, Integer> currentCards;

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
        this.currentCards = new EnumMap<>(Wizards.class);
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

    public ArrayList<AssistantCard> getAvailableCardsForPlayer(Wizards player){
        return new ArrayList<>(
                this.assistantCards
                        .stream()
                        .filter(card -> !card.getDiscarded().get(player))
                        .toList()
        );
    }

    /**
     * Method that gets the current AssistantCard for a certain player
     * @param player the player
     * @return assistantCard instance, if present
     */

    public Optional<AssistantCard> getCurrentCardForPlayer(Wizards player){
        if (this.currentCards.get(player) >= 0)
            return Optional.of(this.assistantCards.get(this.currentCards.get(player)));
        return Optional.empty();
    }

    /**
     *Method for putting a card as discarded
     * @param player
     * @param discarded
     */
    public void setDiscardedForPlayer(Wizards player, boolean discarded) {
        if (this.currentCards.get(player) >= 0) {
            this.assistantCards.get(this.currentCards.get(player)).getDiscarded().put(player, discarded);
        }
    }

    /**
     *Method assigning the currentcard for the player
     * @param player
     * @param cardValue
     */

    public void setCurrentCardForPlayer(Wizards player, int cardValue) {
        for (int i = 0; i<this.assistantCards.size(); i++) {
            if (cardValue == this.assistantCards.get(i).getValue()) {
                this.currentCards.put(player, i);
            }
        }
    }

    /**
     * Reset the current card
     */

    public void resetCurrentCards() {
        this.currentCards.forEach((key, value) -> {
            this.currentCards.put(key, -1);
        });
    }

    /**
     * Initialize the cards for the player
     * @param player : player which cards are initialized
     */

    public void initializeCardsForPlayer(Wizards player) {
        this.assistantCards.forEach(card -> card.getDiscarded().put(player, false));
        this.currentCards.put(player, -1);
    }
}
