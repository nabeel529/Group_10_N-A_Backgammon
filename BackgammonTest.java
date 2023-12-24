import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BackgammonTest {

    private Backgammon game;

    @BeforeEach
    public void setUp() {
        game = new Backgammon();
    }

    @Test
    public void testGetBoard() {
        Table table = game.getBoard();
        assertNotNull(table);
    }

}
