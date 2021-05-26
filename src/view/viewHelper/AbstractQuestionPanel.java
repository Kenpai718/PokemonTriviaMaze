package view.viewHelper;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Maze;
import model.Room;
import view.PokemonPanel;
import view.viewHelper.MazeGUI.MazeModel;

/**
 * Used for QuestionRoomGUI and TextRoomGUI for behaviors that they both have.
 * 
 * Main use is to verify answers and changes maze player location on correct and
 * incorrect answers.
 * 
 * Abstract to prevent instantiation because it does nothing on its own.
 * Technically a controller and a view.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public abstract class AbstractQuestionPanel extends JPanel {

	private Maze myMaze;
	private PokemonPanel myPP;

	public AbstractQuestionPanel(PokemonPanel thePP) {
		super();
		myPP = thePP;
		myMaze = Maze.getInstance();
	}

	/**
	 * Compare user answer and the actual answer and tell user if it was correct
	 * via option pane.
	 * 
	 * @param String theUserAns to compare to the correct answer
	 */
	public void verifyAnswer(final String theUserAns) {

		final Room curr = myMaze.getAttemptRoom();
		String correctAns = curr.getAnswer();
		final String correct = correctAns + " was the correct answer!";
		final String incorrect = "Sorry, but " + theUserAns
				+ " is incorrect... ";

		// format answer to prevent errors
		correctAns = correctAns.toLowerCase().strip();
		String userAns = theUserAns.toLowerCase().strip();
		Boolean isCorrect = userAns.equals(correctAns);

		// call method to change the maze
		doUserAnswer(isCorrect);

		if (isCorrect) {

			firePropertyChange("showpkmn", null, true);
			JOptionPane.showMessageDialog(null, "Good job! " + correct,
					"Correct!", JOptionPane.INFORMATION_MESSAGE);

		} else { // incorrect

			firePropertyChange("showpkmn", null, true);
			JOptionPane.showMessageDialog(null, incorrect + correct,
					"Incorrect!", JOptionPane.INFORMATION_MESSAGE);
		}

		updateGUI();

	}

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
	private void doUserAnswer(Boolean theResult) {

		Room curr = myMaze.getCurrRoom();
		Room attempt = myMaze.getAttemptRoom();
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

	/*
	 * Update gui components to ensure everything is current
	 */
	private void updateGUI() {

		this.enableButtons(false);
		firePropertyChange("newpos", null, null);
		myPP.refreshGUI();

		if (myMaze.isWinCondition()) {
			firePropertyChange("win", null, null);
		}

		// TODO: if maze isLoseCondition() fire lose

	}

	/**
	 * Enable or disable the answer fields
	 */
	public abstract void enableButtons(Boolean theBool);

	/**
	 * alter the answer fields to the default
	 */
	public abstract void setButtons();

}
