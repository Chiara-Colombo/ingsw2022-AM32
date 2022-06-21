package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.AssistantsCardUpdate;
import it.polimi.ingsw.messages.servertoclient.BoardUpdate;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Wizards;

import java.util.ArrayList;

public interface View {
    void showActionPhaseTurn(String nickname);
    void showAssistantCardChosen();
    void showAssistantCardRequest(ArrayList<AssistantCard> availableCards);
    void showAssistantsCardUpdate(AssistantsCardUpdate assistantsCardUpdate);
    void showBoardUpdate(BoardUpdate boardUpdate);
    void showChosenWizardCard();
    void showCloudRequest(ArrayList<Integer> validClouds);
    void showCoinsUpdate();
    void showErrorMessage(String message);
    void showErrorOnPawnPosition();
    void showGameStartingView();
    void showMNPositionUpdate();
    void showMoveMNRequest(int movements, ArrayList<Integer> validIndexes);
    void showMovePawnRequest(int numOfPawns);
    void showNotEnoughCoins();
    void showPlanningPhaseTurn(String nickname);
    void showPlayerChoosingWizard();
    void showRequestExpertMode();
    void showRequestNumOfPlayers();
    void showRequestUsername();
    void showSchoolBoardUpdate();
    void showSelectPawnRequest();
    void showWaitingView();
    void showWinnerMessage(String winner, String reason);
    void showWizardCardRequest(ArrayList<Wizards> validWizards);
    void showYourActionPhaseTurnEnds();
    void showYourPlanningPhaseTurnEnds();
}
