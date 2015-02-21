package resource;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * Question for the quiz class. Contains inner list of answers, randomly shuffled.
 * @author Sophie Koonin
 */
@Data
public class Question implements Serializable {
    private int questionNumber;
    private String question;
    private String correctAnswer;
    private String correctKey;
    private Map<String,String> answers;

    public Question(int questionNumber, String question) {
        this.questionNumber = questionNumber;
        this.question = question;
        answers = new TreeMap<>();
    }

    /**
     * Add a new answer to internal list of answers (max 4). The first answer added is always the correct one.
     * @param ans  - the answer to be added
     */
    public void addAnswers(String... ans){
        correctAnswer = ans[0]; //assign correct answer
        Collections.shuffle(Arrays.asList(ans));//randomise order of answers
        String[] letters = {"a","b","c","d"}; //question letters
        for (int i=0; i<4; i++){
            answers.put(letters[i], ans[i]);
            if (ans[i].equals(correctAnswer)){ correctKey = letters[i]; }
        }

    }

    /**
     * Check whether an answer is correct
     * @param ans - the answer entered - can be the answer itself, or its corresponding letter (a,b,c,d)
     * @return true or false
     */
    public boolean isCorrect(String ans) { return ans.equals(correctKey) || ans.equals(correctAnswer);}

    @Override
    public String toString(){
        return "Question " + questionNumber + ": " + question + "\n"
                + "a. " + answers.get("a") + "\nb. " + answers.get("b") + "\nc. " + answers.get("c") + "\nd. " + answers.get("d");
    }

}
