package clients;

import resource.Player;
import server.QuizServer;
import service.QuizService;

import java.rmi.RemoteException;

/**
 * Client for playing quiz games.
 * @author Sophie Koonin
 * @see clients.Client
 */
public class PlayerClient extends Client {
    private QuizService server;

    public PlayerClient(){ super(); }

    public static void main(String[] args) {
        PlayerClient pc = new PlayerClient();
        pc.launch();
    }

    public void launch(){
        server = connectServer();
        try {
            server.addNewPlayer("Fred");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //TODO get player details
        //TODO play quiz
        //TODO get high scores
    }

    public void playQuiz(int quizId, Player player) throws RemoteException {
        server.playQuiz(quizId,player);

    }
}
