package quiz;

import java.util.ArrayList;


/**
 * Implementation of QuizService
 * @author skooni01
 * @see quiz.QuizService
 */
public class QuizServer implements QuizService {
    private ArrayList<Quiz> quizList;

    public QuizServer(){
        quizList = new ArrayList<>();
    }

    public static void main(String[] args) {
        QuizServer server = new QuizServer();
        server.launch();
    }

    public void launch(){
    }

    /**
     * Play a quiz
     * @param quizId - the id of the quiz to play
     * @return the score
     */
    @Override
    public int playQuiz(int quizId) {
        return 0;
    }

    /**
     * create a new quiz
     * @param quizName
     * @return the ID of the new quiz
     */
    @Override
    public int createQuiz(String quizName) {
        //TODO - check quizName not null
        Quiz newQuiz = new Quiz(quizName);
        quizList.add(newQuiz);
        return newQuiz.getId();
    }

    /**
     * Add a new question to the selected quiz
     * @param quizId the id of the quiz to add the new question to
     * @param question the new question
     * @param answers the four answers to be added
     */
    @Override
    public void addQuestion(int quizId, String question, String...answers){
        //TODO - check question/answers not null
        quizList.stream().filter(q -> q.getId() == quizId).forEach(q -> q.addQuestion(question, answers));
        //TODO - maybe return question number?

    }

    /**
     * Close the selected quiz so nobody else can play it
     * @param quizId - the id of the quiz to close
     */
    @Override
    public void closeQuiz(int quizId) {
        quizList.stream().filter(q -> q.getId() == quizId).forEach(q -> q.closeQuiz());
        //TODO - return high score

    }

    /**
     * Get the internal quiz list
     * @return the list of quizzes
     */
    public ArrayList<Quiz> getQuizList(){ return quizList;}
}
