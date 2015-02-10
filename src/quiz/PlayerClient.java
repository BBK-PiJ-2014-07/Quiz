package quiz;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Client for playing quiz games.
 * @author Sophie Koonin
 */
public class PlayerClient {
    public static void main(String[] args) {
        try {
            Remote service = Naming.lookup("//127.0.0.1/QuizService");
            QuizService quizServer = (QuizServer) service;
            //TODO

        } catch (RemoteException | MalformedURLException | NotBoundException ex) {
            ex.printStackTrace();

        }
    }
}
