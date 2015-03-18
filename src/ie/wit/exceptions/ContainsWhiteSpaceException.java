package ie.wit.exceptions;

public class ContainsWhiteSpaceException extends Exception {

	public ContainsWhiteSpaceException() {
		super("There can be no spaces in First and Second Name");
	}

	public ContainsWhiteSpaceException(String message) {
		super(message);
	}
}
