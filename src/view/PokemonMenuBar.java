package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.Maze;
import model.Pokedex;
import model.Pokemon;
import model.Room;
import view.viewHelper.LabelPanel;
import view.viewHelper.TutorialPanel;

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
	private JMenu myGenSelectMenu;
	private ArrayList<JCheckBox> myGenBoxList;
	private ButtonGroup myGamemodes;
	private Maze myMaze;
	private final JFrame myFrame;
	private final PokemonPanel myPanel;
	private boolean myReveal;
	private final TutorialPanel myTutorial;
	private final Pokedex myPokedex;
	

	/**
	 * The constructor for the Menu Bar that sets up the the fields and starts the
	 * menubar gui setup
	 * 
	 * @param theFrame
	 */
	public PokemonMenuBar(final PokemonGUI theFrame) {
		// TODO Auto-generated constructor stub
		super();
		myFrame = theFrame.getFrame();
		myPanel = theFrame.getPanel();
		myMaze = Maze.getInstance();
		myPokedex = Pokedex.getInstance();
		myTutorial = new TutorialPanel(myPanel);
		setupMenuBar();
		addListeners();

	}

	private void addListeners() {
		// TODO Auto-generated method stub
		this.addPropertyChangeListener(myPanel);
		this.addPropertyChangeListener(myPanel.getMazeGUI());
		this.addPropertyChangeListener(myPanel.getMyControlPanel());

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

		myGenSelectMenu = new JMenu("Select Pokemon Generation(s)");
		setupGenSelectMenu();
		this.add(myGenSelectMenu);

		final JList list = new JList();
		myGenSelectMenu.add(list);

		/*
		 * myGamemodeMenu = new JMenu("Gamemode"); setupGamemodesMenu();
		 * this.add(myGamemodeMenu);
		 */

	}

	/**
	 * Sets up the file menu
	 */
	private void setupFileMenu() {
		// TODO Auto-generated method stub
		final JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				final JFileChooser fileChooser = new JFileChooser("Save");
				if (fileChooser.showSaveDialog(myFrame) == JFileChooser.APPROVE_OPTION) {
					try (final FileOutputStream file = new FileOutputStream(fileChooser.getSelectedFile());
							final ObjectOutputStream out = new ObjectOutputStream(file);) {
						out.writeObject(myMaze);
						JOptionPane.showMessageDialog(null, "Maze has been Saved: " + myMaze);

					} catch (final FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (final IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});
		myFileMenu.add(save);

		final JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				final JFileChooser fileChooser = new JFileChooser("Load");
				if (fileChooser.showOpenDialog(myFrame) == JFileChooser.APPROVE_OPTION) {
					try (final FileInputStream file = new FileInputStream(fileChooser.getSelectedFile());
							final ObjectInputStream in = new ObjectInputStream(file);) {
						System.out.println("Start Load: " + myMaze);
						myMaze = (Maze) in.readObject();
						firePropertyChange("model", null, myMaze.getMatrix());
						myPanel.refreshGUI();
						JOptionPane.showMessageDialog(null, "Maze has been Loaded: " + myMaze);

					} catch (final FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (final IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (final ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});
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

			private final String aboutMessage = "Pokemon Trivia Maze\nCreated by: AJ Downey, Kenneth Ahrens, and Katelyn Malone"
					+ "\nSpring 2021\n"
					+ "\nDISCLAIMER:\n" 
					+ "This is a fan-made-non-profit project based on the \"Who's that Pokemon?\" segment from the original Pokemon anime." 
					+ "\nWe are not affliated or endorsed with the Pokemon company. All copyright belongs to Nintendo/Gamefreak.";

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(myFrame,
						aboutMessage, "About Us",
						JOptionPane.PLAIN_MESSAGE);
			}

		});
		myHelpMenu.add(about);

		final JMenuItem tutorial = new JMenuItem("Tutorial");
		tutorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				JOptionPane.showMessageDialog(myFrame, myTutorial, "How to play", JOptionPane.PLAIN_MESSAGE);
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

		final JCheckBoxMenuItem reveal = new JCheckBoxMenuItem("Reveal Pokemon");
		reveal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				myReveal = reveal.isSelected();
				myPanel.setMyReveal(myReveal);
				firePropertyChange("showpkmn", null, null);
				myPanel.refreshGUI();

			}
		});
		cheats.add(reveal);

		final JCheckBoxMenuItem answer = new JCheckBoxMenuItem("Show Answer");
		answer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final boolean selected = answer.isSelected();
				final LabelPanel labels = myPanel.getLabelPanel();
				myPanel.refreshGUI();
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
				boolean change = true;
				for (int i = 0; i < myMaze.getRows(); i++) {
					for (int j = 0; j < myMaze.getCols(); j++) {
						final Room room = rooms[i][j];
						change = change && room.hasVisited();
						rooms[i][j].setVisited(!change);
						if (room.isPlayerHere() || (i == 0 && j == 0)) {
							rooms[i][j].setVisited(true);
						}

//						repaint();
					}
				}
				if (change) {
					unlock.setText("Unlock All Doors");
				} else {
					unlock.setText("Relock All Doors");
				}
				myPanel.refreshGUI();
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
//						repaint();
					}
				}
				myPanel.refreshGUI();
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

