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
        String input = "what is the capital of France\nparis\nlondon\nrome\nbrussels\nN";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        server.createQuiz("test quiz");
        server.closeQuiz(3);
        assertTrue(server.getQuizList().get(0).isClosed());
    }

    @Test
    public void testPlayQuizScore(){
        String input = "what is the capital of France\nparis\nlondon\nrome\nbrussels\nY\nWhat is 1+1\n2\n3\n4\n5\nN\nparis\n2";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        server.createQuiz("test quiz");
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
        String input = "what is the capital of France\nparis\nlondon\nrome\nbrussels\nY\nWhat is 1+1\n2\n3\n4\n5\nN\nparis\n5";
        server.createQuiz("test quiz");
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        int score = server.playQuiz(6);
        assertEquals(1, score);
    }
}

