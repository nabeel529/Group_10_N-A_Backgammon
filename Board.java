import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Board {

        public static final int NUM_LANES = 24;
        public static final int NUM_BARS = CheckerValue.values().length; // = 2
        public static final int NUM_TERMINUS = CheckerValue.values().length; // = 2

        private List<Stack<Checker>> lanes;
        private List<Stack<Checker>> bars;
        private List<Stack<Checker>> endpoints;

        private Person[] players;
        private View view;
        private Scanner in;
        private Die dice;
        private int matchNumber;
        private int matchRoundNumber = 1;

        Board() { // Constructor: Initializes a new board object by arranging the game board with
                  // displays, inputs, dice, players and the kinds of positions of the pieces.
                view = new View();
                in = new Scanner(System.in);
                dice = new Die();
                this.players = new Person[3]; // players[0] is current player. players[1] is RED player. players[2] is
                                              // WHITE player.

                lanes = new ArrayList<>(NUM_LANES);
                bars = new ArrayList<>(NUM_BARS);
                endpoints = new ArrayList<>(NUM_TERMINUS);

                for (int i = 0; i < NUM_LANES; i++)
                        lanes.add(new Stack<>());
                for (int i = 0; i < NUM_BARS; i++)
                        bars.add(new Stack<>());
                for (int i = 0; i < NUM_TERMINUS; i++)
                        endpoints.add(new Stack<>());
        }

        private int getMaxLaneOnInnerTable(Actions actions, List<Stack<Checker>> lanes, int playerIndex) { // Get the
                                                                                                           // maximum
                                                                                                           // lane on
                                                                                                           // the inner
                                                                                                           // table
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

        private boolean isPathClear(int start, int end) { // Check if the path is clear for a move
                if (start < 0)
                        start = 0;
                if (end > 23)
                        end = 23;
                for (int i = start + 1; i < end; i++) {
                        if (!lanes.get(i).empty() && lanes.get(i).peek().retrieveCheckerValue() == players[0]
                                        .retrieveCheckerValue()) {
                                return false;
                        }
                }
                return true;
        }

        private int getPlayerNumber() { // Get the player number
                if (players[0].retrieveCheckerValue() == CheckerValue.B) {
                        return 1;
                } else // players[0].retrieveCheckerValue() == CheckerValue.G
                        return 0;
        }

        public Board(InputStream inputStream) { // Only for Test.
                this.in = new Scanner(inputStream);
                this.players = new Person[3];
        }

        public void initializePlayer(int playerIndex) { // Initialize a player with the given index
                String playerName = in.nextLine();
                if (Actions.letsTest(playerName))
                        playerName = view.readContentFromFile(playerName, in, "Please enter a new player name: ");
                if (playerIndex == 1) {
                        players[playerIndex] = new Person(playerName, CheckerValue.G);
                } else if (playerIndex == 2)
                        players[playerIndex] = new Person(playerName, CheckerValue.B);
        }

        public void endTurn() { // End the current player's turn
                if (!isOneMatchOver()) {
                        if (players[0] == players[1]) {
                                players[0] = players[2];
                                view.playerTurnCurrent(players[1]);
                                view.playerTurnNext(players[2]);
                        } else if (players[0] == players[2]) {
                                players[0] = players[1];
                                view.playerTurnCurrent(players[2]);
                                view.playerTurnNext(players[1]);
                        }
                } else if (isOneMatchOver()) {
                        if (players[0] == players[1]) {
                                view.playerTurnCurrent(players[1]);
                        } else if (players[0] == players[2])
                                view.playerTurnCurrent(players[2]);
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
                        lanes.get(0).push(new Checker(CheckerValue.B));
                        lanes.get(23).push(new Checker(CheckerValue.G));
                }
                for (int i = 0; i < 3; i++) {
                        lanes.get(16).push(new Checker(CheckerValue.B));
                        lanes.get(7).push(new Checker(CheckerValue.G));
                }
                for (int i = 0; i < 5; i++) {
                        lanes.get(11).push(new Checker(CheckerValue.B));
                        lanes.get(18).push(new Checker(CheckerValue.B));
                        lanes.get(5).push(new Checker(CheckerValue.G));
                        lanes.get(12).push(new Checker(CheckerValue.G));
                }
        }

        public boolean moveIsPossible(Actions actions) { // Check if a move is possible after giving a actions
                boolean isPossible = false;
                if (actions.comeFromBar() && actions.goLane()) {
                        Stack<Checker> bar = bars.get(actions.fromNum());
                        Stack<Checker> lane = lanes.get(actions.toNum());
                        if (!bar.empty())
                                if (bar.peek().retrieveCheckerValue() == players[0].retrieveCheckerValue()
                                                && (lane.empty() || lane.size() == 1
                                                                || bar.peek().retrieveCheckerValue() == lane.peek()
                                                                                .retrieveCheckerValue())
                                                && dice.retrieveNumMoves() != 0) {
                                        if (dice.retrieveDieNum(1) != dice.retrieveDieNum(2)) {
                                                for (int i = 1; i <= 2; i++)
                                                        if (dice.retrieveMoves(i) != 0 && (players[0] == players[1]
                                                                        && actions.fromNum() + 24 == actions.toNum()
                                                                                        + dice.retrieveDieNum(i)
                                                                        || players[0] == players[2] && actions.fromNum()
                                                                                        + dice.retrieveDieNum(
                                                                                                        i) == actions.toNum()
                                                                                                                        + 2)) {
                                                                isPossible = true;
                                                                dice.minusMoves(i);
                                                        }
                                                if (dice.retrieveMoves(1) != 0 && dice.retrieveMoves(2) != 0
                                                                && bar.size() == 1
                                                                && (players[0] == players[1]
                                                                                && actions.fromNum() + 24 == actions
                                                                                                .toNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                + dice.retrieveDieNum(2)
                                                                                && (lanes.get(24 - dice
                                                                                                .retrieveDieNum(1))
                                                                                                .empty()
                                                                                                || lanes.get(24 - dice
                                                                                                                .retrieveDieNum(2))
                                                                                                                .empty()
                                                                                                || lanes.get(24 - dice
                                                                                                                .retrieveDieNum(1))
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                .retrieveCheckerValue()
                                                                                                || lanes.get(24 - dice
                                                                                                                .retrieveDieNum(2))
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                .retrieveCheckerValue())
                                                                                || players[0] == players[2] && actions
                                                                                                .fromNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                + dice.retrieveDieNum(
                                                                                                                2) == actions.toNum()
                                                                                                                                + 2
                                                                                                && (lanes.get(dice
                                                                                                                .retrieveDieNum(1)
                                                                                                                - 1)
                                                                                                                .empty()
                                                                                                                || lanes.get(dice
                                                                                                                                .retrieveDieNum(2)
                                                                                                                                - 1)
                                                                                                                                .empty()
                                                                                                                || lanes.get(dice
                                                                                                                                .retrieveDieNum(1)
                                                                                                                                - 1)
                                                                                                                                .peek()
                                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                                .retrieveCheckerValue()
                                                                                                                || lanes.get(dice
                                                                                                                                .retrieveDieNum(2)
                                                                                                                                - 1)
                                                                                                                                .peek()
                                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                                .retrieveCheckerValue()))) {
                                                        isPossible = true;
                                                        dice.minusMoves(1);
                                                        dice.minusMoves(2);
                                                }
                                        }
                                        if (dice.retrieveDieNum(1) == dice.retrieveDieNum(2)
                                                        && dice.retrieveMoves(1) != 0) {
                                                boolean allConditionsMet = true;
                                                boolean currentCondition = true;
                                                if (players[0] == players[1]) {
                                                        if (actions.fromNum() + 24 == actions.toNum()
                                                                        + dice.retrieveDieNum(1)
                                                                        && (lanes.get(24 - dice.retrieveDieNum(1))
                                                                                        .empty()
                                                                                        || lanes.get(24 - dice
                                                                                                        .retrieveDieNum(1))
                                                                                                        .size() == 1
                                                                                        || lanes.get(24 - dice
                                                                                                        .retrieveDieNum(1))
                                                                                                        .peek()
                                                                                                        .retrieveCheckerValue() == players[1]
                                                                                                                        .retrieveCheckerValue())) {
                                                                isPossible = true;
                                                                dice.minusMoves(1);
                                                        }
                                                        for (int i = 2; i <= dice.retrieveMoves(1); i++)
                                                                if (actions.fromNum() + 24 == actions.toNum()
                                                                                + dice.retrieveDieNum(1) * i
                                                                                && bar.size() == 1) {
                                                                        for (int j = 1; j <= i - 1; j++) {
                                                                                currentCondition = lanes.get(24
                                                                                                - dice.retrieveDieNum(1)
                                                                                                                * j)
                                                                                                .empty()
                                                                                                || lanes.get(24 - dice
                                                                                                                .retrieveDieNum(1)
                                                                                                                * j)
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                .retrieveCheckerValue();
                                                                                allConditionsMet = allConditionsMet
                                                                                                && currentCondition;
                                                                        }
                                                                        currentCondition = lanes
                                                                                        .get(24 - dice.retrieveDieNum(1)
                                                                                                        * i)
                                                                                        .empty()
                                                                                        || lanes.get(24 - dice
                                                                                                        .retrieveDieNum(1)
                                                                                                        * i).peek()
                                                                                                        .retrieveCheckerValue() == players[1]
                                                                                                                        .retrieveCheckerValue()
                                                                                        || lanes.get(24 - dice
                                                                                                        .retrieveDieNum(1)
                                                                                                        * i)
                                                                                                        .size() == 1;
                                                                        allConditionsMet = allConditionsMet
                                                                                        && currentCondition;
                                                                        if (allConditionsMet) {
                                                                                isPossible = true;
                                                                                for (int j = 0; j < i; j++)
                                                                                        dice.minusMoves(1);
                                                                        }
                                                                }
                                                }
                                                if (players[0] == players[2]) {
                                                        if (actions.fromNum()
                                                                        + dice.retrieveDieNum(1) == actions.toNum() + 2
                                                                        && (lanes.get(dice.retrieveDieNum(1) - 1)
                                                                                        .empty()
                                                                                        || lanes.get(dice
                                                                                                        .retrieveDieNum(1)
                                                                                                        - 1).size() == 1
                                                                                        || lanes.get(dice
                                                                                                        .retrieveDieNum(1)
                                                                                                        - 1).peek()
                                                                                                        .retrieveCheckerValue() == players[2]
                                                                                                                        .retrieveCheckerValue())) {
                                                                isPossible = true;
                                                                dice.minusMoves(1);
                                                        }
                                                        for (int i = 2; i <= dice.retrieveMoves(1); i++)
                                                                if (actions.fromNum()
                                                                                + dice.retrieveDieNum(1) * i == actions
                                                                                                .toNum() + 2
                                                                                && bar.size() == 1) {
                                                                        for (int j = 1; j <= i - 1; j++) {
                                                                                currentCondition = lanes.get(
                                                                                                dice.retrieveDieNum(1)
                                                                                                                * j - 1)
                                                                                                .empty()
                                                                                                || lanes.get(dice
                                                                                                                .retrieveDieNum(1)
                                                                                                                * j - 1)
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                .retrieveCheckerValue();
                                                                                allConditionsMet = allConditionsMet
                                                                                                && currentCondition;
                                                                        }
                                                                        currentCondition = lanes
                                                                                        .get(dice.retrieveDieNum(1) * i
                                                                                                        - 1)
                                                                                        .empty()
                                                                                        || lanes.get(dice
                                                                                                        .retrieveDieNum(1)
                                                                                                        * i - 1).peek()
                                                                                                        .retrieveCheckerValue() == players[2]
                                                                                                                        .retrieveCheckerValue()
                                                                                        || lanes.get(dice
                                                                                                        .retrieveDieNum(1)
                                                                                                        * i - 1)
                                                                                                        .size() == 1;
                                                                        allConditionsMet = allConditionsMet
                                                                                        && currentCondition;
                                                                        if (allConditionsMet) {
                                                                                isPossible = true;
                                                                                for (int j = 0; j < i; j++)
                                                                                        dice.minusMoves(1);
                                                                        }
                                                                }
                                                }
                                        }
                                }
                } else if (actions.comeFromLane() && actions.goFinal()) {
                        Stack<Checker> lane = lanes.get(actions.fromNum());
                        Stack<Checker> endpoint = endpoints.get(actions.toNum());
                        int finalStage = endpoint.size();
                        for (int i = 0; i < 6; i++) {
                                if (!lanes.get(i).empty())
                                        if (players[0] == players[1] && lanes.get(i).peek()
                                                        .retrieveCheckerValue() == players[0].retrieveCheckerValue()) {
                                                finalStage += lanes.get(i).size();
                                                if (actions.fromNum() >= 6)
                                                        finalStage += 1;
                                        }
                                if (!lanes.get(i + 18).empty())
                                        if (players[0] == players[2] && lanes.get(i + 18).peek()
                                                        .retrieveCheckerValue() == players[0].retrieveCheckerValue()) {
                                                finalStage += lanes.get(i + 18).size();
                                                if (actions.fromNum() <= 17)
                                                        finalStage += 1;
                                        }
                        }
                        if (!lane.empty())
                                if (getPlayerNumber() == actions.toNum()
                                                && lane.peek().retrieveCheckerValue() == players[0]
                                                                .retrieveCheckerValue()
                                                && finalStage == 15 && dice.retrieveNumMoves() != 0) {
                                        int maxLane = -1;
                                        if (dice.retrieveDieNum(1) != dice.retrieveDieNum(2)) {
                                                int diceIndexToDecrement = -1;
                                                if (players[0] == players[1]) {
                                                        maxLane = getMaxLaneOnInnerTable(actions, lanes, 1);
                                                        for (int i = 1; i <= 2; i++) {
                                                                if (dice.retrieveMoves(i) != 0
                                                                                && actions.fromNum() == maxLane
                                                                                && actions.fromNum() + 1 < actions
                                                                                                .toNum()
                                                                                                + dice.retrieveDieNum(i)
                                                                                || dice.retrieveMoves(i) != 0 && actions
                                                                                                .fromNum()
                                                                                                + 1 == actions.toNum()
                                                                                                                + dice.retrieveDieNum(
                                                                                                                                i)) {
                                                                        isPossible = true;
                                                                        if (diceIndexToDecrement == -1
                                                                                        || dice.retrieveDieNum(i) > dice
                                                                                                        .retrieveDieNum(diceIndexToDecrement))
                                                                                diceIndexToDecrement = i;
                                                                }
                                                        }
                                                        if (diceIndexToDecrement != -1)
                                                                dice.minusMoves(diceIndexToDecrement);
                                                        if (dice.retrieveMoves(1) != 0 && dice.retrieveMoves(2) != 0
                                                                        && actions.fromNum() + 1 > actions.toNum()
                                                                                        + dice.retrieveDieNum(1)
                                                                        && actions.fromNum() + 1 > actions.toNum()
                                                                                        + dice.retrieveDieNum(2))
                                                                if (actions.fromNum() == maxLane
                                                                                && actions.fromNum() + 1 < actions
                                                                                                .toNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                + dice.retrieveDieNum(2)
                                                                                && (lanes.get(actions.fromNum() - dice
                                                                                                .retrieveDieNum(1))
                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                2))
                                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                1))
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                .retrieveCheckerValue()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                2))
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                .retrieveCheckerValue())
                                                                                && lane.size() == 1
                                                                                && (isPathClear(actions.fromNum() - dice
                                                                                                .retrieveDieNum(1),
                                                                                                actions.fromNum())
                                                                                                || isPathClear(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                2),
                                                                                                                actions.fromNum()))
                                                                                || actions.fromNum() + 1 == actions
                                                                                                .toNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                + dice.retrieveDieNum(2)
                                                                                                && (lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                1))
                                                                                                                .empty()
                                                                                                                || lanes.get(actions
                                                                                                                                .fromNum()
                                                                                                                                - dice.retrieveDieNum(
                                                                                                                                                2))
                                                                                                                                .empty()
                                                                                                                || lanes.get(actions
                                                                                                                                .fromNum()
                                                                                                                                - dice.retrieveDieNum(
                                                                                                                                                1))
                                                                                                                                .peek()
                                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                                .retrieveCheckerValue()
                                                                                                                || lanes.get(actions
                                                                                                                                .fromNum()
                                                                                                                                - dice.retrieveDieNum(
                                                                                                                                                2))
                                                                                                                                .peek()
                                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                                .retrieveCheckerValue())) {
                                                                        isPossible = true;
                                                                        dice.minusMoves(1);
                                                                        dice.minusMoves(2);
                                                                }
                                                }
                                                if (players[0] == players[2]) {
                                                        maxLane = getMaxLaneOnInnerTable(actions, lanes, 2);
                                                        for (int i = 1; i <= 2; i++) {
                                                                if (dice.retrieveMoves(i) != 0
                                                                                && actions.fromNum() == maxLane
                                                                                && actions.fromNum() + dice
                                                                                                .retrieveDieNum(i) > actions
                                                                                                                .toNum()
                                                                                                                + 23
                                                                                || dice.retrieveMoves(i) != 0 && actions
                                                                                                .fromNum()
                                                                                                + dice.retrieveDieNum(
                                                                                                                i) == actions.toNum()
                                                                                                                                + 23) {
                                                                        isPossible = true;
                                                                        if (diceIndexToDecrement == -1
                                                                                        || dice.retrieveDieNum(i) > dice
                                                                                                        .retrieveDieNum(diceIndexToDecrement))
                                                                                diceIndexToDecrement = i;
                                                                }
                                                        }
                                                        if (diceIndexToDecrement != -1)
                                                                dice.minusMoves(diceIndexToDecrement);
                                                        if (dice.retrieveMoves(1) != 0 && dice.retrieveMoves(2) != 0
                                                                        && actions.fromNum() + dice.retrieveDieNum(
                                                                                        1) < actions.toNum() + 23
                                                                        && actions.fromNum() + dice.retrieveDieNum(
                                                                                        2) < actions.toNum() + 23)
                                                                if (actions.fromNum() == maxLane && actions.fromNum()
                                                                                + dice.retrieveDieNum(1)
                                                                                + dice.retrieveDieNum(2) > actions
                                                                                                .toNum() + 23
                                                                                && (lanes.get(actions.fromNum() + dice
                                                                                                .retrieveDieNum(1))
                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                + dice.retrieveDieNum(
                                                                                                                                2))
                                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                + dice.retrieveDieNum(
                                                                                                                                1))
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                .retrieveCheckerValue()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                + dice.retrieveDieNum(
                                                                                                                                2))
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                .retrieveCheckerValue())
                                                                                && lane.size() == 1
                                                                                && (isPathClear(actions.fromNum(),
                                                                                                actions.fromNum() + dice
                                                                                                                .retrieveDieNum(1))
                                                                                                || isPathClear(actions
                                                                                                                .fromNum(),
                                                                                                                actions.fromNum()
                                                                                                                                + dice.retrieveDieNum(
                                                                                                                                                2)))
                                                                                || actions.fromNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                + dice.retrieveDieNum(
                                                                                                                2) == actions.toNum()
                                                                                                                                + 23
                                                                                                && (lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                + dice.retrieveDieNum(
                                                                                                                                1))
                                                                                                                .empty()
                                                                                                                || lanes.get(actions
                                                                                                                                .fromNum()
                                                                                                                                + dice.retrieveDieNum(
                                                                                                                                                2))
                                                                                                                                .empty()
                                                                                                                || lanes.get(actions
                                                                                                                                .fromNum()
                                                                                                                                + dice.retrieveDieNum(
                                                                                                                                                1))
                                                                                                                                .peek()
                                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                                .retrieveCheckerValue()
                                                                                                                || lanes.get(actions
                                                                                                                                .fromNum()
                                                                                                                                + dice.retrieveDieNum(
                                                                                                                                                2))
                                                                                                                                .peek()
                                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                                .retrieveCheckerValue())) {
                                                                        isPossible = true;
                                                                        dice.minusMoves(1);
                                                                        dice.minusMoves(2);
                                                                }
                                                }
                                        }
                                        if (dice.retrieveDieNum(1) == dice.retrieveDieNum(2)
                                                        && dice.retrieveMoves(1) != 0) {
                                                boolean allConditionsMet = true;
                                                boolean currentCondition = true;
                                                boolean shouldBreak = false;
                                                if (players[0] == players[1]) {
                                                        maxLane = getMaxLaneOnInnerTable(actions, lanes, 1);
                                                        if (actions.fromNum() == maxLane
                                                                        && actions.fromNum() + 1 < actions.toNum()
                                                                                        + dice.retrieveDieNum(1)
                                                                        || actions.fromNum() + 1 == actions.toNum()
                                                                                        + dice.retrieveDieNum(1)) {
                                                                isPossible = true;
                                                                dice.minusMoves(1);
                                                        }
                                                        for (int i = 2; i <= dice.retrieveMoves(1) && !shouldBreak; i++)
                                                                if (actions.fromNum() == maxLane && actions.fromNum()
                                                                                + 1 < actions.toNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                                * i) {
                                                                        for (int j = 1; j <= i - 1 && actions.fromNum()
                                                                                        - dice.retrieveDieNum(1)
                                                                                                        * j >= 0; j++) {
                                                                                currentCondition = lanes.get(actions
                                                                                                .fromNum()
                                                                                                - dice.retrieveDieNum(1)
                                                                                                                * j)
                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                1)
                                                                                                                                * j)
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                .retrieveCheckerValue();
                                                                                allConditionsMet = allConditionsMet
                                                                                                && currentCondition;
                                                                        }
                                                                        currentCondition = lane.size() == 1
                                                                                        && isPathClear(actions.fromNum()
                                                                                                        - dice.retrieveDieNum(
                                                                                                                        1)
                                                                                                                        * (i - 1),
                                                                                                        actions.fromNum());
                                                                        allConditionsMet = allConditionsMet
                                                                                        && currentCondition;
                                                                        if (allConditionsMet) {
                                                                                isPossible = true;
                                                                                for (int j = 0; j < i; j++)
                                                                                        dice.minusMoves(1);
                                                                        }
                                                                        shouldBreak = true;
                                                                } else if (actions.fromNum() + 1 == actions.toNum()
                                                                                + dice.retrieveDieNum(1) * i) {
                                                                        for (int j = 1; j <= i - 1; j++) {
                                                                                currentCondition = lanes.get(actions
                                                                                                .fromNum()
                                                                                                - dice.retrieveDieNum(1)
                                                                                                                * j)
                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                1)
                                                                                                                                * j)
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                .retrieveCheckerValue();
                                                                                allConditionsMet = allConditionsMet
                                                                                                && currentCondition;
                                                                        }
                                                                        if (allConditionsMet) {
                                                                                isPossible = true;
                                                                                for (int j = 0; j < i; j++)
                                                                                        dice.minusMoves(1);
                                                                        }
                                                                }
                                                }
                                                if (players[0] == players[2]) {
                                                        maxLane = getMaxLaneOnInnerTable(actions, lanes, 2);
                                                        if (actions.fromNum() == maxLane
                                                                        && actions.fromNum() + dice.retrieveDieNum(
                                                                                        1) > actions.toNum() + 23
                                                                        || actions.fromNum() + dice.retrieveDieNum(
                                                                                        1) == actions.toNum() + 23) {
                                                                isPossible = true;
                                                                dice.minusMoves(1);
                                                        }
                                                        for (int i = 2; i <= dice.retrieveMoves(1) && !shouldBreak; i++)
                                                                if (actions.fromNum() == maxLane && actions.fromNum()
                                                                                + dice.retrieveDieNum(1) * i > actions
                                                                                                .toNum() + 23) {
                                                                        for (int j = 1; j <= i - 1 && actions.fromNum()
                                                                                        + dice.retrieveDieNum(1)
                                                                                                        * j <= 23; j++) {
                                                                                currentCondition = lanes.get(actions
                                                                                                .fromNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                                * j)
                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                + dice.retrieveDieNum(
                                                                                                                                1)
                                                                                                                                * j)
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                .retrieveCheckerValue();
                                                                                allConditionsMet = allConditionsMet
                                                                                                && currentCondition;
                                                                        }
                                                                        currentCondition = lane.size() == 1
                                                                                        && isPathClear(actions
                                                                                                        .fromNum(),
                                                                                                        actions.fromNum()
                                                                                                                        + dice.retrieveDieNum(
                                                                                                                                        1)
                                                                                                                                        * (i - 1));
                                                                        allConditionsMet = allConditionsMet
                                                                                        && currentCondition;
                                                                        if (allConditionsMet) {
                                                                                isPossible = true;
                                                                                for (int j = 0; j < i; j++)
                                                                                        dice.minusMoves(1);
                                                                        }
                                                                        shouldBreak = true;
                                                                } else if (actions.fromNum() + dice.retrieveDieNum(1)
                                                                                * i == actions.toNum() + 23) {
                                                                        for (int j = 1; j <= i - 1; j++) {
                                                                                currentCondition = lanes.get(actions
                                                                                                .fromNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                                * j)
                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                + dice.retrieveDieNum(
                                                                                                                                1)
                                                                                                                                * j)
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                .retrieveCheckerValue();
                                                                                allConditionsMet = allConditionsMet
                                                                                                && currentCondition;
                                                                        }
                                                                        if (allConditionsMet) {
                                                                                isPossible = true;
                                                                                for (int j = 0; j < i; j++)
                                                                                        dice.minusMoves(1);
                                                                        }
                                                                }
                                                }
                                        }
                                }
                } else if (actions.comeFromLane() && actions.goLane()) {
                        Stack<Checker> fromLane = lanes.get(actions.fromNum());
                        Stack<Checker> toLane = lanes.get(actions.toNum());
                        if (!fromLane.empty())
                                if (bars.get(getPlayerNumber()).empty()
                                                && fromLane.peek().retrieveCheckerValue() == players[0]
                                                                .retrieveCheckerValue()
                                                && (toLane.empty() || toLane.size() == 1
                                                                || fromLane.peek().retrieveCheckerValue() == toLane
                                                                                .peek().retrieveCheckerValue())
                                                && dice.retrieveNumMoves() != 0) {
                                        if (dice.retrieveDieNum(1) != dice.retrieveDieNum(2)) {
                                                for (int i = 1; i <= 2; i++)
                                                        if (dice.retrieveMoves(i) != 0 && (players[0] == players[1]
                                                                        && actions.fromNum() == actions.toNum()
                                                                                        + dice.retrieveDieNum(i)
                                                                        || players[0] == players[2] && actions.fromNum()
                                                                                        + dice.retrieveDieNum(
                                                                                                        i) == actions.toNum())) {
                                                                isPossible = true;
                                                                dice.minusMoves(i);
                                                        }
                                                if (dice.retrieveMoves(1) != 0 && dice.retrieveMoves(2) != 0
                                                                && (players[0] == players[1]
                                                                                && actions.fromNum() == actions.toNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                + dice.retrieveDieNum(2)
                                                                                && (lanes.get(actions.fromNum() - dice
                                                                                                .retrieveDieNum(1))
                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                2))
                                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                1))
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                .retrieveCheckerValue()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                2))
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                .retrieveCheckerValue())
                                                                                || players[0] == players[2] && actions
                                                                                                .fromNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                + dice.retrieveDieNum(
                                                                                                                2) == actions.toNum()
                                                                                                && (lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                + dice.retrieveDieNum(
                                                                                                                                1))
                                                                                                                .empty()
                                                                                                                || lanes.get(actions
                                                                                                                                .fromNum()
                                                                                                                                + dice.retrieveDieNum(
                                                                                                                                                2))
                                                                                                                                .empty()
                                                                                                                || lanes.get(actions
                                                                                                                                .fromNum()
                                                                                                                                + dice.retrieveDieNum(
                                                                                                                                                1))
                                                                                                                                .peek()
                                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                                .retrieveCheckerValue()
                                                                                                                || lanes.get(actions
                                                                                                                                .fromNum()
                                                                                                                                + dice.retrieveDieNum(
                                                                                                                                                2))
                                                                                                                                .peek()
                                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                                .retrieveCheckerValue()))) {
                                                        isPossible = true;
                                                        dice.minusMoves(1);
                                                        dice.minusMoves(2);
                                                }
                                        }
                                        if (dice.retrieveDieNum(1) == dice.retrieveDieNum(2)
                                                        && dice.retrieveMoves(1) != 0) {
                                                boolean allConditionsMet = true;
                                                boolean currentCondition = true;
                                                if (players[0] == players[1]) {
                                                        if (actions.fromNum() == actions.toNum()
                                                                        + dice.retrieveDieNum(1)
                                                                        && (lanes.get(actions.fromNum()
                                                                                        - dice.retrieveDieNum(1))
                                                                                        .empty()
                                                                                        || lanes.get(actions.fromNum()
                                                                                                        - dice.retrieveDieNum(
                                                                                                                        1))
                                                                                                        .size() == 1
                                                                                        || lanes.get(actions.fromNum()
                                                                                                        - dice.retrieveDieNum(
                                                                                                                        1))
                                                                                                        .peek()
                                                                                                        .retrieveCheckerValue() == players[1]
                                                                                                                        .retrieveCheckerValue())) {
                                                                isPossible = true;
                                                                dice.minusMoves(1);
                                                        }
                                                        for (int i = 2; i <= dice.retrieveMoves(1); i++) {
                                                                if (actions.fromNum() == actions.toNum()
                                                                                + dice.retrieveDieNum(1) * i) {
                                                                        for (int j = 1; j <= i - 1; j++) {
                                                                                currentCondition = lanes.get(actions
                                                                                                .fromNum()
                                                                                                - dice.retrieveDieNum(1)
                                                                                                                * j)
                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                - dice.retrieveDieNum(
                                                                                                                                1)
                                                                                                                                * j)
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[1]
                                                                                                                                .retrieveCheckerValue();
                                                                                allConditionsMet = allConditionsMet
                                                                                                && currentCondition;
                                                                        }
                                                                        currentCondition = lanes.get(actions.fromNum()
                                                                                        - dice.retrieveDieNum(1) * i)
                                                                                        .empty()
                                                                                        || lanes.get(actions.fromNum()
                                                                                                        - dice.retrieveDieNum(
                                                                                                                        1)
                                                                                                                        * i)
                                                                                                        .peek()
                                                                                                        .retrieveCheckerValue() == players[1]
                                                                                                                        .retrieveCheckerValue()
                                                                                        || lanes.get(actions.fromNum()
                                                                                                        - dice.retrieveDieNum(
                                                                                                                        1)
                                                                                                                        * i)
                                                                                                        .size() == 1;
                                                                        allConditionsMet = allConditionsMet
                                                                                        && currentCondition;
                                                                        if (allConditionsMet) {
                                                                                isPossible = true;
                                                                                for (int j = 0; j < i; j++)
                                                                                        dice.minusMoves(1);
                                                                        }
                                                                }
                                                        }
                                                }
                                                if (players[0] == players[2]) {
                                                        if (actions.fromNum() + dice.retrieveDieNum(1) == actions
                                                                        .toNum()
                                                                        && (lanes.get(actions.fromNum()
                                                                                        + dice.retrieveDieNum(1))
                                                                                        .empty()
                                                                                        || lanes.get(actions.fromNum()
                                                                                                        + dice.retrieveDieNum(
                                                                                                                        1))
                                                                                                        .size() == 1
                                                                                        || lanes.get(actions.fromNum()
                                                                                                        + dice.retrieveDieNum(
                                                                                                                        1))
                                                                                                        .peek()
                                                                                                        .retrieveCheckerValue() == players[2]
                                                                                                                        .retrieveCheckerValue())) {
                                                                isPossible = true;
                                                                dice.minusMoves(1);
                                                        }
                                                        for (int i = 2; i <= dice.retrieveMoves(1); i++) {
                                                                if (actions.fromNum() + dice.retrieveDieNum(1)
                                                                                * i == actions.toNum()) {
                                                                        for (int j = 1; j <= i - 1; j++) {
                                                                                currentCondition = lanes.get(actions
                                                                                                .fromNum()
                                                                                                + dice.retrieveDieNum(1)
                                                                                                                * j)
                                                                                                .empty()
                                                                                                || lanes.get(actions
                                                                                                                .fromNum()
                                                                                                                + dice.retrieveDieNum(
                                                                                                                                1)
                                                                                                                                * j)
                                                                                                                .peek()
                                                                                                                .retrieveCheckerValue() == players[2]
                                                                                                                                .retrieveCheckerValue();
                                                                                allConditionsMet = allConditionsMet
                                                                                                && currentCondition;
                                                                        }
                                                                        currentCondition = lanes.get(actions.fromNum()
                                                                                        + dice.retrieveDieNum(1) * i)
                                                                                        .empty()
                                                                                        || lanes.get(actions.fromNum()
                                                                                                        + dice.retrieveDieNum(
                                                                                                                        1)
                                                                                                                        * i)
                                                                                                        .peek()
                                                                                                        .retrieveCheckerValue() == players[2]
                                                                                                                        .retrieveCheckerValue()
                                                                                        || lanes.get(actions.fromNum()
                                                                                                        + dice.retrieveDieNum(
                                                                                                                        1)
                                                                                                                        * i)
                                                                                                        .size() == 1;
                                                                        allConditionsMet = allConditionsMet
                                                                                        && currentCondition;
                                                                        if (allConditionsMet) {
                                                                                isPossible = true;
                                                                                for (int j = 0; j < i; j++)
                                                                                        dice.minusMoves(1);
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                }
                return isPossible;
        }

        public void move(Actions actions) { // Move a piece on the board according to the given actions
                if (actions.comeFromBar() && actions.goLane()) {
                        Stack<Checker> bar = bars.get(actions.fromNum());
                        Stack<Checker> lane = lanes.get(actions.toNum());
                        if (lane.size() == 1
                                        && bar.peek().retrieveCheckerValue() != lane.peek().retrieveCheckerValue()) {
                                Checker barPiece = bar.pop();
                                if (lane.peek().retrieveCheckerValue() == CheckerValue.B) {
                                        Checker lanePiece = lane.pop();
                                        bars.get(1).push(lanePiece);
                                } else if (lane.peek().retrieveCheckerValue() == CheckerValue.G) {
                                        Checker lanePiece = lane.pop();
                                        bars.get(0).push(lanePiece);
                                }
                                lane.push(barPiece);
                        } else {
                                Checker barPiece = bar.pop();
                                lane.push(barPiece);
                        }
                } else if (actions.comeFromLane() && actions.goFinal()) {
                        Stack<Checker> lane = lanes.get(actions.fromNum());
                        Stack<Checker> endpoint = endpoints.get(actions.toNum());
                        Checker lanePiece = lane.pop();
                        endpoint.push(lanePiece);
                } else if (actions.comeFromLane() && actions.goLane()) {
                        Stack<Checker> fromLane = lanes.get(actions.fromNum());
                        Stack<Checker> toLane = lanes.get(actions.toNum());
                        if (toLane.size() == 1 && fromLane.peek().retrieveCheckerValue() != toLane.peek()
                                        .retrieveCheckerValue()) {
                                Checker fromLanePiece = fromLane.pop();
                                if (toLane.peek().retrieveCheckerValue() == CheckerValue.B) {
                                        Checker toLanePiece = toLane.pop();
                                        bars.get(1).push(toLanePiece);
                                } else if (toLane.peek().retrieveCheckerValue() == CheckerValue.G) {
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

        public void setDiceFace(Actions actions) { // Set the dice face according to the given actions
                dice.makeDieNum(actions.retrieveDieNum(1), actions.retrieveDieNum(2));
        }

        public int getDiceFace(int index) { // Get the dice face for the given index
                return switch (index) {
                        case 1 -> dice.retrieveDieNum(1);
                        case 2 -> dice.retrieveDieNum(2);
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

        public int getSize(String index) { // Get the size of the largest stack of pieces for the specified index
                                           // (upLane or downLane)
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
                                if (lanes.get(i).peek().retrieveCheckerValue() == CheckerValue.G) {
                                        pip1 += (i + 1) * lanes.get(i).size();
                                } else if (lanes.get(i).peek().retrieveCheckerValue() == CheckerValue.B)
                                        pip2 += (24 - i) * lanes.get(i).size();
                        if (endpoints.get(0).size() == 15)
                                pip1 = 0;
                        if (endpoints.get(1).size() == 15)
                                pip2 = 0;
                        players[1].makePip(pip1);
                        players[2].makePip(pip2);
                }
        }

        public void makeDiceRoll() { // Roll the dice
                dice.throwDie();
        }

        public int getDiceMoveNumber() { // Get the total distance number that pieces can be moved
                return dice.retrieveNumMoves();
        }

        public void makeDiceSetZero() { // Set the dice values to zero
                dice.Nothing();
        }

        public Person getPlayer(int index) { // Get the player object for the given index
                return switch (index) {
                        case 0 -> players[0];
                        case 1 -> players[1];
                        case 2 -> players[2];
                        default -> players[0];
                };
        }

        public void setCurrentPlayer(int playerIndex) { // Set the current player using the given player index
                players[0] = players[playerIndex];
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

        public void setPlayersScoreToZero() { // Set both players' scores to zero
                players[1].makePoint(0);
                players[2].makePoint(0);
        }

        public void addCurrentPlayerScore() { // Add score to the current player
                players[0].increasePoint(10);
        }

        public int getDiceMoveStep(int index) { // Get the move step of the dice for the given index
                return switch (index) {
                        case 1 -> dice.retrieveMoves(1);
                        case 2 -> dice.retrieveMoves(2);
                        default -> 0;
                };
        }

        public void setDiceMoveStep(int moveStep1, int moveStep2) { // Set the move steps by the given number of dice
                dice.makeMoves(moveStep1, moveStep2);
        }

        public void setPlayer(int index, Person player) { // Only for Test. Set the player object at the given index.
                if (index >= 0 && index < players.length) {
                        players[index] = player;
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
