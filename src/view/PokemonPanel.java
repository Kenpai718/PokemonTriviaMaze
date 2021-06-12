package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import model.Maze;
import view.Win_Lose.LosePane;
import view.Win_Lose.WinPane;
import view.viewHelper.ControlPanel;
import view.viewHelper.ImageUtility;
import view.viewHelper.LabelPanel;
import view.viewHelper.MazeGUI;
import view.viewHelper.MazeGUI.MazeModel;
import view.viewHelper.QuestionPanels.QuestionModePanel;

/**
 * Main visual of the gameplay. Setups playable game visuals on a panel such as
 * pokemon image, question panels and maze visuals.
 * 
 * @author ajdowney
 * @author ken
 * @version Spring 2021
 */
public class PokemonPanel extends JPanel implements PropertyChangeListener {

	/*
	 * Constants
	 */

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 8168838797214673243L;

	/*
	 * Player full avatar used in the starting room
	 */
	private final String PLAYER_M = "./src/images/other/PLAYER_M.png";
	private final String PLAYER_F = "./src/images/other/PLAYER_F.png";
	// TODO: choose the avatar type
	private BufferedImage myAvatar;

	/*
	 * TO adjust the picture position of a Pokemon PNG
	 */
	private final int X_OFFSET = 120;
	private final int Y_OFFSET = 230;

	/*
	 * default sizes of pokemon pictures for scaling
	 */
	private final int POKE_W = 600;
	private final int POKE_H = 600;

	/*
	 * question mark offset for positioning
	 */
	private final int X_OFFSET_QUEST = 820;
	private final int Y_OFFSET_QUEST = 280;

	/*
	 * default layout of the GUI
	 */
	private final SpringLayout springLayout = new SpringLayout();

	/*
	 * Background color of the game (Crimson Red)
	 */
	private final Color BG_COLOR = new Color(220, 20, 60);

	/*
	 * Color of maze
	 */
	final Color MAZE_BG = new Color(51, 51, 51);

	/*
	 * Background color of border
	 */
	private final Color BORDER_COLOR = new Color(51, 153, 204);

	/*
	 * Border
	 */
	final Border BLUE_BORDER = BorderFactory.createLineBorder(BORDER_COLOR, 5);

	/*
	 * Aspect ratio of Jpanel
	 */
	final Dimension PANEL_SIZE = new Dimension(1920, 1080);

	/*
	 * Fields
	 */

	/*
	 * Size of the shine background
	 */
	private int myShineW;
	private int myShineH;

	/*
	 * size of question mark
	 */
	private int myQuestW;
	private int myQuestH;

	/*
	 * Background sparkle effect behind the pokemon
	 */
	private BufferedImage myShine = null;

	/*
	 * Maze of the game
	 */
	private final Maze myMaze;

	/*
	 * Model of the JTable that represents the maze of the game
	 */
	private final MazeModel myMazeModel;

	/*
	 * GUI visual of the maze
	 */
	private final MazeGUI myMazeGUI;

	/*
	 * Non hidden pokemon picture
	 */
	private BufferedImage myPokeLight;
	/*
	 * Hidden pokemon picture
	 */
	private BufferedImage myPokeDark;
	/*
	 * Picture of the pokemon: light or dark
	 */
	private BufferedImage myPoke;

	/*
	 * Question mark icon. Used at the start of the game
	 */
	private BufferedImage myQuestionImg;

	/*
	 * Control button visuals
	 */
	private final ControlPanel myControlPanel;

	/*
	 * Panel with labels that tell game state
	 */
	private final LabelPanel myLabelPanel;

	/*
	 * Panel that holds multiple question/answers gamemodes
	 */
	private final QuestionModePanel myQuestPanels;

	/*
	 * Boolean to know if the picture is hidden or visible
	 */
	private boolean myDark;

	/*
	 * Constant in the case where the reveal cheat is used to keep pokemon shown all
	 * the time
	 */
	private Boolean myReveal;

	/*
	 * Show question mark image or not
	 */
	private Boolean myShowQMark;

