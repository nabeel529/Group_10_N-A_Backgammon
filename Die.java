import java.util.Random;

public class Die {
    private Show show;
    private int[] dieNum;
    private int[] moves;
    private int numMoves;
    private Random random;

    Die() {
        this.dieNum = new int[2];
        random = new Random();
        this.moves = new int[2];
        show = new Show();

        numMoves = 2;
        dieNum[1] = random.nextInt(1, 7);
        dieNum[0] = random.nextInt(1, 7);
        moves[1] = 1;
        moves[0] = 1;

        if (dieNum[1] == dieNum[0]) {
            numMoves = 4;
            moves[1] = 0;
            moves[0] = 4;
        }
    }

    public int retrieveNumMoves() {
        return numMoves;
    }

    public int retrieveMoves(int num) {
        if (num == 1) {
            return moves[0];
        }

        else if (num == 2) {
            return moves[1];
        }

        else {
            return 0;
        }
    }

    public int retrieveDieNum(int num) {
        if (num == 1) {
            return dieNum[0];
        }

        else if (num == 2) {
            return dieNum[1];
        }

        else {
            return 0;
        }
    }

    public void throwDie() {
        numMoves = 2;
        dieNum[1] = random.nextInt(1, 7);
        dieNum[0] = random.nextInt(1, 7);
        moves[1] = 1;
        moves[0] = 1;

        if (dieNum[1] == dieNum[0]) {
            numMoves = 4;
            moves[1] = 0;
            moves[0] = 4;
        }
    }

    public void makeDieNum(int dieNumOne, int dieNumTwo) {
        numMoves = 2;
        moves[1] = 1;
        moves[0] = 1;
        dieNum[1] = dieNumTwo;
        dieNum[0] = dieNumOne;

        if (dieNum[1] == dieNum[0]) {
            numMoves = 4;
            moves[1] = 0;
            moves[0] = 4;
        }

        show.revealDie(dieNumOne, dieNumTwo);
    }

    public void minusMoves(int num) {
        if (num == 1) {
            moves[0]--;
        }

        else if (num == 2) {
            moves[1]--;
        }
    }

    public void makeMoves(int movesOne, int movesTwo) {
        moves[1] = movesTwo;
        moves[0] = movesOne;
        numMoves = moves[1] + moves[0];
    }

    public void Nothing() {
        numMoves = 0;
        moves[1] = 0;
        moves[0] = 0;
        dieNum[1] = 0;
        dieNum[0] = 0;
    }
}
