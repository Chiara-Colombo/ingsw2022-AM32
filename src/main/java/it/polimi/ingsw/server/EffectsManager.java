package it.polimi.ingsw.server;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Handled.INoEntry;

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

    public EffectsManager() {
        this.INSTANTIATE_EFFECT = new EnumMap<>(Characters.class);
        this.initiate();
    }

    private void initiate() {
        this.INSTANTIATE_EFFECT.put(Characters.GRANDMA_HERBS, () -> new GrandmaHerbsEffectHandler(this.board.getIslandsManager().getNoEntryIsland(this.islandIndex)));
        this.INSTANTIATE_EFFECT.put(Characters.CENTAUR, () -> new CentaurEffectHandler(this.board));
        this.INSTANTIATE_EFFECT.put(Characters.FARMER, () -> new FarmerEffectHandler(this.player));
        this.INSTANTIATE_EFFECT.put(Characters.KNIGHT, () -> new KnightEffectHandler(this.player));
        this.INSTANTIATE_EFFECT.put(Characters.MONK, () -> new MonkEffectHandler(this.board.getIslandsManager(), this.pawn, this.islandIndex));
        this.INSTANTIATE_EFFECT.put(Characters.MUSHROOMS_MAN, () -> new MushroomManEffectHandler(this.color, this.game));
        this.INSTANTIATE_EFFECT.put(Characters.MAGIC_MAILMAN, () -> new MagicMailmanEffectHandler(this.card));
        this.INSTANTIATE_EFFECT.put(Characters.SPOILED_PRINCESS, () -> new SpoiledPrincessEffectHandler(this.pawn, this.player, this.board));
    }

    public EffectHandler getEffect(Characters character) {
        System.out.println("RETURNING EFFECT");
        return this.INSTANTIATE_EFFECT.get(character).instantiateEffect();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCard(AssistantCard card) {
        this.card = card;
    }

    public void setColor(PawnsColors color) {
        this.color = color;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setIslandIndex(int islandIndex) {
        this.islandIndex = islandIndex;
    }
}
