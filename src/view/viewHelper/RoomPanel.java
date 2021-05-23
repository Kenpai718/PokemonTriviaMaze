package view.viewHelper;


import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Maze;
import model.Room;

/*
 * Used for QuestionRoomGUI and TextRoomGUI for behaviors that
 * they both have.
 */

public class RoomPanel extends JPanel {
	
	private Maze myMaze;

	public RoomPanel() {
		super();
		myMaze = Maze.getInstance();
	}

	/**
	 * Compare user answer and the actual answer and tell user if it was correct
	 * via option pane.
	 * 
	 * @param String theUserAns to compare to
	 */
	public void verifyAnswer(final String theUserAns) {
		
		final Room curr = myMaze.getCurrRoom();
		String correctAns = curr.getAnswer();
		final String correct = correctAns + " was the correct answer!";
		final String incorrect = "Sorry, but " + theUserAns
				+ " is incorrect... ";

		// format answer to prevent errors
		correctAns = correctAns.toLowerCase().strip();
		String userAns = theUserAns.toLowerCase().strip();

		firePropertyChange("showpkmn", null, true);
		if (userAns.equals(correctAns)) {
			JOptionPane.showMessageDialog(null, "Good job! " + correct,
					"Correct!", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, incorrect + correct,
					"Incorrect!", JOptionPane.INFORMATION_MESSAGE);
		}
		firePropertyChange("showpkmn", null, false);

	}

}
