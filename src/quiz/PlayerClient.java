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

    /**
     * Connect to the server
     * @param host - the host of the server
     * @return true if connected
     */
    public String connectServer(String host){
        try {
            service = Naming.lookup(host+ "QuizService");
            server = (QuizServer) service;
            return server.sendResponse();
        } catch (RemoteException | MalformedURLException | NotBoundException ex) {
            ex.printStackTrace();

        }
        return "no response";
    }

    public static void main(String[] args) {

    }

}
