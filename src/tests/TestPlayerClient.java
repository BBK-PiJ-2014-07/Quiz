package tests;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import clients.PlayerClient;
import server.QuizServer;

import java.rmi.RemoteException;

/**
 * Testing class for Player Client
 * @see clients.PlayerClient
 */
@RunWith(MockitoJUnitRunner.class)
public class TestPlayerClient {
    private PlayerClient player;
    private static QuizServer server;

    @BeforeClass
    public static void doFirst() throws RemoteException {
       server = new QuizServer();
        server.startServer();
    }

    @Before
    public void buildUp(){
        player = new PlayerClient();
    }

    @Test
    public void testConnect() {
        assertEquals("Server response",player.connectServer());
    }
    /*
    @Test
    public void testPlayQuiz(){
        String input = "B\n2";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

    }*/
}
