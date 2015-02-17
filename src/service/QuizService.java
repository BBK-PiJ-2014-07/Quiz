package service;

import resource.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for Quiz Server.
 * @author Sophie Koonin
 */
public interface QuizService extends Remote{
    /**
     * Play a selected quiz
     * @param quizId - the id of the quiz to play
     * @return score
     */
    int playQuiz(int quizId, Player player) throws RemoteException;

    /**
     * Create a new quiz
     * @oaram quizName - name of the quiz to be created
     * @return id of new quiz
     */
    int createQuiz(String quizName) throws RemoteException;


    /**
     * Close the selected quiz
     * @param quizId - the id of the quiz to close
     */
    void closeQuiz(int quizId) throws RemoteException;

    /**
     * Testing method - send a response to a client
     * @return response
     */
    String sendResponse() throws RemoteException;
}
