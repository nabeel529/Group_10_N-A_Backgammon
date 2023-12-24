import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.text.View;

public class Board {

	public static final int NUM_LANES = 24;
	public static final int NUM_BARS = CheckerValue.values().length; // = 2
	public static final int NUM_END = CheckerValue.values().length; // = 2

	private List<Stack<Checker>> lanes;
	private List<Stack<Checker>> bars;
	private List<Stack<Checker>> endpoints;

	private Person[] people;
	private Show show;
	private Scanner in;
	private Die die;
	private int matchNumber;
	private int matchRoundNumber = 1;

	Board() { // Constructor: Initializes a new board object by arranging the game board with
				// displays, inputs, die, people and the kinds of positions of the pieces.
		view = new View();
		in = new Scanner(System.in);
		die = new Die();
		this.people = new Person[3]; // people[0] is current player. people[1] is RED player. people[2] is WHITE
										// player.

		lanes = new ArrayList<>(NUM_LANES);
		bars = new ArrayList<>(NUM_BARS);
		endpoints = new ArrayList<>(NUM_END);

		for (int i = 0; i < NUM_LANES; i++)
			lanes.add(new Stack<>());
		for (int i = 0; i < NUM_BARS; i++)
			bars.add(new Stack<>());
		for (int i = 0; i < NUM_END; i++)
			endpoints.add(new Stack<>());
	}

	private int getMaxLaneOnInnerTable(Actions action, List<Stack<Checker>> lanes, int playerIndex) { // Get the maximum
																										// lane on the
																										// inner table
		int maxLane = -1;
		if (playerIndex == 1) {
			for (int i = 0; i <= 5; i++) {
				Stack<Checker> lanei = lanes.get(i);
				if (!lanei.isEmpty())
					maxLane = i;
			}
			if (action.fromNum() >= 6)
				maxLane = action.fromNum();
		} else if (playerIndex == 2) {
			for (int i = 23; i >= 18; i--) {
				Stack<Checker> lanei = lanes.get(i);
				if (!lanei.isEmpty())
					maxLane = i;
			}
			if (action.fromNum() <= 17)
				maxLane = action.fromNum();
		}
		return maxLane;
	}

	private boolean isPathClear(int start, int end) { // Check if the path is clear for a move
		if (start < 0)
			start = 0;
		if (end > 23)
			end = 23;
		for (int i = start + 1; i < end; i++) {
			if (!lanes.get(i).empty()
					&& lanes.get(i).peek().retrieveCheckerValue() == people[0].retrieveCheckerValue()) {
				return false;
			}
		}
		return true;
	}

	private int getPlayerNumber() { // Get the player number
		if (people[0].retrieveCheckerValue() == CheckerValue.G) {
			return 1;
		} else // people[0].retrieveCheckerValue() == CheckerValue.B
			return 0;
	}

	public Board(InputStream inputStream) { // Only for Test.
		this.in = new Scanner(inputStream);
		this.people = new Person[3];
	}

	public void initializePlayer(int playerIndex) { // Initialize a player with the given index
		String username = in.nextLine();
		if (action.isText(username))
			username = view.readContentFromFile(username, in, "Please enter a new player name: ");
		if (playerIndex == 1) {
			people[playerIndex] = new Person(username, CheckerValue.B);
		} else if (playerIndex == 2)
			people[playerIndex] = new Person(username, CheckerValue.G);
	}

	public void endTurn() { // End the current player's turn
		if (!isOneMatchOver()) {
			if (people[0] == people[1]) {
				people[0] = people[2];
				view.playerTurnCurrent(people[1]);
				view.playerTurnNext(people[2]);
			} else if (people[0] == people[2]) {
				people[0] = people[1];
				view.playerTurnCurrent(people[2]);
				view.playerTurnNext(people[1]);
			}
		} else if (isOneMatchOver()) {
			if (people[0] == people[1]) {
				view.playerTurnCurrent(people[1]);
			} else if (people[0] == people[2])
				view.playerTurnCurrent(people[2]);
		}
	}

