package it.polimi.ingsw.server;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.*;


import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static it.polimi.ingsw.utils.Utils.CARDS_RESOURCE_PATH;

public class ServerController  {
    private Game game;
    private final ArrayList<ClientHandler> clients;
    private final Map<String, ClientHandler> usernames;
    private int numOfPlayers;
    private State stateOfTheGame;

    public ServerController() {
        this.numOfPlayers = -1;
        this.clients = new ArrayList<>(0);
        this.usernames = new HashMap<>(0);
    }

    public void setupGame(ClientHandler player) {
        ServerMessage NumOfPlayersRequest = new NumOfPlayersRequest();
        player.sendObjectMessage(NumOfPlayersRequest);
    }

    public void errorOnPlayerNumber(ClientHandler player){
        ServerMessage errorOnPlayerNumber = new ErrorOnPlayerNumber();
        player.sendObjectMessage(errorOnPlayerNumber);
        this.setupGame(player);
    }

    public void setupGameMode(ClientHandler player) {
        ServerMessage GameModeRequest = new GameModeRequest();
        player.sendObjectMessage(GameModeRequest);
    }

    public synchronized void addPlayer(Socket player) throws IOException {
        if (this.clients.size() >= this.numOfPlayers && this.numOfPlayers > 0) {
            new ClientHandler(player, this).sendObjectMessage(new ConnectionRefused());
            System.out.println("Too many players");
            player.close();
        }
        ClientHandler client = new ClientHandler(player, this);
        new Thread(client).start();
        this.clients.add(client);
        ServerMessage RequestUsername = new RequestUsername();
        client.sendObjectMessage(RequestUsername);
    }

    public void setNumOfPlayers(int numOfPlayers, ClientHandler player) {
        this.numOfPlayers = numOfPlayers;
        this.setupGameMode(player);
    }

