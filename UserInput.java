public class UserInput {
    private enum actions {
        Roll,
        Quit,
        Help,
        Over;
    }

    private actions action;

    public void set_Input(String input) {
        input = input.trim().toUpperCase();

        if (input.equals("Q")) {
            action = actions.Quit;
        } else if (input.equals("R")) {
            action = actions.Roll;
        } else if (input.equals("H")) {
            action = actions.Help;
        } else {
            System.out.print("\nI assume youre testing\n\n");
        }
    }

    public Boolean isQuit() {
        return action == actions.Quit;
    }

    public Boolean isRoll() {
        return action == actions.Roll;
    }

    public Boolean isOver() {
        return action == actions.Over;
    }

    public Boolean isHelp() {
        return action == actions.Help;
    }

    public Boolean isValid_start(String input) {
        input = input.trim().toUpperCase();
        return (input.equals("Q") || input.equals("R") || input.equals("H"));
    }

    public void print_start_actions() {
        System.out.print("\n");
        System.out.println("Here are your available actions at start");
        System.out.println("List of actions: To roll - R/r");
        System.out.println("                  To quit - Q/q");
        System.out.println("                  For help - H/h");
    }

    public void print_actions() {
        System.out.println("List of actions: To roll  - R/r");
        System.out.println("                  To quit  - Q/q");
        System.out.println("                  For help - H/h");
    }
}
