package clients;

import service.QuizService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Superclass for client classes
 */
public abstract class Client {
    private QuizService server;

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

}
