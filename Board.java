
public class Board {
        private Colour green, black, reset;
        private String p1, p2;

        public Board() {
                green = new Colour('G');
                black = new Colour('B');
                reset = new Colour('R');
                p1 = green.printColour() + "X" + reset.printColour();
                p2 = black.printColour() + "O" + reset.printColour();
        }

        public void printBoard() {
                System.out.println("|---------------------------------------------------------------------|");

                System.out.println(
                                "| " + black.printColour() + "13   14   15   16   17   18" + reset.printColour()
                                                + " | "
                                                + "B2" + " | "
                                                + black.printColour() + "19   20   21   22   23   24"
                                                + reset.printColour() + " | " + green.printColour() + "E2"
                                                + reset.printColour() + " |   ");

                System.out.print("| " + green.printColour() + "12   11   10   09   08   07");
                System.out.print(reset.printColour() + " | " + "B2" + " | ");
                System.out.println(
                                green.printColour() + "06   05   04   03   02   01" + reset.printColour() + " | "
                                                + green.printColour()
                                                + "E2" + reset.printColour() + " |" + reset.printColour());

                System.out.println("|---------------------------------------------------------------------|");

                System.out.println("| " + p1 + "                   " + p2 + "       |    | " + p2
                                + "                         "
                                + p1 + reset.printColour() + " | " + green.printColour() + "00" + reset.printColour()
                                + " |");
                System.out.println("| " + p1 + "                   " + p2 + "       |    | " + p2
                                + "                         "
                                + p1 + " |    |");
                System.out.println(
                                "| " + p1 + "                   " + p2 + "       |    | " + p2
                                                + "                           |    |");
                System.out.println("| " + p1 + "                           |    | " + p2
                                + "                           |    |");
                System.out.println("| " + p1 + "                           |    | " + p2
                                + "                           |    |");

                System.out.println("|-----------------------------|    |-----------------------------|    |");

                System.out.println("| " + p2 + "                           |    | " + p1
                                + "                           |    |");
                System.out.println("| " + p2 + "                           |    | " + p1
                                + "                           |    |");
                System.out.println(
                                "| " + p2 + "                   " + p1 + "       |    | " + p1
                                                + "                           |    |");
                System.out.println("| " + p2 + "                   " + p1 + "       |    | " + p1
                                + "                         "
                                + p2 + " |    |");
                System.out.println("| " + p2 + "                   " + p1 + "       |    | " + p1
                                + "                         "
                                + p2 + reset.printColour() + " | " + black.printColour() + "00" + reset.printColour()
                                + " |");

                System.out.println("|---------------------------------------------------------------------|");

                System.out.print("| " + black.printColour() + "12   11   10   09   08   07" + reset.printColour());
                System.out.print(" | " + "B1" + " | ");
                System.out.println(
                                black.printColour() + "06   05   04   03   02   01" + reset.printColour() + " | "
                                                + black.printColour()
                                                + "E2" + reset.printColour() + " |" + reset.printColour());
                System.out.println(
                                "| " + green.printColour() + "13   14   15   16   17   18" + reset.printColour() + " | "
                                                + "B1" + " | " + green.printColour() + "19   20   21   22   23   24"
                                                + reset.printColour() + " | " + black.printColour() + "E2"
                                                + reset.printColour() + " |   ");

                System.out.println("|---------------------------------------------------------------------|");
        }
}
