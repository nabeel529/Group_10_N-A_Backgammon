import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColoursTest {

    @Test
    public void testGreenColorCode() {
        assertEquals("\033[0;32m", Colours.GREEN);
    }

    @Test
    public void testBlackColorCode() {
        assertEquals("\033[0;30m", Colours.BLACK);
    }

    @Test
    public void testResetColorCode() {
        assertEquals("\033[0m", Colours.RESET);
    }
}
