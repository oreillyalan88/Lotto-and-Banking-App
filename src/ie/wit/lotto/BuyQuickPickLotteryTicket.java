package ie.wit.lotto;

import java.util.ArrayList;
import java.util.Collections;

public class BuyQuickPickLotteryTicket {
	ArrayList<Integer> Numbers;
	ArrayList<Integer> Lottery;
	ArrayList<ArrayList<Integer>> Games;

	BuyQuickPickLotteryTicket() {
		Lottery = new ArrayList<Integer>();
		Numbers = new ArrayList<Integer>();
		Games = new ArrayList<ArrayList<Integer>>();
		Numbers(); // call this once from constructor as the total numbers in
					// the Numbers collection is fixed which is from 1 to 49.
	}

	public ArrayList<Integer> Numbers() {
		for (int i = 1; i < 49; i++) {
			Numbers.add(i);
		}
		Collections.shuffle(Numbers);
		return Numbers;
	}

	public void Generate(int numberOfGames) {
		for (int i = 0; i < numberOfGames; i++) {
			Collections.shuffle(Numbers); // shuffle the numbers for every game
			Lottery = new ArrayList<Integer>(); // create a new Lottery ticket
			for (int x = 0; x < 6; x++) {
				Lottery.add(Numbers.get(x));
			}
			Collections.sort(Lottery);
			Games.add(Lottery);
		}
	}

}