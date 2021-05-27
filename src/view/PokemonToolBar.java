package view;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;

/**
 * Make player move left
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class PokemonToolBar extends JToolBar{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4929301655633315425L;

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
