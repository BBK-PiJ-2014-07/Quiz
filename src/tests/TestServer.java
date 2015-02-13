package tests;

import server.QuizServer;
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
        String input = "what is the capital of France\nparis\nlondon\nrome\nbrussels\nY\nWhat is 1+1\n2\n3\n4\n5\nN";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        server.createQuiz("test quiz");
    }

    @Test   //quiz1
    public void testCreateQuizCreatesQuiz(){
        assertEquals("test quiz", server.getQuizList().get(0).getQuizName());
    }
    @Test   //quiz2
    public void testCreateQuizAdds1Question(){
        assertTrue(server.getQuizList().get(0).answerQuestion(1,"paris"));
    }

    @Test   //quiz 3
    public void testCloseQuiz(){
        server.closeQuiz(3);
        assertTrue(server.getQuizList().get(0).isClosed());
    }

    @Test   //quiz 4
    public void testPlayQuizScore(){
        String answers="paris\n2";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        int score = server.playQuiz(4);
        assertEquals(2, score);
    }

    @Test   //quiz 5
    public void testPlayQuizWrongId(){
        assertEquals(-1,server.playQuiz(999));
    }

    @Test   //quiz 6
    public void testPlayQuizWrongAns(){
        String answers="paris\n5";
        System.setIn(new ByteArrayInputStream(answers.getBytes(StandardCharsets.UTF_8)));
        int score = server.playQuiz(6);
        assertEquals(1, score);
    }
}