    public void setGameMode(boolean expertMode, ClientHandler player) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(CARDS_RESOURCE_PATH));
        String jsonCards = input.readLine();
        this.game = new Game(this.numOfPlayers, expertMode, jsonCards);
        checkStartGame(player);
    }

    private void checkStartGame(ClientHandler player) {
        if (this.numOfPlayers != this.clients.size() || this.clients.size() != this.usernames.size()) {
            WaitingForPlayers waitingForPlayers = new WaitingForPlayers();
            player.sendObjectMessage(waitingForPlayers);
        } else {
            GameIsStarting gameIsStarting = new GameIsStarting();
            for (int i = 0; i < clients.size(); i++) {
                if (i < this.numOfPlayers) {
                    clients.get(i).sendObjectMessage(gameIsStarting);
                } else {
                    clients.get(i).sendObjectMessage(new ConnectionRefused());
                    clients.remove(i);
                    i--;
                }
            }
            this.startGame();
        }
    }

    public synchronized void setUsername(String username, ClientHandler player) {
        if (this.usernames.containsKey(username)) {
            UsernameNotAssigned usernameNotAssigned = new UsernameNotAssigned();
            player.sendObjectMessage(usernameNotAssigned);
            RequestUsername requestUsername = new RequestUsername();
            player.sendObjectMessage(requestUsername);
            return;
        }
        this.usernames.put(username, player);
        player.setNickname(username);
        UsernameCorrectlyAssigned usernameCorrectlyAssigned = new UsernameCorrectlyAssigned();
        System.out.println("Added player " + username);
        player.sendObjectMessage(usernameCorrectlyAssigned);
        if (this.numOfPlayers < 0 && player.equals(this.clients.get(0))) {
            this.setupGame(player);
        } else {
            checkStartGame(player);
        }
    }

    public void removePlayer(ClientHandler player) {
        System.out.println("Removing client");
        boolean isFirstPlayer = this.clients.get(0).equals(player);
        this.clients.remove(player);
        if (this.usernames.containsValue(player)) {
            String username = null;
            for (Map.Entry<String, ClientHandler> entry : this.usernames.entrySet()) {
                if (player.equals(entry.getValue())) {
                    username = entry.getKey();
                    this.usernames.remove(username);
                    break;
                }
            }
            System.out.println(username + " disconnected");
            if (Objects.nonNull(this.game) && this.game.getGamePhase() != GamePhase.NO_PHASE) {
                this.calculateWinner();
                PlayerDisconnected playerDisconnectedMessage = new PlayerDisconnected(username);
                for (ClientHandler client : this.clients)
                    client.sendObjectMessage(playerDisconnectedMessage);
            } else {
                this.numOfPlayers = -1;
                if (isFirstPlayer && this.clients.size() > 0 && this.usernames.containsValue(this.clients.get(0)))
                    this.setupGame(this.clients.get(0));
            }
        }
    }

    private void calculateWinner() {
        String winner = "Vince lo sport";
        PlayerWinner playerWinnerMessage = new PlayerWinner(winner, "giocatore disconnesso");
        for (ClientHandler client : this.clients)
            client.sendObjectMessage(playerWinnerMessage);
    }

    private synchronized void startGame() {
        Iterator<String> iterUsernames = this.usernames.keySet().iterator();
        ArrayList<TowersColors> colors = new ArrayList<>(0);
        int towers = 8;
        colors.add(TowersColors.WHITE);
        colors.add(TowersColors.BLACK);
        if (this.numOfPlayers > 2) {
            towers = 6;
            colors.add(TowersColors.GREY);
        }
        for (int i = 0; i < this.numOfPlayers; i++) {
            Player player = new Player(iterUsernames.next(), towers);
            player.setColor(colors.get(i));
            this.game.addPlayer(player);
        }
        this.game.startGame();
        this.stateOfTheGame = new StartState(this.game, this.usernames);
        this.stateOfTheGame.chooseWizard();
    }

    public void setWizard(Wizards wizard) {
        this.game.getCurrentPlayer().setWizard(wizard);
        this.game.getCardsManager().initializeCardsForPlayer(wizard);
        if (this.game.nextPlayer()) {
            this.stateOfTheGame.chooseWizard();
        } else {
            this.stateOfTheGame = this.stateOfTheGame.changeState();
            BoardUpdate boardUpdate = this.calculateBoardUpdate();
            for (ClientHandler client : this.clients) {
                client.sendObjectMessage(boardUpdate);
            }
            this.stateOfTheGame.drawAssistantCard();
        }
    }

    public void setAssistantCard(AssistantCard card) {
        AtomicBoolean invalid = new AtomicBoolean(false);
        this.game.getPlayers().forEach(player -> {
            player.getWizard().flatMap(wizard -> this.game.getCardsManager().getCurrentCardForPlayer(wizard)).ifPresent(assistantCard -> {
                if (assistantCard.getValue() == card.getValue()) {
                    System.out.println("Invalid choice");
                    invalid.set(true);
                }
            });
        });
        if (invalid.get()) return;
        this.game.getCurrentPlayer().getWizard().ifPresent(wizard -> {
            this.game.getCardsManager().setCurrentCardForPlayer(wizard, card.getValue());
            this.game.getCardsManager().setDiscardedForPlayer(wizard, true);
            AssistantsCardUpdate cardUpdate = new AssistantsCardUpdate();
            AssistantCardChosen cardChosen = new AssistantCardChosen();
            YourPlanningPhaseTurnEnds phaseTurnEnds = new YourPlanningPhaseTurnEnds();
            this.usernames.forEach((nickname, client) -> {
                if (this.game.getCurrentPlayer().getNickname().equals(nickname)) {
                    client.sendObjectMessage(cardChosen);
                    client.sendObjectMessage(phaseTurnEnds);
                } else {
                    client.sendObjectMessage(cardUpdate);
                }
            });
            if (this.game.nextPlayer()) {
                this.stateOfTheGame.drawAssistantCard();
            } else {
                ActionPhaseTurn phaseTurn = new ActionPhaseTurn(this.game.getCurrentPlayer().getNickname());
                this.clients.forEach(client -> client.sendObjectMessage(phaseTurn));
                this.stateOfTheGame = this.stateOfTheGame.changeState();
            }
        });
    }

    private BoardUpdate calculateBoardUpdate() {
        ArrayList<PlayerUpdate> playerUpdates = new ArrayList<>();
        for (Player player : this.game.getPlayers()) {
            EnumMap<PawnsColors, Integer> diningRoom = new EnumMap<>(PawnsColors.class);
            for (PawnsColors color : PawnsColors.values())
                diningRoom.put(color, player.getSchoolBoard().getStudentsOfColor(color).size());
            ArrayList<PawnsColors> students = new ArrayList<>(
                    player.getSchoolBoard().getStudentsInEntrance()
                            .stream()
                            .map(Pawn::getColor)
                            .toList());
            ArrayList<PawnsColors> professors = new ArrayList<>(
                    player.getSchoolBoard().getProfessors()
                            .stream()
                            .map(Pawn::getColor)
                            .toList()
            );
            playerUpdates.add(
                    new PlayerUpdate(
                            player.getNickname(),
                            students,
                            professors,
                            diningRoom, player.getColor(), player.getTowers().size()
                    )
            );
        }
        ArrayList<IslandUpdate> islandsUpdate = new ArrayList<>();
        this.game.getGameBoard().getIslands().forEach(island -> {
            ArrayList<PawnsColors> students = new ArrayList<>();
            Iterator<Pawn> studentsOnIsland = island.getStudents();
            while (studentsOnIsland.hasNext()) {
                students.add(studentsOnIsland.next().getColor());
            }
            islandsUpdate.add(new IslandUpdate(
                            island.getGroupOfIslands(),
                            island.getTower().isPresent(),
                            island.isNoEntry(),
                            island.getTower()
                                    .stream()
                                    .map(Tower::getColor)
                                    .reduce(null, (currentColor, towerColor) -> currentColor = towerColor),
                            students
                    )
            );
        });
        ArrayList<CloudUpdate> cloudsUpdate = new ArrayList<>();
        this.game.getGameBoard().getClouds().forEach(cloud -> {
            ArrayList<PawnsColors> students = new ArrayList<>();
            Iterator<Pawn> studentsOnCloud = cloud.getStudents();
            while (studentsOnCloud.hasNext()) {
                students.add(studentsOnCloud.next().getColor());
            }
            cloudsUpdate.add(new CloudUpdate(cloud.isEmpty(), students));
        });
        BoardUpdateContent boardUpdateContent = new BoardUpdateContent(
                this.game.getGameBoard().getMotherNature(),
                this.game.getGameBoard().getCoinsSupply(),
                islandsUpdate,
                new ArrayList<>(this.game.getGameBoard().getAvailableProfessors()
                        .stream()
                        .map(Pawn::getColor)
                        .toList()),
                cloudsUpdate
        );
        GameUpdate gameUpdate = new GameUpdate(
                this.game.getNumOfPlayers(),
                this.game.isExpertMode(),
                this.game.getCurrentPlayer().getNickname(),
                this.game.getGamePhase()
        );
        return new BoardUpdate(playerUpdates, boardUpdateContent, gameUpdate);
    }
}
