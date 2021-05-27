package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import view.PokemonPanel;

/**
 * Move down on maze
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class DownAction extends MovementAction {
	private static final String NAME = "DOWN";
	private static final ImageIcon ICON = new ImageIcon("./src/images/arrows/down.png");
	
	//to control the maze
	//private final Maze myMaze;
	
	public DownAction(final PokemonPanel thePanel) {
		super("", ICON, thePanel);
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

}
