package tests;

import clients.SetupClient;
import org.junit.*;
import static org.junit.Assert.*;

import resource.Question;
import server.QuizServer;
import service.QuizService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sophie on 02/02/15.
 * Test the setup client
 */
public class TestSetupClient {
    private static SetupClient setup;
    private static File file;
    private static QuizService server;

    @BeforeClass
    public static void setUpFirst() throws IOException {
        file = new File("setuptest.txt");
        file.createNewFile();
        server = new QuizServer(file);
        server.start();
    }


    @Test
    public void testCreateQuiz() throws RemoteException{
            String answers = "test quiz\nwhat is 1+1\n2\n3\n4\n5\nN";
            System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
            setup = new SetupClient();
            setup.connectToServer();    //connect the client
            setup.createQuiz();
            assertEquals("test quiz", server.getQuizList().get(0).getQuizName());
    }

    @Test
    public void testCloseQuiz() throws RemoteException{
        List<Question> qList = new ArrayList<>();
        Question q1 = new Question(1, "What is the capital of France?");
        q1.addAnswers("paris","london","tokyo","madrid");
        qList.add(q1);
        server.createQuiz("test quiz", qList);

        String input = "1\nY";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        setup = new SetupClient();
        setup.connectToServer();    //connect the client
        assertEquals(1, setup.closeQuiz());
        }

    @Test
         public void testCloseQuizInvalidId() throws RemoteException {
        String input = "999";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        assertEquals(-1, setup.closeQuiz());
    }

    @Test
    public void testCloseQuizAlreadyClosed() throws RemoteException {
        String input = "1";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        assertEquals(-1, setup.closeQuiz());
    }

    @AfterClass
    public static void tearDown(){
        file.delete();
    }

}
