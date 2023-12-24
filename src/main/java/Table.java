import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.io.InputStream;
import java.util.ArrayList;


public class Table
{
	public static final int count_bars = CheckerValue.values().length;
	public static final int count_lanes = 24;
	public static final int count_term = CheckerValue.values().length;
	
	private List<Stack<Checker>> laner;
	private List<Stack<Checker>> barz;
	private List<Stack<Checker>> end_of_point;
	
	private Person[] people;
	private Show show;
	private Scanner in;
	private Die die;
	private int num_mathc;
	private int round_num = 1;
	
	Table() 
	{ 
		show = new Show();
		in = new Scanner(System.in);
		die = new Die();
		this.people = new Person[3];
		
		laner = new ArrayList<>(count_lanes);
		barz = new ArrayList<>(count_bars);
		end_of_point = new ArrayList<>(count_term);
		
		for (int i=0; i<count_lanes; i++)
			laner.add(new Stack<>());
		for (int i=0; i<count_bars; i++)
			barz.add(new Stack<>());
		for (int i=0; i<count_term; i++)
			end_of_point.add(new Stack<>());
	}
	
	private int retrieveMaxLaneInner (Actions actions, List<Stack<Checker>> lanes, int playerIndex)
	{
		int maxLane = -1;
		if (playerIndex == 1) {
			for (int i = 0; i <= 5; i++) {
	            Stack<Checker> lanei = lanes.get(i);
	            if (!lanei.isEmpty())
	                maxLane = i;
	        }
			if (actions.fromNum() >= 6)
            	maxLane = actions.fromNum();
		} else if (playerIndex == 2) {
			for (int i = 23; i >= 18; i--) {
	            Stack<Checker> lanei = lanes.get(i);
	            if (!lanei.isEmpty())
	                maxLane = i;
	        }
			if (actions.fromNum() <= 17)
            	maxLane = actions.fromNum();
		}
		return maxLane;
	}
	
	private boolean canMoveThere (int start, int end) 
	{
		if (start < 0)
	        start = 0;
		if (end > 23)
			end = 23;
	    for (int i = start + 1; i < end; i++) {
	        if (!laner.get(i).empty() && laner.get(i).peek().retrieveCheckerValue() == people[0].retrieveCheckerValue()) {
	            return false;
	        }
	    }
	    return true;
	}
	
	private int retrievePersonNum () { 
    	if (people[0].retrieveCheckerValue() == CheckerValue.B) {
    		return 1;
    	} else 
    		return 0;
    }
	
	
	
	public Table(InputStream inputStream) { 
        this.in = new Scanner(inputStream);
        this.people = new Person[3];
    }
	
	public void startUpPlayer (int playerIndex)
	{
		String playerName = in.nextLine();
		if (Actions.letsTest(playerName))
			playerName = show.read_file(playerName, in, "Please enter a new player name: ");
		if (playerIndex == 1) {
			people[playerIndex] = new Person(playerName, CheckerValue.G);
		} else if (playerIndex == 2)
			people[playerIndex] = new Person(playerName, CheckerValue.B);
	}
	
	public void turnOver () 
	{
		if (!MatchFinito()) {
	        if (people[0] == people[1]) {
	            people[0] = people[2];
	            show.show_turn(people[1]);
	            show.show_next_turn(people[2]);
	        } else if (people[0] == people[2]) {
	        	people[0] = people[1];
	        	show.show_turn(people[2]);
	            show.show_next_turn(people[1]);
	        }
		} else if (MatchFinito()) {
			if (people[0] == people[1]) {
				show.show_turn(people[1]);
			} else if (people[0] == people[2])
				show.show_turn(people[2]);
		}
    }
	
	public void makeBoard () 
	{
		for (int i = 0; i < 24; i++)
	        laner.get(i).clear();
	    for (int i = 0; i < 2; i++)
	    	barz.get(i).clear();
	    for (int i = 0; i < 2; i++)
	    	end_of_point.get(i).clear();
		for (int i = 0; i < 2; i++) {
			laner.get(0).push(new Checker(CheckerValue.B));
			laner.get(23).push(new Checker(CheckerValue.G));
		}
		for (int i = 0; i < 3; i++) {
			laner.get(16).push(new Checker(CheckerValue.B));
			laner.get(7).push(new Checker(CheckerValue.G));
		}
		for (int i = 0; i < 5; i++) {
			laner.get(11).push(new Checker(CheckerValue.B));
			laner.get(18).push(new Checker(CheckerValue.B));
			laner.get(5).push(new Checker(CheckerValue.G));
			laner.get(12).push(new Checker(CheckerValue.G));
		}
	}
	
