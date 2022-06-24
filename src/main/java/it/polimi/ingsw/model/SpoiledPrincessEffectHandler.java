package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.ISpoiledPrincessHandled;

public class SpoiledPrincessEffectHandler implements EffectHandler{
    private final Pawn student;
    private final ISpoiledPrincessHandled player;
    private final ICoinsSupply coinsSupply;

    /**
     * Constructor of SpoiledPrincessEffectHandler Class
     */
    public SpoiledPrincessEffectHandler(Pawn student, ISpoiledPrincessHandled player, ICoinsSupply coinsSupply) {
        this.player = player;
        this.student = student;
        this.coinsSupply = coinsSupply;
    }



    /**
     * Method that applies SpoiledPrincessMan Character card Effects
     */
    @Override
    public void applyEffect() {
        if (this.player.addStudentInDiningRoom(this.student)) {
            if (this.coinsSupply.getCoinsSupply() > 0) {
                this.coinsSupply.giveCoin();
                this.player.earnCoin();
            }
        }
    }

    /**
     * Method that removes SpoiledPrincessMan Character card Effects
     */
    @Override
    public void removeEffect() {

    }
}
