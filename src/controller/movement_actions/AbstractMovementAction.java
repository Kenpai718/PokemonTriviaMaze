/**
 * 
 */
package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.Maze;
import view.PokemonPanel;
import view.viewHelper.LabelPanel;

/**
 * Controls player movement in the maze
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */
public abstract class AbstractMovementAction extends AbstractAction {

	/*
	 * What needs to be updated after doing this action
	 */
	protected Maze myMaze = Maze.getInstance();
	/*
	 * Game panel that holds everything
	 */
	private final PokemonPanel myPanel;

	/*
	 * Icon of the action
	 */
	private final ImageIcon myIcon;

	/**
	 * 
	 * @param theName
	 * @param theIcon
	 * @param thePanel
	 */
	protected AbstractMovementAction(final String theName,
			final ImageIcon theIcon, final PokemonPanel thePanel) {
		super(theName, theIcon);
		myIcon = theIcon;
		myPanel = thePanel;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		// Do nothing
	}

	/**
	 * Move player/attempted location in given direction
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
			myMaze.setPlayerLocation(newPos);
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
		myPanel.getLabelPanel().setDir(this.getName());
		myPanel.refreshGUI();

	}

	/*
	 * To get the assigned keystroke of the movement
	 */
	public abstract KeyStroke getMovementKey();

	public abstract String getName();

}
