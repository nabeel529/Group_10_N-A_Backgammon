
public class BoardPrint {
    public static void main(String[] args) {
        Colour green = new Colour('G');
        Colour black = new Colour('B');
        Colour reset = new Colour('R');

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
        System.out.println("| " + green.printColour() + "X  " + reset.printColour()
                + "                 O       |    | O                         X | 00 |");
        System.out.println("| X                   O       |    | O                         X |    |");
        System.out.println("| X                   O       |    | O                           |    |");
        System.out.println("| X                           |    | O                           |    |");
        System.out.println("| X                           |    | O                           |    |");
        System.out.println("|-----------------------------|    |-----------------------------|    |");
        System.out.println("| O                           |    | X                           |    |");
        System.out.println("| O                           |    | X                           |    |");
        System.out.println("| O                   X       |    | X                           |    |");
        System.out.println("| O                   X       |    | X                         O |    |");
        System.out.println("| O                   X       |    | X                         O | 00 |");
        System.out.println("|---------------------------------------------------------------------|");
        System.out.print("| " + "12   11   10   09   08   07");
        System.out.print(" | " + "B1" + " | ");
        System.out.println("06   05   04   03   02   01" + " | " + "E2" + " |");
        System.out.println("| " + "13   14   15   16   17   18" + " | " + "B1" + " | " + "19   20   21   22   23   24"
                + " | " + "E2" + " |   ");
        System.out.println("|---------------------------------------------------------------------|");

        System.out.print(green.printColour() + "Hello");
    }

}
