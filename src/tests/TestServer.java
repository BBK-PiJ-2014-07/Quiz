package tests;

import quiz.QuizServer;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class TestServer {
    private QuizServer server;

    @Before
    public void buildUp() throws RemoteException {
        server = new QuizServer();
    }

    @Test
    public void testCreateQuizCreatesQuiz(){
        String input = "what is the capital of France\nparis\nlondon\nrome\nbrussels\nN";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        server.createQuiz("test quiz");
        assertEquals("test quiz", server.getQuizList().get(0).getQuizName());
    }
    @Test
    public void testCreateQuizAdds1Question(){
        String input = "what is the capital of France\nparis\nlondon\nrome\nbrussels\nN";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        server.createQuiz("test quiz");
        assertTrue(server.getQuizList().get(0).answerQuestion(1,"paris"));
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

    @Test
    public void testPlayQuizWrongId(){
        server.createQuiz("test");
        assertEquals(-1,server.playQuiz(999));
    }

    @Test
    public void testPlayQuizWrongAns(){
        server.createQuiz("test quiz");
        server.addQuestion(6, "What comes after A?", "B", "C", "D", "E");
        server.addQuestion(6, "What is 1+1?", "2", "3", "4","5");
        String input = "B\n5";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        int score = server.playQuiz(6);
        assertEquals(1, score);
    }
}

