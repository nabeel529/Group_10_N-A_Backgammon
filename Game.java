public class Game {
	
	Board board = new Board();
	View view = new View();
	Actions actions;
	public void playGame () {
		int startControl = 0;
		boolean oneMatchOverDisplayed = false;
		view.displayWelcome();
		view.displayStart();
		do {
			boolean commandDone = false;
			do {
				actions = view.getUserInput(board);
				if (startControl == 1 && !board.isOneMatchOver())
					if (actions.letsThrow()) {
						board.makeDiceRoll();
						view.showDice(board.getDiceFace(1), board.getDiceFace(2));
						view.displayPiece(board);
						view.showAllAllowedMoves(board);
						commandDone = true;
					} else if (actions.letsMove()) {
						if (board.moveIsPossible(actions)) {
							board.move(actions);
							board.calculateSetPips();
							if (board.isOneMatchOver())
								board.addCurrentPlayerScore();
							view.displayPiece(board);
							if (board.getDiceMoveNumber() != 0) {
								view.showAllAllowedMoves(board);
							} else if (board.getDiceMoveNumber() == 0)
								board.endTurn();
							commandDone = true;
						} else
							view.displayCommandNotPossible();
					} else if (actions.letsStart()) {
						view.displayRestart();
						startControl--;
						commandDone = true;
					} else if (actions.letsExit()) {
						commandDone = true;
					} else if (actions.letsSkip()) {
						view.displayForceJump(board);
						board.addMatchRoundNumber();
						if (!board.isWholeMatchOver()) {
							board.initializeBoard();
							board.calculateSetPips();
							view.FirstDiceRoll(board);
							view.showDice(board.getDiceFace(1), board.getDiceFace(2));
							view.displayPiece(board);
							view.showAllAllowedMoves(board);
						}
						commandDone = true;
					} else if (actions.letsYield()) {
						view.displayWaive(board.getPlayer(0));
						board.endTurn();
						board.makeDiceSetZero();
						commandDone = true;
					} else if (actions.letsDisplayPip()) {
						view.displayPips(board);
					} else if (actions.letsDieNum()) {
						board.setDiceFace(actions);
						view.displayPiece(board);
						view.showAllAllowedMoves(board);
						commandDone = true;
					} else if (actions.letsHint()) {
						view.showHint();
					} else if (actions.letsDisplayMoves())
						view.showAllAllowedMoves(board);
				if (board.isOneMatchOver()) {
					if (!oneMatchOverDisplayed) {
						view.displayOneMatchOver(board);
						board.addMatchRoundNumber();
					}
					if (!board.isWholeMatchOver() && oneMatchOverDisplayed)
						if (actions.letsSkip()) {
							board.initializeBoard();
							board.calculateSetPips();
							view.FirstDiceRoll(board);
							view.showDice(board.getDiceFace(1), board.getDiceFace(2));
							view.displayPiece(board);
							view.showAllAllowedMoves(board);
							oneMatchOverDisplayed = false;
						} else if (actions.letsThrow() || actions.letsMove() || actions.letsYield() || actions.letsDisplayPip() || actions.letsDieNum() || actions.letsDisplayMoves()) {
							view.displayCommandTemporarilyInvalid();
						} else if (actions.letsHint()) {
							view.showHint();
						} else if (actions.letsStart()) {
							view.displayRestart();
							startControl--;
							oneMatchOverDisplayed = false;
							commandDone = true;
						} else if (actions.letsExit())
							commandDone = true;
					if (!oneMatchOverDisplayed && !actions.letsSkip() && !actions.letsStart())
						oneMatchOverDisplayed = true;
				}
				if (startControl == 0) {
					if (actions.letsStart()) {
						view.getStartInformation(board);
						board.initializeBoard();
						board.calculateSetPips();
						board.setPlayersScoreToZero();
						view.FirstDiceRoll(board);
						view.showDice(board.getDiceFace(1), board.getDiceFace(2));
						view.displayPiece(board);
						view.showAllAllowedMoves(board);
						startControl++;
						commandDone = true;
					} else if (actions.letsThrow() || actions.letsMove() || actions.letsYield() || actions.letsDisplayPip() || actions.letsDieNum() || actions.letsSkip() || actions.letsDisplayMoves()) {
						view.displayCommandTemporarilyInvalid();
					} else if (actions.letsHint()) {
						view.showHint();
					} else if (actions.letsExit())
						commandDone = true;
				}
			} while (!commandDone);
		} while (!actions.letsExit() && !board.isWholeMatchOver());
		if (board.isWholeMatchOver()) {
			view.displayWholeMatchOver(board);
		} else
			view.displayQuit();
	}
	
	public Board getBoard () {
		return board;
	}
}
