/**
 * 
 */
package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import model.Maze;
import model.Room;
import view.PokemonPanel;
import view.viewHelper.MazeGUI.MazeModel;
import view.viewHelper.RoomPanel;

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
		//System.out.println("clicked a button");
		final int[] newPos = myMaze.getPlayerLocation().clone();
		for (int i = 0; i < newPos.length; i++) {
			newPos[i] = newPos[i] + theMove[i];
		}

		//set the attempted move location of the direction pressed
		myMaze.setAttemptLocation(newPos);
		updateGUI();

	}
	
	private void updateGUI() {
		final MazeModel model = (MazeModel) myPanel.getTable().getModel();
		model.refresh(myMaze.getMatrix());
		firePropertyChange("newpos", null, null);
		myPanel.setImage();
		myPanel.getQuestionGUI().setButtons();
	}

}
