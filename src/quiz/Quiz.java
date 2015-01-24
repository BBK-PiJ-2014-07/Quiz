package quiz;

import java.io.Serializable;

/**
 * The quiz itself. Contains inner class Question.
 * @author Sophie Koonin
 */
public class Quiz implements Serializable {
    private int id;
    //static int to ensure unique id for each quiz
    private static int questionIds = 0;
    private static final int MAX_QUESTIONS = 4;
    private Question[] questions;

    public Quiz(){
        //instantiate array of questions - max number is 4
        questions = new Question[MAX_QUESTIONS];
        //pre-increment id number to assign as id of this quiz
        id = ++questionIds;
    }
    protected class Question {
        private int questionNumber;
        private String question;
        private String answer;

        private Question(int questionNumber, String question, String answer){
            this.questionNumber = questionNumber;
            this.question = question;
            this.answer = answer;
        }
    }
}
