package clients;

import lombok.Data;
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
@Data
public class PlayerClient {
    private QuizService server;
    private int playerId;

    /**
     * Connects the server, prints welcome message, gets player name and begins the process of playing a quiz.
     * If the player already exists on the server, the player is greeted with "Welcome back".
     * Player identification is done using both id number and name, but the name must be unique.
     */
    public void execute(){
        Scanner input = new Scanner(System.in);
        connectToServer(); //connect server

        for (int i = 0; i < 60; i++) {
            System.out.print("=");      //horizontal rule
        }
        System.out.println("\nPLAY A QUIZ\n");
        System.out.println("Please enter your name.");
        String playerName = input.nextLine();

        while (playerName.trim().isEmpty()){
            System.out.println("Player name cannot be blank!");
            playerName = input.nextLine();
        }

        String findPlayerName = playerName; //local variable to stop lambda complaining
        try {
            if (server.getPlayerList().stream()         // check to see if player exists already (by name)
                    .noneMatch(p -> p.getName().equals(findPlayerName))) {
                playerId = server.addNewPlayer(playerName);
                System.out.println("\nWelcome " + playerName + "!");//if not, create player
            } else {
                playerId = server.getPlayerList().stream()      //otherwise get id of existing player
                        .filter(p -> p.getName().equals(findPlayerName))
                        .findFirst().get().getId();
                System.out.println("\nWelcome back " + playerName + "!");
            }

            chooseQuiz();   //go to quiz selection


        } catch (RemoteException ex){
            ex.printStackTrace();
        }
    }


    /**
     * Connect to the server
     */
    public void connectToServer(){
        try {
            Registry registry = LocateRegistry.getRegistry(1099); //TODO
            server = (QuizService) registry.lookup("QuizService");
        } catch (RemoteException | NotBoundException ex) {
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
        System.out.print("\n");
        for (int i=0; i<server.getQuizList().size(); i++){
            if (!server.getQuizList().get(i).isClosed()){   //only print open quizzes
                System.out.println(server.getQuizList().get(i).getId() + ". " + server.getQuizList().get(i).getQuizName());
            }
        }
        for (int i=0; i<60; i++){
            System.out.print("=");      //horizontal rule
        }
    }

    /**
     * Choose the quiz to play. If the quiz ID given does not exist, or it refers to a closed quiz, the user is
     * prompted to enter a valid ID number.
     * @throws RemoteException
     */
    public void chooseQuiz() throws RemoteException {
        Scanner input = new Scanner(System.in);
        int quizIdToPlay = 0;   //initialise to 0 to silence compiler warning about not being initialised
        boolean invalid;
        do {
            invalid = false;    //assume valid input
            printQuizNames(); //print all the quiz titles
            System.out.println("\nPlease enter the number of the quiz you want to play.");
            try {
                quizIdToPlay = Integer.parseInt(input.nextLine());  //get from user
            } catch (NumberFormatException ex){
                System.out.println("Please enter a quiz number!");
                invalid = true; //invalid option, so repeat
            }
            int thisId = quizIdToPlay;  //create variable equal to quizIdToPlay to allow lambda comparison
            if (server.getQuiz(thisId)==null) {    //check that quiz with this id exists
                System.out.println("There is no quiz with that number! Please try again.");
                invalid = true; //invalid option, so repeat
            } else if (server.getQuiz(thisId).isClosed()){ //check that quiz is not closed.
                System.out.println("This quiz is closed and can't be played.");
                invalid = true;
            }
        } while (invalid);
        playQuiz(quizIdToPlay); //play the quiz with the id
    }

    /**
     * Play a quiz. At the end of the quiz the user will be asked what they want to do next.
     * @param quizId - the ID of the quiz to play.
     * @throws RemoteException
     */
    public void playQuiz(int quizId) throws RemoteException {
        if (server.getQuiz(quizId)==null) { throw new IllegalArgumentException("Quiz not found");}  //Check that quiz exists

        Scanner input = new Scanner(System.in);
        List<String> answers = new ArrayList<>();   //list for user's answers
        Quiz thisQuiz = server.getQuiz(quizId);   //get the quiz
        System.out.println("\n" + thisQuiz.getQuizName().toUpperCase() + "\n");   //print the quiz name

        for (Question q : thisQuiz.getQuestions()) {
            System.out.println(q); //print the question
            System.out.print("\nEnter your answer: ");
            String ans = input.nextLine();   //get the answer from the user
            if (q.isCorrect(ans)){
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect! The answer is " + q.getCorrectAnswer() + ".");
            }
            answers.add(ans);   //add the answer to the list
        }
        int score = server.playQuiz(quizId, playerId, answers);  //play the quiz to get the score
        System.out.println("At the end of the quiz your score is " + score + "!");

        // Check if high score (if empty, automatically is high score)
        if ((thisQuiz.getScores().isEmpty() || score > thisQuiz.getScores().firstKey()) && score!=0){
            System.out.println("NEW HIGH SCORE! CONGRATULATIONS!" + "\n");
        }
        thisQuiz = server.getQuiz(quizId);   //refresh quiz to update scores

        System.out.println("Top scores:");  //print top 5 scores
        SortedMap<Integer, Player> topScores;
        if (thisQuiz.getScores().size() > 5) {
            int fifthKey = thisQuiz.getScores().firstKey();
            for (int i = 0; i < 5; i++) {       //Convoluted way of getting the 5th key value to allow for top 5 scores
                fifthKey = thisQuiz.getScores().lowerKey(fifthKey);
            }
            topScores = thisQuiz.getScores().subMap(thisQuiz.getScores().firstKey(), fifthKey); //trim to top 5 entries
        } else {
            topScores = thisQuiz.getScores();   //if fewer than 5 entries, print them all
        }
            int position = 1;   //allow for 1-5 numbering

            for (Map.Entry<Integer, Player> entry: topScores.entrySet()){
                System.out.println(position + ". " + entry.getValue().getName() + " - " + entry.getKey());  //print score and player name
                position++;
            }
        boolean invalid;
        do {
            System.out.println("\n What would you like to do?");
            System.out.println("1. Replay this quiz");
            System.out.println("2. Play a new quiz");
            System.out.println("3. Return to main menu");
            invalid = false;
            try {
                int choice = Integer.parseInt(input.nextLine());

                    switch (choice) {
                        case 1:
                            playQuiz(quizId);
                            break;
                        case 2:
                            chooseQuiz();
                            break;
                        case 3:
                            return;
                        default:
                            System.out.println("Invalid choice, please enter a number.");
                            invalid = true;     //invalid option - repeat menu
                            break;
                    }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a number!");
            }
        } while (invalid);
    }
}
