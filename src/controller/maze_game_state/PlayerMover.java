package controller.maze_game_state;

import model.Maze;
import model.Room;

/**
 * Controls logic to make a player
 * move in the maze.
 * 
 * @author Kenneth Ahrens
 *
 */
public class PlayerMover {
	
	/*
	 * Maze the player gets moved in
	 */
	private final Maze myMaze = Maze.getInstance();

	
	/**
	 * User answered from a room panel question gui. Read a boolean for if they
	 * were right/wrong. Update player/attempt statuses and locations in maze
	 * based on answer result.
	 * 
	 * 
	 * @param Boolean theRes true = user answered correctly set curr and attempt
	 *                room to visited, false = incorrect, set visited false and
	 *                block the room
	 */
	public void movePlayer(final Boolean theResult) {

		final Room curr = myMaze.getCurrRoom();
		final Room attempt = myMaze.getAttemptRoom();
		if (theResult) { // answered correctly
			// set current player room and attempted room to visited
			curr.setVisited(theResult);
			attempt.setVisited(theResult);
			myMaze.setPlayerLocation(myMaze.getAttemptedLocation());
		} else { // answered incorrectly
			attempt.setEntry(false); // block that room
			// reset attempt location to default
			myMaze.setAttemptLocation(myMaze.getPlayerLocation());
		}

	}

}
