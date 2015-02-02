package tests;

import quiz.Quiz;
import quiz.QuizServer;
import org.junit.*;

import java.util.Iterator;

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
        Iterator iterator = server.getQuizList().iterator();
        Quiz testQuiz = (Quiz) iterator.next();
        assertEquals("test quiz", testQuiz.getQuizName());
    }
    @Test
    public void testAddQuestion(){
        server.createQuiz("test quiz");
        server.addQuestion(1, "What comes after A?", "B", "C", "D", "E");
        Iterator iterator = server.getQuizList().iterator();
        Quiz testQuiz = (Quiz) iterator.next();
        assertTrue(testQuiz.answerQuestion(1, "B"));
    }

}

