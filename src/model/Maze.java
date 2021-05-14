package model;

public class Maze {

	/*
	 * Constants
	 */
	private final int ROWS = 4;
	private final int COLS = 4;
	private final int[] WIN_LOCATION = new int[] { (ROWS * COLS - 1),
			(ROWS * COLS - 1) }; // end of maze

	/*
	 * 2D array to store rooms in the maze
	 */
	private final Room[][] myMatrix;

//        private final boolean winCondition;
	private final int[] myPlayerLocation;

	// keeps track of how many rooms are made
	private int roomCounter;

//       TODO Current win condition is that the player needs to get to the
//        opposite corner that they are in.

	/*
	 * Constructor for maze
	 */
	public Maze() {
		roomCounter = 0;
		myMatrix = getRooms();
//		winCondition = false;
		myPlayerLocation = new int[] { 0, 0 };

		// TODO: test stuff delete later
		myMatrix[0][0].setPlayer(true); // put player location at 0,0
	}

	/*
	 * Gets the room matrix
	 * 
	 * @return Room[][]
	 */
	private Room[][] getRooms() {
		// TODO Auto-generated method stub
		final Room[][] res = new Room[ROWS][COLS];
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[0].length; j++) {
				res[i][j] = new Room(roomCounter);
				roomCounter++;
			}
		}

		return res;
	}

	/**
	 * Returns if the player has won yet
	 * 
	 * @return
	 */
	public boolean isWinCondition() {
		return myPlayerLocation[0] == WIN_LOCATION[0]
				&& myPlayerLocation[1] == WIN_LOCATION[1];
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

	public int getRows() {
		return ROWS;
	}

	public int getCols() {
		return COLS;
	}

}
