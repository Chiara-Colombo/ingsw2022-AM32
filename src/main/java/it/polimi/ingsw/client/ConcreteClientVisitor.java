package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.*;

public class ConcreteClientVisitor implements VisitorClient{

    public View view;

    public ConcreteClientVisitor(View view) {
        this.view = view;
    }
    @Override
    public void visitMessage(NumOfPlayersRequest numOfPlayersRequest) {
        view.showRequestNumOfPlayers();
    }
    public void visitMessage(GameModeRequest gameModeRequest) {view.showRequestExpertMode();}
    public void visitMessage(RequestUsername requestUsername) {
        view.showRequestUsername();
    }
    public void visitMessage(GameIsStarting gameIsStarting) {view.showGameStartingView();}
    public void visitMessage(UsernameCorrectlyAssigned usernameCorrectlyAssigned) {view.showUsernameCorrectlyAssigned();}
    public void visitMessage(UsernameNotAssigned usernameNotAssigned) {view.showErrorMessage("Lo username deve essere univico!");}
    public void visitMessage(ErrorOnPlayerNumber errorOnPlayerNumber) {view.showErrorMessage("Hai Selezionato un numero non corretto!");}
    public void visitMessage(WaitingForPlayers waitingForPlayers) {view.showWaitingView();}



}


