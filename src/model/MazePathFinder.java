package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

/**
 * This class is used to check a Maze for if there is still a path from a start
 * point to an end point. Logic used to figure out the maze lose condition.
 * 
 * In general this class should only be used after the user answered a question.
 * 
 * @author Kenneth Ahrens
 */

public final class MazePathFinder {

	/**
	 * Maze to search in
	 */
	private final static Maze myMaze = Maze.getInstance();

	/**
	 * This is the helper method called to check if there is a path in the
	 * theMaze given a start point and an endpoint.
	 * 
	 * @param int[]    start position of the player
	 * @param int[]    target the end goal we are checking a path for
	 * @return boolean true there is a path, false there is not a path
	 */
	public static Boolean checkForPath(final int[] start, final int[] target) {
		// get parameters to make a matrix of the same size
		Room[][] mazeRooms = myMaze.getMatrix();
		int rows = myMaze.getRows();
		int cols = myMaze.getCols();
		// visited is all false by default and same size as theMaze
		// it is used to track which rooms the dfs has visited
		boolean[][] visited = new boolean[rows][cols];

		// printAllRoomStatus(theMaze);
		// System.out.println("now checking for a path if player has lost");
		boolean result = hasPath(mazeRooms, visited, start, target);
		
		//TODO: delete this print
		printResult(result);
		return result;

	}
	
	/**
	 * Helper method that prints if a path was found ot not
	 * @param theResult
	 */
	private static void printResult(final boolean theResult) {
		String hasAPath = theResult ? "True, there is a path"
				: "False, there is not a path";
		System.out.println(hasAPath + "\n");
	}

	/**
	 * This is the overloaded helper method called to check if there is a path
	 * in a passed in maze matrix and given a start point and an endpoint.
	 * 
	 * @param Room[][] the matrix of rooms
	 * @param int[]    start position of the player
	 * @param int[]    target the end goal we are checking a path for
	 * @return boolean true there is a path, false there is not a path
	 */
	public static Boolean checkForPath(final Room[][] theMaze,
			final int[] start, final int[] target) {
		// get parameters to make a matrix of the same size
		int rows = theMaze.length;
		int cols = theMaze[0].length;
		// visited is all false by default and same size as theMaze
		// it is used to track which rooms the dfs has visited
		boolean[][] visited = new boolean[rows][cols];

		// print results for debugging
		printAllRoomStatus(theMaze);
		System.out.println("now checking for a path if player has lost");
		boolean result = hasPath(theMaze, visited, start, target);
		
		printResult(result);
		return result;

	}
	

	/**
	 * Use a recursive dfs to check all possible paths for if there is a path to
	 * the target in the theMaze
	 * 
	 * @param boolean[][] the theMaze to check for a path
	 * 
	 * @param boolean[][] visited array to denote which cells have been visited.
	 *                    T = visit
	 * 
	 * @param int[]       the current attempted move location
	 * 
	 * @param int[]       the target location we are looking for
	 * 
	 * @return boolean True = has path, false = no path
	 */
	private static boolean hasPath(Room[][] theMaze, boolean[][] visited,
			int[] current, int[] end) {
		// the current move position
		int x = current[0];
		int y = current[1];

		// if not in bounds not a valid route break from this path
		if (!inBounds(theMaze, visited, current)) {
			return false;
		}

		// target found return path found
		if (x == end[0] && y == end[1])
			return true;

		// attempt every direction
		// checking most common directions first closest to the goal

		// left
		if (hasPath(theMaze, markPresent(visited, current),
				new int[] { x - 1, y }, end)) {
			return true;
		}
		// right
		if (hasPath(theMaze, markPresent(visited, current),
				new int[] { x + 1, y }, end)) {
			return true;
		}
		// up
		if (hasPath(theMaze, markPresent(visited, current),
				new int[] { x, y + 1 }, end)) {
			return true;
		}
		// down
		if (hasPath(theMaze, markPresent(visited, current),
				new int[] { x, y - 1 }, end)) {
			return true;
		}

		// no solution on this path
		return false;
	}

	/**
	 * Mark in the visited matrix which rooms have been visited. Use a deep copy
	 * to avoid interferance with subsequent recursive calls of hasPath().
	 * 
	 * @param boolean[][] visited
	 * @param int[][]     current
	 * @return new visited array copy
	 */
	private static boolean[][] markPresent(boolean[][] visited, int[] current) {
		// deep copy so each one has a unique visited list
		boolean[][] copy = new boolean[visited.length][visited[0].length];
		for (int i = 0; i < copy.length; i++) {
			copy[i] = Arrays.copyOf(visited[i], visited[i].length);
		}

		copy[current[0]][current[1]] = true; // mark current location visited
		return copy;
	}

	/**
	 * Check if the current position is blocked. Blocked is when = false;
	 * 
	 * @param theMaze
	 * @param pos
	 * @return isBlocked
	 */
	private static boolean isBlocked(Room[][] theMaze, int[] pos) {
		Room r = theMaze[pos[0]][pos[1]];
		if (!r.canEnter()) {
			//TODO: delete println once guaranteed it works
			System.out.println(pos[0] + ", " + pos[1] + " is blocked.");
			return true;
		}
		return false;
	}

	/**
	 * Checks if the current position is within bounds of the maze or is allowed
	 * to enter the cell based on the rules of the game.
	 * 
	 * @param theMaze
	 * @param visited
	 * @param pos
	 * @return boolean if the cell can be entered or not
	 */
	private static boolean inBounds(final Room[][] theMaze,
			final boolean[][] visited, final int[] pos) {
		int rows = theMaze.length;
		int cols = theMaze[0].length;
		int x = pos[0];
		int y = pos[1];

		// check if in bounds
		if (x < 0 || y < 0 || x >= rows || y >= cols) {
			return false;
		}

		// don't go to a blocked room
		if (isBlocked(theMaze, pos)) {
			return false;
		}

		// don't do a visited path again to avoid cycles
		if (visited[x][y]) {
			return false;
		}

		return true;

	}

	/**
	 * Makes an matrix that shows which rooms are blocked
	 * 
	 * @param theRooms
	 * @return converted maze matrix into boolean for entry status T = can pass,
	 *         F = blocked
	 */
	public static boolean[][] convertRoomsToBoolean(final Room[][] theMaze) {
		int rows = theMaze.length;
		int cols = theMaze[0].length;
		boolean[][] converted = new boolean[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Room r = theMaze[i][j];
				if (r.canEnter()) {
					converted[i][j] = true;
				}
			}
		}

		return converted;
	}

	/*
	 * Debugger to check all rooms for blocked or unblocked
	 */
	public static void printAllRoomStatus(Room[][] theMaze) {
		int rows = theMaze.length;
		int cols = theMaze[0].length;

		for (int i = 0; i < rows; i++) {
			System.out.println();
			for (int j = 0; j < cols; j++) {
				System.out.print(theMaze[i][j].canEnter() + " ");
			}
		}
	}

}
