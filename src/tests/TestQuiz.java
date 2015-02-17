package tests;

import org.junit.*;
import static org.junit.Assert.*;

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
        quiz = new Quiz("test quiz");
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
}
