package resource;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Question for the quiz class. Contains inner list of answers, randomly shuffled.
 * @author Sophie Koonin
 */
@Data
public class Question implements Serializable {
    private int questionNumber;
    private String question;
    private String correctAnswer;
    private List<String> answers;

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
        Collections.shuffle(answers);   //randomise order of answers
    }

    /**
     * Check whether an answer is correct
     * @param ans - the answer entered
     * @return true or false
     */
    public boolean isCorrect(String ans) { return ans.equals(correctAnswer);}

}
