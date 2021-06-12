package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import view.PokemonPanel;

/**
 * Make player move right
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class RightAction extends AbstractMovementAction {
	
        /*
         * The Constants of this Action, the NAME and the ICON
         */
        private static final String NAME = "RIGHT";
	private static final ImageIcon ICON = new ImageIcon("./src/images/arrows/right.png");
	
	/**right arrow key*/
	private final KeyStroke myKey;
	
	/**
         * Calls the super and passes the NAME and the ICON, and Intializes the KeyStroke
         * key for movement
         * 
         * @param thePanel
         */
	public RightAction(final PokemonPanel thePanel) {
		super("", ICON, thePanel);
		myKey = KeyStroke.getKeyStroke(NAME);
	}

	/**
         * Gets the position for down and moves the player
         */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move right if possible
	        final int[] move = new int[] {0, 1};
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
