package resource;

/**
 * Singleton class to allow for non-repetition of quiz id.
 */
public class QuizId {
    private static int nextId = 0;

    public QuizId(){}

    public static synchronized int incrementAndGet(){
        return nextId++;

    }
}
