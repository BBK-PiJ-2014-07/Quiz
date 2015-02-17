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


    public static void main(String[] args) {
        SetupClient client = new SetupClient();
        client.launch();
    }

    /**
     * Launch the setup client and connect to the server.
     */
    public void launch() {
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
     */
    public void createQuiz(String quizName) throws RemoteException {
        //Create the quiz itself
        server.createQuiz(quizName);


        //QUESTIONS
        Scanner input = new Scanner(System.in);
        boolean finished = false;
        String[][] questions = new String[20][5]; //max 20 questions, with 4 answers each;
        int questionNo = 0; //number of question (0-indexed)
        while (!finished) {  //repeat until told otherwise
            System.out.print("\nPlease enter a question: ");
            questions[questionNo][0] = input.nextLine();    //get question from user
            System.out.print("\nPlease enter the CORRECT answer: ");
            questions[questionNo][1] = input.nextLine();  //first member of answer array is correct answer
            for (int i = 1; i < 4; i++) {
                System.out.print("\nPlease enter an incorrect answer: ");
                questions[questionNo][i] = input.nextLine();  //Populate rest of answer array with incorrect answers
            }
        questionNo++;   //increment for next question

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


}

