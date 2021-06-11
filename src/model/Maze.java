package model;

import java.io.Serializable;
import java.util.ArrayList;

import exceptions.InvalidMovementException;

/**
 * Maze composing of rooms with Pokemon questions; represented by a 2D matrix.
 * Main gameplay element where the player starts in one location and tries to
 * answer Pokemon questions to get to the end of the maze.
 * 
 * Class is designed with Singleton principles so only one maze is instantiated
 * for the game.
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class Maze implements Serializable {

	/**
	     * 
	     */
	private static final long serialVersionUID = -1781677412081562393L;

	/*
	 * Constants
	 */
	private final static int DEFAULT_ROWS = 5;
	private final static int DEFAULT_COLS = 5;
	private final static int START = 0;
	private final static int[] STARTLOCATION = new int[] { START, START };
	private int[] myWinLocation;

	/*
	 * length of the matrix rows
	 */
	private int myRows;
	/*
	 * length of the matrix cols
	 */
	private int myCols;
	/*
	 * 2D array to store rooms in the maze
	 */
	private Room[][] myMatrix;

	/*
	 * Location of the player in the maze
	 */
	private int[] myPlayerLocation;

	/*
	 * Location the player is trying to move to
	 */
	private int[] myAttemptLocation;

	/*
	 * Keeps track of how many rooms have been made in the maze. Mainly for
	 * debugging.
	 */
	private transient int roomCounter;

	/*
	 * List of Pokemon objects
	 */
	private ArrayList<Pokemon> myPokemonList;

	/*
	 * Boolean to verify when the player has won the game
	 */
	private transient boolean myWinCondition;

	/*
	 * Boolean to verify when the player has lost the game
	 */
	private transient boolean myLoseCondition;

	/*
	 * Singleton maze instantiation
	 */
	private static Maze singleMaze = null;

	/**
	 * Constructor for maze
	 */
	private Maze() {
		roomCounter = 0;
		myRows = DEFAULT_ROWS;
		myCols = DEFAULT_COLS;

		myMatrix = fillRooms();
		myPlayerLocation = STARTLOCATION;
		myAttemptLocation = myPlayerLocation.clone();
		myWinLocation = new int[] { (myRows - 1), (myCols - 1) }; // end of
																	// maze;
		myPokemonList = fillPokemonList();
		// TODO: test stuff delete later
		myMatrix[START][START].setPlayer(true); // put player location at 0,0
		myWinCondition = false;
		myLoseCondition = false;

		// set the first room to be visited since we dont play that room
		myMatrix[START][START].setVisited(true);

	}

	/**
	 * Singleton maze instantiation
	 * 
	 * @return Maze
	 */
	public static Maze getInstance() {
		if (singleMaze == null) {
			singleMaze = new Maze();
		}
		return singleMaze;
	}

	/**
	 * Fills matrix with new rooms that have questions and pokemon
	 * 
	 * @return Room[][] matrix of instantiated rooms
	 */
	private Room[][] fillRooms() {
	        // TODO Auto-generated method stub
	        Room[][] res = new Room[myRows][myCols];
	        ;
	        if (myMatrix != null) {
	                res = new Room[getRows()][getCols()];
	        }

	        for (int i = 0; i < res.length; i++) {
	                for (int j = 0; j < res[0].length; j++) {
	                        res[i][j] = new Room(roomCounter);
	                        roomCounter++;
	                }
	        }
	        return res;
	}


	/**
	 * Returns if the player has won yet Sets the win variable based on the check.
	 * 
	 * @return boolean t = win, f = not won
	 */
	public void setWinCondition() {
		final boolean result = myPlayerLocation[0] == myWinLocation[0] && myPlayerLocation[1] == myWinLocation[1];
		myWinCondition = result;
	}

	/**
	 * 
	 * @return boolean if player has reached the end goal
	 */
	public Boolean hasWon() {
		return myWinCondition;
	}


	/**
	 *
	 * Sets the lose variable based on the check.
	 * 
	 * @return boolean t = win, f = not won
	 */
	public void setLoseCondition() {
		// invert because the path finder returns true if there is a path
		// we only say lost if it returns false
		final boolean result = !MazePathFinder.checkForPath(myPlayerLocation, myWinLocation);

		myLoseCondition = result;
	}

	/**
	 * 
	 * @return boolean if player has lost and all paths are blocked
	 */
	public Boolean hasLost() {
		return myLoseCondition;
	}

	/**
	 * winning location
	 * 
	 * @return [0] = win row, [1] = win col
	 */
	public int[] getWinLocation() {
		return myWinLocation.clone();
	}

	/**
	 * Returns the players current location
	 * 
	 * @return int[] an integer array of the players current location 0 = row, 1 =
	 *         col
	 */
	public int[] getPlayerLocation() {
		return myPlayerLocation;
	}

	/**
	 * Sets location of the player.
	 * 
	 * @param int[] theNewPos [0] = row, [1] = col
	 * @throws InvalidMovementException 
	 */
	public void setPlayerLocation(final int[] theNewPos) throws InvalidMovementException {
			if (theNewPos[0] < 0 || theNewPos[1] < 0 || theNewPos[0] > getRows() || theNewPos[1] > getCols()) {
			        throw new InvalidMovementException("player", theNewPos);
			} else {

				myMatrix[myPlayerLocation[0]][myPlayerLocation[1]].setPlayer(false);
				myMatrix[theNewPos[0]][theNewPos[1]].setPlayer(true);
				myPlayerLocation = theNewPos.clone();
			}
		// System.out.println(Arrays.toString(getPlayerLocation()));
		if (!getCurrRoom().hasVisited()) {
			getCurrRoom().setVisited(true);
		}

		myAttemptLocation = myPlayerLocation.clone();
	}

	/*
	 * Return current room player is in
	 */
	public Room getCurrRoom() {
		return myMatrix[myPlayerLocation[0]][myPlayerLocation[1]];
	}

	/**
	 * Return current room player is trying to move to
	 */
	public Room getAttemptRoom() {
		return myMatrix[myAttemptLocation[0]][myAttemptLocation[1]];
	}

	/*
	 * Current location in maze the player is trying to move to
	 */
	public int[] getAttemptedLocation() {
		return myAttemptLocation;
	}

	/**
	 * Sets the attempted location to move to
	 * 
	 * @param int[] theNewPos [0] = row, [1] = col
	 * @throws InvalidMovementException 
	 */

	public void setAttemptLocation(final int[] theNewPos) throws InvalidMovementException {
			if (theNewPos[0] < 0 || theNewPos[1] < 0 || theNewPos[0] > getRows() || theNewPos[1] > getCols()) {
				throw new InvalidMovementException("attemmpt", theNewPos);
			} else {
				myAttemptLocation = theNewPos.clone();
			}
	}

	/**
	 * Check if the player has attempted to move to a new room or not
	 * 
	 */
	public boolean hasNotMoved() {
		return myPlayerLocation[0] == myAttemptLocation[0] && myPlayerLocation[1] == myAttemptLocation[1];
	}

	/**
	 * Get a specific room in the matrix
	 * 
	 * @param theR the row
	 * @param theC the col
	 * @return the room at that index
	 * @throws Exception Handled by the room checker
	 */
	public Room getRoom(final int theR, final int theC) throws Exception {
		Room res = null;
		if (theR < 0 || theC < 0 || theR > getRows() || theC > getCols()) {
			throw new Exception("Room does not exist at [" + theR + ", " + theC + "]");
		} else {
			res = myMatrix[theR][theC];
		}
		return res;

	}

	/**
	 * Set a new room at a location in matrix
	 * 
	 * @param the  new room
	 * @param theR the row index
	 * @param theC the col index
	 */
	public void setRoomInMatrix(final Room theRoom, final int theR, final int theC) {
		try {
			if (theR < 0 || theC < 0 || theR > getRows() || theC > getCols()) {
				throw new Exception("Room does not exist at [" + theR + ", " + theC + "]");
			} else {
				System.out.println("added" + theRoom.getAnswer());
				myMatrix[theR][theC] = theRoom;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Make a list of pokemon that are currently put in the maze
	 * 
	 * @return list of pokemon
	 */
	private ArrayList<Pokemon> fillPokemonList() {
		// TODO Auto-generated method stub
		final ArrayList<Pokemon> res = new ArrayList<>();
		for (int i = 0; i < myRows; i++) {
			for (int j = 0; j < myCols; j++) {
				res.add(myMatrix[i][j].getPokemon());
			}
		}
		return res;
	}

	/**
	 * @return get list of pokemon in maze
	 */
	public ArrayList<Pokemon> getPokemonList() {
		return myPokemonList;
	}

	/**
	 * Getter for room matrix
	 * 
	 * @return room[][]
	 */
	public Room[][] getMatrix() {
		return myMatrix.clone();
	}

	/**
	 * @param myMatrix the myMatrix to set
	 */
	public void setMatrix(final Room[][] theMatrix) {
		myMatrix = theMatrix;
		myWinLocation = new int[] { getRows() - 1, getCols() - 1 };
	}

	/**
	 * Getter for row count
	 * 
	 * @return row count
	 */
	public int getRows() {
		// return myMatrix.length;
		return myRows;
	}

	/**
	 * Getter for col count
	 * 
	 * @return col count
	 */
	public int getCols() {
		// return myMatrix[0].length;
		return myCols;
	}

	/**
	 * If player is at the start of the game
	 * 
	 * @return boolean true/false
	 */
	public boolean isAtStart() {
		return myPlayerLocation[0] == START && myPlayerLocation[1] == START && myAttemptLocation[0] == START
				&& myAttemptLocation[1] == START;
	}

	/**
	 * Reset the matrix with a new size
	 * 
	 * @param theRows
	 * @param theCols
	 */
	public void setMatrixSize(final int theRows, final int theCols) {
		myRows = theRows;
		myCols = theCols;
		reset();
	}

	/**
	 * Reset the maze to default and instantiate new rooms Make a new instance of
	 * the maze
	 */
	public void reset() {

		// singleMaze = new Maze();
		myMatrix[0][0].clearUsed();
		roomCounter = 0;
		// clearMatrix();
		myMatrix = fillRooms();
		myPlayerLocation = STARTLOCATION;
		myAttemptLocation = myPlayerLocation.clone();

		myMatrix[0][0].setPlayer(true); // put player location at 0,0
		myWinCondition = false;
		myLoseCondition = false;

		// set the first room to be visited since we dont play that room
		myMatrix[0][0].setVisited(true);
		myPokemonList.clear();
		myPokemonList = fillPokemonList();

	}


	/**
	 * Method for serialization to work. Returns an instance of the new object
	 * 
	 * @return
	 */
	private Object readResolve() {
		final Maze instance = getInstance();
		instance.myMatrix = myMatrix;
		instance.myAttemptLocation = myAttemptLocation;
		instance.myPlayerLocation = myPlayerLocation;
		instance.myRows = myRows;
		instance.myCols = myCols;
		return instance;
	}

//	// TODO: DELETE LATER
//	// used to visually check which rooms are set to blocked
//	// not sure why but when answering incorrect all rooms are blocked off
//	public void printBlockedDebugger() {
//		final StringBuilder sb = new StringBuilder();
//		sb.append("What rooms are blocked?");
//		for (int i = 0; i < myRows; i++) {
//			sb.append("\n");
//			for (int j = 0; j < myCols; j++) {
//				final Room r = myMatrix[i][j];
//				sb.append(r.getRoomName() + " " + r.canEnter() + ", ");
//			}
//		}
//
//		System.out.println(sb);
//
//	}

}
