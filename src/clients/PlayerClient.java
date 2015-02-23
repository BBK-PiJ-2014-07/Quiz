package clients;

import resource.Player;
import resource.Question;
import resource.Quiz;
import service.QuizService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

/**
 * Client for playing quiz games.
 * @author Sophie Koonin
 */
public class PlayerClient {
    private QuizService server;
    private int playerId;
    private Scanner input;
    
    public PlayerClient() {
        input = new Scanner(System.in);
    }

    /**
     * Connects the server, gets player name and begins the process of playing a quiz.
     */
    public void execute(){
        try {
            Registry registry = LocateRegistry.getRegistry(1099); //TODO
            server = (QuizService) registry.lookup("QuizService");
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < 60; i++) {
            System.out.print("=");      //horizontal rule
        }
        System.out.println("\nPLAY A QUIZ\n");
        System.out.println("Please enter your name.");
        String playerName = input.nextLine();

        try {
            if (server.getPlayerList().stream()         // check to see if player exists already
                    .noneMatch(p -> p.getName().equals(playerName))) {
                playerId = server.addNewPlayer(playerName);
                System.out.println("\nWelcome " + playerName + "!");//if not, create player
            } else {
                playerId = server.getPlayerList().stream()      //otherwise get id of existing player
                        .filter(p -> p.getName().equals(playerName))
                        .findFirst().get().getId();
                System.out.println("\nWelcome back " + playerName + "!");
            }

            chooseQuiz();   //go to quiz selection


        } catch (RemoteException ex){
            ex.printStackTrace();
        }
    }


    /**
     * Print quiz names
     */
    public void printQuizNames() throws RemoteException {

        System.out.println("Here are the available quizzes for you to play:\n");

        for (int i=0; i<60; i++){
            System.out.print("=");      //horizontal rule
        }
        System.out.println("\n");       //spacer


        for (int i=1; i==server.getQuizList().size(); i++){
            System.out.println(server.getQuizList().get(i-1).getId() + ". " + server.getQuizList().get(i-1).getQuizName());
        }
        System.out.println("\n");       //spacer
        for (int i=0; i<60; i++){
            System.out.print("=");      //horizontal rule
        }
    }

    /**
     * Choose the quiz to play.
     * @throws RemoteException
     */
    public void chooseQuiz() throws RemoteException {
        printQuizNames();            //print all the quiz titles
        System.out.println("\nPlease enter the number of the quiz you want to play.");
        int quizIdToPlay = Integer.parseInt(input.nextLine());
        playQuiz(quizIdToPlay);
    }

    /**
     * Play a quiz. At the end of the quiz the user will be asked what they want to do next.
     * @param quizId - the ID of the quiz to play.
     * @throws RemoteException
     */
    public void playQuiz(int quizId) throws RemoteException {
        List<String> answers = new ArrayList<>();
        Quiz thisQuiz = server.getQuizList().stream().filter(q -> q.getId() == quizId).findFirst().get();   //get the quiz

        System.out.println("\n" + thisQuiz.getQuizName().toUpperCase() + "\n");   //print the quiz name

        for (Question q : thisQuiz.getQuestions()) {
            System.out.println(q); //print the question
            System.out.print("\nEnter your answer: ");
            String ans = input.nextLine();   //get the answer from the user
            answers.add(ans);   //add the answer to the list
        }
        int score = server.playQuiz(quizId, playerId, answers);  //play the quiz to get the score
        System.out.println("At the end of the quiz your score is " + score + "!");

        // Check if high score
        if (score > thisQuiz.getScores().firstKey()) {
            System.out.println("NEW HIGH SCORE! CONGRATULATIONS!" + "\n");
        }
        thisQuiz = server.getQuizList().stream().filter(q -> q.getId() == quizId).findFirst().get();   //refresh quiz to update scores

        System.out.println("Top 5 scores:");

        int fifthKey = thisQuiz.getScores().firstKey();
        for (int i=0; i<5; i++) {       //Convoluted way of getting the 5th key value to allow for top 5 scores
            fifthKey = thisQuiz.getScores().lowerKey(fifthKey);
        }

        SortedMap<Integer, Player> top5Scores = thisQuiz.getScores().subMap(thisQuiz.getScores().firstKey(),fifthKey);
        int position = 1;   //allow for 1-5 numbering
        for (Map.Entry<Integer, Player> entry: top5Scores.entrySet()){
            System.out.println(position + ". " + entry.getValue().getName() + " - " + entry.getKey());  //print score and player name
        }

        System.out.println("\nType R to replay; N for new quiz; X to exit");
        String choice = input.nextLine().toLowerCase();

        switch (choice) {
            case "n":
                chooseQuiz();
                break;
            case "r":
                playQuiz(quizId);
                break;
            case "x":
                System.out.println("Thanks for playing! See you next time.");
                return;
            default:
                System.out.println("Invalid choice, please enter R, N or X.");
                break;
        }
    }
}
