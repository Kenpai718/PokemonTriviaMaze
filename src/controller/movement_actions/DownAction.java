package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import view.PokemonPanel;
import view.viewHelper.ControlPanel;

public class DownAction extends MovementAction {
	private static final String NAME = "";
	private static final ImageIcon ICON = new ImageIcon(ControlPanel.class.getResource("/arrows/down.png"));
	
	//to control the maze
	//private final Maze myMaze;
	
	public DownAction(final PokemonPanel thePanel) {
		super(NAME, ICON, thePanel);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move left if possible
	        final int[] move = new int[] {1, 0};
                super.movePlayer(move);
	}

}
