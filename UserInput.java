public class UserInput {
    private enum Commands {
        Roll,
        Quit;
    }

    private Commands command;

    public UserInput(String input) {
        input = input.trim().toUpperCase();

        if (input.equals("Q")) {
            command = Commands.Quit;
        } else if (input.equals("R")) {
            command = Commands.Roll;
        } else {
            System.out.print("How did we get here");
        }
    }

    public Boolean isQuit()
    {
        command == Commands.Quit;
    }

    public Boolean isRoll()
    {
        command == Commands.Roll;
    }
}
