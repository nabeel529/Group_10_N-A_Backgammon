public class Player {
    private String username;
    private int dice_value;

    public Player() {
        this.username = new String();
        this.dice_value = 0;
    }

    public void set_dice_value(int d) {
        this.dice_value = d;
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

    public void who_goes() {

    }
}
