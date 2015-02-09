package quiz;

import java.util.ArrayList;


/**
 * Implementation of QuizService
 * @author skooni01
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
        //TODO - check quizName not null
        Quiz newQuiz = new Quiz(quizName);
        quizList.add(newQuiz);
        return newQuiz.getId();
    }

    @Override
    public void addQuestion(int quizId, String question, String...answers){
        //TODO - check question/answers not null
        quizList.stream().filter(q -> q.getId() == quizId).forEach(q -> q.addQuestion(question, answers));

    }

    @Override
    public void closeQuiz(int quizId) {
        quizList.stream().filter(q -> q.getId() == quizId).forEach(q -> q.closeQuiz());

    }

    /**
     * Get the internal quiz list
     * @return the list of quizzes
     */
    public ArrayList<Quiz> getQuizList(){
        return quizList;
    }
}
