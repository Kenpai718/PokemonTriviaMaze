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
	private final LabelPanel myLabelPanel;

	/*
	 * Boolean to know if the picture is hidden or visible
	 */
	private boolean myDark;


        private Boolean myReveal;

	
	

        /**
	 * Constructor
	 */
	public PokemonPanel() {
		super();

		// start a new game on the panel
		// TODO: run the game off of myGame
		myGame = new TriviaGame();
		myMaze = Maze.getInstance();
		myReveal = false;
		myMazeGUI = new MazeGUI();
		addListener(myMazeGUI);
		myMazeModel = (MazeModel) getTable().getModel();
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

		myMazeGUI.setBorder(BLUE_BORDER);

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
		springLayout.putConstraint(SpringLayout.NORTH, myMazeGUI, 10,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, myMazeGUI, 1345,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, myMazeGUI, -10,
				SpringLayout.EAST, this);

		// text room GUI
		springLayout.putConstraint(SpringLayout.SOUTH, myTextRoomGUI, -159,
				SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myTextRoomGUI, -92,
				SpringLayout.EAST, this);

		// myStartPanel
		springLayout.putConstraint(SpringLayout.SOUTH, myStartPanel, 450,
				SpringLayout.SOUTH, myMazeGUI);
		springLayout.putConstraint(SpringLayout.WEST, myStartPanel, 1450,
				SpringLayout.WEST, this);

		// label panel
		springLayout.putConstraint(SpringLayout.NORTH, myLabelPanel, 10,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, myLabelPanel, -22,
				SpringLayout.WEST, myMazeGUI);

		setLayout(springLayout);
		add(myMazeGUI);
		add(myQuestionRoomGUI);
		add(myTextRoomGUI);
		// add(myStartPanel);
		add(myControlPanel);
		add(myLabelPanel);

		// disable question rooms until player selects a direction
		myTextRoomGUI.setVisible(false);
		// fire change to set the buttons for the start
		firePropertyChange("newpos", null, null);
		// myQuestionRoomGUI.setVisible(false);

	}

	/**
	 * Put initial pictures on the panel
	 * 
	 */
	public void setupPictures() {

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

		// draw the pokemon for the room we are in/attempting to go to
		setImage();

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

		myPokeDark = (BufferedImage) BrightnessUtility.setToBlack(myPokeLight);
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

	/*
	 * Updates all important components for the gui
	 */
	public void refreshGUI() {
	        setImgBrightness();
		setImage();
		
		myQuestionRoomGUI.setButtons();
		myTextRoomGUI.setButtons();
		myLabelPanel.updateLabels();
		firePropertyChange("newpos", null, null);
		firePropertyChange("model", null, myMaze.getMatrix());
	}

	/**
	 * Paints the pokemon and background
	 */
	@Override
	protected void paintComponent(final Graphics theG) {
		super.paintComponent(theG);

		theG.drawImage(myShine, 0, 0, myShineW, myShineH, this);
		theG.drawImage(myPoke, X_OFFSET, Y_OFFSET, POKE_W, POKE_H, this);
		theG.drawImage(myQuestionImg, X_OFFSET_QUEST, Y_OFFSET_QUEST, myQuestW,
				myQuestH, this);

		//TODO: change this so this isn't fired every repaint and only when needed
		//property to update the control panel buttons
//		firePropertyChange("newpos", null, null);

	}

	/**
	 * set dark variable
	 * 
	 * @param 0 = dark, anything else = light
	 */
	public void setImgBrightness() {
                myDark = !(myReveal || (myMaze.hasNotMoved() ? myMaze.getCurrRoom().hasVisited() : myMaze.getAttemptRoom().hasVisited()));
//		myPoke = myDark ? myPokeDark : myPokeLight;
		

		repaint();

	}

	/**
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

		if (evt.getNewValue() instanceof Boolean) {
			final boolean res = (Boolean) evt.getNewValue();
			if ("changegm".equals(prop)) { // change the gamemode panel
				// System.out.println("in panel changes question panel");
				setPanels(res);
			}

		} else {
			if ("win".equals(prop)) {
				// TODO: add more to this win message
				JOptionPane.showMessageDialog(null, "You win!");
			} else if ("lose".equals(prop)) {
				// TODO: add more to this lose message
				JOptionPane.showMessageDialog(null, "You lose!");
			} else if ("showpkmn".equals(prop)) { // reveal or hide the pokemon
//                              myReveal = res ;
                                System.out.println("Test");
//                              final int brightness = myReveal ? 1 : 0; // true = light, false = dark
                                setImgBrightness();
//                              repaint();
                        }

		}

	}
}
