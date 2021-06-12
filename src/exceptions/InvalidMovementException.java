package exceptions;

/**
 * Custpom Exception that handels any invalid movements.
 * 
 * @author AJ Downey
 */
public class InvalidMovementException extends Exception {
	
	
	/**
	 * Receives the movement and int[] of the position to move to for error message
	 * 
	 * @param theMovement string of the direction
	 * @param theNewPos int[] of the position
	 */
	public InvalidMovementException(final String theMovement, final int[] theNewPos) {
		super("Cannot set " + theMovement + " location at [" + theNewPos[0] + ", " + theNewPos[1] + "]");
	}

}
