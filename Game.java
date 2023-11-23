import java.util.Scanner;

public class Game {
    Scanner in = new Scanner(System.in);
    Dice dice = new Dice();
    Board board = new Board();
    Player player1 = new Player(), player2 = new Player();
    UserInput actionInput = new UserInput();
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
        actionInput.print_start_actions();
        System.out.println("If you wish to see the list of actions at any time type 'H/h'.");
        formatLine();

        System.out.println("To begin both players will roll a dice.");
        System.out.println("Whoever rolls the higher number gets the first move!!!");
        formatLine();

        who_goes_first();

        do {
            // board.printBoard();
            dice.printValue();
            formatLine();
            System.out.println("Enter your action:");
            input = in.nextLine();
            actionInput.set_Input(input);

            if (actionInput.isRoll()) {
                dice.roll();
            } else if (actionInput.isQuit()) {
                System.out.println("Goodbye !!!");
                System.exit(0);
            }

        } while (!actionInput.isQuit());
    }

    private void formatLine() {
        System.out.print("\n");
    }

    private void who_goes_first() {
        System.out.println(player1.get_username() + " will roll first.");
        input = in.nextLine();
        actionInput.set_Input(input);

        if (!actionInput.isValid_start(input)) {
            do {
                formatLine();
                System.out.println("Invalid input.");
                actionInput.print_start_actions();
                input = in.nextLine();
                actionInput.set_Input(input);
            } while (!actionInput.isValid_start(input));
        }

        do {
            if (actionInput.isRoll()) {
                dice.roll_one_die();
                player1.set_dice_value(dice.getVal());
                formatLine();
                System.out.println(player1.get_username() + " has rolled a ");
                dice.printVal();
            } else if (actionInput.isHelp()) {
                actionInput.print_start_actions();
                input = in.nextLine();
                actionInput.set_Input(input);
            } else /* Users have decided to quit. */ {
                System.out.println("Goodbye !!!");
                System.exit(0);
            }
        } while (player1.get_dice_value() == 0);

        formatLine();
        System.out.println(player2.get_username() + " will now roll.");
        input = in.nextLine();
        actionInput.set_Input(input);

        if (!actionInput.isValid_start(input)) {
            do {
                formatLine();
                actionInput.print_start_actions();
                input = in.nextLine();
                actionInput.set_Input(input);
            } while (!actionInput.isValid_start(input));
        }

        do {
            if (actionInput.isRoll()) {
                dice.roll_one_die();
                player2.set_dice_value(dice.getVal());
                formatLine();
                System.out.println(player2.get_username() + " has rolled a ");
                dice.printVal();
            } else if (actionInput.isHelp()) {
                actionInput.print_start_actions();
                input = in.nextLine();
                actionInput.set_Input(input);
            } else /* Users have decided to quit. */ {
                System.out.println("Goodbye !!!");
                System.exit(0);
            }
        } while (player2.get_dice_value() == 0);

        if (player1.get_dice_value() > player2.get_dice_value()) {
            formatLine();
            System.out.println(player1.get_username() + " will make the first move!");
            player1.set_rank(1);
            player2.set_rank(2);
        } else {
            formatLine();
            System.out.println(player2.get_username() + " will make the first move!");
            player2.set_rank(1);
            player1.set_rank(2);
        }
    }
}