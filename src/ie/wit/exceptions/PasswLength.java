package ie.wit.exceptions;

public class PasswLength extends Exception {

	public PasswLength() {
		super("Password must be at least 6 characthers long");
	}

	public PasswLength(String message) {
		super(message);
	}
}
