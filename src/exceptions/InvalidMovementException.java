package exceptions;

public class InvalidMovementException extends Exception {
	
	
	public InvalidMovementException(final String theMovement, final int[] theNewPos) {
		super("Cannot set " + theMovement + " location at [" + theNewPos[0] + ", " + theNewPos[1] + "]");
	}

}