	/**
	 * Constructor
	 */
	public PokemonPanel() {
		super();

		// start a new game on the panel
		myMaze = Maze.getInstance();
		myReveal = false;
		myShowQMark = false;
		myMazeGUI = new MazeGUI();
		myMazeModel = (MazeModel) getTable().getModel();
		myLabelPanel = new LabelPanel();
		myControlPanel = new ControlPanel(this);
		myQuestPanels = new QuestionModePanel(this);

		// listeners
		addPropertyChangeListener(this);
		addPropertyChangeListener(myControlPanel);
		this.addPropertyChangeListener(myMazeGUI);

		/// initialize panel
		setupPanel();

		// initialize pictures and draw to panel
		setupPictures();

	}

	/**
	 * Setup the components on the panel for the game
	 */
	private void setupPanel() {
		setBackground(BG_COLOR);
		setPreferredSize(PANEL_SIZE);
		setLayout(springLayout);
		setFocusable(true);

		// position panel components

		// maze gui
		springLayout.putConstraint(SpringLayout.NORTH, myMazeGUI, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, myMazeGUI, 1350, SpringLayout.WEST, this);

		// label panel
		springLayout.putConstraint(SpringLayout.NORTH, myLabelPanel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myLabelPanel, -22, SpringLayout.WEST, myMazeGUI);

		// control panel
		springLayout.putConstraint(SpringLayout.SOUTH, myControlPanel, -150, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myControlPanel, 0, SpringLayout.EAST, myLabelPanel);
		
		// question panel
		springLayout.putConstraint(SpringLayout.NORTH, myQuestPanels, 515, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, myQuestPanels, -30, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myQuestPanels, 455, SpringLayout.EAST, myControlPanel);

		// add all components to panel
		add(myMazeGUI);
		add(myControlPanel);
		add(myLabelPanel);
		add(myQuestPanels);

		// fire change to set the buttons for the start
		firePropertyChange("newpos", null, null);

	}

	/**
	 * Put initial pictures on the panel
	 */
	public void setupPictures() {

		// initialize player avatar
		myAvatar = ImageUtility.readImage(PLAYER_M);
		myAvatar = ImageUtility.getScaledImage(myAvatar, POKE_W, POKE_H);

		// iconic question mark
		myQuestionImg = ImageUtility.readImage("./src/images/other/questionmark.png");
		if (myQuestionImg != null) {
			myQuestW = myQuestionImg.getWidth();
			myQuestH = myQuestionImg.getHeight();
		}

		// sparkly thing behind a pokemon
		myShine = ImageUtility.readImage("./src/images/other/sparkle_formatted2.png");
		if (myShine != null) {
			myShineW = myShine.getWidth();
			myShineH = myShine.getHeight();
		}

		/*
		 * // draw the pokemon for the room we are in/attempting to go to setImage();
		 */
		// draw the player avatar since we are starting the game for the first
		// time and
		// there will be no pokemon
		setStartRoomPicture();

	}

	/**
	 * Set pokemon pictures for dark and light depending on the current room. Resize
	 * them if they are not 600x600px
	 */
	private void setImage() {

		myPokeLight = myMaze.getAttemptRoom().getPokemon().getPNG();
		// resize if not the correct width or height
		if (myPokeLight.getWidth() < POKE_W || myPokeLight.getHeight() < POKE_H || myPokeLight.getWidth() > POKE_W
				|| myPokeLight.getHeight() > POKE_H) {
			myPokeLight = ImageUtility.getScaledImage(myPokeLight, POKE_W, POKE_H);
		}

		myPokeDark = (BufferedImage) ImageUtility.setToBlack(myPokeLight);
		myPoke = myDark ? myPokeDark : myPokeLight;

		repaint();

	}

	/**
	 * Put a player avatar instead of a pokemon for the first room picture
	 */
	private void setStartRoomPicture() {
		myPoke = myAvatar;
		myPokeLight = myAvatar;
		myPokeDark = myAvatar;
		myShowQMark = false;
		repaint();
	}

	/**
	 * Full refresh that updates all important components for the Pokemon Panel gui
	 * visuals
	 */
	public void refreshGUI() {

		// show question mark if pokemon has not been revealed yet
		myShowQMark = !(myMaze.getAttemptRoom().hasVisited());
		refreshPokemonImage();

		/*
		 * Show tutorial if at first room
		 */
		if (myMaze.isAtStart()) {
			myQuestPanels.showTutorial();
			setStartRoomPicture();
		} else { // show current question panel
			myQuestPanels.setCurrentGamemode();
			myQuestPanels.updateQP();
		}

		myLabelPanel.updateLabels();

		// fire property changes to update other components
		firePropertyChange("newpos", null, null);
		firePropertyChange("model", null, myMaze.getMatrix());

	}

