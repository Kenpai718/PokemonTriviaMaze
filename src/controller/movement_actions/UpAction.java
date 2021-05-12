package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class UpAction extends AbstractAction{
	
	private static final String NAME = "UP";
	private static final ImageIcon ICON = new ImageIcon("./images/arrows/up.png");
	
	//to control the maze
	//private final Maze myMaze;
	
	public UpAction() {
		super(NAME, ICON);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//make the maze move left if possible
		
	}

}
