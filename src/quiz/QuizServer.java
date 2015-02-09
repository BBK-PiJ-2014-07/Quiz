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
     quizList.stream().filter(q -> q.getId() == quizId).forEach(q -> q.addQuestion(question, answers));

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
