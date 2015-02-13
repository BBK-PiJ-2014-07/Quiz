package clients;


import server.QuizServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
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
    private Remote service;
    private QuizServer server;

    /**
     * Connect to the server
     * @param host - the host of the server
     * @return response string (TODO - this is for testing only)
     */
    public String connectServer(){
        if (System.getSecurityManager() ==null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099); //TODO
            service = (QuizServer) registry.lookup("QuizService");
            server = (QuizServer) service;
            return server.sendResponse();
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();

        }
        return "no response";
    }

    public static void main(String[] args) {

    }

}
