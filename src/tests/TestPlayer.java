package tests;

import static org.junit.Assert.*;
import org.junit.*;
import resource.Player;

/**
 * Unit tests for Player class
 * @see resource.Player
 */
public class TestPlayer {
    private Player player;

    @Before
    public void buildUp(){
        player = new Player("Fred");
    }

    /**
     * Test that player Id is correctly incrementing with every new player
     */
    @Test
    public void testPlayerIdIncrements(){
        assertEquals(2, player.getId());
    }

    /**
     * Test override of equals()
     */
    @Test
    public void testEqualsSameNameId(){
        Player playerSame = new Player("Fred");
        playerSame.setId(6);
        assertTrue(player.equals(playerSame));  //equality with obj with same name and id

    }

    @Test
    public void testEqualsDifferentNameId(){
        Player player1 = new Player("Alan");
        assertFalse(player.equals(player1));    //different name and id

    }

    @Test
    public void testEqualsSameNameDiffId(){
        Player player2 = new Player("Fred");
        assertFalse(player.equals(player2));    //same name, different id

    }
}
