package server;

import resource.Player;
import resource.Question;
import resource.Quiz;
import service.QuizService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO - SERIALIZATION

/**
 * Implementation of QuizService
 * @author Sophie Koonin
 * @see service.QuizService
 */

public class QuizServer implements QuizService {
    private ArrayList<Quiz> quizList;
    private ArrayList<Player> playerList;

    public QuizServer() throws RemoteException {
        quizList = new ArrayList<>();
    }

    public static void main(String[] args) {
        QuizServer server = null;
        try {
            server = new QuizServer();
            server.launch();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void launch(){
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


    /**
     * Play a quiz
     * @param quizId - the id of the quiz to play
     * @param player - the player currently playing
     * @return the score or -1 if there is an error
     */
    @Override
    public int playQuiz(int quizId, Player player) {
        //TODO - move interactive bits over to client
        if (quizList.stream().noneMatch(q -> q.getId() == quizId)){
            System.out.println("Quiz not found. Please try again");
            return -1;
        }

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

        if (score > thisQuiz.getHighScore().getValue()){
            thisQuiz.setHighScore(new AbstractMap.SimpleEntry<>(player, score));
            System.out.println("NEW HIGH SCORE!");
        }

        System.out.println("At the end of the quiz, your score is " + score);
        System.out.println("Current high scorer: " + thisQuiz.getHighScore().getKey().getName() + " - " + thisQuiz.getHighScore().getValue());
        return score;
    }

    /**
     * create a new quiz
     * @param quizName - the name of the quiz
     * @return the ID of the new quiz
     */
    @Override
    public int createQuiz(String quizName, List<Question> questions) {
        //TODO - check quizName not null
        Quiz newQuiz = new Quiz(quizName);
        newQuiz.addQuestions(questions);
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
     * Add a new player to the internal player list
     * @param name - the name of the player to be added
     */
    @Override
    public void addNewPlayer(String name){
        //TODO
    }

    /**
     * Send a response to a connected client
     * @return response
     */

    @Override
    public String echo(){ return "Server response"; }

    /** Return the list of quizzes
     * @return the list of quizzes
     */
    @Override
    public List<Quiz> getQuizList(){ return quizList;}

    /**
     * Return the list of players
     * @return the list of players
     */
    public List<Player> getPlayerList() { return playerList; }
}
