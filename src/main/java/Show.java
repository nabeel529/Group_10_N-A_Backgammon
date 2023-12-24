import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;



public class Show
{
	
	private final static String BLANKY = "  ";
	private final static String BLANKY_2 = "   ";
	private Scanner in;
	private Actions actions;
	
	Show ()
	{
		in = new Scanner(System.in);
	}
	
	public Show (InputStream s_input)
	{
	    this.in = new Scanner(s_input);
	}
	
	public void printStart () 
	{
		System.out.println("This is Backgammon");
	}
	
	public void printBeginning ()
	{
		System.out.println("Type S to start/restart. H is to see the different commands and Q is to exit the game ");
	}
	
	public void printRestart ()
	 {
		System.out.println("Restarting");
	}
	
	public void showPiece (Table table)
	{ 
		String num_Match = Integer.toString(table.retrieveNumMatch());
		String num_Round = Integer.toString(table.retirveRoundnumber());
		int num_space_match = 5 - num_Match.length();
		int num_space_match_round = 5 - num_Round.length();
		int num_goUpLane = Math.max(table.getSize("upLane"),1);
		int num_goDownLane = Math.max(table.getSize("downLane"),1);
		System.out.println("║*********************************************************************║");
		System.out.print("║ Length: " + table.retrieveNumMatch());
		for (int i = 0; i < num_space_match; i++)
			System.out.print(" ");
		System.out.print(" ║");
		System.out.print("    Round: " + table.retirveRoundnumber());
		for (int i = 0; i < num_space_match_round; i++)
			System.out.print(" ");
		System.out.print("║ " + "Points " + Colours.GREEN + table.retrievePerson(1).retrieveColour() + Colours.RESET + ": " + table.retrievePerson(1).retrievePoint() + " ║");
		System.out.print(" " + "Points " + Colours.BLACK + table.retrievePerson(2).retrieveColour() + Colours.RESET + ": " + table.retrievePerson(2).retrievePoint() + "  ║");
		System.out.println("\n║*********************************************************************║");
		if (table.retrievePerson(0).retrieveColour() == "GREEN") {
			System.out.print("║        Players turn: " + Colours.GREEN + table.retrievePerson(0).retrieveColour() + Colours.RESET + "     ║ Num Pips: " + table.retrievePerson(0).retrievePip());
		} else if (table.retrievePerson(0).retrieveColour() == "BLACK")
			System.out.print("║        Players turn: " + Colours.BLACK + table.retrievePerson(0).retrieveColour() + Colours.RESET + "     ║ Num Pips: " + table.retrievePerson(0).retrievePip());
		if (table.RetrieveDiceNumber(1) != table.RetrieveDiceNumber(2)) {
			System.out.print("   ║ Die: ");
			if(table.retrieveStepDie(1) == 1) {
				System.out.print(table.RetrieveDiceNumber(1));
			} else
				System.out.print(" ");
			System.out.print("  ");
			if(table.retrieveStepDie(2) == 1) {
				System.out.print(table.RetrieveDiceNumber(2));
			} else
				System.out.print(" ");
			System.out.print("        ║");
		} else if (table.RetrieveDiceNumber(1) == table.RetrieveDiceNumber(2)) {
			System.out.print("   ║ Die:  ");
			if(table.retrieveStepDie(1) >= 1) {
				System.out.print(table.RetrieveDiceNumber(1));
			} else
				System.out.print(" ");
			System.out.print("  ");
			if(table.retrieveStepDie(1) >= 2) {
				System.out.print(table.RetrieveDiceNumber(1));
			} else
				System.out.print(" ");
			System.out.print("  ");
			if(table.retrieveStepDie(1) >= 3) {
				System.out.print(table.RetrieveDiceNumber(1));
			} else
				System.out.print(" ");
			System.out.print("  ");
			if(table.retrieveStepDie(1) == 4) {
				System.out.print(table.RetrieveDiceNumber(1));
			} else
				System.out.print(" ");
			System.out.print(" ║");
		}
		System.out.println("\n║*********************************************************************║");
		System.out.println("║ " + Colours.BLACK + "13   14   15   16   17   18" + Colours.RESET + " ║ " + Colours.BLACK + "B2" + Colours.RESET + " ║ " + Colours.BLACK + "19   20   21   22   23   24" + Colours.RESET + " ║ " + Colours.GREEN + "E1" + Colours.RESET + " ║   ");
		System.out.print("║ " + Colours.GREEN + "12   11   10   09   08   07" + Colours.RESET);
		System.out.print(" ║ " + Colours.BLACK + "B2"  + Colours.RESET + " ║ ");
		System.out.print(Colours.GREEN + "06   05   04   03   02   01" + Colours.RESET + " ║ " + Colours.GREEN + "E1" + Colours.RESET + " ║\n");

		
		for (int row_num = 0; row_num < num_goUpLane; row_num++) {
			System.out.print("║ ");
			for (int upwards = 11; upwards > 6; upwards--) {
				Stack<Checker> laner = table.retrieveLane(upwards);
				if (row_num < laner.size()) {
					System.out.print(laner.get(row_num) + BLANKY_2);
		        } else
		            System.out.print(BLANKY + BLANKY_2);
			}
			Stack<Checker> six_laner = table.retrieveLane(6);
			if (row_num < six_laner.size()) {
				System.out.print(six_laner.get(row_num) + " ║ ");
	        } else
	            System.out.print(BLANKY + " ║ ");
			Stack<Checker> bar = table.retrieveBar(1);
				if (row_num < bar.size()) {
					System.out.print(bar.get(row_num) + " ║ ");
		        } else
		        	System.out.print(BLANKY + " ║ ");
			for (int up = 5; up > 0; up--) {
				Stack<Checker> lane = table.retrieveLane(up);
				if (row_num < lane.size()) {
					System.out.print(lane.get(row_num) + BLANKY_2);
		        } else
		            System.out.print(BLANKY + BLANKY_2);
			}
			Stack<Checker> zero_laner = table.retrieveLane(0);
			if (row_num < zero_laner.size()) {
				System.out.print(zero_laner.get(row_num));
	        } else
	            System.out.print(BLANKY);
			if (row_num==0) {
				if (table.retrieveEnd(0).size() < 10)
			        System.out.print(" ║ " + Colours.GREEN + "0" + table.retrieveEnd(0).size() + Colours.RESET + " ║");
			    else
			    	System.out.print(" ║ " + Colours.GREEN + table.retrieveEnd(0).size() + Colours.RESET + " ║");
			} else
				System.out.print(" ║    ║");
			System.out.println();
		}
		System.out.println("║*****************************║****║*****************************║****║");
		for (int num_row = 0; num_row < num_goDownLane; num_row++) {
			System.out.print("║ ");
			for (int up = 12; up < 17; up++) {
				Stack<Checker> lane = table.retrieveLane(up);
				if (num_row < num_goDownLane - lane.size()) {
					System.out.print(BLANKY + BLANKY_2);
		        } else
		            System.out.print(lane.get(num_goDownLane - num_row - 1) + BLANKY_2);
			}
			Stack<Checker> laner17 = table.retrieveLane(17);
			if (num_row < num_goDownLane - laner17.size()) {
				System.out.print(BLANKY + " ║ ");
	        } else
	            System.out.print(laner17.get(num_goDownLane - num_row - 1) + " ║ ");
			Stack<Checker> bar = table.retrieveBar(0);
				if (num_row < num_goDownLane - bar.size()) {
					System.out.print(BLANKY + " ║ ");
		        } else
		        	System.out.print(bar.get(num_goDownLane - num_row - 1) + " ║ ");
			for (int up = 18; up < 23; up++) {
				Stack<Checker> lane = table.retrieveLane(up);
				if (num_row < num_goDownLane - lane.size()) {
					System.out.print(BLANKY + BLANKY_2);
		        } else
		        	System.out.print(lane.get(num_goDownLane - num_row - 1) + BLANKY_2);
			}
			Stack<Checker> laner23 = table.retrieveLane(23);
			if (num_row < num_goDownLane - laner23.size()) {
				System.out.print(BLANKY);
	        } else
	            System.out.print(laner23.get(num_goDownLane - num_row - 1));
			if (num_row == num_goDownLane - 1) {
				if (table.retrieveEnd(1).size() < 10)
			        System.out.print(" ║ " + Colours.BLACK + "0" + table.retrieveEnd(1).size() + Colours.RESET + " ║");
			    else
			    	System.out.print(" ║ " + Colours.BLACK + table.retrieveEnd(1).size() + Colours.RESET + " ║");
			} else
				System.out.print(" ║    ║");
			System.out.println();
		}
		


		System.out.print("║ " + Colours.BLACK + "12   11   10   09   08   07" + Colours.RESET);
		System.out.print(" ║ " + Colours.GREEN + "B1" + Colours.RESET + " ║ ");
		System.out.println(Colours.BLACK + "06   05   04   03   02   01" + Colours.RESET + " ║ " + Colours.BLACK + "E2" + Colours.RESET + " ║  ");
		System.out.print("║ " + Colours.GREEN + "13   14   15   16   17   18" + Colours.RESET + " ║ " + Colours.GREEN + "B1" + Colours.RESET + " ║ " + Colours.GREEN + "19   20   21   22   23   24" + Colours.RESET + " ║ " + Colours.BLACK + "E2" + Colours.RESET + " ║");
		System.out.println("\n║*********************************************************************║");
	}
	
