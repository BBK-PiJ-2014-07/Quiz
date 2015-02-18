package server;

import service.QuizService;

import java.io.File;
import java.io.IOException;

/**
 * Factory class to start the server and specify the file it should write/read
 * @author Sophie Koonin
 * @see server.QuizServer
 */
public class QuizServerFactory {

    public static void main(String[] args) {
        QuizService quizService;
        File file = new File("saveData.txt");
        try {
            file.createNewFile();   //create new file if and only if file does not exist
            quizService = new QuizServer(file);
            quizService.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
