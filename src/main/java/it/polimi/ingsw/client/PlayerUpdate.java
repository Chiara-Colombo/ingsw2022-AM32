package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PawnsColors;
import it.polimi.ingsw.model.TowersColors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;

public class PlayerUpdate implements Serializable {
    private final String nickname;
    private final ArrayList<PawnsColors> entranceStudents, professors;
    private final EnumMap<PawnsColors, Integer> diningRoom;
    private final TowersColors towersColor;
    private final int towers, coins;

    /**
     * Class constructor
     * @param nickname name of the player
     * @param entranceStudents list of students on entrance of the school board
     * @param professors list of professors
     * @param diningRoom pawn in the dining room of the schoolboard
     * @param towersColor color of the towers
     * @param towers The towers of a player
     * @param coins the coins of a player
     */

    public PlayerUpdate(String nickname,
                        ArrayList<PawnsColors> entranceStudents,
                        ArrayList<PawnsColors> professors,
                        EnumMap<PawnsColors, Integer> diningRoom,
                        TowersColors towersColor,
                        int towers, int coins) {
        this.nickname = nickname;
        this.entranceStudents = entranceStudents;
        this.professors = professors;
        this.diningRoom = diningRoom;
        this.towersColor = towersColor;
        this.towers = towers;
        this.coins = coins;
    }

    /**
     * Getter for the nickname of the player
     * @return the nickname
     */

    public String getNickname() {
        return this.nickname;
    }

    /**
     * Getter for the students on the entrance of the school board
     * @return list of students
     */

    public ArrayList<PawnsColors> getEntranceStudents() {
        return this.entranceStudents;
    }

    /**
     * Getter for the pawns in the dining room
     * @return dining room
     */

    public EnumMap<PawnsColors, Integer> getDiningRoom() {
        return this.diningRoom;
    }

    /**
     * Getter for the professors
     * @return list of professors
     */

    public ArrayList<PawnsColors> getProfessors() {
        return this.professors;
    }

    /**
     * Getter for the towers
     * @return the tower
     */

    public int getTowers() {
        return this.towers;
    }

    /**
     * Getter for the coins
     * @return the coins
     */

    public int getCoins() {
        return this.coins;
    }

    /**
     * Getter for the tower color
     * @return tower color
     */

    public TowersColors getTowersColor() {
        return towersColor;
    }
}
