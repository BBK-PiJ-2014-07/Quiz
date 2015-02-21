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

/**
 * Client for playing quiz games.
 * @author Sophie Koonin
 */
public class PlayerClient {
    private QuizService server;

    public static void main(String[] args) {
        PlayerClient pc = new PlayerClient();
        pc.launch();
    }

    public void launch(){
        try {
            Registry registry = LocateRegistry.getRegistry(1099); //TODO
            server = (QuizService) registry.lookup("QuizService");
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }

        //TODO get player details
        //TODO play quiz
        //TODO get high scores
    }

    public int playQuiz(int quizId, int playerId) throws RemoteException {
        // print the question
        // print the answers
        // user answers -> call to server answerQuestion()
        // server returns score at end

        //check the server list of quizzes and players to ensure that id exists
        if (server.getQuizList().stream().noneMatch(q -> q.getId() == quizId) || server.getPlayerList().stream().noneMatch(p -> p.getId() == playerId)) {
            return -1;
        }
        List<String> answers = new ArrayList<>();
        Quiz thisQuiz = server.getQuizList().stream().filter(q -> q.getId() == quizId).findFirst().get();   //get the quiz

        for (Question q: thisQuiz.getQuestions()){
            System.out.println(q); //print the question
            System.out.print("\nEnter your answer: ");
            String ans = System.console().readLine();   //get the answer from the user
            answers.add(ans);   //add the answer to the list
        }
        return server.playQuiz(quizId, playerId, answers);  //play the quiz to get the score

    }
}
