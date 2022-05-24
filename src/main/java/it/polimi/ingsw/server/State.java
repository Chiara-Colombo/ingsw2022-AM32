package it.polimi.ingsw.server;

public interface State {

    void chooseWizard();
    boolean moveStudent();
    void chooseCloud();
    void drawAssistantCard();
    void fillClouds();
    void moveMN();
    void endGame();
    State changeState();
}
