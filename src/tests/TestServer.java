package tests;

import resource.Player;
import resource.Question;
import server.QuizServer;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class TestServer {
    private QuizServer server;
    private Player player1;

    @Before
    public void buildUp() throws RemoteException {
        player1 = new Player("Michael");
        server = new QuizServer();
        String[][] questions = new String[2][5];
        questions[0][0] = "What is the capital of France?";
        questions[0][1] = "paris";
        questions[0][2] = "brussels";
        questions[0][3] = "london";
        questions[0][4] = "tokyo";
        questions[1][0] = "What is 1+1";
        questions[2][1] = "2";
        questions[3][2] = "3";
        questions[4][3] = "4";
        questions[5][4] = "5";

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
        assertTrue(server.getQuizList().get(0).isClosed());
    }

    @Test   //quiz 4
    public void testPlayQuizScore(){
        String answers="paris\n2";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        int score = server.playQuiz(4,player1);
        assertEquals(2, score);
    }

    @Test   //quiz 5
    public void testPlayQuizWrongId(){
        assertEquals(-1,server.playQuiz(999,player1));
    }

    @Test   //quiz 6
    public void testPlayQuizWrongAns(){
        String answers="paris\n5";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        int score = server.playQuiz(6,player1);
        assertEquals(1, score);
    }

    @Test   //quiz 7
    public void testHighScore(){
        Player player2 = new Player("Lindsay");
        String answers="paris\n5";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        server.playQuiz(7,player1);
        String newAnswers = "paris\n2";
        System.setIn(new ByteArrayInputStream(newAnswers.getBytes(StandardCharsets.UTF_8)));
        server.playQuiz(7,player2);
        assertEquals(server.getQuizList().get(0).getHighScore().getKey().getName(),"Lindsay");
    }

    /*@Test
    public void testAddPlayer(){
        server.addNewPlayer("Lucille");
        Player lucille = new Player("Lucille");
        lucille.setId(1);
        assertTrue(server.getPlayerList().get(0).equals(lucille));
    }*/
}

