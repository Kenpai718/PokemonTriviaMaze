package view;

import java.awt.Color;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import model.Maze;
import model.TriviaGame;
import view.viewHelper.BrightnessUtility;
import view.viewHelper.ControlPanel;
import view.viewHelper.LabelPanel;
import view.viewHelper.MazeGUI;
import view.viewHelper.MazeGUI.MazeModel;
import view.viewHelper.QuestionModePanel;
import view.viewHelper.QuestionRoomGUI;
import view.viewHelper.TextRoomGUI;
import view.viewHelper.TutorialPanel;

/**
 * Setups playable game visuals on a panel
 * 
 * @author ajdowney
 * @author ken
 * @version Spring 2021
 */
public class PokemonPanel extends JPanel implements PropertyChangeListener {
	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 8168838797214673243L;

	/*
	 * Player full avatar used in the starting room
	 */
	private final String PLAYER_M = "./src/images/other/PLAYER_M.png";
	private final String PLAYER_F = "./src/images/other/PLAYER_F.png";
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
	private QuestionModePanel myQuestPanels;

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
		myControlPanel = new ControlPanel(this);
		addPropertyChangeListener(myControlPanel);
		myLabelPanel = new LabelPanel();
		myQuestPanels = new QuestionModePanel(this);

		// listeners
		addPropertyChangeListener(this);
		addListener(myMazeGUI);

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
		myMazeGUI.setBorder(BLUE_BORDER);
		setLayout(springLayout);

		// position panel components

		// maze gui
		springLayout.putConstraint(SpringLayout.NORTH, myMazeGUI, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, myMazeGUI, 1345, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, myMazeGUI, -10, SpringLayout.EAST, this);

		// question panel
		springLayout.putConstraint(SpringLayout.NORTH, myQuestPanels, 553, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, myQuestPanels, -36, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myQuestPanels, -107, SpringLayout.EAST, this);

		// label panel
		springLayout.putConstraint(SpringLayout.NORTH, myLabelPanel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myLabelPanel, -22, SpringLayout.WEST, myMazeGUI);

		// control panel
		springLayout.putConstraint(SpringLayout.SOUTH, myControlPanel, -150, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myControlPanel, 0, SpringLayout.EAST, myLabelPanel);

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
	 * 
	 */
	public void setupPictures() {

		// initialize player avatar
		myAvatar = getScaledImage(readImage(PLAYER_M), POKE_W, POKE_H);

		// iconic question mark
		myQuestionImg = readImage("./src/images/other/questionmark.png");
		if (myQuestionImg != null) {
			myQuestW = myQuestionImg.getWidth();
			myQuestH = myQuestionImg.getHeight();
		}

		// sparkly thing behind a pokemon
		myShine = readImage("./src/images/other/sparkle_formatted.png");
		if (myShine != null) {
			myShineW = myShine.getWidth();
			myShineH = myShine.getHeight();
		}

		/*
		 * // draw the pokemon for the room we are in/attempting to go to setImage();
		 */
		// draw the player avatar since we are starting the game for the first time and
		// there will be no pokemon
		setStartRoomPicture();

	}

	/**
	 * Set pokemon pictures for dark and light depending on the current room Resize
	 * them if they are not 600x600px
	 * 
	 */
	private void setImage() {

		myPokeLight = myMaze.getAttemptRoom().getPokemon().getPNG();
		// resize if not the correct width or height
		if (myPokeLight.getWidth() < POKE_W || myPokeLight.getHeight() < POKE_H || myPokeLight.getWidth() > POKE_W
				|| myPokeLight.getHeight() > POKE_H) {
			myPokeLight = getScaledImage(myPokeLight, POKE_W, POKE_H);
		}

		myPokeDark = (BufferedImage) BrightnessUtility.setToBlack(myPokeLight);
		myPoke = myDark ? myPokeDark : myPokeLight;

		repaint();

	}

	/*
	 * Put a player avatar instead of a pokemon for the first room picture
	 */
	private void setStartRoomPicture() {
		myPoke = myAvatar;
		myPokeLight = myAvatar;
		myPokeDark = myAvatar;
		myShowQMark = false;
		repaint();
	}

	/*
	 * Full refresh that updates all important components for the gui
	 */
	public void refreshGUI() {
		
		//show question mark if pokemon has not been revealed yet
		myShowQMark = !(myMaze.getAttemptRoom().hasVisited());
		refreshPokemonImage();

		/*
		 * Show tutorial if at first room
		 */
		if (myMaze.isAtStart()) {
			myQuestPanels.showTutorial();
			setStartRoomPicture();
		} else {
			myQuestPanels.setCurrentGamemode();
			myQuestPanels.updateQP();
		}

		myLabelPanel.updateLabels();

		// fire property changes
		firePropertyChange("newpos", null, null);
		firePropertyChange("model", null, myMaze.getMatrix());

	}

	/*
	 * Refreshes the image of the pokemon on the panel For instance
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

		// TODO: change this so this isn't fired every repaint and only when needed
		// property to update the control panel buttons
//		firePropertyChange("newpos", null, null);

	}

	/**
	 * set dark variable Dark = true when not answered/visited
	 * 
	 */
	private void setImgBrightness() {
		myDark = !(myReveal
				|| (myMaze.hasNotMoved() ? myMaze.getCurrRoom().hasVisited() : myMaze.getAttemptRoom().hasVisited()));

		repaint();

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
	 * @param myReveal the myReveal to set
	 */
	public void setMyReveal(final Boolean theReveal) {
		myReveal = theReveal;
	}
	
	/*
	 * Take off the question mark from the paint panel
	 * if the question has been answered already
	 */
	public void setShowQMark() {
		myShowQMark = !(myMaze.getAttemptRoom().hasVisited());
		repaint();
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
				JOptionPane.showMessageDialog(null, "You win!");
			} else if ("lose".equals(prop)) {
				// TODO: add more to this lose message
				JOptionPane.showMessageDialog(null, "You lose!");
			} else if ("showpkmn".equals(prop)) { // reveal or hide the pokemon
				setImgBrightness();
			}

		}

	}
	
	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage. It is
	 * scaled this way to prevent the picture from looking blurry.
	 * 
	 * SOURCE:
	 * https://riptutorial.com/java/example/28299/how-to-scale-a-bufferedimage
	 * 
	 * @param srcImg - source image to scale
	 * @param w      - desired width
	 * @param h      - desired height
	 * @return - the new resized image
	 */
	public BufferedImage getScaledImage(final Image srcImg, final int w, final int h) {

		// Create a new image with good size that contains or might contain
		// arbitrary alpha values between and including 0.0 and 1.0.
		final BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);

		// Create a device-independant object to draw the resized image
		final Graphics2D g2 = resizedImg.createGraphics();

		// improve quality of rendering
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// Finally draw the source image in the Graphics2D with the desired
		// size.
		g2.drawImage(srcImg, 0, 0, w, h, null);

		// Disposes of this graphics context and releases any system resources
		// that it is using
		g2.dispose();

		// Return the image used to create the Graphics2D
		return resizedImg;
	}

	/**
	 * Helper method to read an Image given a filepath
	 * 
	 * @param String theLocation filepath
	 * @return BufferedImage the new image
	 */
	public BufferedImage readImage(final String theLocation) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(theLocation));
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return img;
	}
}