	public void initializeBoard() { // Initialize the board
		for (int i = 0; i < 24; i++)
			lanes.get(i).clear();
		for (int i = 0; i < 2; i++)
			bars.get(i).clear();
		for (int i = 0; i < 2; i++)
			endpoints.get(i).clear();
		for (int i = 0; i < 2; i++) {
			lanes.get(0).push(new Checker(CheckerValue.G));
			lanes.get(23).push(new Checker(CheckerValue.B));
		}
		for (int i = 0; i < 3; i++) {
			lanes.get(16).push(new Checker(CheckerValue.G));
			lanes.get(7).push(new Checker(CheckerValue.B));
		}
		for (int i = 0; i < 5; i++) {
			lanes.get(11).push(new Checker(CheckerValue.G));
			lanes.get(18).push(new Checker(CheckerValue.G));
			lanes.get(5).push(new Checker(CheckerValue.B));
			lanes.get(12).push(new Checker(CheckerValue.B));
		}
	}

	public boolean moveIsPossible(Action action) { // Check if a move is possible after giving a action
		boolean isPossible = false;
		if (action.comeFromBar() && action.isMoveToLane()) {
			Stack<Checker> bar = bars.get(action.fromNum());
			Stack<Checker> lane = lanes.get(action.toNum());
			if (!bar.empty())
				if (bar.peek().retrieveCheckerValue() == people[0].retrieveCheckerValue()
						&& (lane.empty() || lane.size() == 1
								|| bar.peek().retrieveCheckerValue() == lane.peek().retrieveCheckerValue())
						&& die.retrieveNumMoves() != 0) {
					if (die.retrieveDieNum(1) != die.retrieveDieNum(2)) {
						for (int i = 1; i <= 2; i++)
							if (die.retrieveMoves(i) != 0 && (people[0] == people[1]
									&& action.fromNum() + 24 == action.toNum() + die.retrieveDieNum(i)
									|| people[0] == people[2]
											&& action.fromNum() + die.retrieveDieNum(i) == action.toNum() + 2)) {
								isPossible = true;
								die.minusMoves(i);
							}
						if (die.retrieveMoves(1) != 0 && die.retrieveMoves(2) != 0 && bar.size() == 1
								&& (people[0] == people[1]
										&& action.fromNum() + 24 == action.toNum() + die.retrieveDieNum(1)
												+ die.retrieveDieNum(2)
										&& (lanes.get(24 - die.retrieveDieNum(1)).empty()
												|| lanes.get(24 - die.retrieveDieNum(2)).empty()
												|| lanes.get(24 - die.retrieveDieNum(1)).peek()
														.retrieveCheckerValue() == people[1]
																.retrieveCheckerValue()
												|| lanes.get(24 - die.retrieveDieNum(2)).peek()
														.retrieveCheckerValue() == people[1]
																.retrieveCheckerValue())
										|| people[0] == people[2]
												&& action.fromNum() + die.retrieveDieNum(1)
														+ die.retrieveDieNum(2) == action.toNum() + 2
												&& (lanes.get(die.retrieveDieNum(1) - 1).empty()
														|| lanes.get(die.retrieveDieNum(2) - 1).empty()
														|| lanes.get(die.retrieveDieNum(1) - 1).peek()
																.retrieveCheckerValue() == people[2]
																		.retrieveCheckerValue()
														|| lanes.get(die.retrieveDieNum(2) - 1).peek()
																.retrieveCheckerValue() == people[2]
																		.retrieveCheckerValue()))) {
							isPossible = true;
							die.minusMoves(1);
							die.minusMoves(2);
						}
					}
					if (die.retrieveDieNum(1) == die.retrieveDieNum(2) && die.retrieveMoves(1) != 0) {
						boolean allConditionsMet = true;
						boolean currentCondition = true;
						if (people[0] == people[1]) {
							if (action.fromNum() + 24 == action.toNum() + die.retrieveDieNum(1)
									&& (lanes.get(24 - die.retrieveDieNum(1)).empty()
											|| lanes.get(24 - die.retrieveDieNum(1)).size() == 1
											|| lanes.get(24 - die.retrieveDieNum(1)).peek()
													.retrieveCheckerValue() == people[1]
															.retrieveCheckerValue())) {
								isPossible = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1); i++)
								if (action.fromNum() + 24 == action.toNum() + die.retrieveDieNum(1) * i
										&& bar.size() == 1) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(24 - die.retrieveDieNum(1) * j).empty()
												|| lanes.get(24 - die.retrieveDieNum(1) * j).peek()
														.retrieveCheckerValue() == people[1].retrieveCheckerValue();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lanes.get(24 - die.retrieveDieNum(1) * i).empty()
											|| lanes.get(24 - die.retrieveDieNum(1) * i).peek()
													.retrieveCheckerValue() == people[1]
															.retrieveCheckerValue()
											|| lanes.get(24 - die.retrieveDieNum(1) * i).size() == 1;
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
						}
						if (people[0] == people[2]) {
							if (action.fromNum() + die.retrieveDieNum(1) == action.toNum() + 2
									&& (lanes.get(die.retrieveDieNum(1) - 1).empty()
											|| lanes.get(die.retrieveDieNum(1) - 1).size() == 1
											|| lanes.get(die.retrieveDieNum(1) - 1).peek()
													.retrieveCheckerValue() == people[2]
															.retrieveCheckerValue())) {
								isPossible = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1); i++)
								if (action.fromNum() + die.retrieveDieNum(1) * i == action.toNum() + 2
										&& bar.size() == 1) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(die.retrieveDieNum(1) * j - 1).empty()
												|| lanes.get(die.retrieveDieNum(1) * j - 1).peek()
														.retrieveCheckerValue() == people[2].retrieveCheckerValue();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lanes.get(die.retrieveDieNum(1) * i - 1).empty()
											|| lanes.get(die.retrieveDieNum(1) * i - 1).peek()
													.retrieveCheckerValue() == people[2]
															.retrieveCheckerValue()
											|| lanes.get(die.retrieveDieNum(1) * i - 1).size() == 1;
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
						}
					}
				}
		} else if (action.isMoveFromLane() && action.isMoveToEndpoint()) {
			Stack<Checker> lane = lanes.get(action.fromNum());
			Stack<Checker> endpoint = endpoints.get(action.toNum());
			int finalStage = endpoint.size();
			for (int i = 0; i < 6; i++) {
				if (!lanes.get(i).empty())
					if (people[0] == people[1]
							&& lanes.get(i).peek().retrieveCheckerValue() == people[0].retrieveCheckerValue()) {
						finalStage += lanes.get(i).size();
						if (action.fromNum() >= 6)
							finalStage += 1;
					}
				if (!lanes.get(i + 18).empty())
					if (people[0] == people[2]
							&& lanes.get(i + 18).peek().retrieveCheckerValue() == people[0].retrieveCheckerValue()) {
						finalStage += lanes.get(i + 18).size();
						if (action.fromNum() <= 17)
							finalStage += 1;
					}
			}
			if (!lane.empty())
				if (getPlayerNumber() == action.toNum()
						&& lane.peek().retrieveCheckerValue() == people[0].retrieveCheckerValue() && finalStage == 15
						&& die.retrieveNumMoves() != 0) {
					int maxLane = -1;
					if (die.retrieveDieNum(1) != die.retrieveDieNum(2)) {
						int diceIndexToDecrement = -1;
						if (people[0] == people[1]) {
							maxLane = getMaxLaneOnInnerTable(action, lanes, 1);
							for (int i = 1; i <= 2; i++) {
								if (die.retrieveMoves(i) != 0 && action.fromNum() == maxLane
										&& action.fromNum() + 1 < action.toNum() + die.retrieveDieNum(i)
										|| die.retrieveMoves(i) != 0 && action.fromNum()
												+ 1 == action.toNum() + die.retrieveDieNum(i)) {
									isPossible = true;
									if (diceIndexToDecrement == -1
											|| die.retrieveDieNum(i) > die.retrieveDieNum(diceIndexToDecrement))
										diceIndexToDecrement = i;
								}
							}
							if (diceIndexToDecrement != -1)
								die.minusMoves(diceIndexToDecrement);
							if (die.retrieveMoves(1) != 0 && die.retrieveMoves(2) != 0
									&& action.fromNum() + 1 > action.toNum() + die.retrieveDieNum(1)
									&& action.fromNum() + 1 > action.toNum() + die.retrieveDieNum(2))
								if (action.fromNum() == maxLane
										&& action.fromNum() + 1 < action.toNum() + die.retrieveDieNum(1)
												+ die.retrieveDieNum(2)
										&& (lanes.get(action.fromNum() - die.retrieveDieNum(1)).empty()
												|| lanes.get(action.fromNum() - die.retrieveDieNum(2)).empty()
												|| lanes.get(action.fromNum() - die.retrieveDieNum(1)).peek()
														.retrieveCheckerValue() == people[1].retrieveCheckerValue()
												|| lanes.get(action.fromNum() - die.retrieveDieNum(2)).peek()
														.retrieveCheckerValue() == people[1].retrieveCheckerValue())
										&& lane.size() == 1
										&& (isPathClear(action.fromNum() - die.retrieveDieNum(1),
												action.fromNum())
												|| isPathClear(action.fromNum() - die.retrieveDieNum(2),
														action.fromNum()))
										|| action.fromNum() + 1 == action.toNum() + die.retrieveDieNum(1)
												+ die.retrieveDieNum(2)
												&& (lanes.get(action.fromNum() - die.retrieveDieNum(1)).empty()
														|| lanes.get(action.fromNum() - die.retrieveDieNum(2)).empty()
														|| lanes.get(action.fromNum() - die.retrieveDieNum(1)).peek()
																.retrieveCheckerValue() == people[1]
																		.retrieveCheckerValue()
														|| lanes.get(action.fromNum() - die.retrieveDieNum(2)).peek()
																.retrieveCheckerValue() == people[1]
																		.retrieveCheckerValue())) {
									isPossible = true;
									die.minusMoves(1);
									die.minusMoves(2);
								}
						}
						if (people[0] == people[2]) {
							maxLane = getMaxLaneOnInnerTable(action, lanes, 2);
							for (int i = 1; i <= 2; i++) {
								if (die.retrieveMoves(i) != 0 && action.fromNum() == maxLane
										&& action.fromNum() + die.retrieveDieNum(i) > action.toNum() + 23
										|| die.retrieveMoves(i) != 0 && action.fromNum()
												+ die.retrieveDieNum(i) == action.toNum() + 23) {
									isPossible = true;
									if (diceIndexToDecrement == -1
											|| die.retrieveDieNum(i) > die.retrieveDieNum(diceIndexToDecrement))
										diceIndexToDecrement = i;
								}
							}
							if (diceIndexToDecrement != -1)
								die.minusMoves(diceIndexToDecrement);
							if (die.retrieveMoves(1) != 0 && die.retrieveMoves(2) != 0
									&& action.fromNum() + die.retrieveDieNum(1) < action.toNum() + 23
									&& action.fromNum() + die.retrieveDieNum(2) < action.toNum() + 23)
								if (action.fromNum() == maxLane
										&& action.fromNum() + die.retrieveDieNum(1)
												+ die.retrieveDieNum(2) > action.toNum() + 23
										&& (lanes.get(action.fromNum() + die.retrieveDieNum(1)).empty()
												|| lanes.get(action.fromNum() + die.retrieveDieNum(2)).empty()
												|| lanes.get(action.fromNum() + die.retrieveDieNum(1)).peek()
														.retrieveCheckerValue() == people[2].retrieveCheckerValue()
												|| lanes.get(action.fromNum() + die.retrieveDieNum(2)).peek()
														.retrieveCheckerValue() == people[2].retrieveCheckerValue())
										&& lane.size() == 1
										&& (isPathClear(action.fromNum(),
												action.fromNum() + die.retrieveDieNum(1))
												|| isPathClear(action.fromNum(),
														action.fromNum() + die.retrieveDieNum(2)))
										|| action.fromNum() + die.retrieveDieNum(1)
												+ die.retrieveDieNum(2) == action.toNum() + 23
												&& (lanes.get(action.fromNum() + die.retrieveDieNum(1)).empty()
														|| lanes.get(action.fromNum() + die.retrieveDieNum(2)).empty()
														|| lanes.get(action.fromNum() + die.retrieveDieNum(1)).peek()
																.retrieveCheckerValue() == people[2]
																		.retrieveCheckerValue()
														|| lanes.get(action.fromNum() + die.retrieveDieNum(2)).peek()
																.retrieveCheckerValue() == people[2]
																		.retrieveCheckerValue())) {
									isPossible = true;
									die.minusMoves(1);
									die.minusMoves(2);
								}
						}
					}
					if (die.retrieveDieNum(1) == die.retrieveDieNum(2) && die.retrieveMoves(1) != 0) {
						boolean allConditionsMet = true;
						boolean currentCondition = true;
						boolean shouldBreak = false;
						if (people[0] == people[1]) {
							maxLane = getMaxLaneOnInnerTable(action, lanes, 1);
							if (action.fromNum() == maxLane
									&& action.fromNum() + 1 < action.toNum() + die.retrieveDieNum(1)
									|| action.fromNum() + 1 == action.toNum() + die.retrieveDieNum(1)) {
								isPossible = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1) && !shouldBreak; i++)
								if (action.fromNum() == maxLane
										&& action.fromNum() + 1 < action.toNum() + die.retrieveDieNum(1) * i) {
									for (int j = 1; j <= i - 1
											&& action.fromNum() - die.retrieveDieNum(1) * j >= 0; j++) {
										currentCondition = lanes.get(action.fromNum() - die.retrieveDieNum(1) * j)
												.empty()
												|| lanes.get(action.fromNum() - die.retrieveDieNum(1) * j).peek()
														.retrieveCheckerValue() == people[1].retrieveCheckerValue();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lane.size() == 1 && isPathClear(
											action.fromNum() - die.retrieveDieNum(1) * (i - 1), action.fromNum());
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
									shouldBreak = true;
								} else if (action.fromNum() + 1 == action.toNum() + die.retrieveDieNum(1) * i) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(action.fromNum() - die.retrieveDieNum(1) * j)
												.empty()
												|| lanes.get(action.fromNum() - die.retrieveDieNum(1) * j).peek()
														.retrieveCheckerValue() == people[1].retrieveCheckerValue();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
						}
						if (people[0] == people[2]) {
							maxLane = getMaxLaneOnInnerTable(action, lanes, 2);
							if (action.fromNum() == maxLane
									&& action.fromNum() + die.retrieveDieNum(1) > action.toNum() + 23
									|| action.fromNum() + die.retrieveDieNum(1) == action.toNum() + 23) {
								isPossible = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1) && !shouldBreak; i++)
								if (action.fromNum() == maxLane
										&& action.fromNum() + die.retrieveDieNum(1) * i > action.toNum() + 23) {
									for (int j = 1; j <= i - 1
											&& action.fromNum() + die.retrieveDieNum(1) * j <= 23; j++) {
										currentCondition = lanes.get(action.fromNum() + die.retrieveDieNum(1) * j)
												.empty()
												|| lanes.get(action.fromNum() + die.retrieveDieNum(1) * j).peek()
														.retrieveCheckerValue() == people[2].retrieveCheckerValue();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lane.size() == 1 && isPathClear(action.fromNum(),
											action.fromNum() + die.retrieveDieNum(1) * (i - 1));
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
									shouldBreak = true;
								} else if (action.fromNum() + die.retrieveDieNum(1) * i == action.toNum() + 23) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(action.fromNum() + die.retrieveDieNum(1) * j)
												.empty()
												|| lanes.get(action.fromNum() + die.retrieveDieNum(1) * j).peek()
														.retrieveCheckerValue() == people[2].retrieveCheckerValue();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
						}
					}
				}
		} else if (action.isMoveFromLane() && action.isMoveToLane()) {
			Stack<Checker> fromLane = lanes.get(action.fromNum());
			Stack<Checker> toLane = lanes.get(action.toNum());
			if (!fromLane.empty())
				if (bars.get(getPlayerNumber()).empty()
						&& fromLane.peek().retrieveCheckerValue() == people[0].retrieveCheckerValue()
						&& (toLane.empty() || toLane.size() == 1
								|| fromLane.peek().retrieveCheckerValue() == toLane.peek().retrieveCheckerValue())
						&& die.retrieveNumMoves() != 0) {
					if (die.retrieveDieNum(1) != die.retrieveDieNum(2)) {
						for (int i = 1; i <= 2; i++)
							if (die.retrieveMoves(i) != 0 && (people[0] == people[1]
									&& action.fromNum() == action.toNum() + die.retrieveDieNum(i)
									|| people[0] == people[2]
											&& action.fromNum() + die.retrieveDieNum(i) == action.toNum())) {
								isPossible = true;
								die.minusMoves(i);
							}
						if (die.retrieveMoves(1) != 0 && die.retrieveMoves(2) != 0
								&& (people[0] == people[1]
										&& action.fromNum() == action.toNum() + die.retrieveDieNum(1)
												+ die.retrieveDieNum(2)
										&& (lanes.get(action.fromNum() - die.retrieveDieNum(1)).empty()
												|| lanes.get(action.fromNum() - die.retrieveDieNum(2)).empty()
												|| lanes.get(action.fromNum() - die.retrieveDieNum(1)).peek()
														.retrieveCheckerValue() == people[1].retrieveCheckerValue()
												|| lanes.get(action.fromNum() - die.retrieveDieNum(2)).peek()
														.retrieveCheckerValue() == people[1].retrieveCheckerValue())
										|| people[0] == people[2]
												&& action.fromNum() + die.retrieveDieNum(1)
														+ die.retrieveDieNum(2) == action
																.toNum()
												&& (lanes.get(action.fromNum() + die.retrieveDieNum(1)).empty()
														|| lanes.get(action.fromNum() + die.retrieveDieNum(2)).empty()
														|| lanes.get(action.fromNum() + die.retrieveDieNum(1)).peek()
																.retrieveCheckerValue() == people[2]
																		.retrieveCheckerValue()
														|| lanes.get(action.fromNum() + die.retrieveDieNum(2)).peek()
																.retrieveCheckerValue() == people[2]
																		.retrieveCheckerValue()))) {
							isPossible = true;
							die.minusMoves(1);
							die.minusMoves(2);
						}
					}
					if (die.retrieveDieNum(1) == die.retrieveDieNum(2) && die.retrieveMoves(1) != 0) {
						boolean allConditionsMet = true;
						boolean currentCondition = true;
						if (people[0] == people[1]) {
							if (action.fromNum() == action.toNum() + die.retrieveDieNum(1)
									&& (lanes.get(action.fromNum() - die.retrieveDieNum(1)).empty()
											|| lanes.get(action.fromNum() - die.retrieveDieNum(1)).size() == 1
											|| lanes.get(action.fromNum() - die.retrieveDieNum(1)).peek()
													.retrieveCheckerValue() == people[1].retrieveCheckerValue())) {
								isPossible = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1); i++) {
								if (action.fromNum() == action.toNum() + die.retrieveDieNum(1) * i) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(action.fromNum() - die.retrieveDieNum(1) * j)
												.empty()
												|| lanes.get(action.fromNum() - die.retrieveDieNum(1) * j).peek()
														.retrieveCheckerValue() == people[1].retrieveCheckerValue();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lanes.get(action.fromNum() - die.retrieveDieNum(1) * i).empty()
											|| lanes.get(action.fromNum() - die.retrieveDieNum(1) * i).peek()
													.retrieveCheckerValue() == people[1].retrieveCheckerValue()
											|| lanes.get(action.fromNum() - die.retrieveDieNum(1) * i).size() == 1;
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
							}
						}
						if (people[0] == people[2]) {
							if (action.fromNum() + die.retrieveDieNum(1) == action.toNum()
									&& (lanes.get(action.fromNum() + die.retrieveDieNum(1)).empty()
											|| lanes.get(action.fromNum() + die.retrieveDieNum(1)).size() == 1
											|| lanes.get(action.fromNum() + die.retrieveDieNum(1)).peek()
													.retrieveCheckerValue() == people[2].retrieveCheckerValue())) {
								isPossible = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1); i++) {
								if (action.fromNum() + die.retrieveDieNum(1) * i == action.toNum()) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(action.fromNum() + die.retrieveDieNum(1) * j)
												.empty()
												|| lanes.get(action.fromNum() + die.retrieveDieNum(1) * j).peek()
														.retrieveCheckerValue() == people[2].retrieveCheckerValue();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lanes.get(action.fromNum() + die.retrieveDieNum(1) * i).empty()
											|| lanes.get(action.fromNum() + die.retrieveDieNum(1) * i).peek()
													.retrieveCheckerValue() == people[2].retrieveCheckerValue()
											|| lanes.get(action.fromNum() + die.retrieveDieNum(1) * i).size() == 1;
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
							}
						}
					}
				}
		}
		return isPossible;
	}

	public void move(Actions action) { // Move a piece on the board according to the given action
		if (action.comeFromBar() && action.isMoveToLane()) {
			Stack<Checker> bar = bars.get(action.fromNum());
			Stack<Checker> lane = lanes.get(action.toNum());
			if (lane.size() == 1 && bar.peek().retrieveCheckerValue() != lane.peek().retrieveCheckerValue()) {
				Checker barPiece = bar.pop();
				if (lane.peek().retrieveCheckerValue() == CheckerValue.G) {
					Checker lanePiece = lane.pop();
					bars.get(1).push(lanePiece);
				} else if (lane.peek().retrieveCheckerValue() == CheckerValue.B) {
					Checker lanePiece = lane.pop();
					bars.get(0).push(lanePiece);
				}
				lane.push(barPiece);
			} else {
				Checker barPiece = bar.pop();
				lane.push(barPiece);
			}
		} else if (action.isMoveFromLane() && action.isMoveToEndpoint()) {
			Stack<Checker> lane = lanes.get(action.fromNum());
			Stack<Checker> endpoint = endpoints.get(action.toNum());
			Checker lanePiece = lane.pop();
			endpoint.push(lanePiece);
		} else if (action.isMoveFromLane() && action.isMoveToLane()) {
			Stack<Checker> fromLane = lanes.get(action.fromNum());
			Stack<Checker> toLane = lanes.get(action.toNum());
			if (toLane.size() == 1 && fromLane.peek().retrieveCheckerValue() != toLane.peek().retrieveCheckerValue()) {
				Checker fromLanePiece = fromLane.pop();
				if (toLane.peek().retrieveCheckerValue() == CheckerValue.G) {
					Checker toLanePiece = toLane.pop();
					bars.get(1).push(toLanePiece);
				} else if (toLane.peek().retrieveCheckerValue() == CheckerValue.B) {
					Checker toLanePiece = toLane.pop();
					bars.get(0).push(toLanePiece);
				}
				toLane.push(fromLanePiece);
			} else {
				Checker fromLanePiece = fromLane.pop();
				toLane.push(fromLanePiece);
			}
		}
	}

	public void setDiceFace(Actions action) { // Set the die face according to the given action
		die.setFace(action.retrieveDieNum(1), action.retrieveDieNum(2));
	}

	public int getDiceFace(int index) { // Get the die face for the given index
		return switch (index) {
			case 1 -> die.retrieveDieNum(1);
			case 2 -> die.retrieveDieNum(2);
			default -> 0;
		};
	}

	public boolean isOneMatchOver() { // Check if one match in the game is over
		for (Stack<Checker> endpoint : endpoints)
			if (endpoint.size() == 15)
				return true;
		return false;
	}

	public boolean isWholeMatchOver() { // Check if the whole match in the game is over
		return matchNumber + 1 == matchRoundNumber;
	}

	public int getSize(String index) { // Get the size of the largest stack of pieces for the specified index (upLane
										// or downLane)
		int upLaneSize = 0;
		int downLaneSize = 0;
		List<Stack<Checker>> up12Lanes = lanes.subList(0, 12);
		List<Stack<Checker>> down12Lanes = lanes.subList(12, 24);
		for (Stack<Checker> lane : up12Lanes)
			if (lane.size() > upLaneSize)
				upLaneSize = lane.size();
		if (bars.get(1).size() > upLaneSize)
			upLaneSize = bars.get(1).size();
		for (Stack<Checker> lane : down12Lanes)
			if (lane.size() > downLaneSize)
				downLaneSize = lane.size();
		if (bars.get(0).size() > downLaneSize)
			downLaneSize = bars.get(0).size();
		return switch (index) {
			case "upLane" -> upLaneSize;
			case "downLane" -> downLaneSize;
			default -> 0;
		};
	}

	public void calculateSetPips() { // Calculate the number of pips for each player
		int pip1 = 0;
		int pip2 = 0;
		for (int i = 0; i < 24; i++) {
			if (!lanes.get(i).empty())
				if (lanes.get(i).peek().retrieveCheckerValue() == CheckerValue.B) {
					pip1 += (i + 1) * lanes.get(i).size();
				} else if (lanes.get(i).peek().retrieveCheckerValue() == CheckerValue.G)
					pip2 += (24 - i) * lanes.get(i).size();
			if (endpoints.get(0).size() == 15)
				pip1 = 0;
			if (endpoints.get(1).size() == 15)
				pip2 = 0;
			people[1].setPips(pip1);
			people[2].setPips(pip2);
		}
	}

	public void makeDiceRoll() { // Roll the die
		die.throwDie();
	}

	public int getDiceMoveNumber() { // Get the total distance number that pieces can be moved
		return die.retrieveNumMoves();
	}

	public void makeDiceSetZero() { // Set the die values to zero
		die.Nothing();
	}

	public Person getPlayer(int index) { // Get the player object for the given index
		return switch (index) {
			case 0 -> people[0];
			case 1 -> people[1];
			case 2 -> people[2];
			default -> people[0];
		};
	}

	public void setCurrentPlayer(int playerIndex) { // Set the current player using the given player index
		people[0] = people[playerIndex];
	}

	public Stack<Checker> getLane(int index) { // Get the lane stack at the specified index
		return lanes.get(index);
	}

	public Stack<Checker> getBar(int index) { // Get the bar stack at the specified index
		return bars.get(index);
	}

	public Stack<Checker> getEndpoint(int index) { // Get the endpoint stack at the specified index
		return endpoints.get(index);
	}

	public int getMatchNumber() { // Get the current match number
		return matchNumber;
	}

	public void setMatchNumber(int matchNumber) { // Set the match number
		this.matchNumber = matchNumber;
	}

	public int getMatchRoundNumber() { // Get the match round number
		return matchRoundNumber;
	}

	public void setMatchRoundNumber(int matchRoundNumber) { // Set the match round number
		this.matchRoundNumber = matchRoundNumber;
	}

	public void addMatchRoundNumber() { // Increment the match round number
		matchRoundNumber++;
	}

	public void setPlayersScoreToZero() { // Set both people' scores to zero
		people[1].makePoint(0);
		people[2].makePoint(0);
	}

	public void addCurrentPlayerScore() { // Add score to the current player
		people[0].increasePoint(10);
	}

	public int getDiceMoveStep(int index) { // Get the move step of the die for the given index
		return switch (index) {
			case 1 -> die.retrieveMoves(1);
			case 2 -> die.retrieveMoves(2);
			default -> 0;
		};
	}

	public void setDiceMoveStep(int moveStep1, int moveStep2) { // Set the move steps by the given number of die
		die.setMoveStep(moveStep1, moveStep2);
	}

	public void setPlayer(int index, Person player) { // Only for Test. Set the player object at the given index.
		if (index >= 0 && index < people.length) {
			people[index] = player;
		}
	}

	public int getPieceCountAtLane(int laneIndex) { // Get the number of pieces at the specified lane index
		return lanes.get(laneIndex).size();
	}

	@Override
	public boolean equals(Object o) { // Only for Test. Check if two board objects are equal.
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Board board = (Board) o;
		boolean lanesEqual = true;
		boolean barsEqual = true;
		boolean endpointsEqual = true;
		for (int i = 0; i < 24; i++) {
			if (!this.getLane(i).equals(board.getLane(i))) {
				lanesEqual = false;
				break;
			}
		}
		for (int i = 0; i < 2; i++) {
			if (!this.getBar(i).equals(board.getBar(i))) {
				barsEqual = false;
				break;
			}
		}
		for (int i = 0; i < 2; i++) {
			if (!this.getEndpoint(i).equals(board.getEndpoint(i))) {
				endpointsEqual = false;
				break;
			}
		}
		return lanesEqual && barsEqual && endpointsEqual;
	}
}
