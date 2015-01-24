package quiz;

import java.rmi.Remote;

/**
 * Interface for Quiz Server.
 * @author Sophie Koonin
 */
public interface QuizService extends Remote {
    /**
     * Play a selected quiz
     * @param quizId - the id of the quiz to play
     * @return score
     */
    int playQuiz(int quizId);

    /**
     * Create a new quiz
     * @return id of new quiz
     */
    int createQuiz();

    /**
     * Close the selected quiz
     * @param quizId - the id of the quiz to close
     */
    void closeQuiz(int quizId);
}
