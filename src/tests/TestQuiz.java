package tests;

import org.junit.*;
import static org.junit.Assert.*;

import quiz.Quiz;

/**
 * Created by Sophie on 24/01/15.
 */
public class TestQuiz {

    @Test
    public void testQuestionAddAnswers(){
        Quiz.Question testQ = new Quiz.Question(1, "What is the capital of France?");
        testQ.addAnswer("Paris");
        testQ.addAnswer("Moscow");
        testQ.addAnswer("London");
        testQ.addAnswer("Berlin");
        assertEquals(testQ.getAnswerList.get(3),"Paris");
    }
}
