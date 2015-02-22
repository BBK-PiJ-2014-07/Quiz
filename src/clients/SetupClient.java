package clients;

import resource.Question;
import service.QuizService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The client program for creating and closing quizzes.
 */
public class SetupClient {
    QuizService server;

    /**
     * Launch the setup client and connect to the server.
     */
    public void execute() {
        try {
            Registry registry = LocateRegistry.getRegistry(1099); //TODO
            server = (QuizService) registry.lookup("QuizService");
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Create a new quiz.
     * This method asks for input from the user, creates questions from it,
     * and passes that data through to the server's createQuiz method,
     * where the questions will be added to a new Quiz.
     * @param quizName - the name of the quiz to create
     */
    public void createQuiz(String quizName) throws RemoteException {
        //Variables for each question
        String question;
        String[] answers = new String[4];
        int questionNo = 1;
        List<Question> questionsToAdd = new ArrayList<>();

        //QUESTIONS
        Scanner input = new Scanner(System.in);
        boolean finished = false;

        while (!finished) {  //repeat until told otherwise
            System.out.print("\nPlease enter a question: ");
            question = input.nextLine();    //get question from user
            System.out.print("\nPlease enter the CORRECT answer: ");
            answers[0] = input.nextLine();  //first member of answer array is correct answer
            for (int i = 1; i < 4; i++) {
                System.out.print("\nPlease enter an incorrect answer: ");
                answers[i] = input.nextLine();  //Populate rest of answer array with incorrect answers
            }
            Question newQ = new Question(questionNo,question);  //instantiate new question
            newQ.addAnswers(answers);   //add answers to question
            questionsToAdd.add(newQ);   //add question to list of questions for quiz
            questionNo++;   //increment number for next question

            System.out.print("\nDo you want to add another question? Y/N: ");
            String ans = input.nextLine().toLowerCase();
            switch (ans) {
                case "y":   //do nothing, user wants to add another question
                    break;
                case "n": finished = true;  //set finished to true as user has finished
                    break;
                default:
                    System.out.println("Please enter Y or N!");
                    break;
            }



        }
        server.createQuiz(quizName,questionsToAdd);     //create the quiz on the server
    }


}

