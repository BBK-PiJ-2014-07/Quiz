package tests;

import org.junit.*;
import static org.junit.Assert.*;
import resource.Question;

/**
 *  Test class for Question
 *  @see resource.Question
 *  NB. unable to test for correctness as the shuffle changes each time.
 */
public class TestQuestion {
    private Question question;

    @Before
    public void buildUp(){
        question = new Question(1, "What is 1+1?");
        question.addAnswers("2","3","4","5");
    }

    @Test
    public void testToString(){
        System.out.println(question);
    }
}
