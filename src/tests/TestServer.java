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
        server.createQuiz("test quiz");
        assertEquals("test quiz", server.getQuizList().get(0).getQuizName());
    }
}
