package ie.wit.exceptions;

public class MustBeLetters extends Exception {
  
	public MustBeLetters() {
		super("The First and Second name must be a letter ");
	}

	public MustBeLetters(String message) {
		super(message);
	}
}
