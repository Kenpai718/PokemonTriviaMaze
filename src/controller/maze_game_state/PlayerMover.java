package controller.maze_game_state;

import model.Maze;
import model.Room;

/**
 * Controls logic to make a player move in the maze.
 * Sets the win/lose condition of the maze game
 * everytime a player is moved.
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
	public void movePlayer(final Boolean theIsCorrect) {

		if (theIsCorrect) { // answered correctly
			doCorrect();
		} else { // answered incorrectly
			doIncorrect();
		}

	}
	
	/**
	 * Teleport player to specified cordinates
	 * Sets if they have won or lost in this location.
	 * @param thePos
	 * 
	 */
	public void teleportPlayer(int[] thePos) {
		myMaze.setPlayerLocation(thePos);
		myMaze.setWinCondition();
		myMaze.setLoseCondition();
	}

	/**
	 * Player answered correctly. Unlock the room and move player into the
	 * attempted location. Check/set the win condition after the move.
	 */
	private void doCorrect() {
		final Room curr = myMaze.getCurrRoom();
		final Room attempt = myMaze.getAttemptRoom();
		curr.setVisited(true);
		attempt.setVisited(true);
		myMaze.setPlayerLocation(myMaze.getAttemptedLocation());
		myMaze.setWinCondition();
	}

	/**
	 * Player answered incorrectly. Block the attempted room and check/set the
	 * lose condition.
	 */
	private void doIncorrect() {
		final Room curr = myMaze.getCurrRoom();
		final Room attempt = myMaze.getAttemptRoom();
		attempt.setEntry(false); // block that room
		// reset attempt location to default
		myMaze.setAttemptLocation(myMaze.getPlayerLocation());
		myMaze.setLoseCondition();

	}

}
