package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.clienttoserver.*;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.util.*;

import static it.polimi.ingsw.utils.Utils.*;

public class CLI  implements View{
    private ClientController clientController;
    private ArrayList<Characters> validCharacters;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";     // BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";       // RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";     // GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";    // YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";      // BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m";    // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";      // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";     // WHITE
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
        System.exit(0);
    }

    private String readInput() throws CharacterCardException {
        String string = new Scanner(System.in).nextLine();
        if (string.equals("-carta_personaggio")) throw new CharacterCardException();
        return string;
    }

    @Override
    public synchronized void showActionPhaseTurn(String nickname) {
        System.out.println("Fase Azione: è il turno di " + nickname);
    }

    @Override
    public synchronized void showAssistantCardRequest(ArrayList<AssistantCard> availableCards) {

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
                int choice = Integer.parseInt(this.readInput());
                if (choice < 0 || choice >= availableCards.size()) throw new IndexOutOfBoundsException();
                ClientMessage clientMessage = new AssistantCardResponse(availableCards.get(choice));
                this.clientController.sendObjectMessage(clientMessage);
                validInput = true;
            } catch (IndexOutOfBoundsException | NumberFormatException | CharacterCardException error) {
                System.out.println(ANSI_RED + "Hai inserito un valore non valido! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public void showAssistantCardChosen() {
    }

    @Override
    public synchronized void showAssistantsCardUpdate(AssistantsCardUpdate assistantsCardUpdate) {
        System.out.println(ANSI_RED + assistantsCardUpdate.getNickname() + ANSI_RESET + " ha scelto la seguente carta: \n VALORE : " + assistantsCardUpdate.getAssistantCard().getValue() + " Movimenti MN :" + assistantsCardUpdate.getAssistantCard().getMotherNatureMovements() );

    }

    @Override
    public synchronized void showBoardUpdate(BoardUpdate boardUpdate) {
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
                if (islandUpdate.isNoEntry()){
                    System.out.print(ANSI_RED + "! " + ANSI_RESET);
                }
            }
            if(island.get(0).hasTower()) {
                System.out.print(towers + TOWERS_COLORS_STRING_ENUM_MAP.get(island.get(0).getTowerColor()));
            }
            System.out.print("] ");
            if(island.get(0).getIndex() == 3 || island.get(0).getIndex() == 7 || island.get(0).getIndex() == 11){
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
            System.out.println(ANSI_RED + "             Plancia di  " +  boardUpdate.getPlayersUpdate().get(i).getNickname()+ ANSI_RESET + " : \n");
            System.out.print("INGRESSO :  [ " );
            ArrayList<PawnsColors> entrance = boardUpdate.getPlayersUpdate().get(i).getEntranceStudents();
            for(int pawnIndex = 0; pawnIndex < entrance.size(); pawnIndex++){
                System.out.print(PAWNS_COLORS_ANSI_ENUM_MAP.get(entrance.get(pawnIndex)) + ANSI_BLACK +" "+ pawnIndex +" " + ANSI_RESET +  ANSI_RESET + "  ");
            }
            System.out.print("]\n");

            // System.out.println("SALA : " + boardUpdate.getPlayersUpdate().get(i).getDiningRoom());
            EnumMap<PawnsColors, Integer> diningRoom = boardUpdate.getPlayersUpdate().get(i).getDiningRoom();
            for(PawnsColors color : PawnsColors.values()) {
                System.out.print(PAWNS_COLORS_ANSI_ENUM_MAP.get(color) + ANSI_BLACK + "TAVOLO"+ ANSI_RESET + ANSI_RESET + ":  ");
                for(int indexPawn = 0; indexPawn < diningRoom.get(color); indexPawn++) {
                    System.out.print(" "+ PAWNS_COLORS_ANSI_ENUM_MAP.get(color) + "   " + ANSI_RESET + "");
                }
                System.out.println();
            }
            System.out.println("TORRI : " + boardUpdate.getPlayersUpdate().get(i).getTowers() + " " +
                    TOWERS_COLORS_STRING_ENUM_MAP.get(boardUpdate.getPlayersUpdate().get(i).getTowersColor()) + "\n");
            if (boardUpdate.getGameUpdate().isExpertMode()){
                System.out.println("MONETE :" + boardUpdate.getPlayersUpdate().get(i).getCoins() + "\n");}
        }
        if (boardUpdate.getGameUpdate().isExpertMode()){
            this.validCharacters = boardUpdate.getGameUpdate().getValidCharacters();
            for (int i = 0; i < this.validCharacters.size(); i++) {
                System.out.println( "[" + i + "]" + CHARACTERS_NAME_MAP.get(this.validCharacters.get(i)));
            }
            for (Characters character : this.validCharacters) {
                if (character.equals(Characters.MONK) || character.equals(Characters.SPOILED_PRINCESS)) {
                    ArrayList<PawnsColors> pawns = character.equals(Characters.MONK) ? boardUpdate.getGameUpdate().getMonkStudents() : boardUpdate.getGameUpdate().
                            getSpoiledPrincessStudents();
                    if (character.equals(Characters.MONK)) {
                        System.out.println("Monaco: ");
                        System.out.print("[  ");
                        for (PawnsColors pawnsColors : pawns) {
                            System.out.print(PAWNS_COLORS_ANSI_ENUM_MAP.get(pawnsColors) + "   " + ANSI_RESET + "  ");
                        }
                        System.out.println("]");
                    }
                    if (character.equals(Characters.SPOILED_PRINCESS)) {
                        System.out.println("Principessa viziata:");
                        System.out.print("[  ");
                        for (PawnsColors pawnsColors : pawns) {
                            System.out.print(PAWNS_COLORS_ANSI_ENUM_MAP.get(pawnsColors) + "   " + ANSI_RESET + "  ");
                        }
                        System.out.println("]");
                    }
                }

            }
            System.out.println("Digita -carta_personaggio per usare una carta personaggio\n");
        }
    }

    @Override
    public synchronized void showCharacterCardUsed(Characters character, String username) {
        System.out.println(username + "ha usato la carta: " + CHARACTERS_NAME_MAP.get(character) + "\n");
    }

    @Override
    public void showChosenWizardCard() {

    }

    @Override
    public synchronized void showCloudRequest(ArrayList<Integer> validClouds) {
        boolean validInput = false;
        do {
            try {
                System.out.println("Seleziona una nuvola indicandone il numero!");
                int choice = Integer.parseInt(this.readInput());
                ClientMessage CloudResponse = new CloudResponse(choice);
                if (!validClouds.contains(choice)) throw new InputMismatchException();
                this.clientController.sendObjectMessage(CloudResponse);
                validInput = true;
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Valore non accettabile");
            } catch (CharacterCardException e) {
                this.chooseCharacterCard();
                break;
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
    public synchronized void showErrorMessage(String message) {
        System.out.println('\n' + message + '\n');
    }

    @Override
    public synchronized void showErrorOnPawnPosition() {
        System.out.println(ANSI_RED + "Hai selezionato una posizione scorretta! Ritenta! " + ANSI_RESET);
    }

    @Override
    public synchronized void showGameStartingView() {
        System.out.println("Partita avviata");
    }

    @Override
    public synchronized void showMatchRequest() {
        boolean validInput = false;
        do {
            System.out.println("Vuoi creare una nuova partita o partecipare ad una già esistente? (-crea / -partecipa)");
            try {
                String match = this.readInput();
                if (!match.equals("-crea") && !match.equals("-partecipa")) throw new InputMismatchException();
                ClientMessage matchResponse = new MatchResponse(match.equals("-crea"));
                this.clientController.sendObjectMessage(matchResponse);
                validInput = true;
            } catch (InputMismatchException | CharacterCardException error) {
                System.out.println(ANSI_RED + "Hai inserito un valore non corretto! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public void showMNPositionUpdate() {

    }

    @Override
    public synchronized void showMoveMNRequest(int movements, ArrayList<Integer> validIndexes) {
        boolean validInput = false;
        do {
            System.out.println("Muovi madre natura fino a " + movements + " isole: seleziona l'isola su cui spostare madre natura");
            try {
                int choice = Integer.parseInt(this.readInput());
                ClientMessage MNResponse = new MoveMNResponse(choice);
                if (choice <= 0 || !validIndexes.contains(choice)) throw new InputMismatchException();
                this.clientController.sendObjectMessage(MNResponse);
                validInput = true;
            } catch (InputMismatchException error) {
                System.out.println(ANSI_RED + "Hai inserito un valore non corretto! Ritenta! " + ANSI_RESET);
            } catch (CharacterCardException e) {
                this.chooseCharacterCard();
                break;
            }
        } while (!validInput);
    }

    @Override
    public synchronized void showMovePawnRequest(int numOfPawns) {
        boolean validInput = false;
        do {
            System.out.println("Seleziona uno studente dall'ingresso digitando il numero presente sopra la pedina ! ");
            try {
                int student = Integer.parseInt(this.readInput());
                System.out.println("Dove vuoi spostare la pedina? Digita -isola per posizionarla in un isola o -sala per posizionarla in sala ");
                String response = this.readInput();
                if (response.contains("-isola")) {
                    System.out.println("Scegli l'isola digitando il numero corrispondente");
                    int isola = Integer.parseInt(this.readInput());
                    ClientMessage clientMessage = new MovePawnResponse(student, isola, false);
                    this.clientController.sendObjectMessage(clientMessage);
                } else if (response.contains("-sala")) {
                    ClientMessage clientMessage = new MovePawnResponse(student, 0, true);
                    this.clientController.sendObjectMessage(clientMessage);
                } else throw new InputMismatchException();
                validInput = true;
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println(ANSI_RED + "Hai inserito un valore non corretto! Ritenta! " + ANSI_RESET);
            } catch (CharacterCardException e) {
                this.chooseCharacterCard();
                break;
            }
        } while (!validInput);
    }

    @Override
    public void showNoMatchAvailable() {
        System.out.println("Nessuna partita disponibile!");
        this.closeConnection();
    }

    @Override
    public synchronized void showNotEnoughCoins(){
        System.out.println(ANSI_RED + "Non hai abbastanza monete" + ANSI_RED);
    }

    @Override
    public synchronized void showPlanningPhaseTurn(String nickname) {
        System.out.println("Fase Pianificazione: è il turno di " + nickname );
    }

    @Override
    public synchronized void showPlayerChoosingWizard() {
        System.out.println("Un altro giocatore sta scegliendo il suo mago");
    }

    @Override
    public synchronized void showRequestExpertMode() {
        boolean validInput = false;
        do {
            try {
                System.out.print("Vuoi giocare con la modalità esperti? [s - n]\n - ");
                String ans = this.readInput();
                if (ans.toLowerCase().charAt(0) != 's' && ans.toLowerCase().charAt(0) != 'n') throw new InputMismatchException();
                boolean expertMode = ans.toLowerCase().charAt(0) == 's';
                GameModeResponse gameModeResponse = new GameModeResponse(expertMode);
                this.clientController.sendObjectMessage(gameModeResponse);
                validInput = true;
            } catch (InputMismatchException | CharacterCardException e) {
                System.out.println(ANSI_RED + "Hai inserito un valore non accettabile! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public synchronized void showRequestNumOfPlayers() {
        boolean validInput = false;
        do {
            try {
                System.out.print("Quanti giocatori per questa partita? [2 - 3]\n - ");
                int numOfPlayers = Integer.parseInt(this.readInput());
                if (numOfPlayers != 2 && numOfPlayers != 3) throw new InputMismatchException();
                ClientMessage clientMessage = new NumOfPlayersResponse(numOfPlayers);
                this.clientController.sendObjectMessage(clientMessage);
                validInput = true;
            } catch (NumberFormatException | CharacterCardException | InputMismatchException e) {
                System.out.println(ANSI_RED + "Hai inserito un valore non accettabile! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public synchronized void showRequestUsername() {
        boolean validInput = false;
        do {
            try {
                System.out.print("Inserisci uno username:\n - ");
                String username = this.readInput();
                SetUsername setUsername = new SetUsername(username);
                this.clientController.sendObjectMessage(setUsername);
                validInput = true;
            } catch (CharacterCardException e) {
                System.out.println(ANSI_RED + "Non puoi usare questo username " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public void showSchoolBoardUpdate() {

    }

    @Override
    public synchronized void showSelectColorRequest(SelectColorRequest selectColorRequest) {
        boolean validInput = false;
        do {
            try {
                System.out.println("Seleziona un colore tra i seguenti:");
                ArrayList<PawnsColors> colors = selectColorRequest.getValues();
                System.out.print("[  ");
                for(int pawnIndex = 0; pawnIndex < colors.size(); pawnIndex++){
                    System.out.print(PAWNS_COLORS_ANSI_ENUM_MAP.get(colors.get(pawnIndex)) + ANSI_BLACK +" "+ pawnIndex +" " + ANSI_RESET +  ANSI_RESET + "  ");
                }
                System.out.print("]\n\n - ");
                int index = Integer.parseInt(this.readInput());
                if (index < 0 || index >= colors.size()) throw new InputMismatchException();
                SelectColorResponse response = new SelectColorResponse(colors.get(index));
                this.clientController.sendObjectMessage(response);
                validInput = true;
            } catch (CharacterCardException | InputMismatchException | NumberFormatException e) {
                System.out.println(ANSI_RED + "Hai inserito un valore non accettabile! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public synchronized void showSelectIslandRequest(SelectIslandRequest selectIslandRequest) {
        boolean validInput = false;
        do {
            try {
                System.out.println("Seleziona un'isola:");
                ArrayList<Integer> islands = selectIslandRequest.getValidIndexes();
                System.out.print("\n - ");
                int index = Integer.parseInt(this.readInput());
                if (!islands.contains(index)) throw new InputMismatchException();
                SelectIslandResponse response = new SelectIslandResponse(index);
                this.clientController.sendObjectMessage(response);
                validInput = true;
            } catch (CharacterCardException | InputMismatchException | NumberFormatException e) {
                System.out.println(ANSI_RED + "Hai inserito un valore non accettabile! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public synchronized void showSelectPawnRequest(SelectPawnRequest selectPawnRequest) {
        boolean validInput = false;
        do {
            try {
                System.out.println("Seleziona una pedina:");
                ArrayList<PawnsColors> pawns = selectPawnRequest.getValidPawns();
                System.out.print("[  ");
                for(int pawnIndex = 0; pawnIndex < pawns.size(); pawnIndex++){
                    System.out.print(PAWNS_COLORS_ANSI_ENUM_MAP.get(pawns.get(pawnIndex)) + ANSI_BLACK +" "+ pawnIndex +" " + ANSI_RESET +  ANSI_RESET + "  ");
                }
                System.out.print("]\n\n - ");
                int index = Integer.parseInt(this.readInput());
                if (index < 0 || index >= pawns.size()) throw new InputMismatchException();
                SelectPawnResponse response = new SelectPawnResponse(index);
                this.clientController.sendObjectMessage(response);
                validInput = true;
            } catch (CharacterCardException | InputMismatchException | NumberFormatException e) {
                System.out.println(ANSI_RED + "Hai inserito un valore non accettabile! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public synchronized void showWaitingView() {
        System.out.println("In attesa che altri giocatori si colleghino...");
    }

    @Override
    public void showWinnerMessage(String winner, String reason) {
        System.out.println(winner + " vince la partita: " + reason + "!");
        this.closeConnection();
    }

    @Override
    public synchronized void showWizardCardRequest(ArrayList<Wizards> validWizards) {
        boolean validInput = false;
        do {
            System.out.print("Scegli una carta mago selezionando il numero\n");
            for (int i = 0; i < validWizards.size(); i++) {
                System.out.println("[ " + i + " ] " + validWizards.get(i).toString());
            }
            try {
                int wizardCard = Integer.parseInt(this.readInput());
                ClientMessage clientMessage = new WizardCardResponse(validWizards.get(wizardCard));
                this.clientController.sendObjectMessage(clientMessage);
                validInput = true;
            } catch (IndexOutOfBoundsException | InputMismatchException | CharacterCardException error) {
                System.out.println(ANSI_RED + "Hai selezionato un valore scorretto! Ritenta!" + ANSI_RESET);
            }
        } while (!validInput);
    }

    @Override
    public synchronized void showYourActionPhaseTurnEnds() {
        System.out.println("Il tuo turno è terminato");
    }

    @Override
    public synchronized void showYourPlanningPhaseTurnEnds() {
        System.out.println("Il tuo turno è terminato");
    }

    private void chooseCharacterCard() {
        boolean validInput = false;
        do {
            try {
                System.out.println("Quale carta voui usare? segnare il valore accanto al nome:\n");
                for (int i = 0; i < this.validCharacters.size(); i++) {
                    System.out.println("[ " + i + " ] " + CHARACTERS_NAME_MAP.get(this.validCharacters.get(i)));
                }
                System.out.print("\n - ");
                int index = Integer.parseInt(this.readInput());
                if (index < 0 || index >= this.validCharacters.size()) throw new InputMismatchException();
                UseCharacterCard message = new UseCharacterCard(this.validCharacters.get(index));
                this.clientController.sendObjectMessage(message);
                validInput = true;
            } catch (InputMismatchException | NumberFormatException | CharacterCardException e) {
                System.out.println(ANSI_RED + "Hai inserito un valore non accettabile! Ritenta! " + ANSI_RESET);
            }
        } while (!validInput);
    }
}

class CharacterCardException extends Exception {}