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
     * @oaram name of the quiz to be created
     * @return id of new quiz
     */
    int createQuiz(String quizName);

    /**
     * Add a new question to a pre-existing quiz
     * @param quizId the id of the quiz to add the new question to
     * @param question the new question
     * @param answers the four answers to be added
     */
    void addQuestion(int quizId, String question, String...answers);

    /**
     * Close the selected quiz
     * @param quizId - the id of the quiz to close
     */
    void closeQuiz(int quizId);
}
