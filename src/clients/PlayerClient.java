package clients;


import server.QuizServer;
import service.QuizService;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Client for playing quiz games.
 * @author Sophie Koonin
 */
public class PlayerClient {
    private QuizService server;

    public PlayerClient(){
    }
    /**
     * Connect to the server
     * @return response string (TODO - this is for testing only)
     */
    public String connectServer(){

        try {
            Registry registry = LocateRegistry.getRegistry(1099); //TODO
            server = (QuizService) registry.lookup("QuizService");
            return server.sendResponse();
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
        return "no response";
    }

    public static void main(String[] args) {
        PlayerClient pc = new PlayerClient();
        pc.launch();
    }

    public void launch(){
        System.out.println(connectServer());
    }

}
