package it.polimi.ingsw.server;

/**
 * Interface that represent the state of a Game
 */

public interface State {

    /**
     * Method that allows to choose a wizard from the available ones
     */

    void chooseWizard();

    /**
     * Method that asks to move a student
     * @param error error on the movement of a student
     */

    boolean moveStudent(boolean error);

    /**
     * Method that allows to choose a cloud from the available ones
     */

    void chooseCloud();

    /**
     * Method that get the assistant card
     */

    void drawAssistantCard();

    /**
     * Method that fills the empty clouds
     */

    void fillClouds();

    /**
     * method that allows to move Mother Nature in available positions
     */

    void moveMN();

    /**
     * Method that calculate the winner and the reason
     */

    void endGame();

    /**
     *  Method that resume the state
     */

    void resumeState();

    /**
     * Method that change the state of the game
     * @return new phase of the game
     */

    State changeState();
}
