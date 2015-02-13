package tests;

import clients.SetupClient;
import org.junit.*;
import static org.junit.Assert.*;

import server.QuizServer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

/**
 * Created by Sophie on 02/02/15.
 * Test the setup client
 */
public class TestSetupClient {
    SetupClient setup;
    QuizServer server;

    @Before
    public void buildUp() throws FileNotFoundException, RemoteException {
        setup = new SetupClient();
        server = new QuizServer();
    }

    @Test
    public void testCreateQuiz(){
        String input = "test quiz\nwhat is the capital of england?\nlondon\nparis\nbeijing\ntokyo\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        //setup.createQuiz();
        System.setIn(null);
        assertEquals("test quiz", server.getQuizList().get(0).getQuizName());
    }

}
