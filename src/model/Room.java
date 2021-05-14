package model;

public class Room {

	private final char myRoomName;
	
	private char START_LETTER = 'A';
	
	private boolean canEnter;

	public Room(final int theRoomNumber) {
		// TODO Auto-generated constructor stub
		//increment the starting letter for each room to go from A-Z
		myRoomName = (char) (theRoomNumber + 'A');
		canEnter = true;

	}

	@Override
	public String toString() {
		return "" + myRoomName;
	}
	
	private void setBlocked() {
		canEnter = false;
	}
	
	private boolean canEnter() {
		return canEnter;
	}

}
