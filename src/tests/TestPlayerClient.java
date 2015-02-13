package tests;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import clients.PlayerClient;
import server.QuizServer;

/**
 * Testing class for Player Client
 * @see clients.PlayerClient
 */
@RunWith(MockitoJUnitRunner.class)
public class TestPlayerClient {
    private PlayerClient player;
    private QuizServer server;

    @Before
    public void buildUp(){
        player = new PlayerClient();
        server = Mockito.mock(QuizServer.class);
        server.createQuiz("test quiz");

    }

    @Test
    public void testConnect(){
        assertEquals("Server response",player.connectServer("//120.0.0.1/"));


    }
    /*
    @Test
    public void testPlayQuiz(){
        String input = "B\n2";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

    }*/
}
