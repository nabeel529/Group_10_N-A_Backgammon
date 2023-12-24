/*
Group 10 | Nabeel Olusekun - nabeel529 | Alex Brady - alexb-25
Class: Checker
Description: Represents a checker in the backgammon game.
*/

public class Checker {
    private CheckerValue checkerValue;

    // Constructor to initialize the checker with a specific value
    Checker(CheckerValue checkerValue) {
        this.checkerValue = checkerValue;
    }

    // Returns a string representation of the checker value
    public String toString() {
        return checkerValue.toString();
    }

    // Retrieves the value of the checker
    public CheckerValue retrieveCheckerValue() {
        return checkerValue;
    }
}
