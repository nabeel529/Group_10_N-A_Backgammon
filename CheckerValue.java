public enum CheckerValue
{
    G(Colours.GREEN + "O" + Colours.RESET, Colours.GREEN, "GREEN"),
    B(Colours.BLACK + "X" + Colours.RESET, Colours.BLACK, "BLACK");

    private String colour;
    private String sign;
    private String show;

    CheckerValue(String sign, String show,String colour)
    {
        this.colour = colour;
        this.show = show;
        this.sign = sign;
    }

    public String toString()
    {
        return sign;
    }
    public String retrieveShow()
    {
        return show;
    }

    public String retrieveColour()
    {
        return colour;
    }
}
