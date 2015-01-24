package quiz;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The quiz itself. Contains inner class Question.
 * @author Sophie Koonin
 */
public class Quiz implements Serializable {
    private int id;
    //static int to ensure unique id for each quiz
    private static int quizIds = 0;
    private ArrayList<Question> questions;

    public Quiz(){
        questions = new ArrayList<Question>;
        //pre-increment id number to assign as id of this quiz
        id = ++quizIds;
    }
    protected class Question {
        private int questionNumber;
        private String question;
        private String correctAnswer;
        private ArrayList<String> answers;

        protected Question(int questionNumber, String question) {
            this.questionNumber = questionNumber;
            this.question = question;
            answers = new ArrayList<String>();
        }

        /**
         * Add a new answer to internal list of answers (max 4). The first answer added is always the correct one.
         * @param answer
         */
        protected void addAnswer(String answer){
            //check that list is not full
            if (answers.size() < 4) {
                //add answer
            }
        }
    }
}