//	/**
//	 * Sets up the gamemode menu for changing the gamemode
//	 */
//	private void setupGamemodesMenu() {
//
//		myGamemodes = new ButtonGroup();
//		final JRadioButtonMenuItem choice = new JRadioButtonMenuItem("Multiple Choice");
//		myGamemodeMenu.add(choice);
//		myGamemodes.add(choice);
//		choice.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(final ActionEvent e) {
//				// System.out.println("action fired");
//				// myPanel.setPanels(true);
//				firePropertyChange("changegm", null, 1);
//
//			}
//
//		});
//
//		final JRadioButtonMenuItem input = new JRadioButtonMenuItem("User Input");
//		myGamemodeMenu.add(input);
//		myGamemodes.add(input);
//		input.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(final ActionEvent e) {
//				// System.out.println("action fired");
//				// myPanel.setPanels(false);
//				firePropertyChange("changegm", null, 2);
//
//			}
//
//		});
//
//		// set initial gamemodechoice
//		choice.setSelected(true);
//
//	}

	/*
	 * Setup select checkbox for pokemon gen select menu
	 */
	private void setupGenSelectMenu() {

		// current supported pokemon generations
		final String[] gens = { "Gen 1 (1-151)", "Gen 2 (151-251)", "Gen 3 (251-386)", "Gen 4 (386-493)",
				"Gen 5 (493-649)", "Gen 6 (649-721)", "Gen 7 (721-809)" };
		
		myGenBoxList = new ArrayList<JCheckBox>();

		// add all to a checkbox list
		for (int i = 0; i < gens.length; i++) {
			final JCheckBox genBox = new JCheckBox(gens[i]);
			genBox.addActionListener(new GenSelectListener(i + 1, genBox));
			myGenSelectMenu.add(genBox);
			myGenBoxList.add(genBox);

			if (i == 0) {
				genBox.setSelected(true); // gen 1 selected by defau;t
			}
		}
	}

	/*
	 * Reset game to start with new pokemon gens
	 */
	private void resetAll() {
		myMaze.reset();
		firePropertyChange("model", null, myMaze.getMatrix());
		myPanel.refreshGUI();
	}

	/* Listener classes */

	/*
	 * Add a pokemon generation to the pokedex
	 */
	class GenSelectListener implements ActionListener {

		private final JCheckBox myBox;
		private final int myGen;

		public GenSelectListener(final int theNum, final JCheckBox theBox) {
			myGen = theNum;
			myBox = theBox;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			// TODO Auto-generated method stub
			String msg;
			if (myBox.isSelected()) { // add pokemon gen

				try {
					myPokedex.addGenToDex(myGen);
					msg = "Gen " + myGen + " Pokemon can now be encountered! Game has been reset.";
					JOptionPane.showMessageDialog(null, msg);
					resetAll();

				} catch (final Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (!myBox.isSelected()) {
				if (myPokedex.canRemoveGen()) { // remove pokemon gen
					try {
						myPokedex.removeGenFromDex(myGen);
						msg = "Gen " + myGen + " Pokemon have been removed. Game reset!";
						JOptionPane.showMessageDialog(null, msg);
						resetAll();
					} catch (final Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else { // cannot remove condition
					myBox.setSelected(true);
					msg = "Cannot remove Gen " + myGen + " because at least one generation must be selected to play!";
					JOptionPane.showMessageDialog(null, msg);

				}
			}

		}

	}

	/**
	 * Resets the maze to start a new game
	 * 
	 * @author ken
	 *
	 */
	class ResetListener implements ActionListener {

		/**
		 * Displays a input dialog that reads the new location and moves the player to
		 * that location
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			resetAll();
		}

	}

	/**
	 * The action listener to add a new Pokemon to the maze at a specified location
	 * 
	 * @author Kenneth Ahrens
	 *
	 */
	class InsertListener implements ActionListener {

		/*
		 * Icon for ditto
		 */
		private final ImageIcon myDittoIcon = new ImageIcon("./src/images/other/dittoicon.gif");

		/*
		 * Abra icon to represent teleport
		 */
		private final ImageIcon myTeleportIcon = new ImageIcon("./src/images/other/abra_teleport.gif");

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
		public void actionPerformed(final ActionEvent e) {
			final String promptCords = "Where to put a new Pokemon in maze? "
					+ "\nRoom Letter ('D') or \"here\" to put at your location.";
			final String promptPokemon = "What is the name of the Pokemon?";
			myPos = readRoomName(promptCords, myTeleportIcon);
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
				final Room r = myMaze.getRoom(thePos[0], thePos[1]);
				r.setNewPokemon(theNewPkmn);
				myPanel.refreshGUI();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/**
		 * Prompt user for a pokemon and looks for that pokemon name in the pokedex
		 * 
		 * @param theMsg
		 * @return user inputed Pokemon if found
		 */
		public Pokemon readNewPokemonInput(final String theMsg) {
			final String input = (String) JOptionPane.showInputDialog(null, theMsg, "Type a Pokemon name to insert",
					JOptionPane.INFORMATION_MESSAGE, myDittoIcon, null, "");
			Pokemon res = myPokedex.findPokemon(0);
			if (input != null && !input.isEmpty()) {
				myCancel = false;
				if (myPokedex.hasPokemon(input)) {
					res = myPokedex.findPokemon(input);
				} else {
					res = readNewPokemonInput(input + " does not exist in Pokedex. Try again with a new name!");
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
		private final ImageIcon myTeleportIcon = new ImageIcon("./src/images/other/abra_teleport.gif");

		/**
		 * Displays a input dialog that reads the new location and moves the player to
		 * that location
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			// TODO Auto-generated method stub
			final String message = "Please enter a new room to teleport" + " to.\n(Room Letter)";
			final int[] pos = readRoomName(message, myTeleportIcon);

			if (pos[0] != -1) {
				myMaze.setPlayerLocation(pos);
				myPanel.refreshGUI();
			}
		}

	}

	private int[] readRoomName(final String theMessage, final ImageIcon theIcon) {
		final String input = (String) JOptionPane.showInputDialog(null, theMessage, "Choose Location",
				JOptionPane.INFORMATION_MESSAGE, theIcon, null, "");
		int[] res = myMaze.getPlayerLocation().clone();
//	        final Scanner scan;
		if (input != null && !input.isEmpty()) {
			if (!(input.contains(" "))) {
//	                        scan = new Scanner(input);
				final String roomName = input.toUpperCase();
				boolean moved = false;
				for (int i = 0; i < myMaze.getRows(); i++) {
					for (int j = 0; j < myMaze.getCols(); j++) {
						if (myMaze.getMatrix()[i][j].toString().equals(roomName)) {
							res = new int[] { i, j };
							moved = true;
						}
					}
				}
				if (!moved) {
					res = readRoomName("Please Input a valid Room Letter" + "\n(Room Letter):", theIcon);
				}

			} else if (input.toLowerCase().strip().equals("here")) {
				/*
				 * Quick shortcut with "here" to put it at player pos
				 */
				res = myMaze.getAttemptedLocation();
			} else {
				res = readRoomName("Please Input a valid Room Letter\n(Room Letter):", theIcon);
			}
		} else {
			// put -1 to signify user canceled
			res = new int[] { -1, -1 };
		}
		return res;
	}

//	/**
//	 * Helper method to read the input from the Input Dialog Used for the teleport
//	 * cheat
//	 * 
//	 * @param theInput a string of the input
//	 * @param icon     for the option pane
//	 * @return an int[] of the two numbers input
//	 */
//	private int[] readCordinateInput(final String theMessage, final ImageIcon theIcon) {
//		final String input = (String) JOptionPane.showInputDialog(null, theMessage, "Choose Location",
//				JOptionPane.INFORMATION_MESSAGE, theIcon, null, "");
//		int[] res = myMaze.getPlayerLocation().clone();
//		final Scanner scan;
//		if (input != null && !input.isEmpty()) {
//			if (!(input.length() < 3)) {
//				scan = new Scanner(input.toString());
//				try {
//					for (int i = 0; i < 2; i++) {
//						final int num = scan.nextInt() - 1;
//						if (num < myMaze.getRows() && num < myMaze.getCols() && num >= 0) {
//							res[i] = num;
//
//						} else {
//							res = readCordinateInput("One or more numbers out " + "of range of maze\n(X Y):", theIcon);
//							break;
//						}
//					}
//				} catch (final InputMismatchException e) {
//					if (input.toLowerCase().strip().equals("here")) {
//						/*
//						 * Quick shortcut with "here" to put it at player pos
//						 */
//						res = myMaze.getAttemptedLocation();
//					} else {
//						res = readCordinateInput("Please use integers only.\n(X Y):", theIcon);
//					}
//				}
//				scan.close();
//			} else {
//				res = readCordinateInput("Invalid Input\n(X Y):", theIcon);
//			}
//		} else {
//			// put -1 to signify user canceled
//			res = new int[] { -1, -1 };
//		}
//
//		return res;
//
//	}

}
