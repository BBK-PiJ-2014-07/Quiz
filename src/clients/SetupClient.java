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
    boolean connected;

    public SetupClient(){
        input = new Scanner(System.in);
        connected = false;
    }

    /**
     * Connect to server
     */
    public void connectToServer(){
        try {
            Registry registry = LocateRegistry.getRegistry(1099); //TODO
            server = (QuizService) registry.lookup("QuizService");
            connected = true;
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Launch the setup client and connect to the server.
     */
    public void execute() {
        if (!connected) {
            connectToServer();
        }
        boolean finished = false;
        while (!finished) {
        System.out.println("\n");       //spacer
        for (int i = 0; i < 60; i++) {
            System.out.print("=");      //horizontal rule
        }

        System.out.println("\nQUIZ SETUP\n");
        System.out.println("You have 2 options: ");
        System.out.println("1. Create a new quiz");
        System.out.println("2. Close an existing quiz");
        System.out.println("3. Return to main menu");

        int choice;
        try {
            choice = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a number.");
            choice = Integer.parseInt(input.nextLine());
        }

        switch (choice) {
            case 1:
                createQuiz();
                break;
            case 2:
                closeQuiz();
                break;
            case 3:
                finished = true;
                return;
            default:
                System.out.println("Please choose a valid option.");
                break;
        }
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
        System.out.println("\nCREATE A QUIZ\n");
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
            boolean invalid;
            do {
                invalid = false;
                System.out.print("\nDo you want to add another question? Y/N: ");
                String ans = input.nextLine().toLowerCase();
                switch (ans) {
                    case "y":   //do nothing, user wants to add another question
                        break;
                    case "n":
                        finished = true;  //set finished to true as user has finished
                        break;
                    default:
                        System.out.println("Please enter Y or N!");
                        invalid = true; //invalid input so repeat menu  1
                        break;
                }
            } while (invalid);
        }
        try {
            int quizId = server.createQuiz(quizName,questionsToAdd);     //create the quiz on the server
            System.out.println("Quiz "+quizId+" created!");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close a quiz.
     * @return the id of the closed quiz, 0 if no quiz closed, -1 if error (quiz not found)
     */
    public int closeQuiz(){
        System.out.println("\nCLOSE A QUIZ\n");
        try {
            System.out.println("Open quizzes: ");
            for (int i = 0; i<server.getQuizList().size(); i++) {   //print quiz list
                if (!server.getQuizList().get(i).isClosed()) {
                    System.out.println(server.getQuizList().get(i).getId() + ". " + server.getQuizList().get(i).getQuizName());
                }
            }

            System.out.println("\nPlease enter the number of the quiz you wish to close.");
            int idNo = Integer.parseInt(input.nextLine());  //get the id from the user
            if (server.getQuizList().stream().noneMatch(q -> q.getId() == idNo)){   //check quiz exists
                System.out.println("Quiz not found!");
                return -1;  //if not, return -1
            }
            System.out.println("Are you sure you want to close quiz " + idNo + "? You will no longer be able to play it. Y/N");
            String choice = input.nextLine().toLowerCase();

            switch (choice) {
                case "y": server.closeQuiz(idNo);
                    System.out.println("Quiz " + idNo + " closed.");
                    return idNo;
                case "n":
                    System.out.println("Quiz not closed.");
                    break;
                default:
                    System.out.println("Please enter Y or N.");
            }
        } catch (RemoteException ex){
            ex.printStackTrace();
        }
        return 0;
    }

}

