package server;

import service.QuizService;

import java.io.File;

/**
 * Factory class to start the server and specify the file it should write/read
 * @author Sophie Koonin
 * @see server.QuizServer
 */
public class QuizServerFactory {

    public static void main(String[] args) {
        QuizService quizService = new QuizServer(new File("saveData.txt"));
        quizService.start();
    }


}
