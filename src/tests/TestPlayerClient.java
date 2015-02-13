package tests;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import clients.PlayerClient;
import server.QuizServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Testing class for Player Client
 * @see clients.PlayerClient
 */
@RunWith(MockitoJUnitRunner.class)
public class TestPlayerClient {
    private PlayerClient player;
    private QuizServer server;

    @BeforeClass
    public static void doFirst() throws RemoteException {
        LocateRegistry.createRegistry(1099); //TODO
        System.setProperty("java.security.policy","/Users/Sophie/Documents/PiJCoursework/Quiz/src/clients/client.policy");
    }

    @Before
    public void buildUp(){

        player = new PlayerClient();
        server = Mockito.mock(QuizServer.class);

    }

    @Test
    public void testConnect() {
        server.startServer();
        assertEquals("Server response",player.connectServer());


    }
    /*
    @Test
    public void testPlayQuiz(){
        String input = "B\n2";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

    }*/
}
