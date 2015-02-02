package quiz;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Sophie on 02/02/15.
 */
public class QuizServer implements QuizService {
    private Set<Quiz> quizList;

    public QuizServer(){
        quizList = new HashSet<>();
    }

    public static void main(String[] args) {
        QuizServer server = new QuizServer();
        server.launch();
    }

    public void launch(){
    }

    @Override
    public int playQuiz(int quizId) {
        return 0;
    }

    @Override
    public int createQuiz(String quizName) {
        Quiz newQuiz = new Quiz(quizName);
        quizList.add(newQuiz);
        return newQuiz.getId();
    }

    @Override
    public void addQuestion(int quizId, String question, String...answers){

    }

    @Override
    public void closeQuiz(int quizId) {

    }

    /**
     * Get the internal quiz list
     * @return the list of quizzes
     */
    public Set<Quiz> getQuizList(){
        return quizList;
    }
}
