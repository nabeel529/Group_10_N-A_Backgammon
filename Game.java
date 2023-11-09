import java.util.Scanner;

public class Game {
    Scanner in;

    public Game() {
        in = new Scanner(System.in);
        Dice dice = new Dice();

        System.out.printf("Enter a name for player 1:");
        System.out.print("\n");
        String player1 = in.nextLine();

        System.out.print("Enter a name for player 2:");
        System.out.print("\n");
        String player2 = in.nextLine();

        System.out.println("Welcome " + player1 + " & " + player2);
        System.out.print("\n");

        do {
            System.out.print("\n");
            System.out.println("Enter your command:");
            String input = in.nextLine();
            UserInput commandInput = new UserInput(input);

            if (commandInput.isRoll()) {
                dice.roll();
                dice.printValue();
            } else if (commandInput.isQuit()) {
                System.out.println("Goodbye !!!");
                System.exit(0);
            }

        } while (!false);
    }
}
