package tests;

import org.junit.*;
import static org.junit.Assert.*;

import resource.Quiz;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;


/**
* Unit tests for Quiz
 * @see resource.Quiz
 * */
public class TestQuiz {
    private Quiz quiz;

    @Before
    public void buildUp(){
        quiz = new Quiz("test quiz");
        String input = "what is the capital of France\nparis\nlondon\nrome\nbrussels\nN";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        quiz.addQuestions();
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
