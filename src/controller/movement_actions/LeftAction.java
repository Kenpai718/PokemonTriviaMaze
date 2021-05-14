package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import view.viewHelper.ControlPanel;

public class LeftAction extends AbstractAction {
	
	private static final String NAME = "";
	private static final ImageIcon ICON = new ImageIcon(ControlPanel.class.getResource("/arrows/left.png"));
	
	//to control the maze
	//private final Maze myMaze;
	
	public LeftAction() {
		super(NAME, ICON);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move left if possible
		
	}

}
