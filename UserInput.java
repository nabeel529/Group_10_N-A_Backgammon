public class UserInput {
    private enum Commands {
        Roll,
        Quit,
        Help,
        Over;
    }

    private Commands command;

    public void set_Input(String input) {
        input = input.trim().toUpperCase();

        if (input.equals("Q")) {
            command = Commands.Quit;
        } else if (input.equals("R")) {
            command = Commands.Roll;
        } else if (input.equals("H")) {
            command = Commands.Help;
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

    public Boolean isHelp() {
        return command == Commands.Help;
    }

    public Boolean isValid_start(String input) {
        input = input.trim().toUpperCase();
        return (input.equals("Q") || input.equals("R") || input.equals("H"));
    }

    public void print_start_commands() {
        System.out.println("List of commands: To roll - R/r");
        System.out.println("                  To quit - Q/q");
    }

    public void print_commands() {
        System.out.println("List of commands: To roll  - R/r");
        System.out.println("                  To quit  - Q/q");
        System.out.println("                  For help - H/h");
    }
}
