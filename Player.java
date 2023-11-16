public class Player {
    private String username;
    private int dice_value, rank;

    public Player() {
        this.username = new String();
        this.dice_value = 0;
        this.rank = 0;
    }

    public void set_dice_value(int num) {
        this.dice_value = num;
    }

    public int get_dice_value() {
        return this.dice_value;
    }

    public void set_username(String u) {
        this.username = u;
    }

    public String get_username() {
        return this.username;
    }

    public void set_rank(int num) {
        this.rank = num;
    }

    public int get_rank() {
        return this.rank;
    }
}
