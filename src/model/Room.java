package model;

import java.io.Serializable;

/**
 * Every room has a Pokemon extended from QuestionAnswer. The room has a
 * question the player must answer to enter the room. To enter the room the
 * player must correctly answer the question.
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @version Spring 2021
 */

public class Room extends QuestionAnswer implements Serializable {

        /**
         * The serialized ID for Serialization
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
	 * Setter for blocked room
	 * 
	 * @param boolean T = canEnter room, F = blocked room
	 */
	public void setEntry(final Boolean theChoice) {
		canEnter = theChoice;
	}

	/**
	 * Setter for denoting a room had a player in it before
	 * 
	 * @param boolean T = player visited, F = not visited
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
	public void setPlayer(final Boolean theBool) {
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
	 * 
	 * @return if player has been to this room before
	 */
	public Boolean hasVisited() {
		return myVisit;
	}

	/**
	 * Getter for room name IE: 'A'-'Z'
	 * 
	 * @return char roomName
	 */
	public String getRoomName() {
		return myRoomName;
	}

	/**
	 * Room roString, just the Name -> "##"
	 */
	@Override
	public String toString() {
		return "" + myRoomName;
	}

}
