import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckerValueTest {

    @Test
    public void testToString() {
        assertEquals(Colours.GREEN + " O" + Colours.RESET, CheckerValue.G.toString());
        assertEquals(Colours.BLACK + " X" + Colours.RESET, CheckerValue.B.toString());
    }

    @Test
    public void testRetrieveShow() {
        assertEquals(Colours.GREEN, CheckerValue.G.retrieveShow());
        assertEquals(Colours.BLACK, CheckerValue.B.retrieveShow());
    }

    @Test
    public void testRetrieveColour() {
        assertEquals("GREEN", CheckerValue.G.retrieveColour());
        assertEquals("BLACK", CheckerValue.B.retrieveColour());
    }
}
