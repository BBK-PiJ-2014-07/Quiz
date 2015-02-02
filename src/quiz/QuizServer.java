package quiz;

import java.util.ArrayList;

/**
 * Created by Sophie on 02/02/15.
 */
public class QuizServer implements QuizService {
    private ArrayList<Quiz> quizList;

    public QuizServer(){
        quizList = new ArrayList<>();
    }

    @Override
    public int playQuiz(int quizId) {
        return 0;
    }

    @Override
    public int createQuiz() {
        return 0;
    }

    @Override
    public void closeQuiz(int quizId) {

    }

    /**
     * Get the internal quiz list
     * @return the list of quizzes
     */
    public ArrayList<Quiz> getQuizList(){
        return quizList;
    }
}
