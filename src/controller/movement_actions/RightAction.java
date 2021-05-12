package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class RightAction extends AbstractAction {
	private static final String NAME = "RIGHT";
	private static final ImageIcon ICON = new ImageIcon("./images/arrows/right.png");
	
	//to control the maze
	//private final Maze myMaze;
	
	public RightAction() {
		super(NAME, ICON);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move left if possible
		
	}

}
