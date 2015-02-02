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
    private String quizName;
    private boolean closed;
    //static int to ensure unique id for each quiz
    private static int quizIds = 0;
    private ArrayList<Question> questions;

    public Quiz(String quizName){
        questions = new ArrayList<Question>();
        //pre-increment id number to assign as id of this quiz
        this.quizName = quizName;
        id = ++quizIds;
        closed = false;

    }

    /**
     * Add a new question.
     * @param question - the question
     * @param answers - 4 answers. The constructor takes varargs, but the
     *                 number should be 4 (this is checked in another class)
     */
    public void addQuestion(String question, String...answers){
        Question newQ = new Question(questions.size()+1, question);
        newQ.addAnswers(answers);
        questions.add(newQ);
    }

    /**
     * Answer a question
     * @return boolean - whether the answer is correct
     */
    public boolean answerQuestion(int questionNo, String answer){
        return questions.get(questionNo).isCorrect(answer);
    }

    public ArrayList<Question> getQuestions() { return questions; }

    public String getQuizName() {return quizName;}

    public int getId() {return id; }

    /**
     * Close the quiz.
     */
    public void closeQuiz() {closed = true;}

    /**
     * Check whether the quiz is closed.
     * @return boolean isClosed - true if quiz is closed
     */
    public boolean isClosed() {return closed;}

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
        protected void addAnswers(String... ans){
            for (String answer: ans){
                answers.add(answer);
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
