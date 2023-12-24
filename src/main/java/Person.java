/*
Group 10 | Nabeel Olusekun - nabeel529 | Alex Brady - alexb-25
Class: Person
Description: Represents a player in the backgammon game with associated attributes.
*/

public class Person {
    // Attributes of a player
    private CheckerValue checkerValue; // Checker value associated with the player
    private String person_name;       // Name of the player
    private int point;                // Points scored by the player
    private int pip;                  // Pip count for the player

    // Constructor to initialize a player with a name and checker value
    Person(String person_name, CheckerValue checkerValue) {
        this.checkerValue = checkerValue;
        this.point = 0;
        this.person_name = person_name;
        this.pip = 167; // Initial pip count for the player
    }

    // Returns a string representation of the player's details
    public String toString() {
        return checkerValue.retrieveShow() + person_name + Colours.RESET + "<" + this.pip + ">pips";
    }

    // Sets the current pip count for the player
    public void makePip(int pip) {
        this.pip = pip;
    }

    // Retrieves the current pip count for the player
    public int retrievePip() {
        return pip;
    }

    // Retrieves the color associated with the player's checker
    public String retrieveColour() {
        return checkerValue.retrieveColour();
    }

    // Retrieves the name of the player with associated checker color
    public String retrieveName() {
        return checkerValue.retrieveShow() + person_name + Colours.RESET;
    }

    // Retrieves the checker value associated with the player
    public CheckerValue retrieveCheckerValue() {
        return checkerValue;
    }

    // Sets the current points for the player
    public void makePoint(int point) {
        this.point = point;
    }

    // Retrieves the current points of the player
    public int retrievePoint() {
        return point;
    }

    // Increases the player's points by 1
    public void increasePoint(int point) {
        this.point = point + 1;
    }
}
