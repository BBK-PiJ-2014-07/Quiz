package clients;

import resource.Question;
import resource.Quiz;
import service.QuizService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Client for playing quiz games.
 * @author Sophie Koonin
 */
public class PlayerClient {
    private QuizService server;

    public static void main(String[] args) {
        PlayerClient pc = new PlayerClient();
        pc.launch();
    }

    public void launch(){
        try {
            Registry registry = LocateRegistry.getRegistry(1099); //TODO
            server = (QuizService) registry.lookup("QuizService");
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }

        //TODO get player details
        //TODO play quiz
        //TODO get high scores
    }

    /**
     * Print header/welcome message
     * Ask player what they want to do
     */
    public void init(){
        Scanner input = new Scanner(System.in);
        for (int i=0; i<60; i++) {
            System.out.print("*");  //top border
        }
        for (int i=0; i<3; i++) {
            System.out.print("\n*");    // top
            for (int j=0; j<58; j++){
                System.out.print(" ");
            }
            System.out.print("*");
        }
        System.out.print("\n*");
        for (int j=0; j<24; j++){
            System.out.print(" ");
        }
        System.out.print("QUIZ  GAME"); //middle

        for (int j=0; j<24; j++){
            System.out.print(" ");
        }
        System.out.print("*");
        for (int i=0; i<3; i++) {
            System.out.print("\n*");
            for (int j=0; j<58; j++){
                System.out.print(" ");  //bottom
            }
            System.out.print("*");
        }
        System.out.print("\n");
        for (int i=0; i<60; i++) {
            System.out.print("*");      //bottom border
        }


        for (int i=0; i<60; i++){
            System.out.print("=");
        }
        System.out.println("\nPlease enter your name.");
        String playerName = input.nextLine();
        int playerId;

        try {
        if (server.getPlayerList().stream()         // check to see if player exists already
                .noneMatch(p -> p.getName().equals(playerName))) {
            playerId = server.addNewPlayer(playerName);     //if not, create player
        } else {
            playerId = server.getPlayerList().stream()      //otherwise get id of existing player
                    .filter(p -> p.getName().equals(playerName))
                    .findFirst().get().getId();
        }

        System.out.println("\nWelcome" + playerName + "!");
        System.out.println("Here are the available quizzes for you to play:\n");

            //print all the quiz titles
            for (int i=1; i==server.getQuizList().size(); i++){
                System.out.println(server.getQuizList().get(i-1).getId() + ". " + server.getQuizList().get(i-1).getQuizName());
            }

            System.out.println("\nPlease enter the number of the quiz you want to play.");
            int quizIdToPlay = Integer.parseInt(input.nextLine());
            playQuiz(quizIdToPlay, playerId);
        } catch (RemoteException ex){
            ex.printStackTrace();
        }
    }

    public int playQuiz(int quizId, int playerId) throws RemoteException {
        List<String> answers = new ArrayList<>();
        Quiz thisQuiz = server.getQuizList().stream().filter(q -> q.getId() == quizId).findFirst().get();   //get the quiz
        Scanner input = new Scanner(System.in);
        for (Question q: thisQuiz.getQuestions()){
            System.out.println(q); //print the question
            System.out.print("\nEnter your answer: ");
            String ans = input.nextLine();   //get the answer from the user
            answers.add(ans);   //add the answer to the list
        }
        return server.playQuiz(quizId, playerId, answers);  //play the quiz to get the score

    }
}
