package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class LeftAction extends AbstractAction {
	
	private static final String NAME = "LEFT";
	private static final ImageIcon ICON = new ImageIcon("./images/arrows/left.png");
	
	//to control the maze
	//private final Maze myMaze;
	
	public LeftAction() {
		super(NAME, ICON);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move left if possible
		
	}

}
