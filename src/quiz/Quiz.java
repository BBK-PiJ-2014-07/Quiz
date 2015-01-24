package quiz;

/**
 * Interface for a quiz
 * @author Sophie Koonin
 */
public interface Quiz {
    /**
     * @return the id of this quiz
     */
    int getId();

    /**
     * Get the list of questions
     * @return the list of questions
     */
    ArrayList<Question> getQuestionList();

    /**
     * Add a new question to the quiz
     * @return number of new question
     */
    int addQuestion();

    /**
     * Remove a question from the quiz
     * @param question number
     */
    Question removeQuestion(int questionNo);

    /**
     * Play the quiz
     */
    void play();
}
