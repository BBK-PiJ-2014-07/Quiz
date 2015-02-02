package quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

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
        questions = new ArrayList<Question>();
        //pre-increment id number to assign as id of this quiz
        id = ++quizIds;
    }


    public ArrayList<Question> getQuestions() { return questions; }


    protected class Question {
        private int questionNumber;
        private String question;
        private String correctAnswer;
        private ArrayList<String> answers;

        protected Question(int questionNumber, String question) {
            this.questionNumber = questionNumber;
            this.question = question;
            answers = new ArrayList<>();
        }

        /**
         * Add a new answer to internal list of answers (max 4). The first answer added is always the correct one.
         * @param ans  - the answer to be added
         */
        protected void addAnswer(String ans){
            //check that list is not full
            if (answers.size() < 4) {
                answers.add(ans);
            }
            correctAnswer = answers.get(0);
            //randomise order of answers
            Collections.shuffle(answers);
        }

        /**
         * Get the internal answer list
         * @return the list of answers
         */
        protected ArrayList<String> getAnswerList(){
            return answers;
        }

        protected boolean isCorrect(String ans) { return ans.equals(correctAnswer);
        }
    }
}
