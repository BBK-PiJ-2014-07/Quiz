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
    void start() throws RemoteException;

    /**
     * Play a selected quiz
     * @param quizId - the id of the quiz to play
     * @param playerId - the id of the player
     * @param answers - the list of answers the user has entered
     * @return score
     */
    int playQuiz(int quizId, int playerId, List<String> answers) throws RemoteException;


    /**
     * Create a new quiz
     * @param quizName - name of the quiz to be created
     * @param questions - list of questions to add
     * @return id of new quiz
     */
    int createQuiz(String quizName, List<Question> questions) throws RemoteException;


    /**
     * Close the selected quiz
     * @param quizId - the id of the quiz to close
     */
    void closeQuiz(int quizId) throws RemoteException;

    /**
     *  Add a new player - no duplicate names are allowed
     *  @param name - the name of the player to be added
     *  @return playerId = the id of the new player
     */
    int addNewPlayer(String name) throws RemoteException;


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

    /**
     * Return the Player with the specified ID
     * @param playerId - the ID of the player to find
     * @return the player, or null if it does not exist
     */
    Player getPlayer(int playerId) throws RemoteException;

    /**
     * Return the Quiz with the specified ID
     * @param quizId - the ID of the quiz to find
         * @return the quiz, or null if it does not exist
     */
    Quiz getQuiz(int quizId) throws RemoteException;
}
