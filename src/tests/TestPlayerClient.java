package tests;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import clients.PlayerClient;
import resource.Player;
import server.QuizServer;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

/**
 * Testing class for Player Client
 * @see clients.PlayerClient
 */
@RunWith(MockitoJUnitRunner.class)
public class TestPlayerClient {
    private PlayerClient player;
    private static QuizServer server;
    private Player player1;

    @BeforeClass
    public static void doFirst() throws RemoteException {
       server = new QuizServer();
        server.startServer();
    }

    @Before
    public void buildUp(){
        player = new PlayerClient();
        player1 = new Player("Michael");

    }

    @Test
    public void testConnect() {
        assertEquals("Server response",player.connectServer());
    }
    @Test   //quiz 4
    public void testPlayQuizScore(){
        String answers="paris\n2";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        int score = player.playQuiz(4,player1);
        assertEquals(2, score);
    }

    @Test   //quiz 5
    public void testPlayQuizWrongId(){
        assertEquals(-1,player.playQuiz(999,player1));
    }

    @Test   //quiz 6
    public void testPlayQuizWrongAns(){
        String answers="paris\n5";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        int score = player.playQuiz(6,player1);
        assertEquals(1, score);
    }

    @Test   //quiz 7
    public void testHighScore(){
        Player player2 = new Player("Lindsay");
        String answers="paris\n5";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        player.playQuiz(7, player1);
        String newAnswers = "paris\n2";
        System.setIn(new ByteArrayInputStream(newAnswers.getBytes(StandardCharsets.UTF_8)));
        player.playQuiz(7, player2);
        assertEquals(server.getQuizList().get(0).getHighScore().getKey().getName(),"Lindsay");
    }
}
