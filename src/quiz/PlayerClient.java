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
    private Remote service;
    private QuizServer server;


    public boolean connectServer(String host){
        try {
            service = Naming.lookup(host+ "QuizService");
            server = (QuizServer) service;
            return true;
        } catch (RemoteException | MalformedURLException | NotBoundException ex) {
            ex.printStackTrace();
            return false;

        }
    }

    public static void main(String[] args) {

    }
}
