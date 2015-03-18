package ie.wit.exceptions;


public class InvalidWithdrawException extends Exception {

		public InvalidWithdrawException() {
			super("You are not eligable for an overdraft");
		}

		public InvalidWithdrawException(String message) {
			super(message);
		}
	}
