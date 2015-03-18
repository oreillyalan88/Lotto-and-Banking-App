package ie.wit.exceptions;

public class StringLength extends Exception {

	public StringLength() {
		super("Input must be at least 2 characthers long");
	}

	public StringLength(String message) {
		super(message);
	}
}
