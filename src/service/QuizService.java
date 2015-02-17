package service;

import resource.Player;
import resource.Question;
import resource.Quiz;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for Quiz Server.
 * @author Sophie Koonin
 */
public interface QuizService extends Remote{

    /**
     * Launch the server
     */
    void launch() throws RemoteException;

    /**
     * Play a selected quiz
     * @param quizId - the id of the quiz to play
     * @return score
     */
    int playQuiz(int quizId, Player player) throws RemoteException;

    /**
     * Create a new quiz
     * @param quizName - name of the quiz to be created
     * @param questions - the questions to be added to the quiz
     * @return id of new quiz
     */
    int createQuiz(String quizName, List<Question>questions) throws RemoteException;


    /**
     * Close the selected quiz
     * @param quizId - the id of the quiz to close
     */
    void closeQuiz(int quizId) throws RemoteException;

    /**
     *  Add a new player - no duplicate names are allowed
     *  @param name - the name of the player to be added
     */
    void addNewPlayer(String name) throws RemoteException;

    /**
     * Testing method - send a response to a client
     * @return response
     */
    String echo() throws RemoteException;

    /**
     * Get the internal list of quizzes
     * @return list of players
     * @throws RemoteException
     */
    List<Quiz> getQuizList() throws RemoteException;

    /**
     * Get the internal list of players
     * @return the list of players
     * @throws RemoteException
     */
    List<Player> getPlayerList() throws RemoteException;
}
