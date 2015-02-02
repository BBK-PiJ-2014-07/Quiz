package quiz;

import java.util.ArrayList;
import java.util.Scanner;

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
        createQuiz();
    }

    @Override
    public int playQuiz(int quizId) {
        return 0;
    }

    @Override
    public int createQuiz() {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Please enter a name for the quiz.");
        String quizName = inputScanner.nextLine();
        //TODO - check to see if quiz name exists already
        Quiz newQuiz = new Quiz(quizName);
        Boolean finished = false;
        while (!finished) {
            //TODO to be simplified
            System.out.println("Please enter a question.");
            String newQuestion = inputScanner.nextLine();
            System.out.println("Please enter the CORRECT ANSWER for the question \"" + newQuestion+"\"");
            String correctAnswer = inputScanner.nextLine();
            System.out.println("Please enter 3 incorrect answers.");
            System.out.print("1: ");
            String wrong1 = inputScanner.nextLine();
            System.out.print("\n2: ");
            String wrong2 = inputScanner.nextLine();
            System.out.print("\n3: ");
            String wrong3 = inputScanner.nextLine();
            newQuiz.addQuestion(newQuestion, correctAnswer, wrong1, wrong2, wrong3);
            System.out.println("Do you want to add another question? Y/N");
            String response = inputScanner.nextLine();
            if (response == "n" || response == "N") {
                finished = true;
            } else if (response != "y" || response != "Y"){
                System.out.println("Please enter Y or N.");
                response = inputScanner.nextLine();
            }
        }
        inputScanner.close();
        return newQuiz.getId();
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
