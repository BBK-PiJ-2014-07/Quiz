package tests;

import quiz.QuizServer;
import org.junit.*;

import java.io.ByteArrayInputStream;
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
    @Test
    public void testAddQuestion(){
        server.createQuiz("test quiz");
        server.addQuestion(2, "What comes after A?", "B", "C", "D", "E");
        assertTrue(server.getQuizList().get(0).answerQuestion(1,"B"));
    }

    @Test
    public void testCloseQuiz(){
        server.createQuiz("test quiz");
        server.closeQuiz(3);
        assertTrue(server.getQuizList().get(0).isClosed());
    }

    @Test
    public void testPlayQuiz(){
        server.createQuiz("test quiz");
        server.addQuestion(4, "What comes after A?", "B", "C", "D", "E");
        server.addQuestion(4, "What is 1+1?", "2", "3", "4","5");
        String input = "B\n2";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        int score = server.playQuiz(4);
        assertEquals(2, score);
    }
}

