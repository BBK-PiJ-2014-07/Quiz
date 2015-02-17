package clients;

import resource.Question;
import resource.Quiz;
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

    public void launch() {
        try {
            Registry registry = LocateRegistry.getRegistry(1099); //TODO
            server = (QuizService) registry.lookup("QuizService");
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }

    }

    public void createQuiz(){
        //Create the quiz itself
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a name for the quiz");
        //TODO - check user input length
        String quizName = input.nextLine();

        //QUESTIONS
        List<Question> questions = new ArrayList<>();
        boolean finished = false;
        String question;
        String[] answers = new String[4];   //Each question should only have 4 answers
        int questionNumber = 1;
        while (!finished){  //repeat until told otherwise
            System.out.print("\nPlease enter a question: ");
            question = input.nextLine();    //get question from user
            System.out.print("\nPlease enter the CORRECT answer: ");
            answers[0] = input.nextLine();  //first member of answer array is correct answer

            for (int i=1; i<4; i++){
                System.out.print("\nPlease enter an incorrect answer: ");
                answers[i] = input.nextLine();  //Populate rest of answer array with incorrect answers
            }

            //instantiate new question with this data
            Question newQ = new Question(questionNumber, question);
            questions.add(newQ);
            questionNumber++;   //increment question number for next question
            newQ.addAnswers(answers);   //add the answers


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
        try {
            server.createQuiz(quizName,questions);  //add the new quiz to the server
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}

