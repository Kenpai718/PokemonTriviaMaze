/**
 * 
 */
package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import model.Maze;
import view.PokemonPanel;

/**
 * Controls player movement in the maze
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */
public class MovementAction extends AbstractAction {

	protected Maze myMaze = Maze.getInstance();
	private final PokemonPanel myPanel;

	protected MovementAction(final String theName, final ImageIcon theIcon,
			final PokemonPanel thePanel) {
		super(theName, theIcon);
		myPanel = thePanel;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		// Do nothing
	}

	/**
	 * Move player in given direction
	 * 
	 * @param theMove the directions to move [0] row, [1] = col
	 */

	protected void movePlayer(final int[] theMove) {
		// System.out.println("clicked a button");
		final int[] newPos = myMaze.getPlayerLocation().clone();
		for (int i = 0; i < newPos.length; i++) {
			newPos[i] = newPos[i] + theMove[i];
		}

		// set the attempted move location of the direction pressed
		myMaze.setAttemptLocation(newPos);
		if (myMaze.getAttemptRoom().hasVisited()) {
//		        myPanel.setMyReveal(true);
			myPanel.setImgBrightness(); // keep pokemon revealed if it has been
			myMaze.setPlayerLocation(newPos);
		} else {
//		        myPanel.setMyReveal(false);
			myPanel.setImgBrightness();
		}
		updateGUI();

	}

	/**
	 * Update components of the GUI that are modifed when the player tries to
	 * move to a new room
	 * 
	 */
	private void updateGUI() {
		
//		firePropertyChange("newpos", null, null);
		myPanel.refreshGUI();

		if (myMaze.getAttemptRoom().hasVisited()) {
			myPanel.enableAnswerFields(false);
		} else {
			myPanel.enableAnswerFields(true);
		}

	}

}
