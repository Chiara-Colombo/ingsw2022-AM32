package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.AssistantsCardUpdate;
import it.polimi.ingsw.messages.servertoclient.BoardUpdate;
import it.polimi.ingsw.model.AssistantCard;
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
    void showAssistantsCardUpdate(AssistantsCardUpdate assistantsCardUpdate);
    void showBoardUpdate(BoardUpdate boardUpdate);
    void showChosenWizardCard();
    void showCloudRequest(ArrayList<Integer> validClouds);
    void showCoinsUpdate();
    void showMNPositionUpdate();
    void showMoveMNRequest(int movements, ArrayList<Integer> validIndexes);
    void showMovePawnRequest(int numOfPawns);
    void showPlanningPhaseTurn(String nickname);
    void showSchoolBoardUpdate();
    void showSelectPawnRequest();
    void showYourActionPhaseTurnEnds();
    void showYourPlanningPhaseTurnEnds();
    void showAssistantCardRequest(ArrayList<AssistantCard> availableCards);
    void showNotEnoughCoins();
    void showErrorOnPawnPosition();
}
