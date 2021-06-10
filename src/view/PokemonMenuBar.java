package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.maze_game_state.PlayerMover;
import exceptions.MissingPokemonException;
import model.Maze;
import model.Pokedex;
import model.Pokemon;
import model.Room;
import sound.BackgroundMusic;
import view.viewHelper.LabelPanel;
import view.viewHelper.PokedexScrollList;
import view.viewHelper.QuestionPanels.TutorialPanel;

/**
 * Menubar for the trivia game has file, help, DIFFICULTY_OPTIONS and gen select
 * menus. Allows player to choose options for the game state/
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class PokemonMenuBar extends JMenuBar {

	/**
	 * serial constant
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Version number used in about
	 */
	private final String VERSION = "Pokemon Trivia Maze v1.03";
	private final String ABOUT_MESSAGE = VERSION + "\nCreated by: AJ Downey, Kenneth Ahrens, and Katelyn Malone"
			+ "\nSpring 2021\n" + "\nDISCLAIMER:\n"
			+ "This is a fan-made-non-profit project based on the \"Who's that Pokemon?\" segment from the original Pokemon anime."
			+ "\nWe are not affliated or endorsed with the Pokemon company. All copyright belongs to Nintendo/Gamefreak.";

	/*
	 * Gen select constants
	 */
	private final String[] GENS = { "Gen 1 (1-151)", "Gen 2 (151-251)", "Gen 3 (251-386)", "Gen 4 (386-493)",
			"Gen 5 (493-649)", "Gen 6 (649-721)", "Gen 7 (721-804)" };
	private final String[] REGIONS = { "Kanto", "Johto", "Hoenn", "Sinnoh", "Unova", "Kalos", "Alola" };

	/*
	 * Difficulty constants
	 */
	private final String[] DIFFICULTY_OPTIONS = { "Easy", "Normal", "Hard", "Extreme" };
	private final String DEFAULT_DIFFICULTY = "Normal";

	// fields

	/*
	 * Menus in menubar
	 */
	private JMenu myHelpMenu;
	private JMenu myFileMenu;
	private JMenu myCheatsMenu;
	private JMenu myGenSelectMenu;
	private JMenu myDifficultySelectMenu;

	/*
	 * Changable state buttons for cheats menu
	 */
	private JCheckBoxMenuItem myRevealCheat;
	private JCheckBoxMenuItem myAnswerCheat;
	private JMenuItem myUnlockCheat;

	/*
	 * Boolean to hide or unhide a pokemon for myReveal cheat
	 */
	private boolean myReveal;

	/*
	 * Changeable state buttons for gen select menu
	 */
	private ArrayList<JCheckBox> myGenBoxList;
	private JMenuItem mySelectAllGen;
	private JCheckBox myMegaButton;

	/*
	 * Changeable state buttons for DIFFICULTY_OPTIONS select menu
	 */
	private String mySelectedDifficulty;
	private ButtonGroup myDifficultyButtons;

	/*
	 * Model
	 */
	private Maze myMaze;
	private Pokedex myPokedex;

	/*
	 * View
	 */
	private final JFrame myFrame;
	private final PokemonPanel myPanel;
	private final TutorialPanel myTutorial;

	/*
	 * Sound
	 */
	private BackgroundMusic myMusicPlayer;
	private JMenu myMusicMenu;
	private ButtonGroup mySongButtons;

	// private JMenu myGamemodeMenu;
	// private ButtonGroup myGamemodes;

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
		myMusicPlayer = BackgroundMusic.getInstance();
		myTutorial = new TutorialPanel(myPanel);
		setupMenuBar();
		addListeners();

	}

	/*
	 * Put listeners for important classes that should listen for changes in the
	 * menubar
	 */
	private void addListeners() {
		this.addPropertyChangeListener(myPanel);
		this.addPropertyChangeListener(myPanel.getMazeGUI());
		this.addPropertyChangeListener(myPanel.getMyControlPanel());

	}

	/**
	 * Adds the base menus to the menu bar
	 */
	private void setupMenuBar() {

		myFileMenu = new JMenu("File");
		myFileMenu.setMnemonic(KeyEvent.VK_F);
		setupFileMenu();
		this.add(myFileMenu);

		myHelpMenu = new JMenu("Help");
		myHelpMenu.setMnemonic(KeyEvent.VK_H);
		setupHelpMenu();
		this.add(myHelpMenu);

		myMusicMenu = new JMenu("Music");
		myMusicMenu.setMnemonic(KeyEvent.VK_M);
		setupMusicMenu();
		this.add(myMusicMenu);

		myDifficultySelectMenu = new JMenu("Difficulty");
		myDifficultySelectMenu.setMnemonic(KeyEvent.VK_D);
		setupDifficultySelectMenu();
		this.add(myDifficultySelectMenu);

		myGenSelectMenu = new JMenu("Select Pokemon Generation(s)");
		myGenSelectMenu.setMnemonic(KeyEvent.VK_G);
		setupGenSelectMenu();
		this.add(myGenSelectMenu);

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
		save.setMnemonic(KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.addActionListener(new SaveListener());
		myFileMenu.add(save);

		final JMenuItem load = new JMenuItem("Load");
		load.setMnemonic(KeyEvent.VK_L);
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		load.addActionListener(new LoadListener());
		load.addPropertyChangeListener(myPanel.getMyControlPanel());
		myFileMenu.add(load);

		myFileMenu.addSeparator();

		final JMenuItem reset = new JMenuItem("Reset");
		reset.setMnemonic(KeyEvent.VK_R);
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		reset.addActionListener(new ResetListener());
		myFileMenu.add(reset);

		myFileMenu.addSeparator();

		final JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		exit.addActionListener(theEvent -> System.exit(0));
		myFileMenu.add(exit);
	}

	/**
	 * Sets up the Help Menu,
	 */
	private void setupHelpMenu() {
		// TODO Auto-generated method stub

		final JMenuItem about = new JMenuItem("About");
		about.setMnemonic(KeyEvent.VK_A);
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(myFrame, ABOUT_MESSAGE, "About Us", JOptionPane.PLAIN_MESSAGE);
			}

		});
		myHelpMenu.add(about);

		final JMenuItem tutorial = new JMenuItem("Tutorial");
		tutorial.setMnemonic(KeyEvent.VK_T);
		tutorial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
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
	 * Sets up the myCheatsMenu menu for all of the dev myCheatsMenu for debugging
	 */
	private void setUpCheats() {
		myCheatsMenu = new JMenu("Cheats");
		myCheatsMenu.setMnemonic(KeyEvent.VK_C);
		myHelpMenu.add(myCheatsMenu);

		myRevealCheat = new JCheckBoxMenuItem("Reveal Pokemon");
		myRevealCheat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				myReveal = myRevealCheat.isSelected();
				myPanel.setMyReveal(myReveal);
				firePropertyChange("showpkmn", null, null);
				myPanel.refreshGUI();

			}
		});
		myCheatsMenu.add(myRevealCheat);

		myAnswerCheat = new JCheckBoxMenuItem("Show Answer");
		myAnswerCheat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final boolean selected = myAnswerCheat.isSelected();
				final LabelPanel labels = myPanel.getLabelPanel();
				myPanel.refreshGUI();
				if (selected) {
					labels.enableShowAnswer(true);
				} else {
					labels.enableShowAnswer(false);
				}

			}
		});
		myCheatsMenu.add(myAnswerCheat);

		myCheatsMenu.addSeparator();

		final JMenuItem myUnlockCheat = new JMenuItem("Unlock All Doors");
		myUnlockCheat.addActionListener(new ActionListener() {

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

						// repaint();
					}
				}
				if (change) {
					myUnlockCheat.setText("Unlock All Doors");
				} else {
					myUnlockCheat.setText("Relock All Doors");
				}
				myPanel.refreshGUI();
			}

		});
		myCheatsMenu.add(myUnlockCheat);

		final JMenuItem removeBlocked = new JMenuItem("Reset Blocked Rooms");
		removeBlocked.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				final Room[][] rooms = myMaze.getMatrix();
				for (int i = 0; i < myMaze.getRows(); i++) {
					for (int j = 0; j < myMaze.getCols(); j++) {
						rooms[i][j].setEntry(true);
						// repaint();
					}
				}
				myPanel.refreshGUI();
			}

		});
		myCheatsMenu.add(removeBlocked);

		final JMenuItem insert = new JMenuItem("Insert New Pokemon");
		insert.addActionListener(new InsertListener());
		myCheatsMenu.add(insert);

		final JMenuItem teleport = new JMenuItem("Teleport");
		teleport.addActionListener(new TeleportListener());
		myCheatsMenu.add(teleport);

	}

	// /**
	// * Sets up the gamemode menu for changing the gamemode
	// */
	// private void setupGamemodesMenu() {
	//
	// myGamemodes = new ButtonGroup();
	// final JRadioButtonMenuItem choice = new JRadioButtonMenuItem("Multiple
	// Choice");
	// myGamemodeMenu.add(choice);
	// myGamemodes.add(choice);
	// choice.addActionListener(new ActionListener() {
	//
	// @Override
	// public void actionPerformed(final ActionEvent e) {
	// // System.out.println("action fired");
	// // myPanel.setPanels(true);
	// firePropertyChange("changegm", null, 1);
	//
	// }
	//
	// });
	//
	// final JRadioButtonMenuItem input = new JRadioButtonMenuItem("User
	// Input");
	// myGamemodeMenu.add(input);
	// myGamemodes.add(input);
	// input.addActionListener(new ActionListener() {
	//
	// @Override
	// public void actionPerformed(final ActionEvent e) {
	// // System.out.println("action fired");
	// // myPanel.setPanels(false);
	// firePropertyChange("changegm", null, 2);
	//
	// }
	//
	// });
	//
	// // set initial gamemodechoice
	// choice.setSelected(true);
	//
	// }

	/**
	 * Setups DIFFICULTY_OPTIONS settings menu
	 */
	private void setupDifficultySelectMenu() {

		final int[] sizeSetting = { 4, myMaze.getRows(), 7, 10 };

		/*
		 * Ensure only one button can be selected at a time
		 */
		myDifficultyButtons = new ButtonGroup();
		// build buttons
		for (int i = 0; i < DIFFICULTY_OPTIONS.length; i++) {
			final String name = DIFFICULTY_OPTIONS[i];
			final int size = sizeSetting[i];
			final JRadioButton DIFFICULTY_OPTIONSOption = new JRadioButton(name);
			final String sizeTip = name + ", Maze Size: " + size + "-by-" + size;
			DIFFICULTY_OPTIONSOption.setToolTipText(sizeTip);

			DIFFICULTY_OPTIONSOption.addActionListener(new DifficultySelectListener(size, DIFFICULTY_OPTIONSOption));
			myDifficultyButtons.add(DIFFICULTY_OPTIONSOption);
			myDifficultySelectMenu.add(DIFFICULTY_OPTIONSOption);

			// set normal as default selected
			if (DIFFICULTY_OPTIONSOption.getText().equals(DEFAULT_DIFFICULTY)) {
				DIFFICULTY_OPTIONSOption.setSelected(true);
				mySelectedDifficulty = DEFAULT_DIFFICULTY;
			}
		}
	}

	/**
	 * Setup select checkbox for pokemon gen select menu
	 */
	private void setupGenSelectMenu() {

		// current supported pokemon generations

		myGenBoxList = new ArrayList<JCheckBox>();

		// add all to a checkbox list
		for (int i = 0; i < GENS.length; i++) {
			final JCheckBox genBox = new JCheckBox(GENS[i]);
			genBox.setToolTipText(REGIONS[i]);
			genBox.addActionListener(new GenSelectListener(i + 1, genBox));
			myGenSelectMenu.add(genBox);
			myGenBoxList.add(genBox);

			if (i == 0) {
				genBox.setSelected(true); // gen 1 selected by defau;t
			}
		}
		myGenSelectMenu.addSeparator();

		//button to activate mega pokemon
		myMegaButton = new JCheckBox("Enable Megas");
		myMegaButton
				.setToolTipText("Allows mega pokemon to be encounterable or not.");
		myGenSelectMenu.add(myMegaButton);
		myMegaButton.addActionListener(new MegaSelectListener(myMegaButton));
		myGenSelectMenu.addSeparator();

		// mass add or remove gen
		mySelectAllGen = new JMenuItem("Play All Gens");
		mySelectAllGen.addActionListener(new GenSelectAllListener(mySelectAllGen));
		myGenSelectMenu.add(mySelectAllGen);

		// show pokemon in pokedex
		JMenuItem pokemonList = new JMenuItem("See Pokedex");
		pokemonList.setToolTipText("See what Pokemon are currently in the Pokedex");
		pokemonList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JPanel pokemons = new PokedexScrollList();
				JOptionPane.showMessageDialog(myFrame, pokemons, "Current Pokemon in Pokedex",
						JOptionPane.PLAIN_MESSAGE);

			}

		});

		myGenSelectMenu.add(pokemonList);

	}

	/**
	 * Setups menu that controls the song being played
	 */
	private void setupMusicMenu() {
		final ArrayList<String> mySongs = myMusicPlayer.getSongList();
		/*
		 * Ensure only one button can be selected at a time
		 */
		mySongButtons = new ButtonGroup();
		// build buttons
		for (String song : mySongs) {
			String name = song.replace(".wav", "");
			final JRadioButton songOption = new JRadioButton(name);
			songOption.addActionListener(new SongSelectListener(songOption, song));
			mySongButtons.add(songOption);
			myMusicMenu.add(songOption);

			// set normal as default selected
			if (song.equals(myMusicPlayer.DEFAULT_SONG)) {
				songOption.setSelected(true);
			}
		}

		myMusicMenu.addSeparator();
		JMenuItem muteButton = new JMenuItem("Mute music");
		muteButton.addActionListener(new MuteSongListener(muteButton));
		myMusicMenu.add(muteButton);

	}

	/*
	 * Update all the main menubar settings
	 */
	private void updateMenuBarSettings() {
		updateGenSelect();
		updateDifficultySetting();

	}

	/*
	 * Update gen select menu
	 */
	private void updateGenSelect() {
		final Set<Integer> selectGENS = myPokedex.getSelectedGens();
		// update gen selection
		int count = 1;
		for (final JCheckBox jrb : myGenBoxList) {
			if (selectGENS.contains(count)) {
				jrb.setSelected(true);
			} else {
				jrb.setSelected(false);
			}

			count++;
		}

		// firePropertyChange("model", null, myMaze.getMatrix());
		myPanel.refreshGUI();
	}

	/*
	 * Update DIFFICULTY_OPTIONS setting menu. Iterate through each button until it
	 * finds the correct maze size stored in the tool tip
	 */
	private void updateDifficultySetting() {

		for (final Enumeration<AbstractButton> buttons = myDifficultyButtons.getElements(); buttons
				.hasMoreElements();) {
			final AbstractButton box = buttons.nextElement();
			final String text = box.getToolTipText();
			box.setSelected(text.contains(String.valueOf(myMaze.getRows())));
		}

		// firePropertyChange("model", null, myMaze.getMatrix());
		myPanel.refreshGUI();
	}

	/**
	 * Reset game to start with new pokemon GENS
	 */
	private void resetAll() {
		myMaze.reset();
		// firePropertyChange("model", null, myMaze.getMatrix());
		myPanel.refreshGUI();
	}

	/*
	 * ---------------------------------------------------------------- Listener
	 * classes to change maze/game state below
	 * ----------------------------------------------------------------
	 */

	/*
	 * Save game state
	 */
	class SaveListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			doSave();
		}

		private void doSave() {
			final JFileChooser fileChooser = new JFileChooser("Save") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void approveSelection() {
					File saveFile = getSelectedFile();
					final String filestring = saveFile.toString();

					final String[] left_side_of_dot = filestring.split("\\.");

					saveFile = new File(left_side_of_dot[0] + ".maze");
					if (saveFile.exists()) {
						final int n = JOptionPane.showConfirmDialog(this, "Do You Want to Overwrite File?",
								"Confirm Overwrite", JOptionPane.YES_NO_OPTION);

						if (n == JOptionPane.YES_OPTION)
							super.approveSelection();

					} else
						super.approveSelection();
				}
			};
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Pokemon Trivia Maze Files", "maze"));
			fileChooser.setAcceptAllFileFilterUsed(false);
			// read the file from file chooser
			if (fileChooser.showSaveDialog(myFrame) == JFileChooser.APPROVE_OPTION) {
				// get the save file name and add a suffix to it to denote
				// is it a a game file
				// for this maze
				File saveFile = fileChooser.getSelectedFile();
				final String filestring = saveFile.toString();

				final String[] left_side_of_dot = filestring.split("\\.");

				saveFile = new File(left_side_of_dot[0] + ".maze");

				try (final FileOutputStream fileOut = new FileOutputStream(saveFile);
						final ObjectOutputStream objOut = new ObjectOutputStream(fileOut);) {

					// use serialization to write the maze
					saveHelper(objOut);
					showState(saveFile.getName());

				} catch (final FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (final IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}

		/*
		 * Objects to be saved
		 */
		private void saveHelper(final ObjectOutputStream objOut) throws IOException {
			objOut.writeObject(myMaze);
			objOut.writeObject(myPokedex);
		}

		/*
		 * Tell user the save state info
		 */
		private void showState(final String theFileName) {
			final String curr = "Player is at Room " + myMaze.getCurrRoom();
			final String attempt = "Player is attempting to enter Room " + myMaze.getAttemptRoom();
			final String playerLocation = myMaze.hasNotMoved() ? curr : attempt;
			final String stateMessage = "Pokemon Trivia has been Saved: " + theFileName + "\n" + playerLocation
					+ "\nDifficulty Setting = " + mySelectedDifficulty + "\nGens Selected = Gens: "
					+ myPokedex.getSelectedGens();
			JOptionPane.showMessageDialog(null, stateMessage);
		}

	}

	/*
	 * Load game state
	 */
	class LoadListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			doLoad();

		}

		private void doLoad() {
			final JFileChooser fileChooser = new JFileChooser("Load");
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Pokemon Trivia Maze Files", "maze"));
			fileChooser.setAcceptAllFileFilterUsed(false);
			if (fileChooser.showOpenDialog(myFrame) == JFileChooser.APPROVE_OPTION) {
				// get the save file name and check if it has the right
				// suffix
				final String fileName = fileChooser.getSelectedFile().getName();

				/*
				 * Only read files that end with the proper suffix
				 */
				if (fileName.endsWith(".maze")) {
					// final File saveFile = new
					// File(fileChooser.getSelectedFile() + ".maze");

					try (final FileInputStream file = new FileInputStream(fileChooser.getSelectedFile());
							final ObjectInputStream in = new ObjectInputStream(file);) {
						// System.out.println("Start Load: " + myMaze);
						// System.out.println("before: " + myPokedex);
						// objects to be loaded in
						loadObjectsHelper(in);
						updateMenuBarSettings();
						showState(fileName);

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
				} else { // wrong file type inform user
					JOptionPane.showMessageDialog(null,
							"Cannot load " + fileName + " because it is not a \".maze\" file");
				}
			}
		}

		/*
		 * Load the objects we want
		 */
		private void loadObjectsHelper(final ObjectInputStream in) throws ClassNotFoundException, IOException {
			myMaze = (Maze) in.readObject();
			myPokedex = (Pokedex) in.readObject();

		}

		/*
		 * Option pane to tell user of state
		 */
		private void showState(final String fileName) {
			final String curr = "Player is at Room " + myMaze.getCurrRoom();
			final String attempt = "Player is attempting to enter Room " + myMaze.getAttemptRoom();
			final String playerLocation = myMaze.hasNotMoved() ? curr : attempt;
			final String stateMessage = "Pokemon Trivia save has been loaded: " + fileName + "\n" + playerLocation
					+ "\nDifficulty Setting = " + mySelectedDifficulty + "\nGens Selected = Gens: "
					+ myPokedex.getSelectedGens();
			JOptionPane.showMessageDialog(null, stateMessage);
		}

	}

	/**
	 * Select the DIFFICULTY_OPTIONS and changes the maze's size
	 */
	class DifficultySelectListener implements ActionListener {

		private final JRadioButton myBox;
		private final String myName;
		private final int myMazeSize;

		public DifficultySelectListener(final int theNum, final JRadioButton theBox) {
			myMazeSize = theNum;
			myBox = theBox;
			myName = theBox.getText();

		}

		@Override
		public void actionPerformed(final ActionEvent e) {

			if (myBox.isSelected()) { // add pokemon gen
				myMaze.setMatrixSize(myMazeSize, myMazeSize);
				mySelectedDifficulty = myName;

			}

			firePropertyChange("model", null, myMaze.getMatrix());
			myPanel.refreshGUI();
			JOptionPane.showMessageDialog(null,
					"Difficulty is now set to \"" + myName + "\", Size: [" + myMazeSize + ", " + myMazeSize + "]");

		}

	}

	/**
	 * Add a pokemon generation to the pokedex
	 */
	class GenSelectListener implements ActionListener {

		private final JCheckBox myBox;
		private final int myGen;
		private String myMsg;

		public GenSelectListener(final int theNum, final JCheckBox theBox) {
			myGen = theNum;
			myBox = theBox;
			myMsg = "";
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			// TODO Auto-generated method stub
			final SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					// System.out.println("Executing");
					resetAll();
					Thread.sleep(750);
					JOptionPane.showMessageDialog(null, myMsg, "Game reset!", JOptionPane.INFORMATION_MESSAGE);
					return null;
				}
			};
			// final String msg;
			if (myBox.isSelected()) { // add pokemon gen

				try {
					myPokedex.addGenToDex(myGen);
					myMsg = "Gen " + myGen + " Pokemon can now be encountered!" + "\nSelected Gens: "
							+ myPokedex.getSelectedGens() + "\nTotal Entries in Pokedex: " + myPokedex.getCount();
					// resetAll();
					mySwingWorker.execute();
					// JOptionPane.showMessageDialog(null, myMsg);

				} catch (final Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (!myBox.isSelected()) {
				if (myPokedex.canRemoveGen()) { // remove pokemon gen
					try {
						myPokedex.removeGenFromDex(myGen);
						myMsg = "Gen " + myGen + " Pokemon have been removed." + "\nSelected Gens: "
								+ myPokedex.getSelectedGens() + "\nTotal Entries in Pokedex: " + myPokedex.getCount();
						// resetAll();
						mySwingWorker.execute();
						// JOptionPane.showMessageDialog(null, myMsg);

					} catch (final Exception e1) {
						// TODO Auto-generated catch block
						myBox.setSelected(true);
						myMsg = "Cannot remove Gen " + myGen
								+ " because at least one generation must be selected to play!";
						JOptionPane.showMessageDialog(null, myMsg, "Cannot remove!", JOptionPane.ERROR_MESSAGE);
						// e1.printStackTrace();
					}
				}
			}

		}

	}

	/**
	 * Select/unselect all GENS to play on listener
	 */
	class GenSelectAllListener implements ActionListener {

		private final JMenuItem myChangeButton;
		/*
		 * If user has clicked this button once to add all pokemon
		 */
		private boolean myStateChange;

		private String myMsg;

		public GenSelectAllListener(final JMenuItem theChangeButton) {
			myChangeButton = theChangeButton;
			myStateChange = false;
			myMsg = "";
		}

		@Override
		public void actionPerformed(final ActionEvent e) {

			final SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() throws Exception {
					// System.out.println("Executing");
					resetAll();
					JOptionPane.showMessageDialog(null, myMsg, "Game reset!", JOptionPane.INFORMATION_MESSAGE);
					return null;
				}
			};

			if (!myStateChange) { // add all GENS
				myStateChange = true;
				myMsg = "Pokemon from all generations will now be loaded in." + "\nThis may take a while. "
						+ "Please press \"OK\".";
				JOptionPane.showMessageDialog(null, myMsg, "Loading...", JOptionPane.INFORMATION_MESSAGE);

				myPokedex.addAllGensToDex();
				myMsg = "All Pokemon generations have been loaded in. " + "\nSelected Gens: "
						+ myPokedex.getSelectedGens() + "\nTotal Entries in Pokedex: " + myPokedex.getCount();
				// resetAll();
				mySwingWorker.execute();
				// JOptionPane.showMessageDialog(null, myMsg);

				myChangeButton.setText("Reset Gens to Default");
				myStateChange = true;

			} else { // remove all GENS
				myStateChange = false;
				myPokedex.restoreGensToDefault();
				myMsg = "All Pokemon generations except Gen " + myPokedex.DEFAULT_GEN + " has been removed."
						+ "\nSelected Gens: " + myPokedex.getSelectedGens() + "\nTotal Entries in Pokedex: "
						+ myPokedex.getCount();
				// resetAll();
				mySwingWorker.execute();
				// JOptionPane.showMessageDialog(null, myMsg);

				myChangeButton.setText("Play All Gens");
			}

			// initialize box selection based on the action
			selectButtons(myStateChange);
			// resetAll();

		}

		/**
		 * Select all gen box buttons depending on state
		 * 
		 * @param theState true = all boxes selected, false = all unselected excpet gen
		 *                 1
		 */
		private void selectButtons(final Boolean theState) {
			for (final JCheckBox box : myGenBoxList) {
				box.setSelected(theState);
			}

			// keep gen 1 box selected for the reset remove
			if (theState == false) {
				myGenBoxList.get(0).setSelected(true);
			}
		}

	}

	/**
	 * enable or disables megapokemon from pokedex
	 */
	class MegaSelectListener implements ActionListener {

		private final JCheckBox myChangeButton;
		/*
		 * If user has clicked this button once to add all pokemon
		 */
		private boolean myStateChange;

		private String myMsg;

		public MegaSelectListener(final JCheckBox theChangeButton) {
			myChangeButton = theChangeButton;
			myStateChange = false;
			myMsg = "";
		}

		@Override
		public void actionPerformed(final ActionEvent e) {

			final SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() throws Exception {
					// System.out.println("Executing");
					myPokedex.refreshSelectGens();
					resetAll();
					String info = "\nSelected Gens: " + myPokedex.getSelectedGens()
					+ "\nTotal Entries in Pokedex: " + myPokedex.getCount();
					JOptionPane.showMessageDialog(null, myMsg + info, "Game reset!", JOptionPane.INFORMATION_MESSAGE);
					return null;
				}
			};

			myPokedex.setUseMegas(myChangeButton.isSelected());

			if (!myStateChange) { // did an enable
				myStateChange = true;

				myMsg = "Mega Pokemon are now encounterable!";
				// resetAll();
				mySwingWorker.execute();
				// JOptionPane.showMessageDialog(null, myMsg);

				myChangeButton.setText("Disable Megas");
				myStateChange = true;

			} else { // did a disable
				myStateChange = false;
				myMsg = "Mega Pokemon are no longer encounterable!";
				mySwingWorker.execute();
				myChangeButton.setText("Enable Megas");
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

			final SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() throws Exception {
					// System.out.println("Executing");
					resetAll();
					JOptionPane.showMessageDialog(null, "The game has been reset!", "Game Reset",
							JOptionPane.INFORMATION_MESSAGE);
					return null;
				}
			};
			mySwingWorker.execute();

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
					+ "\nRoom Name (Ex: '3') or \"here\" to put at your location.";
			final String promptPokemon = "What is the name of the Pokemon?";

			firePropertyChange("tele", null, true);
			myPos = readRoomName(promptCords, myTeleportIcon);
			if (myPos[0] != -1) {
				myNewMon = readNewPokemonInput(promptPokemon);
				if (!myCancel) {
					putPokemon(myPos, myNewMon);
				}
			}

			firePropertyChange("tele", null, false);

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
			Pokemon res = myMaze.getAttemptRoom().getPokemon(); // leave as
			// default in
			// case
			if (input != null && !input.isEmpty()) {
				myCancel = false;
				if (myPokedex.hasPokemon(input)) {
					try {
						res = myPokedex.findPokemon(input);
					} catch (MissingPokemonException e) {
						res = readNewPokemonInput(input + " cannot be found. Try again with a new name!");
					} catch (NullPointerException e) {
						res = readNewPokemonInput(input + " cannot be found. Try again with a new name!");
					}
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
			firePropertyChange("tele", null, true);
			final String message = "Please enter a new room to teleport" + " to.\n(Room Name)";
			final int[] pos = readRoomName(message, myTeleportIcon);

			if (pos[0] != -1) {
				PlayerMover move = new PlayerMover();
				move.teleportPlayer(pos);
				myPanel.refreshGUI();
				checkWinLoseCondition();
			}

			firePropertyChange("tele", null, false);
		}

		/*
		 * Fire property changes for if the player has won or lost
		 */
		private void checkWinLoseCondition() {
			if (myMaze.hasWon()) {
				// System.out.println("in win panel");
				firePropertyChange("win", null, null);
			} else if (myMaze.hasLost()) {
				// System.out.println("in lose panel");
				firePropertyChange("lose", null, null);
			}

		}

	}

	/**
	 * Takes user input to choose a location in the maze
	 * 
	 * @param theMessage
	 * @param theIcon
	 * @return int[]
	 */
	private int[] readRoomName(final String theMessage, final ImageIcon theIcon) {
		final String input = (String) JOptionPane.showInputDialog(null, theMessage, "Choose Location",
				JOptionPane.INFORMATION_MESSAGE, theIcon, null, "");
		int[] res = myMaze.getPlayerLocation().clone();
		// final Scanner scan;
		if (input != null && !input.isEmpty()) {
			if ((input.toLowerCase().strip().equals("here"))) {
				/*
				 * Quick shortcut with "here" to put it at player pos
				 */
				res = myMaze.getAttemptedLocation();
			} else if (!(input.contains(" "))) {
				// scan = new Scanner(input);
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
					res = readRoomName("Please Input a valid Room Name" + "\n(Room Num):", theIcon);
				}

			} else {
				res = readRoomName("Please Input a valid Room Name\n(Room Num), Ex: \'1\':", theIcon);
			}
		} else {
			// put -1 to signify user canceled
			res = new int[] { -1, -1 };
		}
		return res;
	}

	/**
	 * Select the song to play
	 */
	class SongSelectListener implements ActionListener {

		private final JRadioButton myBox;
		private final String myName;

		public SongSelectListener(final JRadioButton theBox, String theSongName) {
			myBox = theBox;
			myName = theSongName;

		}

		@Override
		public void actionPerformed(final ActionEvent e) {

			if (myBox.isSelected()) {
				myMusicPlayer.loadNewSong(myName);

			}

		}

	}

	/**
	 * Mutes or unmutes the song
	 */
	class MuteSongListener implements ActionListener {

		private final JMenuItem myBox;
		/*
		 * false = not muted, true = muted
		 */
		private boolean myChange;

		public MuteSongListener(final JMenuItem theBox) {
			myBox = theBox;
			myChange = false;

		}

		@Override
		public void actionPerformed(final ActionEvent e) {

			if (!myChange) {
				myMusicPlayer.stopMusic();
				myBox.setText("Unmute music");
				myChange = true;
			} else {

				myMusicPlayer.playMusic();
				myBox.setText("Mute music");
				myChange = false;
			}

		}

	}

}
