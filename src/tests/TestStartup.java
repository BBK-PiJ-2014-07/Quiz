package tests;

import org.junit.*;
import static org.junit.Assert.*;
import quiz.*;

import java.io.*;

/**
 * Created by Sophie on 02/02/15.
 * Test the start-up client
 */
public class TestStartup {
    Setup setup;

    @Before
    public void buildUp() throws FileNotFoundException {
        setup = new Setup();
        System.setIn(new FileInputStream("tests/testStartup.txt"));
    }

    @Test
    public void testAddQuiz(){
        setup.addQuiz("test quiz");
    }

}
