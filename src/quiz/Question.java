package quiz;

/**
 * Interface for Question
 * @author Sophie Koonin
 */
public interface Question {
    /**
     * Checks player answer against value of correct answer and returns corresponding boolean
     * @return true or false (if answer correct)
     */
    boolean isCorrect(String ans);

    /**
     *
     * @return The correct answer to the question
     */
    String getAnswer();


}
