package controller.movement_actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import view.PokemonPanel;

/**
 * Move down on maze
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class DownAction extends AbstractMovementAction {
	private static final String NAME = "DOWN";
	private static final ImageIcon ICON = new ImageIcon("./src/images/arrows/down.png");
	
	/**Down arrow key*/
	private final KeyStroke myKey;
	
	
	public DownAction(final PokemonPanel thePanel) {
		super("", ICON,thePanel);
		myKey = KeyStroke.getKeyStroke(NAME);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		//make the maze move down if possible
	        final int[] move = new int[] {1, 0};
                super.movePlayer(move);
	}
	
	@Override
	public String toString() {
		return NAME;
	}

	@Override
	public KeyStroke getMovementKey() {
		// TODO Auto-generated method stub
		return myKey;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}

}
