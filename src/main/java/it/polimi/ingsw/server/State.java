package it.polimi.ingsw.server;

public interface State {

    public void StartTurn();

    public void chooseCloud();

    public void drawAssistantCard();

    public void fillClouds();

    public void moveMN();

    public void endGame();

    public State changeState();
}
