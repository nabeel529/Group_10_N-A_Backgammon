import java.util.Random;

public class Dice {
    private Random rand;
    private int value[];

    Dice() {
        rand = new Random();
        this.value = new int[2];
    }

    public void roll() {
        value[0] = rand.nextInt(1, 7);
        value[1] = rand.nextInt(1, 7);
    }

    public int[] getValue() {
        return this.value;
    }

    public void printValue() {
        System.out.print("\n");
        System.out.println("|-- Dice --|");
        System.out.println("|    " + value[0] + "     |");
        System.out.println("|    " + value[1] + "     |");
        System.out.println("__________-");
    }
}
