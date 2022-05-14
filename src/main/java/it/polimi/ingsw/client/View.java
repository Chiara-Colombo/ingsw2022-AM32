package it.polimi.ingsw.client;

public interface View {
    void showRequestNumOfPlayers();

    void showRequestExpertMode();

    void showRequestUsername();

    void showErrorMessage(String message);

    void showWaitingView();

    void showGameStartingView();
}
