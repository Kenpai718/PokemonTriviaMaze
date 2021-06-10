package controller.movement_actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import view.PokemonPanel;

/**
 * Make player move up
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class UpAction extends AbstractMovementAction {
	
	private static final String NAME = "UP";
	private static final ImageIcon ICON = new ImageIcon("./src/images/arrows/up.png");
	/** Up arrow key */
	private final KeyStroke myKey;
	
	public UpAction(final PokemonPanel thePanel) {
		super("", ICON, thePanel);
		myKey = KeyStroke.getKeyStroke(NAME);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move up if possible
	        final int[] move = new int[] {-1, 0};
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
