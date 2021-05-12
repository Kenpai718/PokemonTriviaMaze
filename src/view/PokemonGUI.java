/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;

import controller.movement_actions.DownAction;
import controller.movement_actions.LeftAction;
import controller.movement_actions.RightAction;
import controller.movement_actions.UpAction;

import javax.swing.JList;

/**
 * @author Kenneth Ahrens
 *
 */
public class PokemonGUI {
	
	/* constants */
	
	/*
	 * Aspect Ratio of JFrame
	 */
	final int WIDTH = 1920, HEIGHT = 1080;
	
	/*Fields*/
	
	/*
	 * Icon of the program
	 */
	private ImageIcon myImageIcon = new ImageIcon("./images/TriviaIcon.png");
	
	/*
	 * Window for GUI
	 */
	private JFrame myFrame;
	
	
	/*
	 * Panel for the gameplay
	 */
	
	private PokemonPanel myPanel;
	
	/*
	 * Menubar
	 */
	private PokemonMenuBar myMB;
	
	/*
	 * Toolbar with controls
	 */
	private PokemonToolBar myTB;

	/*
	 * Array holding actions for controls
	 */
	private ArrayList<Action> myControlActions;
	
	/*
	 * ButtonGroup of movement controls
	 */
	private ButtonGroup myControlButtons;
	
	
	
	/*
	 * Constructor to initialize GUI components
	 */
	public PokemonGUI() {
		myFrame = new JFrame("Pokemon Trivia");
		myPanel = new PokemonPanel();
		myMB = new PokemonMenuBar();
		myTB = new PokemonToolBar();
		myControlActions = new ArrayList<Action>();
		myControlButtons = new ButtonGroup();
		
		setupGUI();
		setupControlButtons();
	}
	
	/*
	 * Helper to initialize GUI components
	 */
	private void setupGUI() {
		//set name and icon
		myFrame.setIconImage(myImageIcon.getImage());
		
		//set size
		myFrame.setSize(WIDTH, HEIGHT);
		
		//setup game panel and add to frame
		myFrame.add(myPanel);
		
		//add menubar
		myFrame.add(myMB, BorderLayout.NORTH);
		
		//add toolbar
		myFrame.add(myTB, BorderLayout.SOUTH);
		
		
		
		
		//finalize frame
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
		myFrame.setResizable(false);
	}
	
	private void setupControlButtons() {
		myControlActions.add(new LeftAction());
		myControlActions.add(new RightAction());
		myControlActions.add(new UpAction());
		myControlActions.add(new DownAction());
		
		for (final Action act : myControlActions) {
			final JToggleButton tb = new JToggleButton(act);
			myControlButtons.add(tb);
			myTB.add(tb);

		}
		
	}
	


}
