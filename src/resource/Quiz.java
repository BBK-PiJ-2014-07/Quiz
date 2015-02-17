package resource;

import lombok.Data;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * The quiz itself. Contains inner class Question.
 * @author Sophie Koonin
 */
@Data
public class Quiz implements Serializable {
    private int id;
    private String quizName;
    private boolean closed;
    //static int to ensure unique id for each quiz
    private static int quizIds = 0;
    private ArrayList<Question> questions;
    private AbstractMap.SimpleEntry<Player,Integer> highScore;

    public Quiz(String quizName){
        questions = new ArrayList<>();
        //pre-increment id number to assign as id of this quiz
        this.quizName = quizName;
        id = ++quizIds;
        closed = false;
        highScore = new AbstractMap.SimpleEntry<>(null,0);

    }

    /**
     * Add questions to the quiz.
     */
    public void addQuestions(List<Question> newQs) {
        questions.addAll(newQs);
    }
    /**
     * Answer a question (check against question's internal correctAnswer)
     * @return boolean - whether the answer is correct
     */
    public boolean answerQuestion(int questionNo, String answer){
        return questions.get(questionNo-1).isCorrect(answer);
    }

    /**
     * Close the quiz.
     */
    public void setClosed() {closed = true;}

    /**
     * Check whether the quiz is closed.
     * @return boolean isClosed - true if quiz is closed
     */
    public boolean isClosed() {return closed;}

}
