package it.polimi.ingsw.client;

public interface View {
    void showRequestNumOfPlayers();

    void showRequestExpertMode();

    void showRequestUsername();

    void showErrorMessage(String message);

    void showWaitingView();

    void showGameStartingView();

    void showWizardCardRequest(String nickname);

    void showPlayerChoosingWizard(String nickname);

    void showActionPhaseTurn(String nickname);

    void showAssistantCardChosen();

    void showAssistantsCardUpdate();

    void showBoardUpdate();

    void showChosenWizardCard();

    void showCloudRequest();

    void showCoinsUpdate();

    void showMNPositionUpdate();

    void showMoveMNRequest(int movements);

    void showMovePawnRequest();

    void showPlanningPhaseTurn(String nickname);

    void showPlayerChoosingWizard();

    void showSchoolBoardUpdate();

    void showSelectPawnRequest();

    void showWizardCardRequest();

    void showYourActionPhaseTurnEnds();

    void showYourPlanningPhaseTurnEnds();

    void showAssistantCardRequest();

    void showErrorMotherNaturePosition();

    void showNotEnoughCoins();


}
