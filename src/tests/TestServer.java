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

/**
 * Tests for basic server functionality. Make sure no serverTestData.txt file exists before running these tests.
 */
public class TestServer {
    private static QuizServer server;
    private static Player player1;
    private static File testFile;
    private List<String> answers;

    @BeforeClass
    public static void doFirst() throws IOException {
        testFile = new File("serverTestData.txt");
        testFile.createNewFile();
        server = new QuizServer(testFile);
        server.addNewPlayer("Michael");

    }
    @Before
    public void buildUp() {

        List<Question> questions = new ArrayList<>();
        Question q1 = new Question(1, "What is the capital of France?");
        q1.addAnswers("paris","london","tokyo","madrid");
        Question q2 = new Question(2, "What is 1+1");
        q2.addAnswers("2","3","4","5");
        questions.add(q1);
        questions.add(q2);
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

    @Test (expected = IllegalArgumentException.class)
    public void testCloseQuizInvalidId(){
        server.closeQuiz(999);
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

    /**
     * Test that looking up a wrong ID throws an exception.
     * Checking for this is done in PlayerClient
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPlayQuizWrongId(){
        server.playQuiz(999, 1, answers);
    }

    @Test   //quiz 8
    public void testPlayQuizWrongAns(){
        answers.set(1,"5");
        int score = server.playQuiz(1,1 ,answers);
        assertEquals(1, score);
    }

    @Test   //quiz 9
    public void testHighScore(){
        server.addNewPlayer("Lindsay");
        server.playQuiz(1,1,answers);   //both correct
        answers.set(1,"5");
        server.playQuiz(1,2,answers);
        assertTrue(server.getQuizList().get(0).getScores().containsKey(2));
    }

    @Test
    public void testAddPlayer(){
        server.addNewPlayer("Lucille");
        Player lucille = new Player(3,"Lucille");
        assertTrue(server.getPlayerList().contains(lucille));
    }

    @Test
    public void testGetPlayer(){
        Player tobias = new Player(4, "Tobias");
        server.addNewPlayer("Tobias");
        assertEquals(tobias, server.getPlayer(4));
    }


    @Test
    public void testGetPlayerInvalidId(){
        assertNull(server.getPlayer(99));
    }

    @Test
    public void testGetQuiz(){
        assertEquals("test quiz", server.getQuiz(1).getQuizName());
    }

    @Test
    public void testGetQuizInvalidId(){
        assertNull(server.getQuiz(99));
    }
    @Test
    public void testPlayerIdIncrements(){
        server.addNewPlayer("Oscar");
        server.addNewPlayer("George");
        server.addNewPlayer("GOB");
        server.addNewPlayer("Maeby");
        assertEquals("Maeby", server.getPlayer(8).getName());
    }
    @AfterClass
    public static void closeDown(){
        testFile.delete();
    }
}

