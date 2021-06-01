package model;

import java.io.Serializable;

/**
 * Every room has a Pokemon extended from QuestionAnswer. The room has a
 * question the player must answer to enter the room which is correctly pick the
 * Pokemon.
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @version Spring 2021
 */

public class Room extends QuestionAnswer implements Serializable {

	/**
         * 
         */
        private static final long serialVersionUID = 575983420176798332L;

        /*
	 * Starting letter of the room
	 */
	private final char START_LETTER = 'A';

	/*
	 * Name of room
	 */
	private final String myRoomName;

	/*
	 * boolean if player canEnter room Denotes if it is blocked or not
	 */
	private boolean canEnter;

	/*
	 * If player is currently in this room
	 */
	private boolean hasPlayer;
	
	/*
	 * If player has been to this room before and cleared it
	 */
	private boolean myVisit;
	

	/**
	 * Constructor
	 * 
	 * @param theRoomNumber how much to increment the room name letter
	 */
	public Room(final int theRoomNumber) {

		super(); // put a pokemon and question in this room

		// increment the starting letter for each room to go from A-Z
//		myRoomName = (char) (theRoomNumber + START_LETTER);
		myRoomName = "" + (theRoomNumber + 1);
		setEntry(true);
		hasPlayer = false;
		myVisit = false;
	}
	
	/**
	 * Manually add a pokemon to the room
	 * 
	 * @param theRoomNumber how much to increment the room name letter
	 * @param thePokemon the pokemon to put in this room
	 */
	public Room(final int theRoomNumber, final Pokemon thePokemon) {
		super(); // put a pokemon and question in this room

		// increment the starting letter for each room to go from A-Z
//		(char) (theRoomNumber + START_LETTER);
//		myRoomName = setName(theRoomNumber);
		myRoomName = "" + (theRoomNumber + 1);
		setEntry(true);
		hasPlayer = false;
		myVisit = false;
		
	}
	
	private char setName(final int theRoomNumber) {
	        char res = START_LETTER;
	        if (theRoomNumber + START_LETTER <= 'Z') {
	                res = (char) (theRoomNumber + START_LETTER);
	        } else {
	                
	        }
	        return res;
	}

	/**
	 * Setter for blocked room
	 * 
	 * @return boolean T = canEnter room, F = blocked room
	 */
	public void setEntry(final Boolean theChoice) {
		canEnter = theChoice;
	}
	
	/**
         * Setter for blocked room
         * 
         * @return boolean T = canEnter room, F = blocked room
         */
        public void setVisited(final Boolean theChoice) {
                myVisit = theChoice;
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
	
	public Boolean hasVisited() {
	        return myVisit;
	}

	/**
	 * Getter for room name
	 * IE: 'A'-'Z'
	 * 
	 * @return char roomName
	 */
	public String getRoomName() {
		return myRoomName;
	}

	@Override
	public String toString() {
		return "" + myRoomName;
	}

}
