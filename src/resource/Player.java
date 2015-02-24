package resource;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Player class - contains Player details and scores
 */
@Data
public class Player implements Serializable {
    private String name;
    private int id;
    public Player(int id, String name){
        this.id = id;
        this.name = name;
    }


    /**
     * Override equals to allow for easy comparison
     * @param other - the other object to be compared
     * @return true or false depending on whether they are equal
     */
    @Override
    public boolean equals(Object other){
        if (this.getClass() != other.getClass()){
            return false;
        }
        if (this == other) {
            return true;
        }

        Player player2 = (Player) other;

        if (this.getId()!=player2.getId()){
            return false;
        }
        if (!this.getName().equals(player2.getName())){
            return false;
        }
        return true;
    }

    /**
     * Override hashcode because equals overridden
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash =  12 * hash + this.name.hashCode();
        return hash;
    }
}
