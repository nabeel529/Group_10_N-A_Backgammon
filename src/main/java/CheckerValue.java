/*
Group 10 | Nabeel Olusekun - nabeel529 | Alex Brady - alexb-25
Enum: CheckerValue
Description: Represents values (types) of checkers in the backgammon game along with their associated display attributes.
*/

public enum CheckerValue {
    // Enumeration of checker values with associated display attributes
    G(Colours.GREEN + " O" + Colours.RESET, Colours.GREEN, "GREEN"), // Represents a green checker
    B(Colours.BLACK + " X" + Colours.RESET, Colours.BLACK, "BLACK"); // Represents a black checker

    // Attributes for each checker value
    private String colour; // Color attribute for the checker
    private String sign;   // Display symbol for the checker
    private String show;   // Display color for the checker

    // Constructor to initialize checker attributes
    CheckerValue(String sign, String show, String colour) {
        this.colour = colour;
        this.show = show;
        this.sign = sign;
    }

    // Returns the display symbol for the checker
    public String toString() {
        return sign;
    }

    // Retrieves the display color for the checker
    public String retrieveShow() {
        return show;
    }

    // Retrieves the color attribute for the checker
    public String retrieveColour() {
        return colour;
    }
}
