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
    Scanner input;

    public SetupClient(){
        input = new Scanner(System.in);
    }


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

        System.out.println("\n");       //spacer
        for (int i = 0; i < 60; i++) {
            System.out.print("=");      //horizontal rule
        }

        System.out.println("\nQUIZ SETUP\n");
        System.out.println("You have 2 options: ");
        System.out.println("1. Create a new quiz");
        System.out.println("2. Close an existing quiz");

        int choice = Integer.parseInt(input.nextLine());

        switch(choice) {
            case 1: createQuiz();
                break;
            case 2: closeQuiz();
                break;
            default:
                System.out.println("Please choose a valid option.");
                break;
        }

    }

    /**
     * Create a new quiz.
     * This method asks for input from the user, creates questions from it,
     * and passes that data through to the server's createQuiz method,
     * where the questions will be added to a new Quiz.
     */
    public void createQuiz() {
        //Variables for each question
        System.out.println("Please enter a name for your quiz: ");
        String quizName = input.nextLine();

        String question;
        String[] answers = new String[4];
        int questionNo = 1;
        List<Question> questionsToAdd = new ArrayList<>();

        //QUESTIONS
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
        try {
            server.createQuiz(quizName,questionsToAdd);     //create the quiz on the server
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close a quiz.
     */
    public void closeQuiz

}

