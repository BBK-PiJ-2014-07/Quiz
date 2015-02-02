package tests;

import quiz.QuizServer;
import org.junit.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class TestServer {
    private QuizServer server;

    @Before
    public void buildUp(){
        server = new QuizServer();
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
