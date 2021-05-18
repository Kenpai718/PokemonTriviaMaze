package model;

public class Room extends QuestionAnswer {

	
	private final char START_LETTER = 'A';
	
	private final char myRoomName;
	
	private boolean canEnter;
	
	private boolean hasPlayer;
	
	
	

	public Room(final int theRoomNumber, Pokedex thePokedex) {
		// TODO Auto-generated constructor stub
		super(thePokedex); //put a pokemon and question in this room
		//increment the starting letter for each room to go from A-Z
		myRoomName = (char) (theRoomNumber + 'A');
		canEnter = true;
		hasPlayer = false;


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
	
	
	@Override
	public String toString() {
		return "" + myRoomName;
	}
	

}
