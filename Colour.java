public class Colour {

    private static final String GREEN = "\033[0;32m";
    private static final String BLACK = "\033[0;35m";
    private static final String RESET = "\033[0m";
    private String colour;

    public Colour(char c) {
        if (c == 'G') {
            this.colour = GREEN;
        } else if (c == 'B') {
            this.colour = BLACK;
        } else {
            this.colour = RESET;
        }
    }

    public String printColour() {
        return this.colour;
    }
}