	/**
	 * Refreshes the image of the pokemon on the panel. For instance
	 * reveals/unreveals or new pokemon image.
	 */
	public void refreshPokemonImage() {
		setImgBrightness();
		setImage();
	}

	/**
	 * Paints the pokemon and background
	 */
	@Override
	protected void paintComponent(final Graphics theG) {
		super.paintComponent(theG);

		theG.drawImage(myShine, 0, 0, myShineW, myShineH, this);
		theG.drawImage(myPoke, X_OFFSET, Y_OFFSET, POKE_W, POKE_H, this);
		if (myShowQMark) {
			theG.drawImage(myQuestionImg, X_OFFSET_QUEST, Y_OFFSET_QUEST, myQuestW, myQuestH, this);
		}

	}

	/**
	 * set dark variable Dark = true when not answered/visited
	 * 
	 */
	private void setImgBrightness() {
		myDark = !(myReveal
				|| (myMaze.hasNotMoved() ? myMaze.getCurrRoom().hasVisited() : myMaze.getAttemptRoom().hasVisited()));
	}

	/**
	 * @param myReveal the myReveal to set
	 */
	public void setMyReveal(final Boolean theReveal) {
		myReveal = theReveal;
	}

	/**
	 * Take off the question mark from the paint panel if the question has been
	 * answered already
	 */
	public void setShowQMark() {
		myShowQMark = !(myMaze.getAttemptRoom().hasVisited());
		repaint();
	}

	/**
	 * Reveal image when the user answers Expected refreshGUI will be called
	 * afterwards for the next question.
	 */
	private void showAnswerImage() {

		myDark = false;
		myShowQMark = false;
		setImage();

	}

	/**
	 * Getter MazeGUi table
	 * 
	 * @return JTable the table that represents the maze
	 */
	public MazeGUI getMazeGUI() {
		return myMazeGUI;
	}

	/**
	 * Getter MazeGUi table
	 * 
	 * @return JTable the table that represents the maze
	 */
	public JTable getTable() {
		return myMazeGUI.getTable();
	}

	/**
	 * Getter QuestionMode panel
	 */
	public QuestionModePanel getQuestionMode() {
		return myQuestPanels;
	}

	/**
	 * myLabelPanel getter
	 * 
	 * @return myLabelPanel
	 */
	public LabelPanel getLabelPanel() {
		return myLabelPanel;
	}

	/**
	 * @return the myControlPanel
	 */
	public ControlPanel getMyControlPanel() {
		return myControlPanel;
	}

	/**
	 * @return the myReveal
	 */
	public Boolean getMyReveal() {
		return myReveal;
	}

	/**
	 * Add an object that this panel will listen for property changes.
	 * 
	 * @param Container the GUI object
	 */
	public void addListener(final Container theObj) {
		theObj.addPropertyChangeListener(this);
	}

	/**
	 * Property changes that update the panel
	 * 
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		final String prop = evt.getPropertyName();

		if (evt.getNewValue() instanceof Integer) {
			final int gm = (int) evt.getNewValue();
			if ("changegm".equals(prop)) { // change the gamemode panel
				// System.out.println("in panel changes question panel");
				if (!myMaze.isAtStart()) {
					myQuestPanels.setGamemode(gm);
				}
			}

		} else {
			if ("win".equals(prop)) {
				// TODO: add more to this win message
				// JOptionPane.showMessageDialog(null, "You win!");
				final WinPane win = new WinPane(this);
				win.showCondition();
			} else if ("lose".equals(prop)) {
				// TODO: add more to this lose message
				// JOptionPane.showMessageDialog(null, "You lose!");
				final LosePane lose = new LosePane(this);
				lose.showCondition();
			} else if ("showpkmn".equals(prop)) { // reveal or hide the pokemon
				// setImgBrightness();
				showAnswerImage();
			}

		}

	}

}
