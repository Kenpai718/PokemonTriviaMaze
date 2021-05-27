package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import model.Maze;
import model.Pokedex;
import model.Pokemon;
import model.Room;
import view.viewHelper.LabelPanel;
import view.viewHelper.MazeGUI.MazeModel;

/**
 * Menubar for the trivia game Has file, help menus.
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class PokemonMenuBar extends JMenuBar {

	private JMenu myHelpMenu;
	private JMenu myFileMenu;
	private JMenu myGamemodeMenu;
	private ButtonGroup myGamemodes;
	private final Maze myMaze;
	private final JFrame myFrame;
	private final PokemonPanel myPanel;

	/**
	 * The constructor for the Menu Bar that sets up the the fields and starts
	 * the menubar gui setup
	 * 
	 * @param theFrame
	 */
	public PokemonMenuBar(final PokemonGUI theFrame) {
		// TODO Auto-generated constructor stub
		super();
		myFrame = theFrame.getFrame();
		myPanel = theFrame.getPanel();
		myMaze = Maze.getInstance();
		setupMenuBar();
	}

	/**
	 * Adds the base menus to the menu bar
	 */
	private void setupMenuBar() {

		myFileMenu = new JMenu("File");
		setupFileMenu();
		this.add(myFileMenu);

		myHelpMenu = new JMenu("Help");
		setupHelpMenu();
		this.add(myHelpMenu);

		myGamemodeMenu = new JMenu("Gamemode");
		setupGamemodesMenu();
		this.add(myGamemodeMenu);

	}

	/**
	 * Sets up the file menu
	 */
	private void setupFileMenu() {
		// TODO Auto-generated method stub
		final JMenuItem save = new JMenuItem("Save");
		myFileMenu.add(save);

		final JMenuItem load = new JMenuItem("Load");
		myFileMenu.add(load);

		myFileMenu.addSeparator();

		// TODO: game reset button
		final JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(new ResetListener());
		myFileMenu.add(reset);

		myFileMenu.addSeparator();

		final JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(theEvent -> System.exit(0));
		myFileMenu.add(exit);
	}

	/**
	 * Sets up the Help Menu,
	 */
	private void setupHelpMenu() {
		// TODO Auto-generated method stub

		final JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(myFrame,
						"Created by: AJ Downey, Kenneth Ahrens, "
								+ "Katelyn Malone\n Spring 2021",
						"About", JOptionPane.PLAIN_MESSAGE);
			}

		});
		myHelpMenu.add(about);

		final JMenuItem tutorial = new JMenuItem("Tutorial");
		tutorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				JOptionPane.showMessageDialog(myFrame, "This is a Tutorial!",
						"About", JOptionPane.PLAIN_MESSAGE);
			}
		});
		myHelpMenu.add(tutorial);
		setUpCheats();

	}

	/**
	 * Sets up the cheats menu for all of the dev cheats for debugging
	 */
	private void setUpCheats() {
		final JMenu cheats = new JMenu("Cheats");
		myHelpMenu.add(cheats);

		final JCheckBoxMenuItem reveal = new JCheckBoxMenuItem(
				"Reveal Pokemon");
		reveal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final boolean selected = reveal.isSelected();
				myPanel.setImage();
				if (selected) {
					myPanel.setImgBrightness(1);
				} else {
					myPanel.setImgBrightness(0);
				}

			}
		});
		cheats.add(reveal);

		final JCheckBoxMenuItem answer = new JCheckBoxMenuItem("Show Answer");
		answer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final boolean selected = answer.isSelected();
				LabelPanel labels = myPanel.getLabelPanel();
				myPanel.setImage();
				if (selected) {
					labels.enableShowAnswer(true);
				} else {
					labels.enableShowAnswer(false);
				}

			}
		});
		cheats.add(answer);

		cheats.addSeparator();

		final JMenuItem unlock = new JMenuItem("Unlock All Doors");
		unlock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				final Room[][] rooms = myMaze.getMatrix();
				for (int i = 0; i < myMaze.getRows(); i++) {
					for (int j = 0; j < myMaze.getCols(); j++) {
						rooms[i][j].setVisited(true);
						repaint();
					}
				}
			}

		});
		cheats.add(unlock);

		final JMenuItem removeBlocked = new JMenuItem("Reset Blocked Rooms");
		removeBlocked.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				final Room[][] rooms = myMaze.getMatrix();
				for (int i = 0; i < myMaze.getRows(); i++) {
					for (int j = 0; j < myMaze.getCols(); j++) {
						rooms[i][j].setEntry(true);
						repaint();
					}
				}
			}

		});
		cheats.add(removeBlocked);

		final JMenuItem insert = new JMenuItem("Insert New Pokemon");
		insert.addActionListener(new InsertListener());
		cheats.add(insert);

		final JMenuItem teleport = new JMenuItem("Teleport");
		teleport.addActionListener(new TeleportListener());
		cheats.add(teleport);

	}

	/**
	 * Sets up the gamemode menu for changing the gamemode
	 */
	private void setupGamemodesMenu() {

		myGamemodes = new ButtonGroup();
		final JRadioButtonMenuItem choice = new JRadioButtonMenuItem(
				"Multiple Choice");
		myGamemodeMenu.add(choice);
		myGamemodes.add(choice);
		choice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// System.out.println("action fired");
				// myPanel.setPanels(true);
				firePropertyChange("changegm", null, true);

			}

		});

		final JRadioButtonMenuItem input = new JRadioButtonMenuItem(
				"User Input");
		myGamemodeMenu.add(input);
		myGamemodes.add(input);
		input.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// System.out.println("action fired");
				// myPanel.setPanels(false);
				firePropertyChange("changegm", null, false);

			}

		});

		// set initial gamemodechoice
		choice.setSelected(true);

	}

	/* Listener classes */
	
	/**
	 * Resets the maze to start a new game
	 * 
	 * @author ken
	 *
	 */
	class ResetListener implements ActionListener {

		/**
		 * Displays a input dialog that reads the new location and moves the
		 * player to that location
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			myMaze.reset();
			myPanel.refreshGUI();
			
		}

	}

	/**
	 * The action listener to add a new Pokemon to the maze at a specified
	 * location
	 * 
	 * @author Kenneth Ahrens
	 *
	 */
	class InsertListener implements ActionListener {

		/*
		 * Icon for ditto
		 */
		private final ImageIcon myDittoIcon = new ImageIcon(
				"./src/images/other/dittoicon.gif");

		/*
		 * Abra icon to represent teleport
		 */
		private final ImageIcon myTeleportIcon = new ImageIcon(
				"./src/images/other/abra_teleport.gif");

		/*
		 * Data map
		 */
		private final Pokedex myPokedex;
		/*
		 * New pokemon to add
		 */
		private Pokemon myNewMon;
		/*
		 * Position to add new pokemon
		 */
		private int[] myPos;
		/*
		 * Check if player did not cancel the option pane
		 */
		private Boolean myCancel;

		public InsertListener() {
			myPokedex = Pokedex.getInstance();
			myCancel = false;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final String promptCords = "Where to put a new Pokemon in maze? "
					+ "\n(X Y):";
			final String promptPokemon = "What is the name of the Pokemon?";
			myPos = readCordinateInput(promptCords, myTeleportIcon);
			if (myPos[0] != -1) {
				myNewMon = readNewPokemonInput(promptPokemon);
				if (!myCancel) {
					putPokemon(myPos, myNewMon);
				}
			}

		}

		/**
		 * Try putting a new Pokemon
		 * 
		 * @param thePos
		 * @param theNewPkmn
		 */
		public void putPokemon(final int[] thePos, final Pokemon theNewPkmn) {
			try {
				Room r = myMaze.getRoom(thePos[0], thePos[1]);
				r.setNewPokemon(theNewPkmn);
				myPanel.refreshGUI();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/**
		 * Prompt user for a pokemon and looks for that pokemon name in the
		 * pokedex
		 * 
		 * @param theMsg
		 * @return user inputed Pokemon if found
		 */
		public Pokemon readNewPokemonInput(final String theMsg) {
			final String input = (String) JOptionPane.showInputDialog(null,
					theMsg, "Type a Pokemon name to insert",
					JOptionPane.INFORMATION_MESSAGE, myDittoIcon, null, "");
			final Scanner scan;
			Pokemon res = myPokedex.findPokemon(0);
			if (input != null && !input.isEmpty()) {
				myCancel = false;
				if (myPokedex.hasPokemon(input)) {
					res = myPokedex.findPokemon(input);
				} else {
					readNewPokemonInput(input
							+ " does not exist in Pokedex. Try again with a new name!");
				}
			} else {
				myCancel = true;
			}

			return res;
		}

	}

	/**
	 * The action listener for the teleport cheat.
	 * 
	 * @author ajdow
	 *
	 */
	class TeleportListener implements ActionListener {

		/*
		 * Abra icon to represent teleport
		 */
		private final ImageIcon myTeleportIcon = new ImageIcon(
				"./src/images/other/abra_teleport.gif");

		/**
		 * Displays a input dialog that reads the new location and moves the
		 * player to that location
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			// TODO Auto-generated method stub
			final String message = "Please enter a new position to teleport"
					+ " to.\n(X Y):";
			final int[] pos = readCordinateInput(message, myTeleportIcon);

			if (pos[0] != -1) {
				myMaze.setPlayerLocation(pos);
				myPanel.refreshGUI();
			}
		}

	}

	/**
	 * Helper method to read the input from the Input Dialog Used for the
	 * teleport cheat
	 * 
	 * @param theInput a string of the input
	 * @param icon     for the option pane
	 * @return an int[] of the two numbers input
	 */
	private int[] readCordinateInput(final String theMessage,
			final ImageIcon theIcon) {
		final String input = (String) JOptionPane.showInputDialog(null,
				theMessage, "Choose teleport location",
				JOptionPane.INFORMATION_MESSAGE, theIcon, null, "");
		int[] res = myMaze.getPlayerLocation().clone();
		final Scanner scan;
		if (input != null && !input.isEmpty()) {
			if (!(input.length() < 3)) {
				scan = new Scanner(input.toString());
				try {
					for (int i = 0; i < 2; i++) {
						final int num = scan.nextInt() - 1;
						if (num < myMaze.getRows() && num < myMaze.getCols()
								&& num >= 0) {
							res[i] = num;
						} else {
							res = readCordinateInput(
									"One or more numbers out "
											+ "of range of maze\n(X Y):",
									theIcon);
							break;
						}
					}
				} catch (final InputMismatchException e) {
					res = readCordinateInput(
							"Please use integers only.\n(X Y):", theIcon);
				}
				scan.close();
			} else {
				res = readCordinateInput("Invalid Input\n(X Y):", theIcon);
			}
		} else {
			//put -1 to signify user canceled
			res = new int[] {-1, -1};
		}

		return res;

	}

}
