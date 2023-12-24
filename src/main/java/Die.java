import java.util.Random;

/*
Group 10 | Nabeel Olusekun - nabeel529 | Alex Brady - alexb-25
Class: Die
Description: Represents a pair of dice for the backgammon game.
*/

public class Die {
    private Show view;       // Instance of the Show class (Assuming it's a display class)
    private int[] dieNum;    // Array to hold dice values
    private int[] moves;     // Array to hold possible moves
    private int numMoves;    // Number of available moves
    private Random random;   // Random number generator

    // Constructor to initialize dice, moves, and the random number generator
    Die() {
        this.dieNum = new int[2]; // Initialize array for two dice
        random = new Random();
        this.moves = new int[2]; // Initialize array for possible moves
        view = new Show(); // Assuming Show is a class to display dice information

        numMoves = 2; // Default number of moves

        // Generating random values for two dice
        dieNum[1] = random.nextInt(6) + 1; // Random number between 1 and 6 for die 2
        dieNum[0] = random.nextInt(6) + 1; // Random number between 1 and 6 for die 1

        moves[1] = 1; // Default moves for die 2
        moves[0] = 1; // Default moves for die 1

        // If both dice show the same value, adjust the number of moves accordingly
        if (dieNum[1] == dieNum[0]) {
            numMoves = 4;
            moves[1] = 0;
            moves[0] = 4;
        }
    }

    // Retrieves the number of available moves
    public int retrieveNumMoves() {
        return numMoves;
    }

    // Retrieves the number of moves for a specific die (1 or 2)
    public int retrieveMoves(int num) {
        if (num == 1) {
            return moves[0];
        } else if (num == 2) {
            return moves[1];
        } else {
            return 0;
        }
    }

    // Retrieves the value of a specific die (1 or 2)
    public int retrieveDieNum(int num) {
        if (num == 1) {
            return dieNum[0];
        } else if (num == 2) {
            return dieNum[1];
        } else {
            return 0;
        }
    }

    // Throws the dice to generate random values
    public void throwDie() {
        // Resetting values for a new throw
        numMoves = 2;
        dieNum[1] = random.nextInt(6) + 1;
        dieNum[0] = random.nextInt(6) + 1;
        moves[1] = 1;
        moves[0] = 1;

        // Adjusting moves if both dice show the same value
        if (dieNum[1] == dieNum[0]) {
            numMoves = 4;
            moves[1] = 0;
            moves[0] = 4;
        }
    }

    // Sets the values of dice to specific numbers
    public void makeDieNum(int dieNumOne, int dieNumTwo) {
        // Setting specific values for dice
        numMoves = 2;
        moves[1] = 1;
        moves[0] = 1;
        dieNum[1] = dieNumTwo;
        dieNum[0] = dieNumOne;

        // Adjusting moves if both dice show the same value
        if (dieNum[1] == dieNum[0]) {
            numMoves = 4;
            moves[1] = 0;
            moves[0] = 4;
        }

        // Displaying the dice values
        view.show_Dice(dieNumOne, dieNumTwo);
    }

    // Decreases the number of moves for a specific die (1 or 2)
    public void minusMoves(int num) {
        if (num == 1) {
            moves[0]--;
        } else if (num == 2) {
            moves[1]--;
        }
    }

    // Sets the number of moves for each die
    public void makeMoves(int movesOne, int movesTwo) {
        moves[1] = movesTwo;
        moves[0] = movesOne;
        numMoves = moves[1] + moves[0];
    }

    // Resets all dice and move values to zero
    public void Nothing() {
        numMoves = 0;
        moves[1] = 0;
        moves[0] = 0;
        dieNum[1] = 0;
        dieNum[0] = 0;
    }
}
