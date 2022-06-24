package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMagicMailManHandled;

import java.io.Serializable;
import java.util.*;

public class AssistantCard implements IMagicMailManHandled, Serializable {

    private final int motherNatureMovements, value;
    private final EnumMap<Wizards, Boolean> discarded;
    private int extraMotherNatureMovements;

    /**
     * Class constructor
     * @param value value of the card which goes from 1 to 10
     * @param motherNatureMovements integer value of mother nature movements
     */
    public AssistantCard(int value, int motherNatureMovements){
        this.value = value;
        this.motherNatureMovements = motherNatureMovements;
        this.extraMotherNatureMovements = 0;
        this.discarded = new EnumMap<>(Wizards.class);
    }

    /**
     * Getter for the assistantCard value variable
     * @return the value of the assistantCard
     */

    public int getValue() {
        return this.value;
    }

    /**
     * Getter for MotherNatureMovements
     * @return the value of MotherNatureMovements variable
     */

    public int getMotherNatureMovements() {
        return this.motherNatureMovements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExtraMotherNatureMovements(int extraMotherNatureMovements) {
        this.extraMotherNatureMovements = extraMotherNatureMovements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExtraMotherNatureMovements() {
        return this.extraMotherNatureMovements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetExtraMotherNatureMovements() {
        this.extraMotherNatureMovements = 0;
    }

    /**
     *
     * @return the discarded cards of AssistantCardsManager
     */

    EnumMap<Wizards, Boolean> getDiscarded() {
        return this.discarded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AssistantCard [value=" + this.value + ", motherNatureMovements=" + this.motherNatureMovements + "]";
    }
}
