package controller.movement_actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import view.PokemonPanel;

/**
 * Make player move left
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */
public class LeftAction extends AbstractMovementAction {
	
	private static final String NAME = "LEFT";
	private static final ImageIcon ICON = new ImageIcon("./src/images/arrows/left.png");
	/*left arrow key*/
	private final KeyStroke myKey;
	
	
	public LeftAction(final PokemonPanel thePanel) {
		super("", ICON, thePanel);
		myKey = KeyStroke.getKeyStroke(NAME);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move left if possible
	        final int[] move = new int[] {0, -1};
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
