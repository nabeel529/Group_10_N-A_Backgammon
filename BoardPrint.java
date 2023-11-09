
public class BoardPrint {
    public static void main(String[] args) {
        Colour green = new Colour('G');
        Colour black = new Colour('B');
        Colour reset = new Colour('R');
        String p1 = green.printColour() + "X" + reset.printColour();
        String p2 = black.printColour() + "X" + reset.printColour();

        System.out.println("|---------------------------------------------------------------------|");
        System.out.println(
                black.printColour() + "| " + "13   14   15   16   17   18" + reset.printColour() + " | " + "B2" + " | "
                        + black.printColour() + "19   20   21   22   23   24"
                        + green.printColour() + " | " + "E1" + " |   " + reset.printColour());
        System.out.print(green.printColour() + "| " + "12   11   10   09   08   07");
        System.out.print(reset.printColour() + " | " + "B2" + " | ");
        System.out.println(
                green.printColour() + "06   05   04   03   02   01" + " | " + "E1" + " |" + reset.printColour());
        System.out.println("|---------------------------------------------------------------------|");
        System.out.println("| " + p1 + "                   " + p2 + "       |    | " + p2 + "                         "
                + p1 + green.printColour() + " | 00 |" + reset.printColour());
        System.out.println("| " + p1 + "                   " + p2 + "       |    | " + p2 + "                         "
                + p1 + " |    |");
        System.out.println(
                "| " + p1 + "                   " + p2 + "       |    | " + p2 + "                           |    |");
        System.out.println("| " + p1 + "                           |    | " + p2 + "                           |    |");
        System.out.println("| " + p1 + "                           |    | " + p2 + "                           |    |");
        System.out.println("|-----------------------------|    |-----------------------------|    |");
        System.out.println("| " + p2 + "                           |    | " + p1 + "                           |    |");
        System.out.println("| " + p2 + "                           |    | " + p1 + "                           |    |");
        System.out.println(
                "| " + p2 + "                   " + p1 + "       |    | " + p1 + "                           |    |");
        System.out.println("| " + p2 + "                   " + p1 + "       |    | " + p1 + "                         "
                + p2 + " |    |");
        System.out.println("| " + p2 + "                   " + p1 + "       |    | " + p1 + "                         "
                + p2 + black.printColour() + " | 00 |" + reset.printColour());
        System.out.println("|---------------------------------------------------------------------|");
        System.out.print(black.printColour() + "| " + "12   11   10   09   08   07" + reset.printColour());
        System.out.print(" | " + "B1" + " | ");
        System.out.println(
                black.printColour() + "06   05   04   03   02   01" + " | " + "E2" + " |" + reset.printColour());
        System.out.println(green.printColour() + "| " + "13   14   15   16   17   18" + reset.printColour() + " | "
                + "B1" + " | " + green.printColour() + "19   20   21   22   23   24"
                + " | " + "E2" + " |   " + reset.printColour());
        System.out.println("|---------------------------------------------------------------------|");
    }

}
