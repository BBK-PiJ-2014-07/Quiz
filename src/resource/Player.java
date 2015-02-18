package resource;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Player class - contains Player details and scores
 */
@Data
public class Player implements Serializable {
    private String name;
    private int id;
    private static int playerIds = 1;
    private Map<Integer,Integer> scores;

    public Player(String name){
        this.name = name;
        scores = new TreeMap<>();
        id = playerIds++;
    }

    /**
     * Add a score to the internal map
     * @param quizId - the id of the quiz played
     * @param newScore - the score
     */
    public int addScore(int quizId, int newScore){
        //check to see if the quiz already has a score associated with it
        // if the existing score is HIGHER just leave the existing high score for that quiz
        if (scores.containsKey(quizId) && (scores.get(quizId) > newScore)) {
                return scores.get(quizId);
        }
        scores.put(quizId,newScore);
        return newScore;
    }

    public int getHighScore(){
        return scores.values().stream().max((x,y) -> x > y ? 1:-1).get();
    }

    /**
     * Override equals to allow for easy comparison
     * @param other - the other object to be compared
     * @return true or false depending on whether they are equal
     */
    @Override
    public boolean equals(Object other){
        if (this.getClass() != other.getClass()){
            return false;
        }
        if (this == other) {
            return true;
        }

        Player player2 = (Player) other;

        if (this.getId()!=player2.getId()){
            return false;
        }
        if (!this.getName().equals(player2.getName())){
            return false;
        }
        return true;
    }

    /**
     * Override hashcode because equals overridden
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash =  12 * hash + this.name.hashCode();
        return hash;
    }
}
