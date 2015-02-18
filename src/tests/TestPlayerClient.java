package tests;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import clients.PlayerClient;
import resource.Player;
import server.QuizServer;
import service.QuizService;

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
    private static QuizService server;

    @BeforeClass
    public static void doFirst() throws RemoteException {
        server = new QuizServer();
        server.start();
    }

    @Before
    public void buildUp(){
        player = new PlayerClient();
    }



    @Test
    public void testPlayQuiz(){
        player.launch();
        Player player1 = new Player("Alfred");
        String[][] questions = new String[2][5];
        questions[0][0] = "What is the capital of France?";
        questions[0][1] = "paris";
        questions[0][2] = "brussels";
        questions[0][3] = "london";
        questions[0][4] = "tokyo";
        questions[1][0] = "What's 1+1?";
        questions[1][1] = "2";
        questions[1][2] = "3";
        questions[1][3] = "4";
        questions[1][4] = "5";

        try {
            server.createQuiz("test quiz", questions);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        String answers = "paris\n2";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        try {
            player.playQuiz(1, player1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(2,(int)server.getQuizList().get(0).getHighScore().getValue());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
