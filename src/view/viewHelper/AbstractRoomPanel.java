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
 * Main use is to verify answers and sends property changes for correct and
 * incorrect answers.
 * 
 * Abstract to prevent instantiation because it does nothing on its own.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public abstract class AbstractRoomPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8127362927701254911L;
	private Maze myMaze;
	private PokemonPanel myPP;

	public AbstractRoomPanel(PokemonPanel thePP) {
		super();
		myPP = thePP;
		myMaze = Maze.getInstance();
		myMaze.addListener(this);
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

		firePropertyChange("showpkmn", null, true);

		// correct
		if (userAns.equals(correctAns)) {
			JOptionPane.showMessageDialog(null, "Good job! " + correct,
					"Correct!", JOptionPane.INFORMATION_MESSAGE);

			firePropertyChange("correctans", null, true);

		} else { // incorrect
			JOptionPane.showMessageDialog(null, incorrect + correct,
					"Incorrect!", JOptionPane.INFORMATION_MESSAGE);

			firePropertyChange("correctans", null, false);
		}

		updateGUI();

	}

	/*
	 * Update gui components to ensure everything is current
	 */
	private void updateGUI() {

		final MazeModel model = (MazeModel) myPP.getTable().getModel();
		model.refresh(myMaze.getMatrix());
		firePropertyChange("newpos", null, null);
		myPP.setImage();
		myPP.getQuestionGUI().setButtons();
		firePropertyChange("showpkmn", null, false);

		if (myMaze.isWinCondition()) {
			System.out.println("In room panel player wins");
			firePropertyChange("win", null, null);
		}

	}

}
