package tests;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import clients.PlayerClient;
import resource.Question;
import resource.Quiz;
import server.QuizServer;
import service.QuizService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
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
        server = new QuizServer(file);
        server.start();
    }

    @Before
    public void buildUp(){
        player = new PlayerClient();
    }


    @Test
    public void testServerConnection() throws RemoteException {
        assertEquals("Server response", server.echo());
    }

    @Test
    public void testPlayQuiz(){
        player.execute();
        String answers = "paris\n2";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        List<Question> questions = new ArrayList<>();
        Question q1 = new Question(1, "What is the capital of France?");
        q1.addAnswers("paris","london","tokyo","madrid");
        Question q2 = new Question(2, "What is 1+1");
        q2.addAnswers("2","3","4","5");
        questions.add(q1);
        questions.add(q2);

        try {
            server.addNewPlayer("Alfred");
            server.createQuiz("test quiz", questions);
            assertTrue(server.getQuizList().get(0).getScores().containsKey(2));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void doLast(){
        file.delete();
    }
}
