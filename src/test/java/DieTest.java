import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DieTest {
    private Die die;

    @BeforeEach
    public void setUp() {
        die = new Die();
    }

    @Test
    public void testInitialValues() {
        assertEquals(2, die.retrieveNumMoves());
        assertEquals(1, die.retrieveMoves(1));
        assertEquals(1, die.retrieveMoves(2));
        assertTrue(die.retrieveDieNum(1) >= 1 && die.retrieveDieNum(1) <= 6);
        assertTrue(die.retrieveDieNum(2) >= 1 && die.retrieveDieNum(2) <= 6);
    }

    @Test
    public void testMakeDieNum() {
        die.makeDieNum(2, 4);
        assertEquals(2, die.retrieveNumMoves());
        assertEquals(1, die.retrieveMoves(1));
        assertEquals(1, die.retrieveMoves(2));
        assertEquals(2, die.retrieveDieNum(1));
        assertEquals(4, die.retrieveDieNum(2));
    }

    @Test
    public void testMakeMoves() {
        die.makeMoves(0, 3);
        assertEquals(3, die.retrieveMoves(2));
        assertEquals(3, die.retrieveNumMoves());
    }

    @Test
    public void testNothing() {
        die.Nothing();
        assertEquals(0, die.retrieveNumMoves());
        assertEquals(0, die.retrieveMoves(1));
        assertEquals(0, die.retrieveMoves(2));
        assertEquals(0, die.retrieveDieNum(1));
        assertEquals(0, die.retrieveDieNum(2));
    }
}
