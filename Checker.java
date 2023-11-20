public class Checker
{
    private CheckerValue checkerValue;

    Checker (CheckerValue checkerValue)
    {
        this.checkerValue = checkerValue;
    }

    public String toString()
    {
        return checkerValue.toString();
    }

    public CheckerValue retrieveCheckerValue()
    {
        return checkerValue;
    }
}
