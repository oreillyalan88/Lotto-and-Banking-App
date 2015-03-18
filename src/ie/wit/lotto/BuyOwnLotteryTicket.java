package ie.wit.lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class BuyOwnLotteryTicket {
	HashSet<Integer> SelectedNumbers;
	ArrayList<Integer> Lottery;
	ArrayList<ArrayList<Integer>> Games;

	BuyOwnLotteryTicket() {
		Lottery = new ArrayList<Integer>();
		SelectedNumbers = new HashSet<Integer>();
		Games = new ArrayList<ArrayList<Integer>>();

	}

	public void Generate() {

		Lottery = new ArrayList<Integer>(); // create a new Lottery ticket
		Lottery.addAll(SelectedNumbers);
		Collections.sort(Lottery);
		Games.add(Lottery);

	}
}
