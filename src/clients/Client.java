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

    /**
     * Connect to the server
     * @return response string (TODO - this is for testing only)
     */
    public QuizService connectServer(){

        try {
            Registry registry = LocateRegistry.getRegistry(1099); //TODO
            return (QuizService) registry.lookup("QuizService");
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
