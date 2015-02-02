package tests;

import org.junit.*;
import static org.junit.Assert.*;

import quiz.Quiz;



/**
 * Created by Sophie on 24/01/15.
 */
public class TestQuiz {
    private Quiz quiz;
    @Before
    public void buildUp(){
        quiz = new Quiz();
    }

    @Test
    public void testQuestionCorrectAns(){
        quiz.addQuestion("what is the capital of France", "paris","london","rome","brussels");
        assertTrue(quiz.answerQuestion(0,"paris"));
    }
    @Test
    public void testQuestionWrongAns(){
        quiz.addQuestion("what is the capital of France", "paris","london","rome","brussels");
        assertFalse(quiz.answerQuestion(0,"brussels"));
    }
}
