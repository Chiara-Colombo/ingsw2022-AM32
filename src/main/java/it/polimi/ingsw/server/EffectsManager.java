package it.polimi.ingsw.server;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.EnumMap;

public class EffectsManager {
    private int islandIndex;
    private Player player;
    private Pawn pawn;
    private Board board;
    private AssistantCard card;
    private PawnsColors color;
    private Game game;
    private final ArrayList<Integer> entrancePawnsIndexes, jesterPawnsIndexes;
    private final ArrayList<PawnsColors> diningRoomPawns;
    private final EnumMap<Characters, IInstantiateEffect> INSTANTIATE_EFFECT;

    /**
     * Class constructor
     */

    public EffectsManager() {
        this.INSTANTIATE_EFFECT = new EnumMap<>(Characters.class);
        this.entrancePawnsIndexes = new ArrayList<>();
        this.diningRoomPawns = new ArrayList<>();
        this.jesterPawnsIndexes = new ArrayList<>();
        this.initiate();
    }

    /**
     * method that initiate the Character Cards
     */

    private void initiate() {
        this.INSTANTIATE_EFFECT.put(Characters.GRANDMA_HERBS, () -> new GrandmaHerbsEffectHandler(this.board.getIslandsManager().getNoEntryIsland(this.islandIndex)));
        this.INSTANTIATE_EFFECT.put(Characters.CENTAUR, () -> new CentaurEffectHandler(this.board));
        this.INSTANTIATE_EFFECT.put(Characters.FARMER, () -> new FarmerEffectHandler(this.player));
        this.INSTANTIATE_EFFECT.put(Characters.KNIGHT, () -> new KnightEffectHandler(this.player));
        this.INSTANTIATE_EFFECT.put(Characters.MONK, () -> new MonkEffectHandler(this.board.getIslandsManager(), this.pawn, this.islandIndex));
        this.INSTANTIATE_EFFECT.put(Characters.MUSHROOMS_MAN, () -> new MushroomManEffectHandler(this.color, this.game));
        this.INSTANTIATE_EFFECT.put(Characters.MAGIC_MAILMAN, () -> new MagicMailmanEffectHandler(this.card));
        this.INSTANTIATE_EFFECT.put(Characters.SPOILED_PRINCESS, () -> new SpoiledPrincessEffectHandler(this.pawn, this.player, this.board));
        this.INSTANTIATE_EFFECT.put(Characters.JESTER, () -> new JesterEffectHandler(this.player, this.game, this.jesterPawnsIndexes, this.entrancePawnsIndexes));
        this.INSTANTIATE_EFFECT.put(Characters.THIEF, () -> new ThiefEffectHandler(this.game, this.color));
        this.INSTANTIATE_EFFECT.put(Characters.MINSTREL, () -> new MinstrelEffectHandler(this.player, this.entrancePawnsIndexes, this.diningRoomPawns));
        this.INSTANTIATE_EFFECT.put(Characters.HERALD, () -> new HeraldEffectHandler(this.board.getIslandsManager().getExtraInfluenceIsland(this.islandIndex)));
    }

    /**
     * Method that gets an effect
     * @param character Character card
     * @return
     */

    EffectHandler getEffect(Characters character) {
        return this.INSTANTIATE_EFFECT.get(character).instantiateEffect();
    }

    /**
     * Method that sets a player
     * @param player the player
     */

    void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Setter for pawns
     * @param pawn
     */

    void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    /**
     * Setter for the board
     * @param board the game board
     */

    void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Setter for Assistant Card
     * @param card Assistant Card
     */

    void setCard(AssistantCard card) {
        this.card = card;
    }

    /**
     * Setter for pawns colors
     * @param color color of a pawn
     */

    void setColor(PawnsColors color) {
        this.color = color;
    }

    /**
     * Setter for the game
     * @param game the game
     */

    void setGame(Game game) {
        this.game = game;
    }

    /**
     * Setter for the index of an island
     * @param islandIndex index that identifies an island
     */

    void setIslandIndex(int islandIndex) {
        this.islandIndex = islandIndex;
    }

    /**
     * Setter for the pawns that have to be swapped
     * @param pawnIndex pawns that have to be swapped
     */

    boolean addEntrancePawnIndex(int pawnIndex) {
        if (this.entrancePawnsIndexes.contains(pawnIndex)) return false;
        this.entrancePawnsIndexes.add(pawnIndex);
        return true;
    }

    int entrancePawnIndexesSize() {
        return this.entrancePawnsIndexes.size();
    }

    void clearEntrancePawnIndex() {
        this.entrancePawnsIndexes.clear();
    }

    void addDiningRoomPawn(PawnsColors pawn) {
        this.diningRoomPawns.add(pawn);
    }

    void clearDiningRoomPawns() {
        this.diningRoomPawns.clear();
    }

    int neededPawnsInDiningRoom(PawnsColors color) {
        return this.diningRoomPawns.stream().filter(pawn -> pawn.equals(color)).toList().size();
    }

    boolean addJesterPawn(int pawnIndex) {
        if (this.jesterPawnsIndexes.contains(pawnIndex)) return false;
        this.jesterPawnsIndexes.add(pawnIndex);
        return true;
    }

    void clearJesterPawnsIndexes() {
        this.jesterPawnsIndexes.clear();
    }

    int jesterPawnsIndexesSize() {
        return this.jesterPawnsIndexes.size();
    }
}
