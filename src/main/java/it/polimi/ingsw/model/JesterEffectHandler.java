package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IJesterHandled;

import java.util.ArrayList;
import java.util.Comparator;

public class JesterEffectHandler implements EffectHandler {

    private final IJesterHandled player;
    private final IJesterStudents jesterStudents;
    private final ArrayList<Integer> jesterPawnsIndexes, entrancePawnsIndexes;

    /**
     * Class Constructor
     */

    public JesterEffectHandler(IJesterHandled player, IJesterStudents jesterStudents, ArrayList<Integer> jesterPawnsIndexes, ArrayList<Integer> entrancePawnsIndexes){
        this.player = player;
        this.jesterStudents = jesterStudents;
        this.jesterPawnsIndexes = jesterPawnsIndexes;
        this.entrancePawnsIndexes = entrancePawnsIndexes;
    }

    /**
     * Method that applies Jester Card Effect
     * (it adds students in school board entrance)
     */

    @Override
    public void applyEffect() {
        if (this.jesterPawnsIndexes.size() == this.entrancePawnsIndexes.size()) {
            this.jesterPawnsIndexes.sort(Comparator.reverseOrder());
            this.entrancePawnsIndexes.sort(Comparator.reverseOrder());
            for (int i = 0; i < this.entrancePawnsIndexes.size(); i++) {
                Pawn entrancePawn = this.player.removeStudent(this.entrancePawnsIndexes.get(i));
                this.player.addStudentInEntrance(this.jesterStudents.getJesterStudents().remove(this.jesterPawnsIndexes.get(i).intValue()));
                this.jesterStudents.getJesterStudents().add(entrancePawn);
            }
        }
    }

    /**
     * Method that remove Jester Class Effect
     */

    @Override
    public void removeEffect() {

    }
}
