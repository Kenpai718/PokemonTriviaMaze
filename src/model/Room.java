package model;

/**
 * Every room has a Pokemon extended from QuestionAnswer. The room has a
 * question the player must answer to enter the room which is correctly pick the
 * Pokemon.
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @version Spring 2021
 */

public class Room extends QuestionAnswer {

	/*
	 * Starting letter of the room
	 */
	private final char START_LETTER = 'A';

	/*
	 * Name of room
	 */
	private final char myRoomName;

	/*
	 * boolean if player canEnter room Denotes if it is blocked or not
	 */
	private static boolean canEnter;

	/*
	 * If player is currently in this room
	 */
	private boolean hasPlayer;

	/**
	 * Constructor
	 * 
	 * @param theRoomNumber how much to increment the room name letter
	 * @param thePokedex    data of all the pokemon to fill the room questions
	 *                      with
	 */
	public Room(final int theRoomNumber, Pokedex thePokedex) {

		super(thePokedex); // put a pokemon and question in this room

		// increment the starting letter for each room to go from A-Z
		myRoomName = (char) (theRoomNumber + START_LETTER);
		canEnter = true;
		hasPlayer = false;

	}

	/**
	 * Setter for blocked room
	 * 
	 * @return boolean T = canEnter room, F = blocked room
	 */
	private void setEntry(Boolean theChoice) {
		canEnter = theChoice;
	}
	

	/**
	 * Getter to inform if the room is blocked or not
	 * 
	 * @return boolean T = canEnter room, F = blocked room
	 */
	public boolean canEnter() {
		return canEnter;
	}

	/**
	 * Set player is here
	 * 
	 * @param boolean T = player in room, F = not in room
	 */
	protected void setPlayer(final Boolean theBool) {
		hasPlayer = theBool;
	}

	/**
	 * Tells if player is in this room or not
	 * 
	 * @return boolean T = player in room, F = not in room
	 */
	public Boolean isPlayerHere() {
		return hasPlayer;
	}

	/**
	 * Getter for room name
	 * IE: 'A'-'Z'
	 * 
	 * @return char roomName
	 */
	public char getRoomName() {
		return myRoomName;
	}

	@Override
	public String toString() {
		return "" + myRoomName;
	}

}
