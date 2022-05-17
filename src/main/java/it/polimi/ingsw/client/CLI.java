package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.clienttoserver.ClientMessage;
import it.polimi.ingsw.messages.clienttoserver.GameModeResponse;
import it.polimi.ingsw.messages.clienttoserver.NumOfPlayersResponse;
import it.polimi.ingsw.messages.clienttoserver.SetUsername;
import it.polimi.ingsw.model.Wizards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.ingsw.utils.Utils.*;

public class CLI  implements View{
    private ClientController clientController;

    public void start() {
        this.clientController = new ClientController(DEFAULT_SERVER_PORT, DEFAULT_SERVER_ADDRESS, this, false);
        try {
            this.clientController.connect();
            System.out.println("Controller initialized");
        } catch (IOException e) {
            System.out.println("Cannot instantiate connection towards server");
            return;
        }
        new Thread(this.clientController).start();
    }



    @Override
    public void showRequestNumOfPlayers() {
        System.out.print("Quanti giocatori per questa partita? [2 - 3]\n - ");
        int numOfPlayers = new Scanner(System.in).nextInt();
        ClientMessage clientMessage = new NumOfPlayersResponse(numOfPlayers);
        this.clientController.sendObjectMessage(clientMessage);
    }

    @Override
    public void showRequestExpertMode() {
        System.out.print("Vuoi giocare con la modalità esperti? [y - n]\n - ");
        String ans = new Scanner(System.in).nextLine();
        boolean expertMode = ans.toLowerCase().charAt(0) == 'y';
        GameModeResponse gameModeResponse = new GameModeResponse(expertMode);
        this.clientController.sendObjectMessage(gameModeResponse);
    }

    @Override
    public void showRequestUsername() {
        System.out.print("Inserisci uno username:\n - ");
        String username = new Scanner(System.in).nextLine();
        SetUsername setUsername = new SetUsername(username);
        this.clientController.sendObjectMessage(setUsername);
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println('\n' + message + '\n');
    }

    @Override
    public void showWaitingView() {
        System.out.println("In attesa che altri giocatori si colleghino...");
    }

    @Override
    public void showGameStartingView() {
        System.out.println("Partita avviata");
    }

    @Override
    public void showWizardCardRequest(ArrayList<Wizards> validWizards) {
        //System.out.println("Scegli una carta mago " + nickname);
    }

    @Override
    public void showPlayerChoosingWizard() {
        System.out.println("Un altro giocatore sta scegliendo il suo mago");
    }

    @Override
    public void showActionPhaseTurn(String nickname) {
        System.out.println("Fase Azione: è il turno di " + nickname);
    }

    @Override
    public void showAssistantCardChosen() {

    }

    @Override
    public void showAssistantsCardUpdate() {

    }

    @Override
    public void showBoardUpdate() {

    }

    @Override
    public void showChosenWizardCard() {

    }

    @Override
    public void showCloudRequest() {

    }

    @Override
    public void showCoinsUpdate() {

    }

    @Override
    public void showMNPositionUpdate() {

    }

    @Override
    public void showMoveMNRequest(int movements) {
        System.out.println("Muovi madre natura: seleziona  un numero compreso tra 1 e" + movements);
    }

    @Override
    public void showMovePawnRequest() {

    }

    @Override
    public void showPlanningPhaseTurn(String nickname) {
        System.out.println("Fase Pianificazione: è il turno di " + nickname );
    }

    @Override
    public void showSchoolBoardUpdate() {

    }

    @Override
    public void showSelectPawnRequest() {

    }

    @Override
    public void showYourActionPhaseTurnEnds() {
        System.out.println("Il tuo turno è terminato");
    }

    @Override
    public void showYourPlanningPhaseTurnEnds() {
        System.out.println("Il tuo turno è terminato");
    }

    @Override
    public void showAssistantCardRequest() {
        System.out.println("Seleziona una carta assistente");
    }

    @Override
    public void showErrorMotherNaturePosition(){
        System.out.println("Hai scelto una posizione di Madre Natura non valida"); }

    @Override
    public void showNotEnoughCoins(){System.out.println("Non hai abbastanza soldi");}

}
