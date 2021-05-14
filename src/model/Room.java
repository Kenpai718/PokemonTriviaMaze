package model;

public class Room {

	private final char myRoomName;
	
	private char START_LETTER = 'A';

	public Room(final int theRoomNumber) {
		// TODO Auto-generated constructor stub
		//increment the starting letter for each room to go from A-Z
		myRoomName = (char) (theRoomNumber + 'A');

	}

	@Override
	public String toString() {
		return "" + myRoomName;
	}

}
