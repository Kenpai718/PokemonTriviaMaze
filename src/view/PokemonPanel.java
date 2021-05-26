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
import sound.BackgroundMusic;
import view.viewHelper.BrightnessUtility;
import view.viewHelper.ControlPanel;
import view.viewHelper.LabelPanel;
import view.viewHelper.MazeGUI;
import view.viewHelper.QuestionRoomGUI;
import view.viewHelper.StartRoomPanel;
import view.viewHelper.TextRoomGUI;

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
	 * Background sparkle effect behind the pokemon
	 */
	private BufferedImage myShine = null;

	// TODO: (KEN) testing setting up the game
	/*
	 * Used to store the game pieces
	 */
	private final TriviaGame myGame;
	/*
	 * Maze of the game
	 */
	private final Maze myMaze;

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
	 * GUI visual of the maze
	 */
	private final MazeGUI mazeGUI;
	/*
	 * Question multiple choice GUI
	 */
	private final QuestionRoomGUI myQuestionRoomGUI;

	
	/*
	 * user input gui
	 */
	private final TextRoomGUI myTextRoomGUI;

	/*
	 * Control button visuals
	 */
	private final ControlPanel myControlPanel;

	/*
	 * TODO: panel for the starting room instead of questions
	 */
	private final StartRoomPanel myStartPanel;

	/*
	 * Panel with labels that tell game state
	 */
	private LabelPanel myLabelPanel;

	/*
	 * Boolean to know if the picture is hidden or visible
	 */
	private boolean myDark;

	/**
	 * Constructor
	 */
	public PokemonPanel() {
		super();

		// start a new game on the panel
		// TODO: run the game off of myGame
		myGame = new TriviaGame();
		myMaze = Maze.getInstance();
		mazeGUI = new MazeGUI();
		myQuestionRoomGUI = new QuestionRoomGUI(this);
		addListener(myQuestionRoomGUI);
		myTextRoomGUI = new TextRoomGUI(this);
		addListener(myTextRoomGUI);
		myControlPanel = new ControlPanel(this);
		addPropertyChangeListener(myControlPanel);
		add(myControlPanel);
		myStartPanel = new StartRoomPanel(myControlPanel);
		myLabelPanel = new LabelPanel();

		addPropertyChangeListener(this);

		/// initialize panel
		setupPanel();

		// draw onto panel Pokemon and background
		setupPictures();

	}


	/**
	 * Setup the components on the panel for the game
	 */
	private void setupPanel() {
		setBackground(BG_COLOR);
		setPreferredSize(PANEL_SIZE);

		// put stuff on the panel

		mazeGUI.setBorder(BLUE_BORDER);

		// control panel property change
		springLayout.putConstraint(SpringLayout.SOUTH, myControlPanel, -60,
				SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myControlPanel, -131,
				SpringLayout.WEST, myQuestionRoomGUI);

		// question room
		springLayout.putConstraint(SpringLayout.NORTH, myQuestionRoomGUI, 553,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, myQuestionRoomGUI, -36,
				SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myQuestionRoomGUI, -107,
				SpringLayout.EAST, this);
		// myQuestionRoomGUI.setVisible(false);

		// maze gui
		springLayout.putConstraint(SpringLayout.NORTH, mazeGUI, 10,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, mazeGUI, 1345,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, mazeGUI, -10,
				SpringLayout.EAST, this);

		// text room GUI
		springLayout.putConstraint(SpringLayout.SOUTH, myTextRoomGUI, -159,
				SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myTextRoomGUI, -92,
				SpringLayout.EAST, this);

		// myStartPanel
		springLayout.putConstraint(SpringLayout.SOUTH, myStartPanel, 450,
				SpringLayout.SOUTH, mazeGUI);
		springLayout.putConstraint(SpringLayout.WEST, myStartPanel, 1450,
				SpringLayout.WEST, this);
		
		//label panel
		springLayout.putConstraint(SpringLayout.NORTH, myLabelPanel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myLabelPanel, -22, SpringLayout.WEST, mazeGUI);

		setLayout(springLayout);
		add(mazeGUI);
		add(myQuestionRoomGUI);
		add(myTextRoomGUI);
		// add(myStartPanel);
		add(myControlPanel);
		add(myLabelPanel);

		// disable question rooms until player selects a direction
		myTextRoomGUI.setVisible(false);
		// myQuestionRoomGUI.setVisible(false);

	}

	/**
	 * Put initial pictures on the panel
	 * 
	 */
	public void setupPictures() {

		//iconic question mark
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
		
		// draw the pokemon for the room we are in/attempting to go to
		setImage();
		setImgBrightness(0);

	}

	/**
	 * Set pokemon pictures for dark and light depending on the current room
	 * Resize them if they are not 600x600px
	 * 
	 */
	public void setImage() {

		myPokeLight = myMaze.getAttemptRoom().getPokemon().getPNG();
		// resize if not the correct width or height
		if (myPokeLight.getWidth() < POKE_W || myPokeLight.getHeight() < POKE_H
				|| myPokeLight.getWidth() > POKE_W
				|| myPokeLight.getHeight() > POKE_H) {
			myPokeLight = getScaledImage(myPokeLight, POKE_W, POKE_H);
		}

		myPokeDark = (BufferedImage) BrightnessUtility
				.adjustBrighness(myPokeLight, 0f);
		myPoke = myDark ? myPokeDark : myPokeLight;

		repaint();

	}

	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage. It
	 * is scaled this way to prevent the picture from looking blurry.
	 * 
	 * SOURCE:
	 * https://riptutorial.com/java/example/28299/how-to-scale-a-bufferedimage
	 * 
	 * @param srcImg - source image to scale
	 * @param w      - desired width
	 * @param h      - desired height
	 * @return - the new resized image
	 */
	private BufferedImage getScaledImage(final Image srcImg, final int w,
			final int h) {

		// Create a new image with good size that contains or might contain
		// arbitrary alpha values between and including 0.0 and 1.0.
		final BufferedImage resizedImg = new BufferedImage(w, h,
				BufferedImage.TRANSLUCENT);

		// Create a device-independant object to draw the resized image
		final Graphics2D g2 = resizedImg.createGraphics();

		// improve quality of rendering
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

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
	private BufferedImage readImage(final String theLocation) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(theLocation));
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return img;
	}

	/**
	 * Paints the pokemon and background
	 */
	@Override
	protected void paintComponent(final Graphics theG) {
		super.paintComponent(theG);

		theG.drawImage(myShine, 0, 0, myShineW, myShineH, this);
		theG.drawImage(myPoke, X_OFFSET, Y_OFFSET, POKE_W, POKE_H, this);
		theG.drawImage(myQuestionImg, X_OFFSET_QUEST, Y_OFFSET_QUEST, myQuestW, myQuestH, this);
		
		firePropertyChange("newpos", null, null);
		myLabelPanel.updateLabels(); // debugger

	}

	/**
	 * set dark variable
	 */
	public void setImgBrightness(final int thePercentage) {

		myPoke = thePercentage == 0 ? myPokeDark : myPokeLight;
		myDark = thePercentage == 0;

		repaint();

	}

	/*
	 * Set which question panel is visible
	 * 
	 * @param theVal false = user input, true = multiple choice
	 */
	public void setPanels(final boolean theValue) {
		myQuestionRoomGUI.setVisible(theValue);
		myTextRoomGUI.setVisible(!theValue);
	}

	/**
	 * Used to allow/unallow the user to answer the question Used for when the
	 * user already answered the room so it shouldn't be answered again.
	 * 
	 * @param theBool true = enable buttons, false = disable
	 */
	public void enableAnswerFields(final Boolean theBool) {
		myTextRoomGUI.enableButtons(theBool);
		myQuestionRoomGUI.enableButtons(theBool);
	}

	/**
	 * Getter MazeGUi table
	 * 
	 * @return JTable the table that represents the maze
	 */
	public JTable getTable() {
		return mazeGUI.getTable();
	}

	/**
	 * Getter QuestionRoomGUI
	 * 
	 * @return QuestionRoomGUI
	 */
	public QuestionRoomGUI getQuestionGUI() {
		return myQuestionRoomGUI;
	}
	
	/**
	 * Getter TextRoomGUI
	 * 
	 * @return TextRoomGUI
	 */
	public TextRoomGUI getTextGUI() {
		return myTextRoomGUI;
	}
	
	/**
	 * Getter TextRoomGUI
	 * 
	 * @return TextRoomGUI
	 */
	public LabelPanel getLabelPanel() {
		return myLabelPanel;
	}

	/*
	 * Add an object that this panel will listen for property changes.
	 * 
	 * @param Container the GUI object
	 */
	public void addListener(final Container theObj) {
		theObj.addPropertyChangeListener(this);
	}

	/*
	 * Property changes that update the panel
	 * 
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		final String prop = evt.getPropertyName();

		if (evt.getNewValue() instanceof Boolean) {
			final Boolean res = (Boolean) evt.getNewValue();
			if ("changegm".equals(prop)) { // change the gamemode panel
				// System.out.println("in panel changes question panel");
				setPanels(res);
			}

			if ("showpkmn".equals(prop)) { // reveal or hide the pokemon
				// System.out.println("in panel reveal");
				int brightness = res ? 1 : 0; // true = light, false = dark
				setImgBrightness(brightness);
				repaint();
			}

		} else {
			if ("win".equals(prop)) {
				final Object[] options = {"New Game", "Exit"};
				int result = JOptionPane.showOptionDialog(null, "You really are a Pokemon Master!", 
					"Congratulations!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 
						options[1]);
				switch (result) {
					case 0:
						Maze.reset();
						Maze.getInstance();
						BackgroundMusic.stopMusic();
						// need to get the frame so you can dispose of it
						//final JFrame frame = (JFrame) this.getParent();
						//frame.dispose();
						new PokemonGUI();
					case 1:
						System.exit(0);
				}
			}

			if ("lose".equals(prop)) {
				final Object[] options = {"New Game", "Exit"};
				int result = JOptionPane.showOptionDialog(null, "Looks like you should have brought HM Cut with you!", 
						"Better luck next time!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 
						options[1]);
				switch (result) {
				case 0:
					Maze.reset();
					Maze.getInstance();
					BackgroundMusic.stopMusic();
					// need to get the frame to dispose of it
					//JFrame frame = mazeGUI.getParent().getParent();
					//frame.dispose();
					new PokemonGUI();
				case 1:
					System.exit(0);
				}
			}

		}

	}
}
