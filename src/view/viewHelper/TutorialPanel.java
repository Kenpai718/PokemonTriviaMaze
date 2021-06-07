package view.viewHelper;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import model.Maze;
import view.PokemonPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Holds a panel with an image tutorial
 * on how to play the game.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class TutorialPanel extends JPanel{
	
	/*
	 * Constants
	 */
	private final int W = 350;
	private final int H = 500;
	private final String TUT_PATH = "./src/images/tutorial/tutorial_summary.png";
	private final Dimension SIZE = new Dimension(350, 500);
	/*
	 * Background color of border
	 */
	private final Color BORDER_COLOR = new Color(51, 153, 204);

	/*
	 * Border
	 */
	final Border BLUE_BORDER = BorderFactory.createLineBorder(BORDER_COLOR, 5);
	
	/*
	 * Reference to main panel to use its methods
	 */
	private PokemonPanel myPanel;

	/*
	 * Reference to maze
	 */
	private Maze myMaze;
	
	/*
	 * Image that represents the tutorial
	 */
	private BufferedImage myTutorialSum;
	
	
	/**
	 * Constructor
	 * 
	 * @param PokemonPanel a reference so it can update the gui
	 */
	public TutorialPanel(final PokemonPanel thePP) {
		myPanel = thePP;
		myMaze = Maze.getInstance();
		setPreferredSize(SIZE);
		setMaximumSize(SIZE);
		myTutorialSum = ImageUtility.readImage(TUT_PATH);
		myTutorialSum = ImageUtility.getScaledImage(myTutorialSum, W, H);
		
		this.repaint();
		this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, BORDER_COLOR));
		
	}
	
	
	
	/**
	 * Paints the pokemon and background
	 */
	@Override
	protected void paintComponent(final Graphics theG) {
		super.paintComponent(theG);

		theG.drawImage(myTutorialSum, 0, 0, W, H, this);


	}
	
	

}
