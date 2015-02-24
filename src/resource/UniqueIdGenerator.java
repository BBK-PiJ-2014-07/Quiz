package resource;

import java.io.Serializable;

/**
 * Singleton class to allow for non-repetition of quiz id.
 */
public class UniqueIdGenerator implements Serializable {
    private int nextId = 1;

    public UniqueIdGenerator(){}

    public synchronized int incrementAndGet(){
        return nextId++;

    }
}
