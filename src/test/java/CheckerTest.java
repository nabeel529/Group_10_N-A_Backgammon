import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckerTest {

    @Test
    public void testToString() {
        Checker checkerG = new Checker(CheckerValue.G);
        Checker checkerB = new Checker(CheckerValue.B);

        assertEquals(Colours.GREEN + " O" + Colours.RESET, checkerG.toString());
        assertEquals(Colours.BLACK + " X" + Colours.RESET, checkerB.toString());
    }

    @Test
    public void testRetrieveCheckerValue() {
        Checker checkerG = new Checker(CheckerValue.G);
        Checker checkerB = new Checker(CheckerValue.B);

        assertEquals(CheckerValue.G, checkerG.retrieveCheckerValue());
        assertEquals(CheckerValue.B, checkerB.retrieveCheckerValue());
    }
}
