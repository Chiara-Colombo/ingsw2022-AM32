package it.polimi.ingsw.server;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.*;


import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class ServerController  {
    private final ExecutorService executor;
    private final EffectsManager effectsManager;
    private final ArrayList<ClientHandler> clients;
    private final Map<String, ClientHandler> usernames;
    private final Map<String, Integer> cardValues;
    private final EnumMap<Characters, ISetCharacterParameters> charactersParameters;
    private int numOfPlayers;
    private Game game;
    private State stateOfTheGame;

    public ServerController() {
        this.numOfPlayers = -1;
        this.clients = new ArrayList<>();
        this.usernames = new HashMap<>();
        this.cardValues = new HashMap<>();
        this.executor = Executors.newCachedThreadPool();
        this.charactersParameters = new EnumMap<>(Characters.class);
        this.effectsManager = new EffectsManager();
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
            player.close();
        }
        ClientHandler client = new ClientHandler(player, this);
        executor.submit(client);
        this.clients.add(client);
        ServerMessage RequestUsername = new RequestUsername();
        client.sendObjectMessage(RequestUsername);
    }

    /**
     * Method that sets the number of player
     * @param numOfPlayers chosen by the first player
     * @param player
     */
    public void setNumOfPlayers(int numOfPlayers, ClientHandler player) {
        this.numOfPlayers = numOfPlayers;
        this.setupGameMode(player);
    }

    public void setGameMode(boolean expertMode, ClientHandler player) throws IOException {
        BufferedReader input = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("assistantCards.json"))
                )
        );
        String jsonCards = input.readLine();
        this.game = new Game(this.numOfPlayers, expertMode, jsonCards);
        if (expertMode) {
            this.setCharactersParameters();
        }
        checkStartGame(player);
    }

    private void setCharactersParameters() {
        this.charactersParameters.put(Characters.GRANDMA_HERBS, () -> {
            if (this.game.takeGrandmaHerbsNoEntryTile()) {
                SelectIslandRequest selectIslandRequest = new SelectIslandRequest(
                        Characters.GRANDMA_HERBS, new ArrayList<>(this.game.getGameBoard().getIslandsManager().getAllIslands()
                        .stream()
                        .filter(island -> !island.isNoEntry())
                        .map(IIsland::getIndex)
                        .toList()
                )
                );
                this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(selectIslandRequest);
            } else {
                CharacterCardError message = new CharacterCardError("Non ci sono altre tessere disponibili!");
                this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(message);
            }
        });
        this.charactersParameters.put(Characters.CENTAUR, () -> {
            this.effectsManager.setBoard(this.game.getGameBoard());
            this.applyEffect();
        });
        this.charactersParameters.put(Characters.FARMER, () -> {
            this.effectsManager.setPlayer(this.game.getCurrentPlayer());
            this.applyEffect();
        });
        this.charactersParameters.put(Characters.KNIGHT, () -> {
            this.effectsManager.setPlayer(this.game.getCurrentPlayer());
            this.applyEffect();
        });
        this.charactersParameters.put(Characters.MONK, () -> {
            if (this.game.getMonkStudents().size() > 0) {
                SelectPawnRequest selectPawnRequest = new SelectPawnRequest(
                        Characters.MONK, new ArrayList<>(
                        this.game.getMonkStudents()
                                .stream()
                                .map(Pawn::getColor)
                                .toList()
                )
                );
                this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(selectPawnRequest);
            } else {
                CharacterCardError message = new CharacterCardError("Non ci sono più studenti!");
                this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(message);
            }
        });
        this.charactersParameters.put(Characters.MUSHROOMS_MAN, () -> {
            SelectColorRequest selectColorRequest = new SelectColorRequest(Characters.MUSHROOMS_MAN, new ArrayList<>(List.of(PawnsColors.values())));
            this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(selectColorRequest);
        });
        this.charactersParameters.put(Characters.MAGIC_MAILMAN, () -> {
            this.game.getCurrentPlayer().getWizard().ifPresentOrElse(wizard -> {
                this.game.getCardsManager().getCurrentCardForPlayer(wizard).ifPresentOrElse(card -> {
                    this.effectsManager.setCard(card);
                    this.applyEffect();
                }, () -> {
                    CharacterCardError message = new CharacterCardError("Non hai una assistente!");
                    this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(message);
                });
            }, () -> {
                CharacterCardError message = new CharacterCardError("Non hai un mazzo di carte!");
                this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(message);
            });
        });
        this.charactersParameters.put(Characters.SPOILED_PRINCESS, () -> {
            if (this.game.getSpoiledPrincessStudents().size() > 0) {
                SelectPawnRequest selectPawnRequest = new SelectPawnRequest(
                        Characters.SPOILED_PRINCESS, new ArrayList<>(
                        this.game.getSpoiledPrincessStudents()
                                .stream()
                                .map(Pawn::getColor)
                                .toList()
                )
                );
                this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(selectPawnRequest);
            } else {
                CharacterCardError message = new CharacterCardError("Non ci sono più studenti!");
                this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(message);
            }
        });
    }

    private void checkStartGame(ClientHandler player) {
        if (this.numOfPlayers != this.clients.size() || this.clients.size() != this.usernames.size() || Objects.isNull(this.game)) {
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

    /**
     * Method that sets the username
     * @param username is the username chosen by the user
     * @param player is the player to whom assign the username
     */
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
        this.cardValues.put(username, 0);
        UsernameCorrectlyAssigned usernameCorrectlyAssigned = new UsernameCorrectlyAssigned();
        player.sendObjectMessage(usernameCorrectlyAssigned);
        if (this.numOfPlayers < 0 && player.equals(this.clients.get(0))) {
            this.setupGame(player);
        } else {
            checkStartGame(player);
        }
    }

    /**
     * Method that removes a player when it disconnects
     * @param player that has to be removed
     */
    public void removePlayer(ClientHandler player) {
        boolean isFirstPlayer = this.clients.get(0).equals(player);
        String username = player.getNickname();
        player.close();
        this.clients.remove(player);
        if (this.usernames.containsKey(username)) {
            this.usernames.remove(username);
            if (Objects.nonNull(this.game) && this.game.getGamePhase() != GamePhase.NO_PHASE) {
                PlayerDisconnected playerDisconnectedMessage = new PlayerDisconnected(username);
                for (ClientHandler client : this.clients)
                    client.sendObjectMessage(playerDisconnectedMessage);
                this.stateOfTheGame = new EndState(this.game, this.usernames);
                this.stateOfTheGame.endGame();
                this.endController();
            } else {
                this.numOfPlayers = -1;
                if (isFirstPlayer && this.clients.size() > 0 && this.usernames.containsValue(this.clients.get(0)))
                    this.setupGame(this.clients.get(0));
            }
        }
    }

    private boolean isEndOfGame(boolean endOfTurn) {
        for (Player player : this.game.getPlayers()) {
            if (player.getTowers() == 0) return true;
        }
        ArrayList<Integer> differentGroups = new ArrayList<>();
        for (int i = 0; i < this.game.getGameBoard().getIslandsManager().getIslandsSize(); i++) {
            int group = this.game.getGameBoard().getIslandsManager().getIslandGroup(i);
            if (!differentGroups.contains(group)) {
                differentGroups.add(group);
            }
        }
        if (differentGroups.size() <= 3) return true;
        if (endOfTurn) {
            if (this.game.getGameBoard().isBagEmpty()) return true;
            AtomicBoolean cardsNotAvailable = new AtomicBoolean(false);
            for (Player player : this.game.getPlayers()) {
                player.getWizard().ifPresent(wizard -> {
                    if (this.game.getCardsManager().getAvailableCardsForPlayer(wizard).size() == 0)
                        cardsNotAvailable.set(true);
                });
            }
            return cardsNotAvailable.get();
        }
        return false;
    }

    /**
     * This method is called when the game starts
     */
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

    private void sendUpdate() {
        final BoardUpdate boardUpdate = this.calculateBoardUpdate();
        this.clients.forEach(client -> client.sendObjectMessage(boardUpdate));
    }

    /**
     * method that set the Wizard
     * @param wizard that the player has chosen
     */
    public void setWizard(Wizards wizard) {
        this.game.getCurrentPlayer().setWizard(wizard);
        this.game.getCardsManager().initializeCardsForPlayer(wizard);
        if (this.game.nextPlayer()) {
            this.stateOfTheGame.chooseWizard();
        } else {
            this.stateOfTheGame = this.stateOfTheGame.changeState();
            this.stateOfTheGame.fillClouds();
            this.sendUpdate();
            this.stateOfTheGame.drawAssistantCard();
        }
    }

    public void setAssistantCard(AssistantCard card) {
        AtomicBoolean invalid = new AtomicBoolean(false);
        this.game.getPlayers().forEach(
                player -> player.getWizard().flatMap(wizard -> this.game.getCardsManager().getCurrentCardForPlayer(wizard)).ifPresent(
                        assistantCard -> {
                            if (assistantCard.getValue() == card.getValue()) {
                                invalid.set(true);
                            }
                        })
        );
        if (invalid.get()) {
            invalid.set(false);
            this.game.getCurrentPlayer().getWizard().ifPresent(wizard -> {
                for (AssistantCard differentValidCard : this.game.getCardsManager().getAvailableCardsForPlayer(wizard)) {
                    if (differentValidCard.getValue() != card.getValue()) {
                        AtomicBoolean valid = new AtomicBoolean(true);
                        for (Player player : this.game.getPlayers()) {
                            player.getWizard().ifPresent(playerWizard -> {
                                if (!playerWizard.equals(wizard)) {
                                    this.game.getCardsManager().getCurrentCardForPlayer(playerWizard).ifPresent(playerCard -> {
                                        if (playerCard.getValue() == differentValidCard.getValue())
                                            valid.set(false);
                                    });
                                }
                            });
                            if (!valid.get()) break;
                        }
                        if (valid.get()) {
                            invalid.set(true);
                            return;
                        }
                    }
                }
            });
        }
        if (invalid.get()) return;
        this.game.getCurrentPlayer().getWizard().ifPresent(wizard -> {
            this.game.getCardsManager().setCurrentCardForPlayer(wizard, card.getValue());
            this.game.getCardsManager().setDiscardedForPlayer(wizard, true);
            this.cardValues.replace(this.game.getCurrentPlayer().getNickname(), card.getValue());
            AssistantsCardUpdate cardUpdate = new AssistantsCardUpdate(card,this.game.getCurrentPlayer().getNickname());
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
                Map<String, Integer> sorted = cardValues .entrySet() .stream()
                        .sorted(comparingByValue())
                        .collect( toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
                System.out.println(sorted);
                ArrayList<String> keys = new ArrayList<>(sorted.keySet());
                this.game.ChangePlayersOrder(keys);
                System.out.print("Game --> getPlayers = ");
                for(Player player : this.game.getPlayers()) {
                    System.out.print( "[ " + player.getNickname() + " ] ");
                }
                System.out.println("");
                System.out.print("Real order determinated through cards -->  = ");
                for(String player : this.game.getPlayersCopyList()){
                    System.out.print("[ " + player + " ] ");
                }
                System.out.println("");
                System.out.print("Current Player --> ");
                System.out.println(this.game.getCurrentPlayer().getNickname());
                ActionPhaseTurn phaseTurn = new ActionPhaseTurn(this.game.getCurrentPlayer().getNickname());
                this.clients.forEach(client -> client.sendObjectMessage(phaseTurn));
                this.stateOfTheGame = this.stateOfTheGame.changeState();
                this.stateOfTheGame.moveStudent(false);
            }
        });
    }

    public void moveStudent(int pawnIndex, int islandIndex, boolean moveOnSchoolBoard) {
        Pawn student;
        try {
            student = this.game.getCurrentPlayer().removeStudent(pawnIndex);
        } catch (IndexOutOfBoundsException e) {
            ServerMessage errorOnPawn = new ErrorOnPawnResponse();
            this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(errorOnPawn);
            this.stateOfTheGame.moveStudent(true);
            return;
        }
        if (moveOnSchoolBoard) {
            if (this.game.getCurrentPlayer().addStudentInDiningRoom(student)) {
                if (this.game.getGameBoard().getCoinsSupply() > 0) {
                    this.game.getGameBoard().giveCoin();
                    this.game.getCurrentPlayer().earnCoin();
                }
            }
            this.checkProfessor(student.getColor());
        } else {
            try {
                this.game.getGameBoard().getIslandsManager().setStudentOnIsland(student, islandIndex);
            } catch (IndexOutOfBoundsException e) {
                this.game.getCurrentPlayer().addStudentInEntrance(student);
                ServerMessage errorOnPawn = new ErrorOnPawnResponse();
                this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(errorOnPawn);
                this.stateOfTheGame.moveStudent(true);
                return;
            }
        }
        this.sendUpdate();
        ActionPhaseTurn phaseTurn = new ActionPhaseTurn(this.game.getCurrentPlayer().getNickname());
        this.clients.forEach(client -> client.sendObjectMessage(phaseTurn));
        if (!this.stateOfTheGame.moveStudent(false)) {
            this.stateOfTheGame.moveMN();
        }
    }

    private void checkProfessor(PawnsColors color) {
        final Player currentPlayer = this.game.getCurrentPlayer();
        final int studentsOnDiningRoom = currentPlayer.getSchoolBoard().getStudentsOfColor(color).size() + currentPlayer.getExtraStudent();
        this.game.getPlayers().forEach(player -> {
            if (player.getNickname().equals(currentPlayer.getNickname()))
                return;
            for (int i = 0; i < player.getSchoolBoard().getProfessors().size(); i++) {
                if (player.getSchoolBoard().getProfessors().get(i).getColor().equals(color)) {
                    if (player.getSchoolBoard().getStudentsOfColor(color).size() < studentsOnDiningRoom) {
                        Pawn professor = player.removeProfessor(i);
                        this.game.getCurrentPlayer().addProfessor(professor);
                        break;
                    }
                }
            }
        });
        for (int i = 0; i < this.game.getGameBoard().getAvailableProfessors().size(); i++) {
            if (this.game.getGameBoard().getAvailableProfessors().get(i).equals(color)) {
                Pawn professor = this.game.getGameBoard().removeProfessor(i);
                this.game.getCurrentPlayer().addProfessor(professor);
            }
        }
    }

    public void moveMotherNature(int position) {
        this.game.getGameBoard().moveMotherNature(position);
        this.sendUpdate();
        if (this.game.getGameBoard().getIslandsManager().getIsland(position).isNoEntry()) {
            new GrandmaHerbsEffectHandler(this.game.getGameBoard().getIslandsManager().getNoEntryIsland(position)).restoreIsland();
            this.game.putGrandmaHerbsNoEntryTile();
        } else {
            this.checkTowers();
        }
        if (this.isEndOfGame(false)) {
            this.stateOfTheGame = new EndState(this.game, this.usernames);
            this.stateOfTheGame.endGame();
            this.endController();
        } else {
            if (this.game.getGameBoard().isBagEmpty()) this.chooseCloud(-1);
            else this.stateOfTheGame.chooseCloud();
        }
    }

    public void chooseCloud(int index) {
        if (index >= 0) {
            while (!this.game.getGameBoard().getClouds().get(index).isEmpty()) {
                Pawn student = this.game.getGameBoard().removeStudentFromCloud(index);
                this.game.getCurrentPlayer().addStudentInEntrance(student);
            }
        }
        YourActionPhaseTurnEnds turnEnds = new YourActionPhaseTurnEnds();
        this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(turnEnds);
        this.game.removeCharacterEffect();
        this.sendUpdate();
        if (this.game.nextPlayer()) {
            ActionPhaseTurn phaseTurn = new ActionPhaseTurn(this.game.getCurrentPlayer().getNickname());
            this.clients.forEach(client -> client.sendObjectMessage(phaseTurn));
            this.stateOfTheGame.moveStudent(false);
        } else {
            if (this.isEndOfGame(true)) {
                this.stateOfTheGame = new EndState(this.game, this.usernames);
                this.stateOfTheGame.endGame();
                this.endController();
            } else {
                this.stateOfTheGame = this.stateOfTheGame.changeState();
                this.stateOfTheGame.fillClouds();
                this.sendUpdate();
                this.stateOfTheGame.drawAssistantCard();
            }
        }
    }

    void endController() {
        this.clients.forEach(ClientHandler::close);
        this.clients.clear();
        this.usernames.clear();
        this.numOfPlayers = -1;
        this.cardValues.clear();
        this.game = null;
    }

    private void checkTowers() {
        final int motherNature = this.game.getGameBoard().getMotherNature();
        this.game.getGameBoard().getIslandsManager().getIsland(motherNature).getTower().ifPresentOrElse(tower -> {
            int maxInfluence = 0, playerIndex = -1, actualPlayerIndex = -1;
            boolean equalsInfluences = true;
            ArrayList<Player> players = this.game.getPlayers();
            for (int i = 0; i < players.size(); i++) {
                int playerInfluence = this.influenceForPlayer(players.get(i));
                if (players.get(i).getColor().equals(tower.getColor())) {
                    actualPlayerIndex = i;
                    playerInfluence += this.game.getGameBoard().getTowersInfluence();
                }
                if (playerInfluence == maxInfluence) equalsInfluences = true;
                if (playerInfluence > maxInfluence) {
                    equalsInfluences = false;
                    maxInfluence = playerInfluence;
                    playerIndex = i;
                }
            }
            if (equalsInfluences) playerIndex = -1;
            if (playerIndex >= 0 && this.game.getPlayers().get(playerIndex).getTowers() > 0 && playerIndex != actualPlayerIndex) {
                final int groupOfIslands = this.game.getGameBoard().getIslandsManager().getIslandGroup(motherNature);
                for (int i = 0; i < this.game.getGameBoard().getIslandsManager().getIslandsSize(); i++) {
                    if (this.game.getGameBoard().getIslandsManager().getIslandGroup(i) == groupOfIslands){
                        final int finalActualPlayerIndex = actualPlayerIndex;
                        this.game.getGameBoard().getIslandsManager().getIsland(i).getTower().ifPresent(tower1 -> players.get(finalActualPlayerIndex).addTower(tower1));
                        this.game.getGameBoard().getIslandsManager().setTowerOnIsland(players.get(playerIndex).removeTower(), i);
                    }
                }
                this.sendUpdate();
                this.checkIslandsMerge(groupOfIslands, players.get(playerIndex).getColor());
            }
        }, () -> {
            int maxInfluence = 0, playerIndex = -1;
            ArrayList<Player> players = this.game.getPlayers();
            boolean equalsInfluence = true;
            for (int i = 0; i < players.size(); i++) {
                int playerInfluence = this.influenceForPlayer(players.get(i));
                if (playerInfluence == maxInfluence) equalsInfluence = true;
                if (playerInfluence > maxInfluence) {
                    equalsInfluence = false;
                    maxInfluence = playerInfluence;
                    playerIndex = i;
                }
            }
            if (equalsInfluence) playerIndex = -1;
            if (playerIndex >= 0 && players.get(playerIndex).getTowers() > 0) {
                final int groupOfIslands = this.game.getGameBoard().getIslandsManager().getIslandGroup(motherNature);
                for (int i = 0; i < this.game.getGameBoard().getIslandsManager().getIslandsSize(); i++) {
                    if (this.game.getGameBoard().getIslandsManager().getIslandGroup(i) == groupOfIslands){
                        Tower tower = this.game.getPlayers().get(playerIndex).removeTower();
                        this.game.getGameBoard().getIslandsManager().setTowerOnIsland(tower, i);
                    }
                }
                this.sendUpdate();
                this.checkIslandsMerge(groupOfIslands, players.get(playerIndex).getColor());
            }
        });
    }

    synchronized void useCharacterCard(Characters character) {
        if (this.game.getCurrentPlayer().getCoins() >= this.game.getCharacterCost(character)) {
            if (!this.game.isCharacterActive()) {
                this.charactersParameters.get(character).setCharacterParameters();
                this.game.getCurrentPlayer().payCoins(this.game.getCharacterCost(character));
                this.game.getGameBoard().addCoins(this.game.getCharacterCost(character));
                this.game.activateCharacter(character);
            } else {
                CharacterCardError message = new CharacterCardError("Hai già usato una carta in questo turno!");
                this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(message);
            }
        } else {
            NotEnoughCoins notEnoughCoins = new NotEnoughCoins();
            this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(notEnoughCoins);
        }
    }

    void applyEffect() {
        this.game.useCharacterEffect(this.effectsManager.getEffect(this.game.getActiveCharacter()));
        CharacterCardUsed message = new CharacterCardUsed(this.game.getCurrentPlayer().getNickname(), this.game.getActiveCharacter());
        this.clients.forEach(client -> client.sendObjectMessage(message));
        this.sendUpdate();
        this.stateOfTheGame.resumeState();
    }

    void selectColor(PawnsColors color) {
        this.effectsManager.setGame(this.game);
        this.effectsManager.setColor(color);
        this.applyEffect();
    }

    void selectPawn(int pawnIndex) {
        if (this.game.getActiveCharacter().equals(Characters.SPOILED_PRINCESS)) {
            this.effectsManager.setPlayer(this.game.getCurrentPlayer());
            this.effectsManager.setBoard(this.game.getGameBoard());
            this.effectsManager.setPawn(this.game.getSpoiledPrincessStudents().remove(pawnIndex));
            this.game.getGameBoard().drawFromBag().ifPresent(pawn -> this.game.getSpoiledPrincessStudents().add(pawn));
            this.applyEffect();
        }
        if (this.game.getActiveCharacter().equals(Characters.MONK)) {
            this.effectsManager.setPawn(this.game.getMonkStudents().remove(pawnIndex));
            this.game.getGameBoard().drawFromBag().ifPresent(pawn -> this.game.getMonkStudents().add(pawn));
            SelectIslandRequest selectIslandRequest = new SelectIslandRequest(
                    this.game.getActiveCharacter(), new ArrayList<>(
                            this.game.getGameBoard().getIslandsManager().getAllIslands()
                                    .stream()
                                    .map(IIsland::getIndex)
                                    .toList()
                    )
            );
            this.usernames.get(this.game.getCurrentPlayer().getNickname()).sendObjectMessage(selectIslandRequest);
        }
    }

    void selectIsland(int islandIndex) {
        this.effectsManager.setIslandIndex(islandIndex);
        this.effectsManager.setBoard(this.game.getGameBoard());
        this.applyEffect();
    }

    private int influenceForPlayer(Player player) {
        int playerInfluence = 0;
        ArrayList<Pawn> professors = player.getSchoolBoard().getProfessors();
        for (Pawn professor : professors) {
            PawnsColors color = professor.getColor();
            Iterator<Pawn> students = this.game.getGameBoard().getIslandsManager().getIsland(this.game.getGameBoard().getMotherNature()).getStudents();
            while (students.hasNext()) {
                if (students.next().getColor().equals(color))
                    playerInfluence += this.game.getInfluenceForColor(color);
            }
        }
        playerInfluence += player.getExtraInfluence();
        return playerInfluence;
    }

    private void checkIslandsMerge(int groupOfIsland, TowersColors color) {
        final int islands = this.game.getGameBoard().getIslandsManager().getIslandsSize();
        int minIndex = -1;
        int maxIndex = -1;
        AtomicInteger leftGroup = new AtomicInteger(-1), rightGroup = new AtomicInteger(-1);
        for (int i = 0; i < islands; i++)
            if (this.game.getGameBoard().getIslandsManager().getIslandGroup(i) == groupOfIsland) {
                minIndex = i;
                break;
            }
        for (int i = islands - 1; i >= 0; i--)
            if (this.game.getGameBoard().getIslandsManager().getIslandGroup(i) == groupOfIsland) {
                maxIndex = i;
                break;
            }
        final int leftIndex = (minIndex - 1 >= 0) ? (minIndex - 1) : (minIndex + islands - 1),
                rightIndex = (maxIndex + 1 < islands) ? (maxIndex + 1) : (maxIndex - islands + 1);
        this.game.getGameBoard().getIslandsManager().getIsland(leftIndex).getTower().ifPresent(tower -> {
            if (tower.getColor().equals(color))
                leftGroup.set(this.game.getGameBoard().getIslandsManager().getIslandGroup(leftIndex));
        });
        this.game.getGameBoard().getIslandsManager().getIsland(rightIndex).getTower().ifPresent(tower -> {
            if (tower.getColor().equals(color))
                rightGroup.set(this.game.getGameBoard().getIslandsManager().getIslandGroup(rightIndex));
        });
        if (leftGroup.get() >= 0 && rightGroup.get() >= 0) {
            this.game.getGameBoard().getIslandsManager().mergeIslands(groupOfIsland, leftGroup.get(), rightGroup.get());
            this.sendUpdate();
        } else {
            if (leftGroup.get() >= 0) {
                this.game.getGameBoard().getIslandsManager().mergeIslands(groupOfIsland, leftGroup.get());
                this.sendUpdate();
            } if (rightGroup.get() >= 0) {
                this.game.getGameBoard().getIslandsManager().mergeIslands(groupOfIsland, rightGroup.get());
                this.sendUpdate();
            }
        }
    }

    private BoardUpdate calculateBoardUpdate() {
        ArrayList<PlayerUpdate> playerUpdates = this.calculatePlayersUpdate();
        BoardUpdateContent boardUpdateContent = this.calculateBoardUpdateContent();
        GameUpdate gameUpdate = new GameUpdate(
                this.game.getNumOfPlayers(),
                this.game.getGrandmaHerbsNoEntryTiles(),
                this.game.isExpertMode(),
                this.game.getCurrentPlayer().getNickname(),
                this.game.getGamePhase(),
                this.game.getValidCharacters(),
                new ArrayList<>(this.game.getMonkStudents().stream().map(Pawn::getColor).toList()),
                new ArrayList<>(this.game.getSpoiledPrincessStudents().stream().map(Pawn::getColor).toList())
        );
        return new BoardUpdate(playerUpdates, boardUpdateContent, gameUpdate);
    }

    private ArrayList<PlayerUpdate> calculatePlayersUpdate() {
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
                            diningRoom, player.getColor(), player.getTowers(), player.getCoins()
                    )
            );
        }
        return playerUpdates;
    }

    private BoardUpdateContent calculateBoardUpdateContent() {
        ArrayList<ArrayList<IslandUpdate>> islandsUpdate = new ArrayList<>();
        ArrayList<IslandUpdate> groupOfIslandsUpdate = new ArrayList<>();
        ArrayList<ArrayList<IIsland>> islands = this.game.getGameBoard().getIslandsManager().getIslands();
        for (ArrayList<IIsland> group : islands) {
            groupOfIslandsUpdate.clear();
            for (IIsland island : group) {
                ArrayList<PawnsColors> students = new ArrayList<>();
                Iterator<Pawn> islandStudents = island.getStudents();
                while (islandStudents.hasNext())
                    students.add(islandStudents.next().getColor());
                groupOfIslandsUpdate.add(
                        new IslandUpdate(
                                island.getIndex(),
                                island.getTower().isPresent(),
                                island.isNoEntry(),
                                island.getTower()
                                        .stream()
                                        .map(Tower::getColor)
                                        .reduce(null, (currentColor, towerColor) -> currentColor = towerColor),
                                students
                        )
                );
            }
            islandsUpdate.add(new ArrayList<>(groupOfIslandsUpdate));
        }
        ArrayList<CloudUpdate> cloudsUpdate = new ArrayList<>();
        this.game.getGameBoard().getClouds().forEach(cloud -> {
            ArrayList<PawnsColors> students = new ArrayList<>();
            Iterator<Pawn> studentsOnCloud = cloud.getStudents();
            while (studentsOnCloud.hasNext()) {
                students.add(studentsOnCloud.next().getColor());
            }
            cloudsUpdate.add(new CloudUpdate(cloud.isEmpty(), students));
        });
        return new BoardUpdateContent(
                this.game.getGameBoard().getMotherNature(),
                this.game.getGameBoard().getCoinsSupply(),
                islandsUpdate,
                this.game.getGameBoard().getAvailableProfessors(),
                cloudsUpdate
        );
    }
}
