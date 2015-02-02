package quiz;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * The client program for creating and closing quizzes.
 */
public class SetupClient {
    QuizServer server;

    public SetupClient(){
        try {
            Remote service = Naming.lookup("//localhost/");
            server = (QuizServer) service;

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public void createQuiz(){
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Please enter a name for the quiz.");
        String quizName = inputScanner.nextLine();
        //TODO - check to see if quiz name exists already
        int newQuizId = server.createQuiz(quizName);
        Boolean finished = false;

        while (!finished) {
            //TODO to be simplified
            System.out.println("Please enter a question.");
            String newQuestion = inputScanner.nextLine();
            System.out.println("Please enter the CORRECT ANSWER for the question \"" + newQuestion+"\"");
            String correctAnswer = inputScanner.nextLine();
            System.out.println("Please enter 3 incorrect answers.");
            System.out.print("1: ");
            String wrong1 = inputScanner.nextLine();
            System.out.print("\n2: ");
            String wrong2 = inputScanner.nextLine();
            System.out.print("\n3: ");
            String wrong3 = inputScanner.nextLine();
            server.addQuestion(newQuizId, newQuestion, correctAnswer,wrong1, wrong2, wrong3);
            System.out.println("Do you want to add another question? Y/N");
            String response = inputScanner.nextLine();

            while (!response.equals("y") && !response.equals("Y") && !response.equals("n") & !response.equals("N")){
                System.out.println("Please enter Y or N.");
                response = inputScanner.nextLine();
            }
            if (response.equals("n") || response.equals("N")) {
                finished = true;
            }
        }
        inputScanner.close();
    }
}
