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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import model.Maze;
import model.TriviaGame;
import view.viewHelper.BrightnessUtility;
import view.viewHelper.ControlPanel;
import view.viewHelper.MazeGUI;
import view.viewHelper.QuestionRoomGUI;
import view.viewHelper.AbstractRoomPanel;
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
	 * default layout of the GUI
	 */
	private final SpringLayout springLayout = new SpringLayout();

	/*
	 * Size of the shine background
	 */
	private int myShineW;
	private int myShineH;

	/*
	 * Background color of the game (Crimson Red)
	 */
	private final Color BG_COLOR = new Color(220, 20, 60);

	/*
	 * Background color of border
	 */
	private final Color BORDER_COLOR = new Color(51, 153, 204);

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
	 * GUI visual of the maze
	 */
	private final MazeGUI mazeGUI;
	/*
	 * Question GUI
	 */
	private final QuestionRoomGUI myQuestionRoomGUI;

	private final TextRoomGUI myTextRoomGUI;
	
	private final ControlPanel myControlPanel;
	
	private final StartRoomPanel myStartPanel;
	
	//debuggers
	private JLabel myRoomLbl;
	private JLabel myDirLbl;
	private JLabel myAnsLbl;


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
		
		
		addPropertyChangeListener(this);
		setupDebugLabels();
		

		/// initialize panel
		setupPanel();

		// draw onto panel Pokemon and background
		setupPictures();


	}
	/*
	 * Show game progress via labels
	 * Main use is for debugging
	 */
	public void setupDebugLabels() {
		myRoomLbl = new JLabel();
		myRoomLbl.setForeground(Color.WHITE);
		myDirLbl = new JLabel();
		myDirLbl.setForeground(Color.WHITE);
		myAnsLbl = new JLabel();
		myAnsLbl.setForeground(Color.WHITE);
		
		springLayout.putConstraint(SpringLayout.NORTH, myDirLbl, 20,
				SpringLayout.NORTH, this);
		
		springLayout.putConstraint(SpringLayout.NORTH, myAnsLbl, 20,
				SpringLayout.NORTH, myDirLbl);
		add(myRoomLbl);
		add(myDirLbl);
		add(myAnsLbl);
	}
	
	/*
	 * Update progression in game such as where player is
	 */
	public void updateLabels() {
		myRoomLbl.setText("Currently in Room " + myMaze.getCurrRoom());
		myAnsLbl.setText("Answer is " + myMaze.getAttemptRoom().getAnswer());
		if(myMaze.hasNotMoved()) {
			myDirLbl.setText("Chosen Direction: None");
		} else {
			myDirLbl.setText("Attempting to go to Room " + myMaze.getAttemptRoom());
		}
	}
	

	/**
	 * Setup the components on the panel for the game
	 */
	private void setupPanel() {
		setBackground(BG_COLOR);
		setPreferredSize(PANEL_SIZE);

		// put stuff on the panel
		final Border blueLine = BorderFactory.createLineBorder(BORDER_COLOR, 5);
		mazeGUI.setBorder(blueLine);
		
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
		
		//myStartPanel
		springLayout.putConstraint(SpringLayout.SOUTH, myStartPanel, 450,
				SpringLayout.SOUTH, mazeGUI);
		springLayout.putConstraint(SpringLayout.WEST, myStartPanel, 1450,
				SpringLayout.WEST, this);
		
		
		

		setLayout(springLayout);
		add(mazeGUI);
		add(myQuestionRoomGUI);
		add(myTextRoomGUI);
		//add(myStartPanel);
		add(myControlPanel);
;

		// disable question rooms until player selects a direction
		myTextRoomGUI.setVisible(false);
		//myQuestionRoomGUI.setVisible(false);
		

	}
	

	/**
	 * Put initial pictures on the panel
	 * 
	 */
	public void setupPictures() {

		// draw the pokemon
		setImage();
		setImgBrightness(0);

		// sparkly thing behind a pokemon
		myShine = readImage("./src/images/other/sparkle_formatted.png");
		if (myPoke != null && myShine != null) {
			myShineW = myShine.getWidth();
			myShineH = myShine.getHeight();
		}

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
//        final BufferedImage myPoke = (BufferedImage) BrightnessUtility.adjustBrighness(myPoke, 0f);

		theG.drawImage(myShine, 0, 0, myShineW, myShineH, this);
		theG.drawImage(myPoke, X_OFFSET, Y_OFFSET, POKE_W, POKE_H, this);
		firePropertyChange("newpos", null, null);
		updateLabels(); //debugger
		// theG.drawImage(myPoke, 0, 0, POKE_W, POKE_H, this);

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

	/*
	 * Add an object that this panel will listen for property changes.
	 * 
	 * @param Container the GUI object
	 */
	public void addListener(Container theObj) {
		((Container) theObj).addPropertyChangeListener(this);
	}

	
	/*
	 * Property changes that update the panel
	 * 
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		
		if (evt.getNewValue() instanceof Boolean) {
			Boolean res = (Boolean) evt.getNewValue();
			if ("changegm".equals(prop)) { //change the gamemode panel
				// System.out.println("in panel changes question panel");
				setPanels(res);
			}

			if ("showpkmn".equals(prop)) { //reveal or hide the pokemon
				//System.out.println("in panel reveal");
				myPoke = res ? myPokeLight : myPokeDark;
				repaint();
			}
			
			
		} else {
			if("win".equals(prop)) {
				//TODO: add more to this win message
				JOptionPane.showMessageDialog(null, "You win!");
			}
			
			if("lose".equals(prop) ) {
				//TODO: add more to this lose message 
				JOptionPane.showMessageDialog(null, "You lose!");
			}

		}
		
		
	}
}
