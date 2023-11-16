import java.util.Scanner;

public class Game {
    Scanner in = new Scanner(System.in);
    Dice dice = new Dice();
    Board board = new Board();
    Player player1 = new Player(), player2 = new Player();
    UserInput commandInput = new UserInput();
    String input;

    public Game() {
        System.out.printf("Enter a name for player 1:");
        formatLine();
        input = in.nextLine();
        player1.set_username(input);
        formatLine();

        System.out.print("Enter a name for player 2:");
        formatLine();
        input = in.nextLine();
        player2.set_username(input);

        System.out.print("\n");
        System.out.println("Welcome " + player1.get_username() + " & " + player2.get_username());
        System.out.println("Here are your available commands at start");
        commandInput.print_start_commands();
        System.out.println("If you wish to see the list of commands at any time type 'H/h'.");
        formatLine();

        System.out.println("To begin both players will roll a dice.");
        System.out.println("Whoever rolls the higher number gets the first move!!!");
        formatLine();

        System.out.println(player1.get_username() + " will roll first.");
        input = in.nextLine();
        commandInput.set_Input(input);

        if (!commandInput.isValid_start(input)) {
            do {
                formatLine();
                commandInput.print_start_commands();
                input = in.nextLine();
                commandInput.set_Input(input);
            } while (!commandInput.isValid_start(input));
        }

        do {
            if (commandInput.isRoll()) {
                dice.roll_one_die();
                player1.set_dice_value(dice.getVal());
                System.out.println(player1.get_username() + "has rolled a ");
                dice.printVal();
            } else if (commandInput.isHelp()) {
                commandInput.print_commands();
            } else // Users have decided to quit.
            {
                System.out.println("Goodbye !!!");
                System.exit(0);
            }
        } while (player1.get_dice_value() == 0);

        System.out.println(player2.get_username() + " will now roll.");
        input = in.nextLine();
        commandInput.set_Input(input);

        if (!commandInput.isValid_start(input)) {
            do {
                formatLine();
                commandInput.print_start_commands();
                input = in.nextLine();
                commandInput.set_Input(input);
            } while (!commandInput.isValid_start(input));
        }

        do {
            if (commandInput.isRoll()) {
                dice.roll_one_die();
                player2.set_dice_value(dice.getVal());
                System.out.println(player2.get_username() + "has rolled a ");
                dice.printVal();
            } else if (commandInput.isHelp()) {
                commandInput.print_commands();
            } else /* Users have decided to quit. */ {
                System.out.println("Goodbye !!!");
                System.exit(0);
            }
        } while (player2.get_dice_value() == 0);

        if (player1.get_dice_value() > player2.get_dice_value()) {
            System.out.println(player1.get_username() + "will go first!");
        } else {
            System.out.println(player2.get_username() + "will go first!");
        }

        do {
            // board.printBoard();
            dice.printValue();
            formatLine();
            System.out.println("Enter your command:");
            input = in.nextLine();
            commandInput.set_Input(input);

            if (commandInput.isRoll()) {
                dice.roll();
            } else if (commandInput.isQuit()) {
                System.out.println("Goodbye !!!");
                System.exit(0);
            }

        } while (!commandInput.isQuit());
    }

    private void formatLine() {
        System.out.print("\n");
    }
}