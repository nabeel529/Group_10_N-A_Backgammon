public class Person
{
    private CheckerValue checkerValue;
    private String person_name;
    private int point, pip;

    Person(String person_name, CheckerValue checkerValue)
    {
        this.checkerValue = checkerValue;
        this.point = 0;
        this.person_name = person_name;
        this.pip = 167;
    }

    public String toString()
    {
        return checkerValue.retrieveShow()+person_name+Colours.RESET;
    }

    public void makePip(int pip)
    {
        this.pip = pip;
    }

    public int retrievePip()
    {
        return pip;
    }

    public String retrieveColour()
    {
        return checkerValue.retrieveColour();
    }

    public String retrieveName()
    {
        return checkerValue.retrieveShow()+person_name+Colours.RESET;
    }

    public CheckerValue retrieveCheckerValue()
    {
        return checkerValue;
    }

    public void makePoint (int point)
    {
        this.point = point;
    }

    public int retrievePoint()
    {
        return point;
    }

    public void increasePoint(int point)
    {
        this.point = point + 1;
    }

}
