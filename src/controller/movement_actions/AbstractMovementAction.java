/**
 * 
 */
package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import exceptions.InvalidMovementException;
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

	/**
	 * Does nothing, since the child classes are handling it
	 */
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
		try {
                        myMaze.setAttemptLocation(newPos);
                        if (myMaze.getAttemptRoom().hasVisited()) {
                        	myMaze.setPlayerLocation(newPos);
                        }
                } catch (final InvalidMovementException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
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

	/**
	 * Returns the name of the action
	 * 
	 * @return the name of the action
	 */
	public abstract String getName();

}
