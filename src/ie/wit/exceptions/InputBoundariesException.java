/**
 * 
 */
package ie.wit.exceptions;


public class InputBoundariesException extends Exception {

	public InputBoundariesException() {
		super("Number selected between 1 and 7 please. ");
	}

	public InputBoundariesException(String message) {
		super(message);
	}
}