	public Actions retrieveInput (Table table)
	{
		boolean commando = false;
		do {
			System.out.print("Enter a command: ");
			String input = in.nextLine();
			if (Actions.letsTest(input))
				input = read_file(input, in, "Enter a new command: ");
			if (Actions.letsCheck(input)) {
				actions = new Actions(input);
				if (actions.letsMove()) {
					String inputFormatted = input.trim();
					if (inputFormatted.length() == 4 && table.retrievePerson(0) == table.retrievePerson(2)) {
						String[] inputs = new String[2];
						inputs[0] = inputFormatted.substring(0, 2);
						inputs[1] = inputFormatted.substring(2, 4);
						if (inputs[0].matches("\\d+")) {
							int number1 = Integer.parseInt(inputs[0]);
							inputs[0] = String.format("%02d", 25 - number1);
						}
						if (inputs[1].matches("\\d+")) {
							int number2 = Integer.parseInt(inputs[1]);
							inputs[1] = String.format("%02d", 25 - number2);
						}
						inputFormatted = inputs[0] + inputs[1];
					}
					actions = new Actions(inputFormatted);
				}
				commando = true;
			} else
				System.out.println("Type a valid command.");
		} while (!commando);
		return actions;
	}
	
	public void show_Possible_Moves(Table table)
	{ 
		String improved_in, fresh_in;
		int moveStep1, moveStep2;
		int num_output = 0;
	 	String[] pref = new String[25];
	    String[] suff = new String[25];
	    if (table.retrievePerson(0) == table.retrievePerson(1)) {
	    	pref[0] = "B1";
			suff[24] = "E1";
		    for (int i = 1; i <= 24; i++) {
		        pref[25 - i] = String.format("%02d", i);
		        suff[24 - i] = String.format("%02d", i);
		    }
	    } else if (table.retrievePerson(0) == table.retrievePerson(2)) {
	    	pref[0] = "B2";
		    suff[24] = "E2";
		    for (int i = 1; i <= 24; i++) {
		    	pref[25 - i] = String.format("%02d", i);
		        suff[24 - i] = String.format("%02d", i);
		    }
	    }
	    for (int i = 0; i < Actions.retrieveLegit().length; i++)
	        Actions.makeLegit(i, null);
	    for (int i = 0; i < pref.length; i++)
	        for (int j = i; j < suff.length; j++) {
	        	improved_in = pref[i] + suff[j];
	        	fresh_in = improved_in;
                if (table.retrievePerson(0) == table.retrievePerson(2)) {
                	String tempPrefix = pref[i];
                    String tempSuffix = suff[j];
					if (pref[i].matches("\\d+")) {
						int number1 = Integer.parseInt(pref[i]);
						tempPrefix = String.format("%02d", 25 - number1);
					}
					if (suff[j].matches("\\d+")) {
						int number2 = Integer.parseInt(suff[j]);
						tempSuffix = String.format("%02d", 25 - number2);
					}
					improved_in = tempPrefix + tempSuffix;
                }
				actions = new Actions(improved_in);
                moveStep1 = table.retrieveStepDie(1);
                moveStep2 = table.retrieveStepDie(2);
                if (table.movementPossible(actions)) {
                	Actions.makeLegit(num_output, improved_in);
                	num_output++;
                	if (num_output == 1)
                    	System.out.println("Possible moves:");
                	if (num_output > 0 && num_output < 10)
                		System.out.print(" ");
                    System.out.println(num_output + ": " + fresh_in);
                }
                table.makeStepDie(moveStep1, moveStep2);
	        }
	    if (num_output == 0)
        	System.out.println("No more possible moves to make. Type Y to end your turn");
	}
	
