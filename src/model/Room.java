package model;

public class Room {

	
	private final char START_LETTER = 'A';
	
	private final char myRoomName;
	
	private boolean canEnter;
	
	private boolean hasPlayer;
	

	public Room(final int theRoomNumber) {
		// TODO Auto-generated constructor stub
		//increment the starting letter for each room to go from A-Z
		myRoomName = (char) (theRoomNumber + 'A');
		canEnter = true;
		hasPlayer = false;
		

	}

	@Override
	public String toString() {
		return "" + myRoomName;
	}
	
	private void setBlocked() {
		canEnter = false;
	}
	
	public boolean canEnter() {
		return canEnter;
	}
	
	protected void setPlayer(final Boolean theBool) {
		hasPlayer = theBool;
	}
	
	public Boolean isPlayerHere() {
		return hasPlayer;
	}
	
	public char getRoomName() {
		return myRoomName;
	}
	

}
