/*
Group 10 | Nabeel Olusekun - nabeel529 | Alex Brady - alexb-25
Class: Backgammon
Description: Manages the gameplay flow of the backgammon game.
*/

public class Backgammon {

	public static void main(String... args) {
		// Start the game by creating an instance of Backgammon and invoking Backgammon_play
		Backgammon game = new Backgammon();
		game.Backgammon_play();
	}

	Table table = new Table(); // Create an instance of the Table class
	Show show = new Show(); // Create an instance of the Show class
	Actions actions; // Instance of the Actions class for game actions

	public void Backgammon_play() {
		int Begin = 0;
		boolean isMatchFinished = false;

		// Display initial game start and beginning information
		show.printStart();
		show.printBeginning();

		// Game loop
		do {
			boolean actionFinished = false;
			do {
				actions = show.retrieveInput(table); // Get user input
				if (Begin == 1 && !table.MatchFinito())
					if (actions.letsThrow()) {
						table.makethrowDie();
						show.show_Dice(table.RetrieveDiceNumber(1), table.RetrieveDiceNumber(2));
						show.showPiece(table);
						show.show_Possible_Moves(table);
						actionFinished = true;
					} else if (actions.letsMove()) {
						if (table.movementPossible(actions)) {
							table.move(actions);
							table.pip_calc();
							if (table.MatchFinito())
								table.IncreaseScore();
							show.showPiece(table);
							if (table.retrieveNumMoves() != 0) {
								show.show_Possible_Moves(table);
							} else if (table.retrieveNumMoves() == 0)
								table.turnOver();
							actionFinished = true;
						} else
							show.print_Cant_Do();
					} else if (actions.letsStart()) {
						show.printRestart();
						Begin--;
						actionFinished = true;
					} else if (actions.letsExit()) {
						actionFinished = true;
					} else if (actions.letsSkip()) {
						show.showSkip(table);
						table.Increment_round();
						if (!table.EntireMatchFinito()) {
							table.makeBoard();
							table.pip_calc();
							show.ThrowOne(table);
							show.show_Dice(table.RetrieveDiceNumber(1), table.RetrieveDiceNumber(2));
							show.showPiece(table);
							show.show_Possible_Moves(table);
						}
						actionFinished = true;
					} else if (actions.letsYield()) {
						show.show_turn_end(table.retrievePerson(0));
						table.turnOver();
						table.setnothing();
						actionFinished = true;
					} else if (actions.letsDisplayPip()) {
						show.show_pip(table);
					} else if (actions.letsDieNum()) {
						table.makeDieNumber(actions);
						show.showPiece(table);
						show.show_Possible_Moves(table);
						actionFinished = true;
					} else if (actions.letsHint()) {
						show.show_all_Hints();
					} else if (actions.letsDisplayMoves())
						show.show_Possible_Moves(table);
				if (table.MatchFinito()) {
					if (!isMatchFinished) {
						show.show_End(table);
						table.Increment_round();
					}
					if (!table.EntireMatchFinito() && isMatchFinished)
						if (actions.letsSkip()) {
							table.makeBoard();
							table.pip_calc();
							show.ThrowOne(table);
							show.show_Dice(table.RetrieveDiceNumber(1), table.RetrieveDiceNumber(2));
							show.showPiece(table);
							show.show_Possible_Moves(table);
							isMatchFinished = false;
						} else if (actions.letsThrow() || actions.letsMove() || actions.letsYield() || actions.letsDisplayPip() || actions.letsDieNum() || actions.letsDisplayMoves()) {
							show.print_Not_Valid();
						} else if (actions.letsHint()) {
							show.show_all_Hints();
						} else if (actions.letsStart()) {
							show.printRestart();
							Begin--;
							isMatchFinished = false;
							actionFinished = true;
						} else if (actions.letsExit())
							actionFinished = true;
					if (!isMatchFinished && !actions.letsSkip() && !actions.letsStart())
						isMatchFinished = true;
				}
				if (Begin == 0) {
					if (actions.letsStart()) {
						show.Initialisation(table);
						table.makeBoard();
						table.pip_calc();
						table.makeNothingPoint();
						show.ThrowOne(table);
						show.show_Dice(table.RetrieveDiceNumber(1), table.RetrieveDiceNumber(2));
						show.showPiece(table);
						show.show_Possible_Moves(table);
						Begin++;
						actionFinished = true;
					} else if (actions.letsThrow() || actions.letsMove() || actions.letsYield() || actions.letsDisplayPip() || actions.letsDieNum() || actions.letsSkip() || actions.letsDisplayMoves()) {
						show.print_Not_Valid();
					} else if (actions.letsHint()) {
						show.show_all_Hints();
					} else if (actions.letsExit())
						actionFinished = true;
				}
			} while (!actionFinished); // Continue loop until an action is finished

		} while (!actions.letsExit() && !table.EntireMatchFinito()); // Continue game until exit or entire match finished

		// Game ending messages based on match completion
		if (table.EntireMatchFinito()) {
			show.Show_Complete_End(table);
		} else {
			show.show_Exit();
		}
	}

	// Getter method for the game board (Table instance)
	public Table getBoard() {
		return table;
	}
}