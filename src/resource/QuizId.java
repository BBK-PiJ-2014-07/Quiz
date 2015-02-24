package resource;

import java.io.Serializable;

/**
 * Singleton class to allow for non-repetition of quiz id.
 */
public class QuizId implements Serializable {
    private int nextId = 0;

    public QuizId(){}

    public synchronized int incrementAndGet(){
        return nextId++;

    }
}
