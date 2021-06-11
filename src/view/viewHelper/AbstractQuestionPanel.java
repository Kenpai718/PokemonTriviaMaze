package view.viewHelper;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.AnswerFormatter;
import controller.maze_game_state.PlayerMover;
import model.Maze;
import model.Room;
import view.PokemonPanel;

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
	/*
	 * Sizes to be shared amongst question panels
	 */
	public final int DEFAULT_WIDTH = 350;
	public final int DEFAULT_HEIGHT = 500;
	public final Dimension DEFAULT_DIM = new Dimension(WIDTH, HEIGHT);

	/*
	 * Icons for the option pane
	 */
	private final ImageIcon CORRECT_ICON = new ImageIcon("./src/images/other/correct_icon.png");
	private final ImageIcon INCORRECT_ICON = new ImageIcon("./src/images/other/incorrect_icon.png");

	/*
	 * Maze
	 */
	private final Maze myMaze;
	/*
	 * Game panel
	 */
	private final PokemonPanel myPP;

	/*
	 * Class that moves the player after an answer
	 */
	private final PlayerMover myMover;

	/**
	 * Constructor
	 * 
	 * @param thePP so the panel can be modified after user answers
	 */
	public AbstractQuestionPanel(final PokemonPanel thePP) {
		super();
		myPP = thePP;
		myMaze = Maze.getInstance();
		myMover = new PlayerMover();
	}

	/**
	 * Compare user answer and the actual answer and tell user if it was correct via
	 * option pane.
	 * 
	 * @param String theUserAns to compare to the correct answer
	 */
	public void verifyAnswer(final String theUserAns) {

		final Room curr = myMaze.getAttemptRoom();
		String correctAns = curr.getAnswer();
		final String correct = correctAns + " was the correct answer!";

		// format answer to prevent errors
		correctAns = AnswerFormatter.formatAnswer(correctAns);
		final String userAns = AnswerFormatter.formatAnswer(theUserAns);
		final Boolean isCorrect = userAns.equals(correctAns);

		// myPP.setShowQMark(); //turn off q mark because the user answered

		if (isCorrect) {
			// myPP.setMyReveal(isCorrect);
			firePropertyChange("showpkmn", null, true);
			JOptionPane.showMessageDialog(null,
					("Great job!\n" + correct + "\nRoom " + myMaze.getAttemptRoom() + " is unlocked."), "Correct!",
					JOptionPane.INFORMATION_MESSAGE, CORRECT_ICON);

		} else { // incorrect
					// myPP.setMyReveal(isCorrect);
			firePropertyChange("showpkmn", null, false);
			final String incorrect = "Sorry, but " + theUserAns + " is incorrect... " + "\n" + correct + "\nRoom "
					+ myMaze.getAttemptRoom() + " is now blocked.";
			JOptionPane.showMessageDialog(null, incorrect, "Incorrect...", JOptionPane.INFORMATION_MESSAGE,
					INCORRECT_ICON);
			// if (myMaze.isLoseCondition())
			// firePropertyChange("lose", null, null);
		}

		// call method to change the player's position in maze
		myMover.movePlayer(isCorrect);

		// disable the buttons after the user answers
		this.enableButtons(false);
		myPP.refreshGUI(); // one full refresh of the gui

		// fire a win or lose condition based on current position after the answer
		checkWinLoseCondition();

	}

	/*
	 * Fire property changes for if the player has won or lost
	 */
	private void checkWinLoseCondition() {
		if (myMaze.hasWon()) {
			// System.out.println("in win panel");
			firePropertyChange("win", null, null);
		} else if (myMaze.hasLost()) {
			// System.out.println("in lose panel");
			firePropertyChange("lose", null, null);
		}

	}

	/**
	 * Enable or disable the answer fields
	 * 
	 * @param theBool if the user input buttons/fields should be enabled
	 */
	public abstract void enableButtons(Boolean theBool);

	/**
	 * alter the answer fields to the default
	 */
	public abstract void setButtons();

}
