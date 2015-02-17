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
    private SetupClient setup;
    private static QuizService server;

    @BeforeClass
    public static void setUpFirst() throws RemoteException {
        server = new QuizServer();
        server.launch();

    }
    @Before
    public void buildUp() {
        setup = new SetupClient();
        setup.launch();
    }

    @Test
    public void testCreateQuiz(){
        String input = "test quiz\nwhat is the capital of england?\nlondon\nparis\nbeijing\ntokyo\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        setup.createQuiz();
        try {
            assertEquals("test quiz", server.getQuizList().get(0).getQuizName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
