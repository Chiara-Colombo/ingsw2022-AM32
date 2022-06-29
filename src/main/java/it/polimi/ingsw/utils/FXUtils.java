package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.model.PawnsColors;
import it.polimi.ingsw.model.TowersColors;
import it.polimi.ingsw.model.Wizards;
import javafx.scene.image.Image;

import java.util.*;

public class FXUtils {
    public static final ArrayList<Image> ASSISTANT_CARDS_IMAGES = new ArrayList<>(List.of(
            new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/assistente_1.png"))),
            new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/assistente_2.png"))),
            new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/assistente_3.png"))),
            new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/assistente_4.png"))),
            new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/assistente_5.png"))),
            new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/assistente_6.png"))),
            new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/assistente_7.png"))),
            new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/assistente_8.png"))),
            new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/assistente_9.png"))),
            new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/assistente_10.png")))
    ));
    public static final Image CLOUD_IMAGE = new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/cloud.png")));
    public static final Image ISLAND_IMAGE = new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/island.png")));
    public static final Image MOTHER_NATURE_IMAGE = new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/mother_nature.png")));
    public static final EnumMap<Characters, Image> CHARACTERS_IMAGE_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(Characters.MONK, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_monk.jpg")))),
            Map.entry(Characters.CENTAUR, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_centaur.jpg")))),
            Map.entry(Characters.FARMER, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_farmer.jpg")))),
            Map.entry(Characters.GRANDMA_HERBS, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_grandma_herbs.jpg")))),
            Map.entry(Characters.KNIGHT, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_knight.jpg")))),
            Map.entry(Characters.MAGIC_MAILMAN, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_magic_mailman.jpg")))),
            Map.entry(Characters.MUSHROOMS_MAN, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_mushroom_man.jpg")))),
            Map.entry(Characters.SPOILED_PRINCESS, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_spoiled_princess.jpg")))),
            Map.entry(Characters.JESTER, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_jester.jpg")))),
            Map.entry(Characters.THIEF, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_thief.jpg")))),
            Map.entry(Characters.HERALD, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_herald.jpg")))),
            Map.entry(Characters.MINSTREL, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_minstrel.jpg"))))
    ));
    public static final EnumMap<Characters, Image> EMPTY_CHARACTERS_IMAGE_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(Characters.MONK, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_monk_empty.jpg")))),
            Map.entry(Characters.MUSHROOMS_MAN, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_mushroom_man_empty.jpg")))),
            Map.entry(Characters.SPOILED_PRINCESS, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_spoiled_princess_empty.jpg")))),
            Map.entry(Characters.THIEF, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/character_thief_empty.jpg"))))
    ));
    public static final Image COIN_IMAGE = new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/coin.png")));
    public static final Image NO_ENTRY_TILE_IMAGE = new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/no_entry_tile.png")));
    public static final EnumMap<Wizards, Image> WIZARDS_IMAGE_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(Wizards.FIRST, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/wizard_1.png")))),
            Map.entry(Wizards.SECOND, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/wizard_2.png")))),
            Map.entry(Wizards.THIRD, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/wizard_3.png")))),
            Map.entry(Wizards.FOURTH, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/wizard_4.png"))))
    ));
    public static final EnumMap<PawnsColors, Image> PAWNS_COLORS_IMAGE_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(PawnsColors.PINK, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/pawn_pink.png")))),
            Map.entry(PawnsColors.BLUE, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/pawn_blue.png")))),
            Map.entry(PawnsColors.RED, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/pawn_red.png")))),
            Map.entry(PawnsColors.GREEN, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/pawn_green.png")))),
            Map.entry(PawnsColors.YELLOW, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/pawn_yellow.png"))))
    ));
    public static final EnumMap<PawnsColors, Image> PROFESSOR_COLORS_IMAGE_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(PawnsColors.PINK, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/professor_pink.png")))),
            Map.entry(PawnsColors.BLUE, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/professor_blue.png")))),
            Map.entry(PawnsColors.RED, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/professor_red.png")))),
            Map.entry(PawnsColors.GREEN, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/professor_green.png")))),
            Map.entry(PawnsColors.YELLOW, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/professor_yellow.png"))))
    ));
    public static final EnumMap<TowersColors, Image> TOWERS_COLORS_IMAGE_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(TowersColors.BLACK, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/black_tower.png")))),
            Map.entry(TowersColors.WHITE, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/white_tower.png")))),
            Map.entry(TowersColors.GREY, new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/grey_tower.png"))))
    ));
    public static final Image SCHOOL_BOARD = new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/school_board.png")));
    public static final Image SCHOOL_BOARD_ROTATED = new Image(Objects.requireNonNull(FXUtils.class.getResourceAsStream("/assets/school_board_rotated.png")));
}
