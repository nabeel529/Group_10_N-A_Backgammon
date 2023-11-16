import java.util.Scanner;

public class Game {
    Scanner in;
    String input;
    Boolean pee;
    int penis1, penis2;

    public Game() {
        in = new Scanner(System.in);
        Dice dice = new Dice();
        // Board board = new Board();

        System.out.printf("Enter a name for player 1:");
        System.out.print("\n");
        String player1 = in.nextLine();
        System.out.print("\n");

        System.out.print("Enter a name for player 2:");
        System.out.print("\n");
        String player2 = in.nextLine();

        System.out.print("\n");
        System.out.println("Welcome " + player1 + " & " + player2);

        System.out.print("\n");
        System.out.println("List of Commands: To roll - R/r");
        System.out.println("                  To quit - Q/q");
        System.out.print("\n");

        do {
            // board.printBoard();
            dice.printValue();
            System.out.print("\n");
            System.out.println("List of Commands: To roll - R/r");
            System.out.println("                  To quit - Q/q");
            System.out.print("\n");
            System.out.println("Enter your command:");
            input = in.nextLine();
            UserInput commandInput = new UserInput(in.nextLine());

            if (commandInput.isRoll()) {
                dice.roll();
            } else if (commandInput.isQuit()) {
                System.out.println("Goodbye !!!");
                System.exit(0);
            }

        } while (pee);
    }

    private Boolean isValid_start(String input) {
        input = input.trim().toUpperCase();
        return (input.equals("Q") || input.equals("R"));
    }
}