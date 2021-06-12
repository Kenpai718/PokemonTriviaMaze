package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Maze;
import model.MazePathFinder;
import model.Room;

/**
 * Tests the dfs search for goal path finding in the maze
 * @author Kenneth Ahrens
 *
 */
class MazePathFinderTest {

	// shorthand for boolean matrix
	final boolean T = true;
	final boolean F = false;
	
	//default variables for testing
	Maze myMaze;
	int[] start;
	int[] target;

	@BeforeEach
	void setUp() throws Exception {
		myMaze = Maze.getInstance();
		start = myMaze.getPlayerLocation();
		target = myMaze.getWinLocation();
	}

	//test if there is a path at the start of the game
	void checkHasPathStart() {
		myMaze.reset();
		boolean hasPath = MazePathFinder.checkForPath(start, target);

		assertTrue(hasPath, "Path finder is asserting there is no path at the start of the game!");
	}

	//test a maze state that should return true from start to end
	@Test
	void checkHasPathTrue() {
		/*
		 * by default maze is 5x5 this test maze still has a path from top left to
		 * bottom right. T = can pass //F = can't pass
		 */
		boolean[][] states = { 
				{ T, F, T, T, T }, 
				{ T, F, F, T, T }, 
				{ T, F, T, F, T }, 
				{ T, T, T, T, T },
				{ T, F, F, T, T } };

		if (states.length != myMaze.getRows() || states[0].length != myMaze.getCols()) {
			fail("Test maze not the same size as the original maze. Cannot load new state into maze!");
		}

		//load the maze with state that will have a path at the start to end
		Room[][] maze = loadRoomState(states);
		boolean hasPath = MazePathFinder.checkForPath(maze, start, target);
		String status = MazePathFinder.getRoomStatus(maze);
		System.out.println(status);
		printResult(hasPath, start, target);

		assertTrue(hasPath, "The DFS search returned there was no path when there should be.");
	}

	//test a maze state where the player is blocked in and should return false
	@Test
	void checkHasPathFalse() {
		
		/*
		 * by default maze is 5x5 this test maze will not have a path from the top left to the
		 * bottom right. T = can pass //F = can't pass
		 */
		boolean[][] states = { 
				{ T, F, T, T, T }, 
				{ T, F, F, T, T }, 
				{ F, F, F, F, F }, 
				{ T, T, T, T, T },
				{ T, F, F, T, T } };

		if (states.length != myMaze.getRows() || states[0].length != myMaze.getCols()) {
			fail("Test maze not the same size as the original maze. Cannot load new state into maze!");
		}

		//load the maze with state that will have a path at the start to end
		Room[][] maze = loadRoomState(states);
		boolean hasPath = MazePathFinder.checkForPath(maze, start, target);
		String status = MazePathFinder.getRoomStatus(maze);
		System.out.println(status);
		printResult(hasPath, start, target);

		assertFalse(hasPath, "The DFS search returned there was a path when there should not be.");

	}
	
	//check a midway point in the maze and make sure there is still a path
	@Test
	void checkHasPathMidYes() {
		boolean[][] states = { 
				{ T, F, T, T, T }, 
				{ T, F, T, T, T }, 
				{ T, F, F, F, F }, 
				{ T, T, T, T, T },
				{ T, F, F, F, T } };
		
		int[] pos = {3,2};

		if (states.length != myMaze.getRows() || states[0].length != myMaze.getCols()) {
			fail("Test maze not the same size as the original maze. Cannot load new state into maze!");
		}

		//load the maze with state that will have a path at the start to end
		Room[][] maze = loadRoomState(states);
		boolean hasPath = MazePathFinder.checkForPath(maze, pos, target);
		String status = MazePathFinder.getRoomStatus(maze);
		System.out.println(status);
		printResult(hasPath, pos, target);

		assertTrue(hasPath, "The DFS search returned there was a path when there should not be.");

	}
	
	//test to make sure the player loses if the end goal is blocked
	@Test
	void checkHasPathGoalBlocked() {
		
		boolean[][] states = { 
				{ T, T, T, T, T }, 
				{ T, T, T, T, T }, 
				{ T, T, T, T, T }, 
				{ T, T, T, T, T },
				{ T, T, T, T, F } };
		

		if (states.length != myMaze.getRows() || states[0].length != myMaze.getCols()) {
			fail("Test maze not the same size as the original maze. Cannot load new state into maze!");
		}

		//load the maze with state that will have a path at the start to end
		Room[][] maze = loadRoomState(states);
		boolean hasPath = MazePathFinder.checkForPath(maze, start, target);
		String status = MazePathFinder.getRoomStatus(maze);
		System.out.println(status);
		printResult(hasPath, start, target);

		assertFalse(hasPath, "The DFS search returned there was a path when there should not be.");

	}
	
	//another test to see that it should return false
	@Test
	void checkHasPathNo2() {
		boolean[][] states = { 
				{ T, T, T, T, T }, 
				{ T, T, T, T, T }, 
				{ T, T, T, T, T }, 
				{ T, T, T, T, F },
				{ T, T, T, F, T } };
		

		int[] pos = {4,2};
		if (states.length != myMaze.getRows() || states[0].length != myMaze.getCols()) {
			fail("Test maze not the same size as the original maze. Cannot load new state into maze!");
		}

		//load the maze with state that will have a path at the start to end
		Room[][] maze = loadRoomState(states);
		boolean hasPath = MazePathFinder.checkForPath(maze, pos, target);
		String status = MazePathFinder.getRoomStatus(maze);
		System.out.println(status);
		printResult(hasPath, pos, target);

		assertFalse(hasPath, "The DFS search returned there was a path when there should not be.");
	}

	/**
	 * Helper method used for unit testing. Sets the state of the rooms to the same state as a
	 * boolean matrix. This will allow us to check if the user is blocked in or not.
	 * 
	 */
	private Room[][] loadRoomState(final boolean[][] theStates) {
		Room[][] roomCopy = myMaze.getMatrix();

		// set entry status to the same as the parameter matrix
		for (int i = 0; i < myMaze.getRows(); i++) {
			for (int j = 0; j < myMaze.getCols(); j++) {
				Room r = roomCopy[i][j];
				boolean canEnter = r.canEnter();
				boolean newState = theStates[i][j];
				roomCopy[i][j].setEntry(newState);

			}
		}

		return roomCopy;

	}
	
	
	
	/**
	 * Helper method that prints if a path was found or not
	 * 
	 * @param theResult
	 * @param the starting pos
	 * @param the target pos
	 */
	private static void printResult(final boolean theResult, int[] pos, int[] end) {
		String starting = "(" + pos[0] + " " + pos[1] + ")";
		String ending = "(" + end[0] + " " + end[1] + ")";
		String path = "path from " + starting + " to " + ending;
		final String hasAPath = theResult ? "True, there is a " + path
				: "False, there is not a " + path;
		System.out.println(hasAPath + "\n");
	}


}
