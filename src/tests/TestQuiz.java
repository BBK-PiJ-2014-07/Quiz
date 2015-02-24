package tests;

import org.junit.*;
import static org.junit.Assert.*;

import resource.Player;
import resource.Question;
import resource.Quiz;



/**
* Unit tests for Quiz
 * @see resource.Quiz
 * */
public class TestQuiz {
    private Quiz quiz;

    @Before
    public void buildUp(){
        quiz = new Quiz(1,"test quiz");
        Question q1 = new Question(1, "What is the capital of France?");
        q1.addAnswers("paris", "brussels", "london", "tokyo");
        quiz.addQuestion(q1);
    }

    @Test
    public void testQuestionCorrectAns(){
        assertTrue(quiz.answerQuestion(1,"paris"));
    }
    @Test
    public void testQuestionWrongAns(){
        assertFalse(quiz.answerQuestion(1, "brussels"));
    }

    @Test
    public void testScoreReverseSort(){
        quiz.getScores().put(3, new Player("blah"));
        quiz.getScores().put(15, new Player("blah"));
        quiz.getScores().put(12, new Player("blah"));
        quiz.getScores().put(1, new Player("blah"));
        assertEquals(15, (int) quiz.getScores().firstKey());
    }
}
