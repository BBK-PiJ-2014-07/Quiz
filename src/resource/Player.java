package resource;

import lombok.Data;
import java.util.Map;
import java.util.TreeMap;

/**
 * Player class - contains Player details and scores
 */
@Data
public class Player {
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
}
