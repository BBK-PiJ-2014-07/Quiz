package tests;

import org.junit.*;
import quiz.*;

import java.io.*;

/**
 * Created by Sophie on 02/02/15.
 * Test the start-up client
 */
public class TestSetupClient {
    SetupClient setup;

    @Before
    public void buildUp() throws FileNotFoundException {
        setup = new SetupClient();
        System.setIn(new FileInputStream("tests/testStartup.txt"));
    }

    @Test
    public void testCreateQuiz(){
        setup.createQuiz();
        assertEquals("test quiz", setup.getQuizList.get(0).getQuizName);

    }

}
