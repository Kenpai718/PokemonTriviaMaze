package model;

public class Maze {
    
    
        private final Room[][] myMatrix;
        
//        private final boolean winCondition;
        private final int[] myPlayerLocation;
        

	

//       TODO Current win condition is that the player needs to get to the
//        opposite corner that they are in.

        public Maze() {
		myMatrix = getRooms();
//		winCondition = false;
		myPlayerLocation = new int[] {0,0};
	}
	
	
        private Room[][] getRooms() {
                // TODO Auto-generated method stub
                final Room[][] res = new Room[4][4];
                for (int i = 0; i < res.length; i++) {
                        for (int j = 0; j < res[0].length; j++) {
                                res[i][j] = new Room(i + j);
                        }
                }
                
                return res;
        }


        /**
         * Returns if the player has one yet
         * 
         * @return
         */
        public boolean isWinCondition() {                
                return myPlayerLocation[0] == 15 && myPlayerLocation[1] == 15;
        }

        /**
         * Returns the players current location
         * 
         * @return an integer array of the players current location
         */
        public int[] getPlayerLocation() {
                return myPlayerLocation;
        }


        /**
         * @return
         */
        public Room[][] getMatrix() {
            return myMatrix;
        }
        
        

}
