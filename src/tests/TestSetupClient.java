package tests;

import org.junit.*;
import static org.junit.Assert.*;
import quiz.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by Sophie on 02/02/15.
 * Test the setup client
 */
public class TestSetupClient {
    SetupClient setup;
    QuizServer server;

    @Before
    public void buildUp() throws FileNotFoundException {
        setup = new SetupClient();
        server = new QuizServer();
    }

    @Test
    public void testCreateQuiz(){
        String input = "test quiz\nwhat is the capital of england?\nlondon\nparis\nbeijing\ntokyo\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        setup.createQuiz();
        System.setIn(null);

        Quiz testQuiz = (Quiz) server.getQuizList().iterator().next();
        assertEquals("test quiz", testQuiz.getQuizName());
    }

}
