import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TableTest{

    @Test
    public void testMakePersonCurrently() {
        // Create a Table instance (you may need to initialize it based on your actual implementation)
        Table table = new Table(/* pass necessary parameters */);

        // Set up the initial state if needed

        // Call the makePersonCurrently method and check if the correct Person object is set
        int playerIndex = 1; // Change the player index as needed
        table.makePersonCurrently(playerIndex);

        // Perform assertions to check if the correct Person object is set
        assertEquals(table.retrievePerson(0), table.retrievePerson(playerIndex));

    }

    @Test
    public void testRetrieveNumMatch() {
        // Create a Table instance (you may need to initialize it based on your actual implementation)
        Table table = new Table(/* pass necessary parameters */);

        // Set up the initial state if needed

        // Call the retrieveNumMatch method and check if the correct number of matches is retrieved
        int expectedNumMatch = 5; // Change the expected number of matches as needed
        table.makeNum_match(expectedNumMatch);
        int actualNumMatch = table.retrieveNumMatch();

        // Perform assertions to check if the correct number of matches is retrieved
        assertEquals(expectedNumMatch, actualNumMatch);

    }

    @Test
    public void testMakeNum_match() {
        // Create a Table instance (you may need to initialize it based on your actual implementation)
        Table table = new Table(/* pass necessary parameters */);

        // Set up the initial state if needed

        // Call the makeNum_match method with a specific number of matches
        int numMatch = 3; // Change the number of matches as needed
        table.makeNum_match(numMatch);

        // Perform assertions or checks as needed based on your implementation
        assertEquals(numMatch, table.retrieveNumMatch());

    }

    @Test
    public void testRetirveRoundnumber() {
        // Create a Table instance (you may need to initialize it based on your actual implementation)
        Table table = new Table(/* pass necessary parameters */);

        // Set up the initial state if needed

        // Call the retirveRoundnumber method and check if the correct round number is retrieved
        int expectedRoundNumber = 1; // Change the expected round number as needed
        table.makeRoundnumber(expectedRoundNumber);
        int actualRoundNumber = table.retirveRoundnumber();

        // Perform assertions to check if the correct round number is retrieved
        assertEquals(expectedRoundNumber, actualRoundNumber);

    }

    @Test
    public void testMakeRoundnumber() {
        // Create a Table instance (you may need to initialize it based on your actual implementation)
        Table table = new Table(/* pass necessary parameters */);

        // Set up the initial state if needed

        // Call the makeRoundnumber method with a specific match round number
        int matchRoundNumber = 2; // Change the match round number as needed
        table.makeRoundnumber(matchRoundNumber);

        // Perform assertions or checks as needed
        assertEquals(matchRoundNumber, table.retirveRoundnumber());

    }

    @Test
    public void testIncrementRound() {
        // Create a Table instance (you may need to initialize it based on your actual implementation)
        Table table = new Table(/* pass necessary parameters */);

        // Set up the initial state if needed

        // Call the Increment_round method and check if the round number is incremented
        int initialRoundNumber = 1; // Set the initial round number as needed
        table.makeRoundnumber(initialRoundNumber);
        table.Increment_round();

        // Perform assertions to check if the round number is incremented
        assertEquals(initialRoundNumber + 1, table.retirveRoundnumber());

    }

    @Test
    public void testMakePerson() {
        // Create a Table instance (you may need to initialize it based on your actual implementation)
        Table table = new Table(/* pass necessary parameters */);

        // Create a Person instance for testing
        Person testPerson = new Person("TestPerson", CheckerValue.G);

        // Call the makePerson method with a valid index
        table.makePerson(0, testPerson);

        // Perform assertions or checks as needed
        assertEquals(testPerson, table.retrievePerson(0));

    }

    @Test
    public void testRetrieveCheckerNumLane() {
        // Create a Table instance (you may need to initialize it based on your actual implementation)
        Table table = new Table(/* pass necessary parameters */);

        // Set up the initial state if needed

        // Call the retrieveCheckerNumLane method for a specific lane number
        int laneIndex = 0; // Set the lane index for testing
        // Perform assertions or checks as needed
        assertEquals(0, table.retrieveCheckerNumLane(laneIndex));

    }
}