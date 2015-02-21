package tests;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import clients.PlayerClient;
import resource.Player;
import resource.Question;
import server.QuizServer;
import service.QuizService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Testing class for Player Client
 * @see clients.PlayerClient
 */
@RunWith(MockitoJUnitRunner.class)
public class TestPlayerClient {
    private PlayerClient player;
    private static File file;
    private static QuizService server;

    @BeforeClass
    public static void doFirst() throws IOException {
        file = new File("playerClientTest.txt");
        file.createNewFile();
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
        List<Question> questions = new ArrayList<>();
        Question q1 = new Question(1, "What is the capital of France?");
        q1.addAnswers("paris","london","tokyo","madrid");
        Question q2 = new Question(2, "What is 1+1");
        q2.addAnswers("2","3","4","5");

        try {
            server.createQuiz("test quiz", questions);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        String answers = "paris\n2";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        /*try {
            //player.playQuiz(1, player1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(2,(int)server.getQuizList().get(0).getHighScore().getValue());
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/

    }

    @AfterClass
    public static void doLast(){
        file.delete();
    }
}
