package ie.wit.lotto;

import java.util.ArrayList;
import java.util.Collections;

public class StartLotto {
	ArrayList<Integer> Numbers;
	ArrayList<Integer> Lottery;

	StartLotto() {
		Lottery = new ArrayList<Integer>();
		Numbers = new ArrayList<Integer>();

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

	public void Generate() {
		Lottery = new ArrayList<Integer>(); // create a new Lottery ticket
		for (int x = 0; x < 6; x++) {
			Lottery.add(Numbers.get(x));
		}
		Collections.sort(Lottery);

	}

}
