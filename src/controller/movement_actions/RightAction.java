package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import view.PokemonPanel;

/**
 * Make player move right
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class RightAction extends MovementAction {
	private static final String NAME = "RIGHT";
	private static final ImageIcon ICON = new ImageIcon("./src/images/arrows/right.png");
	
	public RightAction(final PokemonPanel thePanel) {
		super("", ICON, thePanel);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move right if possible
	        final int[] move = new int[] {0, 1};
                super.movePlayer(move);
	}

	@Override
	public String toString() {
		return NAME;
	}
}
