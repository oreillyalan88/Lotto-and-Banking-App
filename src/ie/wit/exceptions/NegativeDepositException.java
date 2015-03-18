package ie.wit.exceptions;


public class NegativeDepositException extends RuntimeException {


	public NegativeDepositException() {
		super("You can not deposit a negative value ");

	}


	public NegativeDepositException(String message) {
		super(message);
	}

}
