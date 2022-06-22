package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.clienttoserver.*;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.util.*;

import static it.polimi.ingsw.utils.Utils.*;

public class CLI  implements View{
    private ClientController clientController;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
    private static final EnumMap<PawnsColors, String> PAWNS_COLORS_ANSI_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(PawnsColors.BLUE, "\033[0;104m"),
            Map.entry(PawnsColors.PINK, "\033[0;105m"),
            Map.entry(PawnsColors.RED, "\033[0;101m"),
            Map.entry(PawnsColors.GREEN, "\033[0;102m"),
            Map.entry(PawnsColors.YELLOW, "\033[0;103m")
    ));
    private static final EnumMap<TowersColors, String> TOWERS_COLORS_STRING_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(TowersColors.NONE, "N"),
            Map.entry(TowersColors.BLACK, "B"),
            Map.entry(TowersColors.WHITE, "W"),
            Map.entry(TowersColors.GREY, "G")
    ));


    public void start() {
        boolean validInput = false;
        do {
            try {
                System.out.println("Inserisci i parametri per connetterti al server.");
                System.out.println("\nIndirizzo IP (-default per usare l'indirizzo di default: localhost)");
                String address = new Scanner(System.in).nextLine();
                System.out.println("Porta (-default per usare la porta di default: 30300)");
                String portString = new Scanner(System.in).nextLine();
                if (address.equals("-default")) address = DEFAULT_SERVER_ADDRESS;
                int port = portString.equals("-default") ? DEFAULT_SERVER_PORT : Integer.parseInt(portString);
                if (port < 1024 || port > 65535) throw new NumberFormatException();
                this.connect(address, port);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Valore non accettabile");
            }
        } while (!validInput);
    }

    private void connect(String address, int port) {
        try {
            this.clientController = new ClientController(port, address, this, false);
        } catch (IOException e) {
            this.showErrorMessage("Impossibile connettersi al server");
            return;
        }
        new Thread(this.clientController).start();
    }

    private void closeConnection() {
        this.clientController.close();
    }

    @Override
    public void showActionPhaseTurn(String nickname) {
        System.out.println("Fase Azione: è il turno di " + nickname);
    }

    @Override
    public void showAssistantCardRequest(ArrayList<AssistantCard> availableCards) {

        System.out.println("Seleziona una carta assistente digitando il suo numero! : ");

        int index = 0;
        for(int i = 0; i <= 4; i++) {
            for (int j = 0; j <=3 ; j++) {
                if(index >= availableCards.size()){
                    System.out.print("");
                } else {
                    System.out.print("  [ " + index + " ] : " + " Valore : " + availableCards.get(index).getValue() + "  Movimenti MN " + availableCards.get(index).getMotherNatureMovements());
                }
                index++;
            }
            System.out.println();
        }
        boolean validInput = false;
        do {
            try {
                System.out.print(" - ");
                int choice = new Scanner(System.in).nextInt();
                if (choice < 0 || choice >= availableCards.size()) throw new IndexOutOfBoundsException();
                ClientMessage clientMessage = new AssistantCardResponse(availableCards.get(choice));
                this.clientController.sendObjectMessage(clientMessage);
                validInput = true;
            } catch (IndexOutOfBoundsException | InputMismatchException error) {
                System.out.println(ANSI_RED + "Hai selezionato una carta sbagliata! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public void showAssistantCardChosen() {
    }

    @Override
    public void showAssistantsCardUpdate(AssistantsCardUpdate assistantsCardUpdate) {
        System.out.println(ANSI_RED + assistantsCardUpdate.getNickname() + ANSI_RESET + " ha scelto la seguente carta: \n VALORE : " + assistantsCardUpdate.getAssistantCard().getValue() + " Movimenti MN :" + assistantsCardUpdate.getAssistantCard().getMotherNatureMovements() );

    }

    @Override
    public void showBoardUpdate(BoardUpdate boardUpdate) {

        System.out.println(ANSI_RED + "                     ISOLE" + ANSI_RESET + "\n");
        ArrayList<ArrayList<IslandUpdate>> islands = boardUpdate.getBoardUpdateContent().getIslands();

        for (ArrayList<IslandUpdate> island : islands) {
            ArrayList<PawnsColors> colors = new ArrayList<>();
            System.out.print("[ ");
            for(int j = 0; j < island.size(); j++) {
                ArrayList<PawnsColors> islandcolor = island.get(j).getStudents();
                colors.addAll(islandcolor);
                if( j ==0){
                    System.out.print(island.get(j).getIndex());
                }
                else
                    System.out.print("/" + island.get(j).getIndex());
            }
            System.out.print(" ] : [ ");

            for(PawnsColors pawnsColors : colors){
                System.out.print(PAWNS_COLORS_ANSI_ENUM_MAP.get(pawnsColors) + "   " + ANSI_RESET + " ");
            }
            int towers = 0;
            for (IslandUpdate islandUpdate : island) {
                if (islandUpdate.getIndex() == boardUpdate.getBoardUpdateContent().getMotherNature()) {
                    System.out.print(ANSI_GREEN + "MN " + ANSI_RESET);
                }
                if (islandUpdate.hasTower()) {
                    towers++;
                }
            }
            if(island.get(0).hasTower()) {
                System.out.print(towers + TOWERS_COLORS_STRING_ENUM_MAP.get(island.get(0).getTowerColor()));
            }
            System.out.print("] ");
            if(island.get(0).getIndex() == 4 || island.get(0).getIndex() == 8 || island.get(0).getIndex() == 0){
                System.out.println("\n");
            }
        }

        System.out.println(ANSI_RED + "                    NUVOLE"  + ANSI_RESET + "\n");
        System.out.print("  ");
        for( int i = 0 ; i < boardUpdate.getGameUpdate().getNumOfPlayers(); i++ ){
            System.out.print("[" + i + "] : " + "[ ");
            for(PawnsColors color : boardUpdate.getBoardUpdateContent().getClouds().get(i).getStudents()) {
                System.out.print(PAWNS_COLORS_ANSI_ENUM_MAP.get(color) +  "   " + ANSI_RESET +  " ");
            }
            System.out.print("] ");
        }
        System.out.println("\n");

        for( int i = 0; i < boardUpdate.getGameUpdate().getNumOfPlayers(); i++) {
            System.out.println(ANSI_RED + "             SchoolBoard di  " +  boardUpdate.getPlayersUpdate().get(i).getNickname()+ ANSI_RESET + " : \n");
            System.out.print("INGRESSO :  [ " );
            ArrayList<PawnsColors> entrance = boardUpdate.getPlayersUpdate().get(i).getEntranceStudents();
            int PawnIndex = 0;
            for(PawnsColors pawnsColors : entrance){
                System.out.print(PAWNS_COLORS_ANSI_ENUM_MAP.get(pawnsColors) + ANSI_BLACK +" "+ PawnIndex +" " + ANSI_RESET +  ANSI_RESET + "  ");
                PawnIndex ++;
            }
            System.out.print("]\n");

            // System.out.println("SALA : " + boardUpdate.getPlayersUpdate().get(i).getDiningRoom());
            EnumMap<PawnsColors, Integer> diningRoom = boardUpdate.getPlayersUpdate().get(i).getDiningRoom();
            for(PawnsColors color : PawnsColors.values()) {
                System.out.print(PAWNS_COLORS_ANSI_ENUM_MAP.get(color) + ANSI_BLACK + "TABLE"+ ANSI_RESET + ANSI_RESET + ":  ");
                for(int indexPawn = 0; indexPawn < diningRoom.get(color); indexPawn++) {
                    System.out.print(" "+ PAWNS_COLORS_ANSI_ENUM_MAP.get(color) + "   " + ANSI_RESET + "");
                }
                System.out.println();
            }
            System.out.println("TORRI : " + boardUpdate.getPlayersUpdate().get(i).getTowers() + " " +
                    TOWERS_COLORS_STRING_ENUM_MAP.get(boardUpdate.getPlayersUpdate().get(i).getTowersColor()) + "\n");
        }
    }

    @Override
    public void showCharacterCardUsed(Characters character, String username) {

    }

    @Override
    public void showChosenWizardCard() {

    }

    @Override
    public void showCloudRequest(ArrayList<Integer> validClouds) {
        boolean validInput = false;
        do {
            try {
                System.out.println("Seleziona una nuvola indicandone il numero!");
                int choice = new Scanner(System.in).nextInt();
                ClientMessage CloudResponse = new CloudResponse(choice);
                if (!validClouds.contains(choice)) throw new InputMismatchException();
                this.clientController.sendObjectMessage(CloudResponse);
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Valore non accettabile");
            }
        } while (!validInput);
    }

    @Override
    public void showCoinsUpdate() {

    }

    @Override
    public void showConnectionLost() {
        System.out.println("Connessione con il server persa!");
        this.closeConnection();
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println('\n' + message + '\n');
    }

    @Override
    public void showErrorOnPawnPosition() {
        System.out.println(ANSI_RED + "Hai selezionato una posizione scorretta! Ritenta! " + ANSI_RESET);
    }

    @Override
    public void showGameStartingView() {
        System.out.println("Partita avviata");
    }

    @Override
    public void showMNPositionUpdate() {

    }

    @Override
    public void showMoveMNRequest(int movements, ArrayList<Integer> validIndexes) {
        boolean validInput = false;
        do {
            System.out.println("Muovi madre natura fino a " + movements + " isole: seleziona l'isola su cui spostare madre natura");
            try {
                int choice = new Scanner(System.in).nextInt();
                ClientMessage MNResponse = new MoveMNResponse(choice);
                if (choice <= 0 || !validIndexes.contains(choice)) throw new InputMismatchException();
                this.clientController.sendObjectMessage(MNResponse);
                validInput = true;
            } catch (InputMismatchException error) {
                System.out.println(ANSI_RED + "Hai inserito un valore non corretto! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public void showMovePawnRequest(int numOfPawns) {
        boolean validInput = false;
        do {
            System.out.println("Seleziona uno studente dall'ingresso digitando il numero presente sopra la pedina ! ");
            try {
                int student = new Scanner(System.in).nextInt();
                System.out.println("Dove vuoi spostare la pedina? Digita -isola per posizionarla in un isola o -sala per posizionarla in sala ");
                String response = new Scanner(System.in).nextLine();
                if (response.contains("-isola")) {
                    System.out.println("Scegli l'isola digitando il numero corrispondente");
                    int isola = new Scanner(System.in).nextInt();
                    ClientMessage clientMessage = new MovePawnResponse(student, isola, false);
                    this.clientController.sendObjectMessage(clientMessage);
                } else if (response.contains("-sala")) {
                    ClientMessage clientMessage = new MovePawnResponse(student, 0, true);
                    this.clientController.sendObjectMessage(clientMessage);
                } else throw new InputMismatchException();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Hai inserito un valore non corretto! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public void showNotEnoughCoins(){
        System.out.println(ANSI_RED + "Non hai abbastanza monete" + ANSI_RED);
    }

    @Override
    public void showPlanningPhaseTurn(String nickname) {
        System.out.println("Fase Pianificazione: è il turno di " + nickname );
    }

    @Override
    public void showPlayerChoosingWizard() {
        System.out.println("Un altro giocatore sta scegliendo il suo mago");
    }

    @Override
    public void showRequestExpertMode() {
        boolean validInput = false;
        do {
            try {
                System.out.print("Vuoi giocare con la modalità esperti? [s - n]\n - ");
                String ans = new Scanner(System.in).nextLine();
                if (ans.toLowerCase().charAt(0) != 's' && ans.toLowerCase().charAt(0) != 'n') throw new InputMismatchException();
                boolean expertMode = ans.toLowerCase().charAt(0) == 's';
                GameModeResponse gameModeResponse = new GameModeResponse(expertMode);
                this.clientController.sendObjectMessage(gameModeResponse);
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Valore non accettabile");
            }
        } while (!validInput);
    }

    @Override
    public void showRequestNumOfPlayers() {
        boolean validInput = false;
        do {
            try {
                System.out.print("Quanti giocatori per questa partita? [2 - 3]\n - ");
                int numOfPlayers = new Scanner(System.in).nextInt();
                if (numOfPlayers != 2 && numOfPlayers != 3) throw new InputMismatchException();
                ClientMessage clientMessage = new NumOfPlayersResponse(numOfPlayers);
                this.clientController.sendObjectMessage(clientMessage);
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Valore non accettabile");
            }
        } while (!validInput);
    }

    @Override
    public void showRequestUsername() {
        System.out.print("Inserisci uno username:\n - ");
        String username = new Scanner(System.in).nextLine();
        SetUsername setUsername = new SetUsername(username);
        this.clientController.sendObjectMessage(setUsername);
    }

    @Override
    public void showSchoolBoardUpdate() {

    }

    @Override
    public void showSelectColorRequest(SelectColorRequest selectColorRequest) {

    }

    @Override
    public void showSelectIslandRequest(SelectIslandRequest selectIslandRequest) {

    }

    @Override
    public void showSelectPawnRequest(SelectPawnRequest selectPawnRequest) {

    }

    @Override
    public void showWaitingView() {
        System.out.println("In attesa che altri giocatori si colleghino...");
    }

    @Override
    public void showWinnerMessage(String winner, String reason) {
        System.out.println(winner + " vince la partita: " + reason + "!");
        this.closeConnection();
    }

    @Override
    public void showWizardCardRequest(ArrayList<Wizards> validWizards) {
        boolean validInput = false;
        do {
            System.out.print("Scegli una carta mago selezionando il numero\n");
            for (int i = 0; i < validWizards.size(); i++) {
                System.out.println("[ " + i + " ] " + validWizards.get(i).toString());
            }
            try {
                int wizardCard = new Scanner(System.in).nextInt();
                ClientMessage clientMessage = new WizardCardResponse(validWizards.get(wizardCard));
                this.clientController.sendObjectMessage(clientMessage);
                validInput = true;
            } catch (IndexOutOfBoundsException | InputMismatchException error) {
                System.out.println(ANSI_RED + "Hai selezionato un valore scorretto! Ritenta!" + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public void showYourActionPhaseTurnEnds() {
        System.out.println("Il tuo turno è terminato");
    }

    @Override
    public void showYourPlanningPhaseTurnEnds() {
        System.out.println("Il tuo turno è terminato");
    }
}
