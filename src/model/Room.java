package model;

public class Room {

        
        private final char myRoomName;
        
        
	public Room(final int theRoomNumber) {
		// TODO Auto-generated constructor stub
	        myRoomName = (char)(theRoomNumber + 'A');
	        
	}


        @Override
        public String toString() {
                return "" + myRoomName;
        }
	
	

}
