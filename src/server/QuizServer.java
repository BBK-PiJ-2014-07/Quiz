package server;

import resource.Player;
import resource.Question;
import resource.Quiz;
import service.QuizService;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of QuizService. The file for I/O is passed into the constructor.
 * This enables a different file to be used for testing.
 * After every call to createQuiz, playQuiz or closeQuiz the data file is written
 * @author Sophie Koonin
 * @see service.QuizService
 */

public class QuizServer extends UnicastRemoteObject implements QuizService {
    private List<List<?>> data;   //list for storing playerList + quizList
    private List<Quiz> quizList;    //list of all quizzes
    private List<Player> playerList;    //list of all players
    private File file;  //the file to be written to/read from - supplied by factory

    public QuizServer() throws RemoteException {
    }

    public QuizServer(File file) throws IOException {
        this.file = file;

        //TODO - check warnings
        if (file.length() > 0){
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(file));

            try {
                data = (List<List<?>>) inStream.readObject();   //the object written to file is the data list
                quizList = (List<Quiz>) data.get(0);    //first object in data list is quizList
                playerList = (List<Player>)data.get(1); //second object is playerList
                inStream.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {    //if the file is empty the lists must be created
            data = new ArrayList<>();
            quizList = new ArrayList<>();
            playerList = new ArrayList<>();
            data.add(quizList); //add the quizList to the output list
            data.add(playerList);   //add the playerList to the output list


        }

    }

    /**
     * Start the server.
     */
    public void start(){
        try {
            String name = "QuizService";
            QuizService server = new QuizServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(name, server);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Play a quiz
     * @param quizId - the id of the quiz to play
     * @param playerId - the id of player currently playing
     * @return the score or -1 if there is an error
     */
    public int playQuiz(int quizId, int playerId, List<String> answers) {

        //check that quiz and player IDs both valid. if not, return -1


        int score = 0;
        Quiz thisQuiz = quizList.stream().filter(q->q.getId()==quizId).findFirst().get();

       for (int i=0; i<thisQuiz.getQuestions().size(); i++){
           //Check the answer for each question, and increment score accordingly
           if (thisQuiz.answerQuestion(i+1,answers.get(i))) { score++; }
       }
        Player thisPlayer = playerList.stream()
                .filter(p -> p.getId() == playerId)
                .findFirst().get(); //get the player details

        //Check to see if score is a high score
        if (score > thisQuiz.getHighScore().getValue()) {   //compare to existing high score

            thisQuiz.setHighScore(new AbstractMap.SimpleEntry<>(thisPlayer, score));    //set the high score
        }
        thisPlayer.getScores().put(quizId,score);   //store the score for this quiz in player's score map
        return score;
    }

    /**
     * create a new quiz
     * @param quizName - the name of the quiz
     * @param questionMatrix - a 2d array of questions and answers
     * @return the ID of the new quiz
     */
    @Override
    public int createQuiz(String quizName, List<Question> questions) {
        Quiz newQuiz = new Quiz(quizName);
        questions.forEach(newQuiz::addQuestion);
        quizList.add(newQuiz);
        writeToFile();
        return newQuiz.getId();
    }

    /**
     * Close the selected quiz so nobody else can play it
     * @param quizId - the id of the quiz to close
     */
    @Override
    public void closeQuiz(int quizId) {
        quizList.stream().filter(q -> q.getId() == quizId).forEach(Quiz::setClosed);
        writeToFile();
    }

    /**
     * Add a new player to the internal player list
     * @param name - the name of the player to be added
     */
    @Override
    public void addNewPlayer(String name){
        Player newPlayer = new Player(name);
        playerList.add(newPlayer);
    }

    /**
     * Send a response to a connected client
     * @return response
     */

    @Override
    public String echo(){ return "Server response"; }

    /** Return the list of quizzes
     * @return the list of quizzes
     */
    @Override
    public List<Quiz> getQuizList(){ return quizList;}

    /**
     * Return the list of players
     * @return the list of players
     */
    public List<Player> getPlayerList() { return playerList; }

    /**
     * Write the data list to file.
     */
    public void writeToFile(){
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file));
            outStream.reset();  //clear the file, to avoid appending rather than overwriting
            outStream.writeObject(data);    //write the data list to file
            outStream.close();  //close the OutputStream
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
