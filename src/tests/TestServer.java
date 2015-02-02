package tests;

import quiz.QuizServer;
import org.junit.*;

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
        server.addQuestion(1, "What comes after A?", "B", "C", "D", "E");
        assertTrue(server.getQuizList().get(0).answerQuestion(1,"B"));
    }

}

