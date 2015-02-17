package resource;

import lombok.Data;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
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
     * Add questions to the quiz. Loops until user is finished
     */
    public void addQuestions(){
        boolean finished = false;
        String question;
        String[] answers = new String[4];   //Each question should only have 4 answers
        Scanner input = new Scanner(System.in);

        while (!finished){
            System.out.print("\nPlease enter a question: ");
            question = input.nextLine();    //get question from user
            System.out.print("\nPlease enter the CORRECT answer: ");
            answers[0] = input.nextLine();  //first member of answer array is correct answer

            for (int i=1; i<4; i++){
                System.out.print("\nPlease enter an incorrect answer: ");
                answers[i] = input.nextLine();  //Populate rest of answer array with incorrect answers
            }

            Question newQ = new Question(questions.size()+1, question); //instantiate new question wih this data
            newQ.addAnswers(answers);   //add the answers
            questions.add(newQ);        //add this question to the internal array of questions

            boolean confirm = false;    // Ask whether user is finished
            while (!confirm) {
                System.out.print("\nDo you want to add another question? Y/N: ");
                String ans = input.nextLine();

                if (ans.toUpperCase().equals("N")) {
                    finished = true;    //User finished; stop the loop
                    confirm = true;
                } else if (ans.toUpperCase().equals("Y")) {
                    confirm = true;
                }
                else {  //If user enters neither Y or N it will loop again
                    System.out.println("Please enter Y or N!");
                }
            }

        }
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
