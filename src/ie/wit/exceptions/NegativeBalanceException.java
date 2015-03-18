package ie.wit.exceptions;


public class NegativeBalanceException extends RuntimeException {


	public NegativeBalanceException() {
		super("You can not have a negative lotto balance");
	}

	public NegativeBalanceException(String message) {
		super(message);
	}

}