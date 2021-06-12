package controller.movement_actions;

import java.awt.event.ActionEvent;

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
	
        /*
         * The Constants of this Action, the NAME and the ICON
         */
	private static final String NAME = "LEFT";
	private static final ImageIcon ICON = new ImageIcon("./src/images/arrows/left.png");
	
	/*left arrow key*/
	private final KeyStroke myKey;
	
	/**
         * Calls the super and passes the NAME and the ICON, and Initializes the KeyStroke
         * key for movement
         * 
         * @param thePanel
         */
	public LeftAction(final PokemonPanel thePanel) {
		super("", ICON, thePanel);
		myKey = KeyStroke.getKeyStroke(NAME);
	}

	/**
         * Gets the position for down and moves the player
         */
	@Override
	public void actionPerformed(final ActionEvent e) {
		//make the maze move left if possible
	        final int[] move = new int[] {0, -1};
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
		return myKey;
	}

	/**
         * Gets the name of the Action
         */
	@Override
	public String getName() {
		return NAME;
	}

}
