package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import view.ControlPanel;

public class RightAction extends AbstractAction {
	private static final String NAME = "";
	private static final ImageIcon ICON = new ImageIcon(ControlPanel.class.getResource("/arrows/right.png"));
	
	//to control the maze
	//private final Maze myMaze;
	
	public RightAction() {
		super(NAME, ICON);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move left if possible
		
	}

}
