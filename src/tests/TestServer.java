package tests;

import resource.Player;
import resource.Question;
import resource.Quiz;
import server.QuizServer;
import org.junit.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;

public class TestServer {
    private static QuizServer server;
    private static Player player1;
    private static File testFile;
    private List<String> answers;

    @BeforeClass
    public static void doFirst() throws IOException {
        player1 = new Player("Michael");
        testFile = new File("testFile.txt");
        testFile.createNewFile();
        server = new QuizServer(testFile);
        server.getPlayerList().add(player1);
    }
    @Before
    public void buildUp() {

        String[][] questions = new String[20][5];
        questions[0][0] = "What is the capital of France?";
        questions[0][1] = "paris";
        questions[0][2] = "brussels";
        questions[0][3] = "london";
        questions[0][4] = "tokyo";
        questions[1][0] = "What is 1+1";
        questions[1][1] = "2";
        questions[1][2] = "3";
        questions[1][3] = "4";
        questions[1][4] = "5";
        answers = new ArrayList<>();
        answers.add("paris");
        answers.add("2");
        server.createQuiz("test quiz", questions);
    }

    @Test   //quiz1
    public void testCreateQuizCreatesQuiz(){
        assertEquals("test quiz", server.getQuizList().get(0).getQuizName());
    }

    @Test   //quiz2
    public void testCreateQuizAdds1Question(){
        assertTrue(server.getQuizList().get(0).answerQuestion(1,"paris"));
    }

    @Test   //quiz 3
    public void testCloseQuiz(){
        server.closeQuiz(3);
        assertTrue(server.getQuizList().get(2).isClosed());
    }

    @Test   //quiz 4
    public void testWriteToFileWritesSomething(){
        assertTrue(testFile.length() > 0);
    }

    @Test   //quiz 5
    public void testWriteToFileWritesCorrectly() throws IOException, ClassNotFoundException{
        ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(testFile));
        List<List<?>> data = (List) inStream.readObject();
        List<Quiz> quizList = (List) data.get(0);
        inStream.close();
        assertEquals(quizList.get(0).getQuizName(), "test quiz");
    }

    @Test   //quiz 6
    public void testPlayQuizScore(){
        int score = server.playQuiz(1, 1, answers);
        assertEquals(2, score);
    }

    @Test   //quiz 7
    public void testPlayQuizWrongId(){
        assertEquals(-1,server.playQuiz(999,1,answers));
    }

    @Test   //quiz 8
    public void testPlayQuizWrongAns(){
        answers.set(1,"5");
        int score = server.playQuiz(1,1 ,answers);
        assertEquals(1, score);
    }

    @Test   //quiz 9
    public void testHighScore(){
        Player player2 = new Player("Lindsay");
        server.getPlayerList().add(player2);
        server.playQuiz(1,1,answers);   //both correct
        answers.set(1,"5");
        server.playQuiz(1,2,answers);
        assertEquals(server.getQuizList().get(0).getHighScore().getKey().getName(),"Michael");
    }

    @Test
    public void testAddPlayer(){
        server.addNewPlayer("Lucille");
        Player lucille = new Player("Lucille");
        lucille.setId(1);
        assertTrue(server.getPlayerList().get(0).equals(lucille));
    }

    @AfterClass
    public static void closeDown(){
        testFile.delete();
    }
}

