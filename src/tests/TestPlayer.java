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

    /**
     * Test that player Id is correctly incrementing with every new player
     */
    @Test
    public void testPlayerIdIncrements(){
        assertEquals(2, player.getId());
    }

    /**
     * Test that a new, higher score for a quiz that's already been played
     * will replace the existing score in the map
     */
    @Test
    public void testExistingQuizHigherScore(){
        player.addScore(1,12);
        player.addScore(2,15);
        player.addScore(1,25);
        assertEquals(25,(int) player.getScores().get(1));
    }

    /**
     * Test that a new but LOWER score for quiz that's already been played
     * will not replace anything and will not be added to the map
     */
    @Test
    public void testExistingQuizLowerScore(){
        player.addScore(1,12);
        player.addScore(2,15);
        player.addScore(1,5);
        assertEquals(12, (int) player.getScores().get(1));
    }
    /**
     * test getHighScore correctly returns highest score
     */
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
