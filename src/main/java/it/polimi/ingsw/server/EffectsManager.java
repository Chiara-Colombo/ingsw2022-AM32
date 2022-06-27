package it.polimi.ingsw.server;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.EnumMap;

public class EffectsManager {
    private Player player;
    private Pawn pawn;
    private Board board;
    private AssistantCard card;
    private PawnsColors color;
    private Game game;
    private int islandIndex;
    private final EnumMap<Characters, IInstantiateEffect> INSTANTIATE_EFFECT;

    /**
     * Class constructor
     */

    public EffectsManager() {
        this.INSTANTIATE_EFFECT = new EnumMap<>(Characters.class);
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
      //  this.INSTANTIATE_EFFECT.put(Characters.JESTER, () -> new JesterEffectHandler(this.pawn,this.player));
        this.INSTANTIATE_EFFECT.put(Characters.WITCH, () -> new WitchEffectHandler(this.game,this.color));

        //this.INSTANTIATE_EFFECT.put(Characters.MINSTREL, () -> new MinstrelEffectHandler(this.pawn,this.player));
        // this.INSTANTIATE_EFFECT.put(Characters.HERALD, () -> new HeraldEffectHandler());
        // this.INSTANTIATE_EFFECT.put(Characters.JESTER, () -> new WitchEffectHandler());
    }

    /**
     * Method that gets an effect
     * @param character Character card
     * @return
     */

    public EffectHandler getEffect(Characters character) {
        return this.INSTANTIATE_EFFECT.get(character).instantiateEffect();
    }

    /**
     * Method that sets a player
     * @param player the player
     */

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Setter for pawns
     * @param pawn
     */

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    /**
     * Setter for the board
     * @param board the game board
     */

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Setter for Assistant Card
     * @param card Assistant Card
     */

    public void setCard(AssistantCard card) {
        this.card = card;
    }

    /**
     * Setter for pawns colors
     * @param color color of a pawn
     */

    public void setColor(PawnsColors color) {
        this.color = color;
    }

    /**
     * Setter for the game
     * @param game the game
     */

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Setter for the index of an island
     * @param islandIndex index that identifies an island
     */

    public void setIslandIndex(int islandIndex) {
        this.islandIndex = islandIndex;
    }
}
