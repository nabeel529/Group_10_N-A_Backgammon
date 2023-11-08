public enum Colour {
    Green,
    Black;

    private static final String GREEN = "\033[0;32m";
    private static final String BLACK = "\033[0;35m";
    private static final String RESET = "\033[om";

    public String printColour(Colour c, Boolean s) {
        String symbol;

        if (s) {
            switch (c) {
                case Green:
                    symbol = GREEN;
                    break;

                case Black:
                    symbol = BLACK;
                    break;

                default:
                    symbol = RESET;
                    break;
            }
        } else {
            symbol = "uh oh";
        }

        return symbol;
    }

    public String resetColour() {
        String symbol = RESET;
        return symbol;
    }
}
