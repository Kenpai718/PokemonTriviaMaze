package view;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;

public class PokemonToolBar extends JToolBar{
	
	private ButtonGroup myControls;
	
	/*
	 * Icon of the program
	 */
	private ImageIcon UP = new ImageIcon("./images/arrows/up.png");
	private ImageIcon DOWN = new ImageIcon("./images/arrows/up.png");
	private ImageIcon LEFT = new ImageIcon("./images/arrows/up.png");
	private ImageIcon RIGHT = new ImageIcon("./images/arrows/up.png");
	

	public PokemonToolBar() {
		// TODO Auto-generated constructor stub
		super();
		setupToolBar();
	}
	
	private void setupToolBar() {
		myControls = new ButtonGroup();
		
	}

}
