package tests;

import org.junit.*;
import static org.junit.Assert.*;

import quiz.Quiz;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;


/**
* Unit tests for Quiz
 * @see quiz.Quiz
 * */
public class TestQuiz {
    private Quiz quiz;
    @Before
    public void buildUp(){
        quiz = new Quiz("test quiz");
    }

    @Test
    public void testQuestionCorrectAns(){
        String input = "what is the capital of France\nparis\nlondon\nrome\nbrussels\nN";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        quiz.addQuestions();

        assertTrue(quiz.answerQuestion(0,"paris"));
    }
    @Test
    public void testQuestionWrongAns(){
        String input = "what is the capital of France\nparis\nlondon\nrome\nbrussels\nN";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        quiz.addQuestions();
        assertFalse(quiz.answerQuestion(0,"brussels"));
    }
}
