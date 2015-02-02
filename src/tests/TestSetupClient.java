package tests;

import org.junit.*;
import static org.junit.Assert.*;
import quiz.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by Sophie on 02/02/15.
 * Test the start-up client
 */
public class TestSetupClient {
    SetupClient setup;
    QuizServer server;

    @Before
    public void buildUp() throws FileNotFoundException {
        setup = new SetupClient();
        server = new QuizServer();
        System.setIn(new FileInputStream("tests/testStartup.txt"));
    }

    @Test
    public void testCreateQuiz(){
        String input = "test quiz\nwhat is the capital of england?\nlondon\nparis\nbeijing\ntokyo\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        server.createQuiz();
        System.setIn(null);
        assertEquals("test quiz", server.getQuizList().get(0).getQuizName());
    }

}
