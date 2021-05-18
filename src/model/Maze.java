package model;

import java.util.Arrays;

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
	private int[] myPlayerLocation;

	// keeps track of how many rooms are made
	private int roomCounter;
	
	private static Pokedex myPokedex;
	
	private static Maze singleMaze = null;
//       TODO Current win condition is that the player needs to get to the
//        opposite corner that they are in.

	/*
	 * Constructor for maze
	 */
	private Maze() {
		roomCounter = 0;
		myMatrix = getRooms();
//		winCondition = false;
		final int[] h = new int[] {0, 0};
		myPlayerLocation = h;

		// TODO: test stuff delete later
		myMatrix[0][0].setPlayer(true); // put player location at 0,0
	}
	
	public Maze(Pokedex thePokedex) {
		myPokedex = thePokedex;
		roomCounter = 0;
		myMatrix = getRooms();
		final int[] h = new int[] {0, 0};
		myPlayerLocation = h;

		// TODO: test stuff delete later
		myMatrix[0][0].setPlayer(true); // put player location at 0,0
		
	}

	public static Maze getInstance() {
	        if (singleMaze == null) {
	                singleMaze = new Maze(myPokedex);
	        }
	        return singleMaze;
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
				res[i][j] = new Room(roomCounter, myPokedex);
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
	
	public void setPlayerLocation(final int[] theNewPos) {
	        myMatrix[myPlayerLocation[0]][myPlayerLocation[1]].setPlayer(false);
	        myMatrix[theNewPos[0]][theNewPos[1]].setPlayer(true);
	        myPlayerLocation = theNewPos.clone();
	        
	        System.out.println(Arrays.toString(getPlayerLocation()));
	}

	public Room getRoom(final int theR, final int theC) {
	        return  myMatrix[theR][theC];
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
	
	/*
	 * Return current room player is in
	 */
	public Room getCurrRoom() {
		return myMatrix[myPlayerLocation[0]][myPlayerLocation[1]];
	}

}
