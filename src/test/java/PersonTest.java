import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {


    @Test
    public void testRetrievePip() {
        CheckerValue checkerValue = CheckerValue.B; // Using BLACK checker for testing
        Person person = new Person("Bob", checkerValue);
        person.makePip(100);

        assertEquals(100, person.retrievePip());
    }

    @Test
    public void testRetrieveColour() {
        CheckerValue checkerValue = CheckerValue.G; // Using GREEN checker for testing
        Person person = new Person("Charlie", checkerValue);

        assertEquals("GREEN", person.retrieveColour());
    }


    @Test
    public void testRetrieveCheckerValue() {
        CheckerValue checkerValue = CheckerValue.G; // Using GREEN checker for testing
        Person person = new Person("Emma", checkerValue);

        assertEquals(checkerValue, person.retrieveCheckerValue());
    }

    @Test
    public void testRetrievePoint() {
        CheckerValue checkerValue = CheckerValue.B; // Using BLACK checker for testing
        Person person = new Person("Frank", checkerValue);
        person.makePoint(25);

        assertEquals(25, person.retrievePoint());
    }

    @Test
    public void testIncreasePoint() {
        CheckerValue checkerValue = CheckerValue.G; // Using GREEN checker for testing
        Person person = new Person("Grace", checkerValue);
        person.increasePoint(10);

        assertEquals(11, person.retrievePoint());
    }
}
