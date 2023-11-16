public class UserInput {
    private enum Commands {
        Roll,
        Quit,
        Over;
    }

    private Commands command;

    public UserInput(String input) {
        input = input.trim().toUpperCase();

        if (input.equals("Q")) {
            command = Commands.Quit;
        } else if (input.equals("R")) {
            command = Commands.Roll;
        } else {
            System.out.print("\nI assume youre testing\n\n");
        }
    }

    public Boolean isQuit() {
        return command == Commands.Quit;
    }

    public Boolean isRoll() {
        return command == Commands.Roll;
    }

    public Boolean isOver() {
        return command == Commands.Over;
    }
}
