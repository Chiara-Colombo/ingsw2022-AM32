package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Wizards;

import java.util.ArrayList;

public interface View {
    void showRequestNumOfPlayers();

    void showRequestExpertMode();

    void showRequestUsername();

    void showErrorMessage(String message);

    void showWaitingView();

    void showGameStartingView();

    void showWizardCardRequest(ArrayList<Wizards> validWizards);

    void showPlayerChoosingWizard();

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

    void showSchoolBoardUpdate();

    void showSelectPawnRequest();

    void showYourActionPhaseTurnEnds();

    void showYourPlanningPhaseTurnEnds();

    void showAssistantCardRequest();

    void showErrorMotherNaturePosition();

    void showNotEnoughCoins();
}
