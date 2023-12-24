import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TypeAction Enum Tests")
public class TypeActionTest {

    @Test
    @DisplayName("Enum Values Test")
    public void testEnumValues() {
        TypeAction[] actions = TypeAction.values();

        assertEquals(10, actions.length);

        assertArrayEquals(new TypeAction[]{
                TypeAction.EXIT,
                TypeAction.PIP,
                TypeAction.DIENUM,
                TypeAction.THROW,
                TypeAction.MOVE,
                TypeAction.HINT,
                TypeAction.SKIP,
                TypeAction.DISPLAYMOVES,
                TypeAction.START,
                TypeAction.YIELD
        }, actions);
    }

    @Test
    @DisplayName("Enum ValueOf Test")
    public void testEnumValueOf() {
        TypeAction exitAction = TypeAction.valueOf("EXIT");
        TypeAction moveAction = TypeAction.valueOf("MOVE");
        TypeAction hintAction = TypeAction.valueOf("HINT");

        assertEquals(TypeAction.EXIT, exitAction);
        assertEquals(TypeAction.MOVE, moveAction);
        assertEquals(TypeAction.HINT, hintAction);
    }

    @Test
    @DisplayName("Enum Values Comparison Test")
    public void testEnumValuesComparison() {
        TypeAction exitAction = TypeAction.EXIT;
        TypeAction moveAction = TypeAction.MOVE;
        TypeAction hintAction = TypeAction.HINT;

        assertNotEquals(exitAction, moveAction);
        assertNotEquals(moveAction, hintAction);
        assertNotEquals(hintAction, exitAction);
    }
}
