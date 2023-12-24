import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionsTest {

    private Actions actions;

    @Test
    public void testConstructor_DisplayPipAction() {
        Actions actions = new Actions("P");
        assertTrue(actions.letsDisplayPip());
        assertFalse(actions.letsExit());
        assertFalse(actions.letsDieNum());
        assertFalse(actions.letsThrow());
    }

    @Test
    public void testConstructor_DieNumAction() {
        Actions actions = new Actions("R34");
        assertTrue(actions.letsDieNum());
        assertEquals(3, actions.retrieveDieNum(1));
        assertEquals(4, actions.retrieveDieNum(2));
        assertFalse(actions.letsExit());
        assertFalse(actions.letsDisplayPip());
        assertFalse(actions.letsThrow());
        // Test more assertions for other methods
    }

    @Test
    public void testConstructor_InvalidAction() {
        Actions actions = new Actions("InvalidInput");
        assertFalse(actions.letsExit());
        assertFalse(actions.letsDisplayPip());
        assertFalse(actions.letsDieNum());
        assertFalse(actions.letsThrow());
    }

    @Test
    public void testRetrieveIn_LongInput() {
        Actions actions = new Actions("test:some_long_file_name.txt");
        assertEquals("some_long_file_name.txt", actions.retrieveIn("test:some_long_file_name.txt"));
    }

    @Test
    public void testRetrieveIn_ShortInput() {
        Actions actions = new Actions("test:test.txt");
        assertEquals("test.txt", actions.retrieveIn("test:test.txt"));
    }


    @Test
    public void testLetsCheck_InvalidInput() {
        assertFalse(Actions.letsCheck("Z")); // Invalid action
        assertFalse(Actions.letsCheck("B10")); // Invalid lane
        assertFalse(Actions.letsCheck("E3")); // Invalid end
        assertFalse(Actions.letsCheck("27")); // Invalid move

    }

    @Test
    public void testLetsTest() {
        assertTrue(Actions.letsTest("test:somefile.txt"));
        assertTrue(Actions.letsTest("test:example.txt"));
        assertFalse(Actions.letsTest("somethingelse.txt"));
        assertFalse(Actions.letsTest("test:somefile.pdf"));
    }

    @Test
    public void testLetsCheck() {
        assertTrue(Actions.letsCheck("Q"));
        assertTrue(Actions.letsCheck("P"));
        assertTrue(Actions.letsCheck("Y"));
        assertTrue(Actions.letsCheck("R"));
        assertTrue(Actions.letsCheck("H"));
        assertFalse(Actions.letsCheck("invalid"));
        assertFalse(Actions.letsCheck("30"));
    }


}
