package controller.movement_actions;

import java.awt.event.ActionEvent;

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
	
        /*
         * The Constants of this ACtion, the NAME and the ICON
         */
	private static final String NAME = "UP";
	private static final ImageIcon ICON = new ImageIcon("./src/images/arrows/up.png");
	
	/** Up arrow key */
	private final KeyStroke myKey;
	
	/**
         * Calls the super and passes the NAME and the ICON, and Intializes the KeyStroke
         * key for movement
         * 
         * @param thePanel
         */
	public UpAction(final PokemonPanel thePanel) {
		super("", ICON, thePanel);
		myKey = KeyStroke.getKeyStroke(NAME);
	}

	/**
         * Gets the position for down and moves the player
         */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move up if possible
	        final int[] move = new int[] {-1, 0};
	        super.movePlayer(move);
		
	}

	/**
         * The Action toString
         */
	@Override
	public String toString() {
		return NAME;
	}

	/**
         * Gets the movement key
         */
	@Override
	public KeyStroke getMovementKey() {
		// TODO Auto-generated method stub
		return myKey;
	}

	/**
         * Gets the name of the Action
         */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}
}
