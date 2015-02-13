package quiz;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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

    public Quiz(String quizName){
        questions = new ArrayList<>();
        //pre-increment id number to assign as id of this quiz
        this.quizName = quizName;
        id = ++quizIds;
        closed = false;

    }

    /**
     * Add questions to the quiz.
     */
    public void addQuestions(){
        boolean finished = false;
        String question;
        String[] answers = new String[4];
        Scanner input = new Scanner(System.in);
        while (!finished){
            System.out.print("\nPlease enter a question: ");
            question = input.nextLine();
            System.out.print("\nPlease enter the CORRECT answer: ");
            answers[0] = input.nextLine();
            for (int i=1; i<4; i++){
                System.out.print("\nPlease enter an incorrect answer: ");
                answers[i] = input.nextLine();
            }
            Question newQ = new Question(questions.size()+1, question);
            newQ.addAnswers(answers);
            questions.add(newQ);
            boolean confirm = false;
            while (!confirm) {
                System.out.print("\nDo you want to add another question? Y/N: ");
                String ans = input.nextLine();
                if (ans.toUpperCase().equals("N")) {
                    finished = true;
                    confirm = true;
                } else if (ans.toUpperCase().equals("Y")) {
                    confirm = true;
                }
                else {
                    System.out.println("Please enter Y or N!");
                }
            }

        }
    }

    /**
     * Answer a question
     * @return boolean - whether the answer is correct
     */
    public boolean answerQuestion(int questionNo, String answer){
        return questions.get(questionNo-1).isCorrect(answer);
    }

    /**
     * Close the quiz.
     */
    public void closeQuiz() {closed = true;}

    /**
     * Check whether the quiz is closed.
     * @return boolean isClosed - true if quiz is closed
     */
    public boolean isClosed() {return closed;}

    @Data
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
         * Check whether an answer is correct
         * @param ans
         * @return true or false
         */
        protected boolean isCorrect(String ans) { return ans.equals(correctAnswer);}

    }
}
