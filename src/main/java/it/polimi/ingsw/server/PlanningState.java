package it.polimi.ingsw.server;

public class PlanningState implements State{
    @Override
    public void StartTurn() {

    }

    @Override
    public void chooseCloud() {

    }

    @Override
    public void drawAssistantCard() {
        this.changeState();
    }

    @Override
    public void fillClouds() {

    }

    @Override
    public void moveMN() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public State changeState() {
        return new ActionState();
    }
}