	public boolean movementPossible (Actions actions) 
	{ 
		boolean canDo = false;
		if (actions.comeFromBar() && actions.goLane()) {
			Stack<Checker> bar = barz.get(actions.fromNum());
			Stack<Checker> lane = laner.get(actions.toNum());
			if (!bar.empty())
				if (bar.peek().retrieveCheckerValue() == people[0].retrieveCheckerValue() && (lane.empty() || lane.size() == 1 || bar.peek().retrieveCheckerValue() == lane.peek().retrieveCheckerValue()) && die.retrieveNumMoves() != 0) {
					if (die.retrieveDieNum(1) != die.retrieveDieNum(2)) {
						for (int i = 1; i <= 2; i++)
							if (die.retrieveMoves(i) != 0 && (people[0] == people[1] && actions.fromNum() + 24 == actions.toNum() + die.retrieveDieNum(i) || people[0] == people[2] && actions.fromNum() + die.retrieveDieNum(i) == actions.toNum() + 2)) {
								canDo = true;
								die.minusMoves(i);
							}
						if (die.retrieveMoves(1) != 0 && die.retrieveMoves(2) != 0 && bar.size() == 1
						&& (people[0] == people[1] && actions.fromNum() + 24 == actions.toNum() + die.retrieveDieNum(1) + die.retrieveDieNum(2) && (laner.get(24 - die.retrieveDieNum(1)).empty() || laner.get(24 - die.retrieveDieNum(2)).empty() || laner.get(24 - die.retrieveDieNum(1)).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue() || laner.get(24 - die.retrieveDieNum(2)).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue())
						|| people[0] == people[2] && actions.fromNum() + die.retrieveDieNum(1) + die.retrieveDieNum(2) == actions.toNum() + 2 && (laner.get(die.retrieveDieNum(1) - 1).empty() || laner.get(die.retrieveDieNum(2) - 1).empty() || laner.get(die.retrieveDieNum(1) - 1).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue() || laner.get(die.retrieveDieNum(2) - 1).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue()))) {
							canDo = true;
							die.minusMoves(1);
							die.minusMoves(2);
						}
					}
					if (die.retrieveDieNum(1) == die.retrieveDieNum(2) && die.retrieveMoves(1) != 0) {
					boolean allConditionsMet = true;
					boolean currentCondition = true;
						if (people[0] == people[1]) {
							if (actions.fromNum() + 24 == actions.toNum() + die.retrieveDieNum(1) && (laner.get(24 - die.retrieveDieNum(1)).empty() || laner.get(24 - die.retrieveDieNum(1)).size() == 1 || laner.get(24 - die.retrieveDieNum(1)).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue())) {
								canDo = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1); i++)
								if (actions.fromNum() + 24 == actions.toNum() + die.retrieveDieNum(1) * i && bar.size() == 1) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = laner.get(24 - die.retrieveDieNum(1) * j).empty() || laner.get(24 - die.retrieveDieNum(1) * j).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = laner.get(24 - die.retrieveDieNum(1) * i).empty() || laner.get(24 - die.retrieveDieNum(1) * i).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue() || laner.get(24 - die.retrieveDieNum(1) * i).size() == 1;
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										canDo = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
						}
						if (people[0] == people[2]) {
							if (actions.fromNum() + die.retrieveDieNum(1) == actions.toNum() + 2 && (laner.get(die.retrieveDieNum(1) - 1).empty() || laner.get(die.retrieveDieNum(1) - 1).size() == 1 || laner.get(die.retrieveDieNum(1) - 1).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue())) {
								canDo = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1); i++)
								if (actions.fromNum() + die.retrieveDieNum(1) * i == actions.toNum() + 2 && bar.size() == 1) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = laner.get(die.retrieveDieNum(1) * j - 1).empty() || laner.get(die.retrieveDieNum(1) * j - 1).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = laner.get(die.retrieveDieNum(1) * i - 1).empty() || laner.get(die.retrieveDieNum(1) * i - 1).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue() || laner.get(die.retrieveDieNum(1) * i - 1).size() == 1;
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										canDo = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
						}
					}
				}
		} else if (actions.comeFromLane() && actions.goFinal())
		{
			Stack<Checker> endpoint = end_of_point.get(actions.toNum());
			Stack<Checker> lane = laner.get(actions.fromNum());
			int finalStage = endpoint.size();
			for (int i = 0; i < 6; i++) {
				if (!laner.get(i).empty())
					if (people[0] == people[1] && laner.get(i).peek().retrieveCheckerValue() == people[0].retrieveCheckerValue()) {
						finalStage += laner.get(i).size();
						if (actions.fromNum() >= 6)
							finalStage += 1;
					}
				if (!laner.get(i+18).empty())
					if (people[0] == people[2] && laner.get(i+18).peek().retrieveCheckerValue() == people[0].retrieveCheckerValue()) {
						finalStage += laner.get(i+18).size();
						if (actions.fromNum() <= 17)
							finalStage += 1;
					}
			}
			if (!lane.empty())
				if (retrievePersonNum() == actions.toNum() && lane.peek().retrieveCheckerValue() == people[0].retrieveCheckerValue() && finalStage == 15 && die.retrieveNumMoves() != 0) {
					int maxLane = -1;
					if (die.retrieveDieNum(1) != die.retrieveDieNum(2)) {
						int Incrementation_Die = -1;
						if (people[0] == people[1]) {
							maxLane = retrieveMaxLaneInner(actions, laner, 1);
							for (int i = 1; i <= 2; i++) {
								if (die.retrieveMoves(i) != 0 && actions.fromNum() == maxLane && actions.fromNum() + 1 < actions.toNum() + die.retrieveDieNum(i) || die.retrieveMoves(i) != 0 && actions.fromNum() + 1 == actions.toNum() + die.retrieveDieNum(i)) {
									canDo = true;
									if (Incrementation_Die == -1 || die.retrieveDieNum(i) > die.retrieveDieNum(Incrementation_Die))
							            Incrementation_Die = i;
								}
							}
							if (Incrementation_Die != -1)
							    die.minusMoves(Incrementation_Die);
							if (die.retrieveMoves(1) != 0 && die.retrieveMoves(2) != 0 && actions.fromNum() + 1 > actions.toNum() + die.retrieveDieNum(1) && actions.fromNum() + 1 > actions.toNum() + die.retrieveDieNum(2))
								if (actions.fromNum() == maxLane && actions.fromNum() + 1 < actions.toNum() + die.retrieveDieNum(1) + die.retrieveDieNum(2)
								&& (laner.get(actions.fromNum() - die.retrieveDieNum(1)).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(2)).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(1)).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue() || laner.get(actions.fromNum() - die.retrieveDieNum(2)).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue())
								&& lane.size() == 1 && (canMoveThere(actions.fromNum() - die.retrieveDieNum(1), actions.fromNum()) || canMoveThere(actions.fromNum() - die.retrieveDieNum(2), actions.fromNum()))
								|| actions.fromNum() + 1 == actions.toNum() + die.retrieveDieNum(1) + die.retrieveDieNum(2)
								&& (laner.get(actions.fromNum() - die.retrieveDieNum(1)).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(2)).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(1)).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue() || laner.get(actions.fromNum() - die.retrieveDieNum(2)).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue())) {
									canDo = true;
									die.minusMoves(1);
									die.minusMoves(2);
								}
						}
						if (people[0] == people[2]) {
							maxLane = retrieveMaxLaneInner(actions, laner, 2);
							for (int i = 1; i <= 2; i++) {
								if (die.retrieveMoves(i) != 0 && actions.fromNum() == maxLane && actions.fromNum() + die.retrieveDieNum(i) > actions.toNum() + 23 || die.retrieveMoves(i) != 0 && actions.fromNum() + die.retrieveDieNum(i) == actions.toNum() + 23) {
									canDo = true;
									if (Incrementation_Die == -1 || die.retrieveDieNum(i) > die.retrieveDieNum(Incrementation_Die))
							            Incrementation_Die = i;
								}
							}
							if (Incrementation_Die != -1)
							    die.minusMoves(Incrementation_Die);
							if (die.retrieveMoves(1) != 0 && die.retrieveMoves(2) != 0 && actions.fromNum() + die.retrieveDieNum(1) < actions.toNum() + 23 && actions.fromNum() + die.retrieveDieNum(2) < actions.toNum() + 23)
								if (actions.fromNum() == maxLane && actions.fromNum() + die.retrieveDieNum(1) + die.retrieveDieNum(2) > actions.toNum() + 23
								&& (laner.get(actions.fromNum() + die.retrieveDieNum(1)).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(2)).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(1)).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue() || laner.get(actions.fromNum() + die.retrieveDieNum(2)).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue())
								&& lane.size() == 1 && (canMoveThere(actions.fromNum(), actions.fromNum() + die.retrieveDieNum(1)) || canMoveThere(actions.fromNum(), actions.fromNum() + die.retrieveDieNum(2)))
								|| actions.fromNum() + die.retrieveDieNum(1) + die.retrieveDieNum(2) == actions.toNum() + 23
								&& (laner.get(actions.fromNum() + die.retrieveDieNum(1)).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(2)).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(1)).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue() || laner.get(actions.fromNum() + die.retrieveDieNum(2)).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue())) {
									canDo = true;
									die.minusMoves(1);
									die.minusMoves(2);
								}
						}
					}
					if (die.retrieveDieNum(1) == die.retrieveDieNum(2) && die.retrieveMoves(1) != 0) {
						boolean StatesAllGood = true;
						boolean breakable = false;
						boolean State_currently = true;
						if (people[0] == people[1]) {
							maxLane = retrieveMaxLaneInner(actions, laner, 1);
							if (actions.fromNum() == maxLane && actions.fromNum() + 1 < actions.toNum() + die.retrieveDieNum(1) || actions.fromNum() + 1 == actions.toNum() + die.retrieveDieNum(1)) {
								canDo = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1) && !breakable; i++)
								if (actions.fromNum() == maxLane && actions.fromNum() + 1 < actions.toNum() + die.retrieveDieNum(1) * i) {
									for (int j = 1; j <= i - 1 && actions.fromNum() - die.retrieveDieNum(1) * j >= 0; j++) {
										State_currently = laner.get(actions.fromNum() - die.retrieveDieNum(1) * j).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(1) * j).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue();
										StatesAllGood = StatesAllGood && State_currently;
									}
									State_currently = lane.size() == 1 && canMoveThere(actions.fromNum() - die.retrieveDieNum(1) * (i - 1), actions.fromNum());
									StatesAllGood = StatesAllGood && State_currently;
									if (StatesAllGood) {
										canDo = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
									breakable = true;
								} else if (actions.fromNum() + 1 == actions.toNum() + die.retrieveDieNum(1) * i) {
									for (int j = 1; j <= i - 1; j++) {
										State_currently = laner.get(actions.fromNum() - die.retrieveDieNum(1) * j).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(1) * j).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue();
										StatesAllGood = StatesAllGood && State_currently;
									}
									if (StatesAllGood) {
										canDo = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
						}
						if (people[0] == people[2]) {
							maxLane = retrieveMaxLaneInner(actions, laner, 2);
							if (actions.fromNum() == maxLane && actions.fromNum() + die.retrieveDieNum(1) > actions.toNum() + 23 || actions.fromNum() + die.retrieveDieNum(1) == actions.toNum() + 23) {
								canDo = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1) && !breakable; i++)
								if (actions.fromNum() == maxLane && actions.fromNum() + die.retrieveDieNum(1) * i > actions.toNum() + 23) {
									for (int j = 1; j <= i - 1 && actions.fromNum() + die.retrieveDieNum(1) * j <= 23; j++) {
										State_currently = laner.get(actions.fromNum() + die.retrieveDieNum(1) * j).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(1) * j).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue();
										StatesAllGood = StatesAllGood && State_currently;
									}
									State_currently = lane.size() == 1 && canMoveThere(actions.fromNum(), actions.fromNum() + die.retrieveDieNum(1) * (i - 1));
									StatesAllGood = StatesAllGood && State_currently;
									if (StatesAllGood) {
										canDo = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
									breakable = true;
								} else if (actions.fromNum() + die.retrieveDieNum(1) * i == actions.toNum() + 23) {
									for (int j = 1; j <= i - 1; j++) {
										State_currently = laner.get(actions.fromNum() + die.retrieveDieNum(1) * j).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(1) * j).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue();
										StatesAllGood = StatesAllGood && State_currently;
									}
									if (StatesAllGood) {
										canDo = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
						}
					}
				}
		} else if (actions.comeFromLane() && actions.goLane())
		{
			Stack<Checker> lane_coming = laner.get(actions.fromNum());
			Stack<Checker> golane = laner.get(actions.toNum());
			if (!lane_coming.empty())
				if (barz.get(retrievePersonNum()).empty() && lane_coming.peek().retrieveCheckerValue() == people[0].retrieveCheckerValue() && (golane.empty() || golane.size() == 1 || lane_coming.peek().retrieveCheckerValue() == golane.peek().retrieveCheckerValue()) && die.retrieveNumMoves() != 0) {
					if (die.retrieveDieNum(1) != die.retrieveDieNum(2)) {
						for (int i = 1; i <= 2; i++)
							if (die.retrieveMoves(i) != 0 && (people[0] == people[1] && actions.fromNum() == actions.toNum() + die.retrieveDieNum(i) || people[0] == people[2] && actions.fromNum() + die.retrieveDieNum(i) == actions.toNum())) {
								canDo = true;
								die.minusMoves(i);
							}
						if (die.retrieveMoves(1) != 0 && die.retrieveMoves(2) != 0
						&& (people[0] == people[1] && actions.fromNum() == actions.toNum() + die.retrieveDieNum(1) + die.retrieveDieNum(2) && (laner.get(actions.fromNum() - die.retrieveDieNum(1)).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(2)).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(1)).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue() || laner.get(actions.fromNum() - die.retrieveDieNum(2)).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue())
						|| people[0] == people[2] && actions.fromNum() + die.retrieveDieNum(1) + die.retrieveDieNum(2) == actions.toNum() && (laner.get(actions.fromNum() + die.retrieveDieNum(1)).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(2)).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(1)).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue() || laner.get(actions.fromNum() + die.retrieveDieNum(2)).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue()))) {
							canDo = true;
							die.minusMoves(1);
							die.minusMoves(2);
						}
					}
					if (die.retrieveDieNum(1) == die.retrieveDieNum(2) && die.retrieveMoves(1) != 0) {
						boolean GoodStates = true;
						boolean currentState = true;
						if (people[0] == people[1]) {
							if (actions.fromNum() == actions.toNum() + die.retrieveDieNum(1) && (laner.get(actions.fromNum() - die.retrieveDieNum(1)).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(1)).size() == 1 || laner.get(actions.fromNum() - die.retrieveDieNum(1)).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue())) {
								canDo = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1); i++) {
								if (actions.fromNum() == actions.toNum() + die.retrieveDieNum(1) * i) {
									for (int j = 1; j <= i - 1; j++) {
										currentState = laner.get(actions.fromNum() - die.retrieveDieNum(1) * j).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(1) * j).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue();
										GoodStates = GoodStates && currentState;
									}
									currentState = laner.get(actions.fromNum() - die.retrieveDieNum(1) * i).empty() || laner.get(actions.fromNum() - die.retrieveDieNum(1) * i).peek().retrieveCheckerValue() == people[1].retrieveCheckerValue() || laner.get(actions.fromNum() - die.retrieveDieNum(1) * i).size() == 1;
									GoodStates = GoodStates && currentState;
									if (GoodStates) {
										canDo = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
							}
						}
						if (people[0] == people[2]) {
							if (actions.fromNum() + die.retrieveDieNum(1) == actions.toNum() && (laner.get(actions.fromNum() + die.retrieveDieNum(1)).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(1)).size() == 1 || laner.get(actions.fromNum() + die.retrieveDieNum(1)).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue())) {
								canDo = true;
								die.minusMoves(1);
							}
							for (int i = 2; i <= die.retrieveMoves(1); i++) {
								if (actions.fromNum() + die.retrieveDieNum(1) * i == actions.toNum()) {
									for (int j = 1; j <= i - 1; j++) {
										currentState = laner.get(actions.fromNum() + die.retrieveDieNum(1) * j).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(1) * j).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue();
										GoodStates = GoodStates && currentState;
									}
									currentState = laner.get(actions.fromNum() + die.retrieveDieNum(1) * i).empty() || laner.get(actions.fromNum() + die.retrieveDieNum(1) * i).peek().retrieveCheckerValue() == people[2].retrieveCheckerValue() || laner.get(actions.fromNum() + die.retrieveDieNum(1) * i).size() == 1;
									GoodStates = GoodStates && currentState;
									if (GoodStates) {
										canDo = true;
										for (int j = 0; j < i; j++)
											die.minusMoves(1);
									}
								}
							}
						}
					}
				}
		}
		return canDo;
	}
	
	public void move (Actions actions) 
	{
		if (actions.comeFromBar() && actions.goLane()) {
			Stack<Checker> bar = barz.get(actions.fromNum());
			Stack<Checker> lane = laner.get(actions.toNum());
			if (lane.size() == 1 && bar.peek().retrieveCheckerValue() != lane.peek().retrieveCheckerValue()) {
				Checker barPiece = bar.pop();
				if (lane.peek().retrieveCheckerValue() == CheckerValue.B) {
					Checker lanePiece = lane.pop();
					barz.get(1).push(lanePiece);
				} else if (lane.peek().retrieveCheckerValue() == CheckerValue.G) {
					Checker lanePiece = lane.pop();
					barz.get(0).push(lanePiece);
				}
				lane.push(barPiece);
			} else {
				Checker barPiece = bar.pop();
				lane.push(barPiece);
			}
		} else if (actions.comeFromLane() && actions.goFinal()) {
			Stack<Checker> lane = laner.get(actions.fromNum());
			Stack<Checker> endpoint = end_of_point.get(actions.toNum());
			Checker lanePiece = lane.pop();
			endpoint.push(lanePiece);
		} else if (actions.comeFromLane() && actions.goLane()) {
			Stack<Checker> fromLane = laner.get(actions.fromNum());
			Stack<Checker> toLane = laner.get(actions.toNum());
			if (toLane.size() == 1 && fromLane.peek().retrieveCheckerValue() != toLane.peek().retrieveCheckerValue()) {
				Checker fromLanePiece = fromLane.pop();
				if (toLane.peek().retrieveCheckerValue() == CheckerValue.B) {
					Checker toLanePiece = toLane.pop();
					barz.get(1).push(toLanePiece);
				} else if (toLane.peek().retrieveCheckerValue() == CheckerValue.G) {
					Checker toLanePiece = toLane.pop();
					barz.get(0).push(toLanePiece);
				}
				toLane.push(fromLanePiece);
			} else {
				Checker fromLanePiece = fromLane.pop();
				toLane.push(fromLanePiece);
			}
		}
	}
	
	public void makeDieNumber (Actions actions) 
	{
		die.makeDieNum(actions.retrieveDieNum(1), actions.retrieveDieNum(2));
	}
	
	public int RetrieveDiceNumber (int index) 
	{ 
		return switch (index) {
			case 1 -> die.retrieveDieNum(1);
			case 2 -> die.retrieveDieNum(2);
			default -> 0;
		};
	}
	
	public boolean MatchFinito () 
	{
		for (Stack<Checker> endpoint : end_of_point)
			if (endpoint.size() == 15)
				return true;
		return false;
	}
	
	public boolean EntireMatchFinito () 
	{
		return num_mathc + 1 == round_num;
	}
	
	public int getSize (String index) 
	{
		int upLaneSize = 0;
		int downLaneSize = 0;
		List<Stack<Checker>> up12Lanes = laner.subList(0, 12);
		List<Stack<Checker>> down12Lanes = laner.subList(12, 24);
		for (Stack<Checker> lane : up12Lanes)
		    if (lane.size() > upLaneSize)
		    	upLaneSize = lane.size();
		if (barz.get(1).size() > upLaneSize)
	    	upLaneSize = barz.get(1).size();
		for (Stack<Checker> lane : down12Lanes)
		    if (lane.size() > downLaneSize)
		    	downLaneSize = lane.size();
		if (barz.get(0).size() > downLaneSize)
	    	downLaneSize = barz.get(0).size();
		return switch(index) {
			case "upLane" -> upLaneSize;
			case "downLane" -> downLaneSize;
			default -> 0;
		};
	}
	
	public void pip_calc () 
	{
		int pip_1 = 0;
		int pip_2 = 0;
   		for (int i=0; i<24; i++) {
   			if (!laner.get(i).empty())
   				if (laner.get(i).peek().retrieveCheckerValue() == CheckerValue.G) {
   					pip_1 += (i+1)*laner.get(i).size();
   				} else if (laner.get(i).peek().retrieveCheckerValue() == CheckerValue.B)
   					pip_2 += (24-i)*laner.get(i).size();
   			if (end_of_point.get(0).size() == 15)
   				pip_1 = 0;
   			if (end_of_point.get(1).size() == 15)
   				pip_2 = 0;
   			people[1].makePip(pip_1);
   			people[2].makePip(pip_2);
   		}
	}
	
	public void makethrowDie () 
	{
		die.throwDie();
	}
	
	public int retrieveNumMoves () 
	{ 
		return die.retrieveNumMoves();
	}
	
	public void setnothing () 
	{
		die.Nothing();
	}
    
    public Person retrievePerson (int index) 
	{ 
    	return switch (index) {
			case 0 -> people[0];
			case 1 -> people[1];
			case 2 -> people[2];
			default -> people[0];
    	};
    }
    
    public void makePersonCurrently (int playerIndex) 
	{
    	people[0] = people[playerIndex];
    }
	
	public Stack<Checker> retrieveLane (int index) 
	{ 
		return laner.get(index);
	}
	
	public Stack<Checker> retrieveBar (int index) 
	{
		return barz.get(index);
	}
	
	public Stack<Checker> retrieveEnd (int increment) 
	{
		return end_of_point.get(increment);
	}
	
	public int retrieveNumMatch () 
	{
		return num_mathc;
	}
	
	public void makeNum_match (int nummatch) 
	{
		this.num_mathc = nummatch;
	}
	
	public int retirveRoundnumber () 
	{
		return round_num;
	}
	
	public void makeRoundnumber (int matchRoundNumber) 
	{
		this.round_num = matchRoundNumber;
	}
	
	public void Increment_round () 
	{
		round_num++;
	}
	
	public void makeNothingPoint () 
	{
		people[1].makePoint(0);
		people[2].makePoint(0);
	}
	
	public void IncreaseScore () 
	{
		people[0].increasePoint(10);
	}
	
	public int retrieveStepDie (int index) 
	{ 
    	return switch (index) {
			case 1 -> die.retrieveMoves(1);
			case 2 -> die.retrieveMoves(2);
			default -> 0;
		};
    }
	
	public void makeStepDie (int moveStep1, int moveStep2) 
	{
		die.makeMoves(moveStep1, moveStep2);
	}
	
	public void makePerson(int index, Person player) 
	{ 
	    if (index >= 0 && index < people.length) {
	        people[index] = player;
	    }
	}
	
	public int retrieveCheckerNumLane(int lanenum) 
	{ 
	    return laner.get(lanenum).size();
	}
	
	@Override
	public boolean equals(Object o) 
	{ 
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Table table = (Table) o;
	    boolean lanesEqualisation = true;
	    boolean bars_same = true;
	    boolean same_endpoints = true;
	    for (int i = 0; i < 24; i++) {
	        if (!this.retrieveLane(i).equals(table.retrieveLane(i))) {
	            lanesEqualisation = false;
	            break;
	        }
	    }
	    for (int i = 0; i < 2; i++) {
	        if (!this.retrieveBar(i).equals(table.retrieveBar(i))) {
	            bars_same = false;
	            break;
	        }
	    }
	    for (int i = 0; i < 2; i++) {
	        if (!this.retrieveEnd(i).equals(table.retrieveEnd(i))) {
	            same_endpoints = false;
	            break;
	        }
	    }
	    return lanesEqualisation && bars_same && same_endpoints;
	}
}
