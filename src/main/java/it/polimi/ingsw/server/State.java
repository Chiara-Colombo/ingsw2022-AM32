package it.polimi.ingsw.server;

public interface State {

    void chooseWizard();
    boolean moveStudent(boolean error);
    void chooseCloud();
    void drawAssistantCard();
    void fillClouds();
    void moveMN();
    void endGame();
    void resumeState();
    State changeState();
}
