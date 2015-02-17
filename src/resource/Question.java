package resource;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Question for the quiz
 * @author Sophie Koonin
 */
@Data
public class Question implements Serializable {
    private int questionNumber;
    private String question;
    private String correctAnswer;
    private ArrayList<String> answers;

    public Question(int questionNumber, String question) {
        this.questionNumber = questionNumber;
        this.question = question;
        answers = new ArrayList<>();
    }

    /**
     * Add a new answer to internal list of answers (max 4). The first answer added is always the correct one.
     * @param ans  - the answer to be added
     */
    public void addAnswers(String... ans){
        Collections.addAll(answers, ans);
        correctAnswer = answers.get(0); //assign correct answer
        //randomise order of answers
        Collections.shuffle(answers);
    }

    /**
     * Check whether an answer is correct
     * @param ans - the answer entered
     * @return true or false
     */
    public boolean isCorrect(String ans) { return ans.equals(correctAnswer);}

}
