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
     * Check that addScore works correctly
     */
    @Test
    public void testAddScore(){
        player.addScore(1,12);
        assertTrue(player.getScores().containsValue(12) && player.getScores().containsKey(1));
    }
    @Test
    public void testPlayerIdIncrements(){
        assertEquals(2, player.getPlayerId());
    }

    @Test
    public void testGetHighScore(){
        player.addScore(1,12);
        player.addScore(2,12);
        player.addScore(3,16);
        player.addScore(4,13);
        player.addScore(5,153);
        player.addScore(6,20);

        assertEquals(153,player.getHighScore());
    }
}
