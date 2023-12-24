/*
Group 10 | Nabeel Olusekun - nabeel529 | Alex Brady - alexb-25
Class: Actions
Description: Handles user actions and translates input into game actions for the backgammon game.
*/

public class Actions {
    private String goTo;
    private String comeFrom;
    private TypeAction typeAction;
    private int[] dieNum;
    private String input;
    private String[] die;
    private static String[] legit = new String[70];

    // Constructor to interpret user input and determine the type of action
    Actions(String in)
    {
        // Initialization and parsing of user input
        this.die = new String[2];
        this.dieNum = new int[2];
        input = in.toUpperCase().trim();

        if(input.equals("Q"))
        {
            typeAction = TypeAction.EXIT;
        }
        else if(in.matches("([1-9]|0[1-9]|[1-9][0-9])") && legit[Integer.parseInt(in) - 1] != null)
        {
            typeAction = TypeAction.MOVE;
            goTo = legit[Integer.parseInt(in)-1].substring(2,4);
            comeFrom = legit[Integer.parseInt(in)-1].substring(0,2);
        }
        else if(input.matches("(0[1-9]|1[0-9]|2[0-4]|B[1-2])(0[1-9]|1[0-9]|2[0-4]|E[1-2])"))
        {
            typeAction = TypeAction.MOVE;
            goTo = input.substring(2,4);
            comeFrom= input.substring(0,2);
        }
        else if(input.matches("R[1-6][1-6]"))
        {
            typeAction = TypeAction.DIENUM;
            die[1] = input.substring(2,3);
            die[0] = input.substring(1,2);
            dieNum[1]= Integer.parseInt(die[1]);
            dieNum[0]= Integer.parseInt(die[0]);
        }
        else if(input.equals("P"))
        {
            typeAction = TypeAction.PIP;
        }
        else if(input.equals("R"))
        {
            typeAction = TypeAction.THROW;
        }
        else if(input.equals("H"))
        {
            typeAction = TypeAction.HINT;
        }
        else if(input.equals("SK"))
        {
            typeAction = TypeAction.SKIP;
        }
        else if(input.equals("DM"))
        {
            typeAction = TypeAction.DISPLAYMOVES;
        }
        else if(input.equals("S"))
        {
            typeAction = TypeAction.START;
        }
        else if(input.equals("Y"))
        {
            typeAction = TypeAction.YIELD;
        }

    }

    public boolean letsExit()
    {
        return typeAction == TypeAction.EXIT;
    }
    public boolean letsDisplayPip()
    {
        return typeAction == TypeAction.PIP;
    }
    public boolean letsDieNum()
    {
        return typeAction == TypeAction.DIENUM;
    }
    public boolean letsThrow()
    {
        return typeAction == TypeAction.THROW;
    }
    public boolean letsHint()
    {
        return typeAction == TypeAction.HINT;
    }
    public boolean letsSkip()
    {
    return typeAction == TypeAction.SKIP;
    }
    public boolean letsDisplayMoves()
    {
        return typeAction == TypeAction.DISPLAYMOVES;
    }
    public boolean letsStart()
    {
        return typeAction == TypeAction.START;
    }
    public boolean letsMove()
    {
        return typeAction == TypeAction.MOVE;
    }
    public boolean letsYield()
    {
        return typeAction == TypeAction.YIELD;
    }

    public String toString()
    {
        return input;
    }
    public boolean goLane()
    {
        return goTo.matches("0[1-9]|1[0-9]|2[0-4]");
    }
    public boolean comeFromLane()
    {
        return comeFrom.matches("0[1-9]|1[0-9]|2[0-4]");
    }
    public boolean goFinal()
    {
        return goTo.matches("E1|E2");
    }
    public boolean comeFromBar()
    {
        return comeFrom.matches("B1|B2");
    }

    public static  String[] retrieveLegit()
    {
        return legit;
    }

    public int retrieveDieNum(int num)
    {
        if (num == 1)
        {
            return dieNum[0];
        }

        else if (num == 2)
        {
            return dieNum[1];
        }

        else
        {
            return 0;
        }
    }

    public static void makeLegit(int num, String num_move)
    {
        legit[num] = num_move;
    }

    public static boolean letsTest(String in)
    {
        String input = in.trim();
        return input.matches("test:(.+\\.txt)");
    }
    public static boolean letsCheck(String in)
    {
        String input = in.trim().toUpperCase();

        if (input.equals("Q") ||
                input.equals("P") ||
                input.equals("Y") ||
                input.equals("R") ||
                input.equals("H") ||
                input.equals("S") ||
                input.equals("DM") ||
                input.equals("SK") ||
                input.matches("R[1-6][1-6]") ||
                input.matches("(0[1-9]|1[0-9]|2[0-4]|B[1-2])(0[1-9]|1[0-9]|2[0-4]|E[1-2])")) {
            return true;
        }
        else
        {
            if (in.matches("([1-9]|0[1-9]|[1-9][0-9])"))
            {
                int num = Integer.parseInt(in) - 1;
                return num >= 0 && num < legit.length && legit[num] != null;
            }
        }

        return false;
    }

    public static String retrieveIn(String in)
    {
        String input = in.trim();

        if (input.length() > 5)
        {
            return input.substring(5);
        }
        else
            return "";
    }

    private int barToVal(String word)
    {
        if (word.equals("B1"))
        {
            return 0;
        }
        else if (word.equals("B2"))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    private int endToVal(String word)
    {
        if (word.equals("E1"))
        {
            return 0;
        }
        else if (word.equals("E2"))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public int fromNum ()
    {
        if (comeFromLane())
            return Integer.parseInt(comeFrom) - 1;
        else
            return barToVal(comeFrom);
    }

    public int toNum ()
    {
        if (goLane())
            return Integer.parseInt(goTo) - 1;
        else
            return endToVal(goTo);
    }

}