	public void ThrowOne (Table table)
	{
		do {
			table.makethrowDie();
			if (table.RetrieveDiceNumber(1) > table.RetrieveDiceNumber(2)) {
				System.out.println("Die 1: " + table.RetrieveDiceNumber(1) + ". Die 2: " + table.RetrieveDiceNumber(2)+ ". Die 1 is larger than the number in Die 2. So green goes first.");
				table.makePersonCurrently(1);
			} else if (table.RetrieveDiceNumber(1) < table.RetrieveDiceNumber(2)) {
				System.out.println("Die 1: " + table.RetrieveDiceNumber(1) + ". Die 2: " + table.RetrieveDiceNumber(2)+ ". Die 2 is larger than the number in Die 1. So Black goes first.");
				table.makePersonCurrently(2);
			} else if (table.RetrieveDiceNumber(1) == table.RetrieveDiceNumber(2)) {
				System.out.println("Die 1: " + table.RetrieveDiceNumber(1) + ". Die 2: " + table.RetrieveDiceNumber(2)+ ". The number in Die 1 is equal to the number in Die 2. Reroll the dies.");
			}
		} while (table.RetrieveDiceNumber(1) == table.RetrieveDiceNumber(2));
	}
	
	public void Initialisation (Table table)
	{
		boolean validInput = false;
		String Message = "Match Length:";
		System.out.print("Name player GREEN: ");
		table.startUpPlayer(1);
		System.out.print("Name player BLACK: ");
		table.startUpPlayer(2);
		while (!validInput) {
            System.out.print(Message);
            String matchNumberInput = in.nextLine();
    		if (Actions.letsTest(matchNumberInput))
    			matchNumberInput = read_file(matchNumberInput, in, "New match length: ");
            try {
                double doubleValue = Double.parseDouble(matchNumberInput);
                if (doubleValue > 0 && Math.floor(doubleValue) == doubleValue) {
                    table.makeNum_match((int) doubleValue);
                    table.makeRoundnumber(1);
                    validInput = true;
                } else if (Math.floor(doubleValue) != doubleValue) {
                    System.out.println("Invalid: Decimal entered");
                } else {
                    System.out.println("Invalid: Negative number entred");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid: String cannot be converted to a number");
            }
            Message = "New match length:";
        }
        System.out.println("Match length: " + table.retrieveNumMatch());
	}
	
	public void print_Cant_Do () 
	{ 
		System.out.println("Make a possible move");
	}
	
	public void print_Not_Valid () {
		System.out.println("Invalid");
	}
	
	public void show_End (Table table) {
		System.out.println("Round " + table.retirveRoundnumber() + " is finished. " + table.retrievePerson(0).retrieveName() + " wins.");
		if (table.retrieveNumMatch() == 1) {
			System.out.println("There is " + table.retrieveNumMatch() + " rounds. The whole match is over.");
		} else if (table.retrieveNumMatch() > 1)
			System.out.print("There is " + table.retrieveNumMatch() + " rounds overall, ");
		if (table.retrieveNumMatch() - table.retirveRoundnumber() == 1) {
			System.out.println("There are " + (table.retrieveNumMatch() - table.retirveRoundnumber()) + " rounds remaining. Type SK to play the next round.");
		} else if (table.retrieveNumMatch() - table.retirveRoundnumber() > 1)
			System.out.println("There are " + (table.retrieveNumMatch() - table.retirveRoundnumber()) + " rounds remaining. Type SK to play the next round.");
	}
	
	public void showSkip (Table table)
	{ 
		System.out.println("Match ended early. No points added.");
		if (table.retrieveNumMatch() == 1) {
			System.out.println("There is " + table.retrieveNumMatch() + " round overall, The whole match is finished.");
		} else if (table.retrieveNumMatch() > 1)
			System.out.print("There is " + table.retrieveNumMatch() + " rounds overall, ");
		if (table.retrieveNumMatch() - table.retirveRoundnumber() == 1) {
			System.out.println("there are " + (table.retrieveNumMatch() - table.retirveRoundnumber()) + " rounds remaining. Match has started");
		} else if (table.retrieveNumMatch() - table.retirveRoundnumber() > 1)
			System.out.println("there are " + (table.retrieveNumMatch() - table.retirveRoundnumber()) + " rounds remaining. Match ha started");
	}
	
	public void Show_Complete_End (Table table)
	{
		if (table.retrievePerson(1).retrievePoint() > table.retrievePerson(2).retrievePoint()) {
			System.out.println(table.retrievePerson(1).retrieveName() + " has won");
		} else if (table.retrievePerson(1).retrievePoint() < table.retrievePerson(2).retrievePoint()) {
			System.out.println(table.retrievePerson(2).retrieveName() + " has won.");
		} else if (table.retrievePerson(1).retrievePoint() == table.retrievePerson(2).retrievePoint())
			System.out.println("Its a draw");
		System.out.println("Game has ended");
	}
	
	public void show_Exit ()
	{
		System.out.println("Quit.");
	}
	
	public void show_turn (Person player)
	{
		System.out.println(player + "(" + player.retrieveColour() + ") finishes moving.");
	}
	
	public void show_next_turn (Person player)
	{
		System.out.println("Now it's the " + player + "(" + player.retrieveColour() + ")'s turn.");
	}
	
	public void show_Dice (int face1, int face2) 
	{
		if (face1 != face2) {
			System.out.println("The number of two die thrown are " + face1 + " and " + face2 );
		} else if (face1 == face2)
			System.out.println("The number of two die thrown are " + face1 + " and " + face2 );
	}
	
	public void show_turn_end (Person player) {
		System.out.println(player + " Turn ended");
	}
	
	public void show_pip (Table table)
	{
		System.out.println(table.retrievePerson(1).retrieveName() + " pips are " + table.retrievePerson(1).retrievePip() );
		System.out.println(table.retrievePerson(2).retrieveName() + " pips are " + table.retrievePerson(2).retrievePip() );
	}
	
	public String read_file(String string, Scanner in, String Message)
	{
	    boolean read_file = false;
	    String in_file = "";
	    do {
	        String fileName = Actions.retrieveIn(string);
	        File dir = new File(".");
	        File[] match = dir.listFiles((dir1, name) -> name.equals(fileName));
	        File[] case_insensitive_matches = dir.listFiles((dir1, name) -> name.equalsIgnoreCase(fileName));
	        if (!((case_insensitive_matches != null && case_insensitive_matches.length > 0) && !(match != null && match.length > 0))) {
	            try {
	                BufferedReader br = new BufferedReader(new FileReader(fileName));
	                StringBuilder file_content_builder = new StringBuilder();
	                String line;
	                while ((line = br.readLine()) != null)
	                    file_content_builder.append(line).append("\n");
	                br.close();
	                in_file = file_content_builder.toString().trim();
	                read_file = true;
	            } catch (FileNotFoundException e) {
	                System.out.println("Invalid: Unable to find file - " + e.getMessage());
	            } catch (IOException e) {
	                System.out.println("Unable to read file: " + e.getMessage());
	            }
	        } else {
	            System.out.println("Filenames do not match");
	        }
	        if (!read_file) {
	            System.out.print(Message);
	            if (in.hasNextLine())
				{
	                string = in.nextLine();
	            } else
	                break;
	        }
	    } while (!read_file);
	    return in_file;
	}
	
	public Scanner retireveScan()
	{
	    return in;
	}
	
	public void show_all_Hints ()
	{
		System.out.println("S: Start the game");
		System.out.println("R: Roll the dice.");
		System.out.println("R + 1 digit + 1 digit: Roll the dice to get those 2 specific numbers");
		System.out.println("Y: Yield your turn when you have finished your turn");
		System.out.println("P: See pips");
		System.out.println("2 digits + 2 digits: To move a piece on Lane.");
		System.out.println("B + 1 digit + 2 digits: Movement of a piece from bar to lane.");
		System.out.println("2 digits + E + 1 digit: Movement of a piece from lane to endpoint.");
		System.out.println("1 digit or 2 digits: Move a piece by the number of the specific movement step.");
		System.out.println("H: See all commands.");
		System.out.println("DM: See all possible moves");
		System.out.println("SK: Skip the current match round and start the next match round will be played.");
		System.out.println("Q: Quit");
		System.out.println("Typing \"test:file_name.txt\" read the commands in that file.");
	}
}
