package tests;

import clients.SetupClient;
import org.junit.*;
import static org.junit.Assert.*;

import server.QuizServer;
import service.QuizService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

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
        setup = new SetupClient();
        setup.execute();

    }


    @Test
    public void testCreateQuiz(){
        try {
            String answers = "test quiz\nwhat is 1+1\n2\n3\n4\n5\nN";
            System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
            setup.createQuiz();
            assertEquals("test quiz", server.getQuizList().get(0).getQuizName());

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown(){
        file.delete();
    }

}
