package server;

import lombok.Data;
import resource.Quiz;
import service.QuizService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

//TODO - SERIALIZATION

/**
 * Implementation of QuizService
 * @author Sophie Koonin
 * @see service.QuizService
 */
@Data

public class QuizServer implements QuizService {
    private ArrayList<Quiz> quizList;

    public QuizServer() throws RemoteException {
        quizList = new ArrayList<>();
    }

    public static void main(String[] args) {
        QuizServer server = null;
        try {
            server = new QuizServer();
            server.startServer();
            server.launch();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void startServer(){

        try {
            String name = "QuizService";
            QuizService server = new QuizServer();
            QuizService serverStub =
                    (QuizService) UnicastRemoteObject.exportObject(server, 0);  //port chosen at runtime
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(name, serverStub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    public void launch(){
    }

    /**
     * Play a quiz
     * @param quizId - the id of the quiz to play
     * @return the score or -1 if there is an error
     */
    @Override
    public int playQuiz(int quizId) {
        //TODO - move this over to client?
        if (quizList.stream().noneMatch(q -> q.getId() == quizId)){
            System.out.println("Quiz not found. Please try again");
            return -1;
        }
        //TODO - check quiz id
        //TODO - player high scores
        Scanner inputScanner = new Scanner(System.in);
        Quiz thisQuiz = quizList.stream().filter(q -> q.getId() == quizId).findFirst().get();
        int score = 0;
        for (int i=0; i<thisQuiz.getQuestions().size(); i++){
            System.out.println("Question " + (i+1) + ": " + thisQuiz.getQuestions().get(i).getQuestion());   //print the question
            thisQuiz.getQuestions().get(i).getAnswers().forEach(System.out::println);
            String input = inputScanner.nextLine();
            if (thisQuiz.answerQuestion(i+1,input)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The answer is "+thisQuiz.getQuestions().get(i).getCorrectAnswer());
            }
        }
        System.out.println("At the end of the quiz, your score is " + score);
        return score;
    }

    /**
     * create a new quiz
     * @param quizName - the name of the quiz
     * @return the ID of the new quiz
     */
    @Override
    public int createQuiz(String quizName) {
        //TODO - check quizName not null
        Quiz newQuiz = new Quiz(quizName);
        newQuiz.addQuestions();
        quizList.add(newQuiz);
        return newQuiz.getId();
    }

    /**
     * Close the selected quiz so nobody else can play it
     * @param quizId - the id of the quiz to close
     */
    @Override
    public void closeQuiz(int quizId) {
        quizList.stream().filter(q -> q.getId() == quizId).forEach(Quiz::setClosed);
        //TODO - return high score

    }

    /**
     * Send a response to a connected client
     * @return response
     */
    public String sendResponse(){ return "Server response"; }

}
