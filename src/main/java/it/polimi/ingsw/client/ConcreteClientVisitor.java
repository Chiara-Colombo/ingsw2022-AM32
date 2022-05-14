package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.*;

public class ConcreteClientVisitor implements VisitorClient{

    public View view;

    public ConcreteClientVisitor(View view) {
        this.view = view;
    }

    @Override
    public void visitMessage(ConnectionRefused connectionRefused) {
        this.view.showErrorMessage("Raggiunto il massimo limite di giocatori");
    }
    @Override
    public void visitMessage(NumOfPlayersRequest numOfPlayersRequest) {
        this.view.showRequestNumOfPlayers();
    }
    @Override
    public void visitMessage(GameModeRequest gameModeRequest) { this.view.showRequestExpertMode(); }
    @Override
    public void visitMessage(RequestUsername requestUsername) {
        this.view.showRequestUsername();
    }
    @Override
    public void visitMessage(GameIsStarting gameIsStarting) { this.view.showGameStartingView(); }
    @Override
    public void visitMessage(UsernameNotAssigned usernameNotAssigned) { this.view.showErrorMessage("Lo username deve essere univoco!"); }
    @Override
    public void visitMessage(ErrorOnPlayerNumber errorOnPlayerNumber) { this.view.showErrorMessage("Hai Selezionato un numero non corretto!"); }
    @Override
    public void visitMessage(WaitingForPlayers waitingForPlayers) { this.view.showWaitingView(); }
    @Override
    public void visitMessage(PlayerDisconnected playerDisconnected) {
        this.view.showErrorMessage(playerDisconnected.getPlayer() + " si è disconnesso!");
    }
    @Override
    public void visitMessage(PlayerWinner playerWinner) {
        this.view.showErrorMessage(playerWinner.getWinner() + " vince per " + playerWinner.getReason());
    }
}


