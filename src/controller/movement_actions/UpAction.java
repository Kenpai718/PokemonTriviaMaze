package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import view.PokemonPanel;

public class UpAction extends MovementAction {
	
	private static final String NAME = "";
	private static final ImageIcon ICON = new ImageIcon("./src/images/arrows/up.png");
	
	//to control the maze
	//private final Maze myMaze;
	
	public UpAction(final PokemonPanel thePanel) {
		super(NAME, ICON, thePanel);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move left if possible
	        final int[] move = new int[] {-1, 0};
	        super.movePlayer(move);
		
	}

}
