package clients;

import resource.Player;
import service.QuizService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Client for playing quiz games.
 * @author Sophie Koonin
 * @see clients.Client
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

    public void playQuiz(int quizId, Player player) throws RemoteException {
        server.playQuiz(quizId,player);

    }
}
