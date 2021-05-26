package view.viewHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import model.Maze;

/**
 * Contains labels that tell user about important game state
 * such as what room and which direction they are trying to go
 * has show answer cheat
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class LabelPanel extends JPanel {
	
	/*
	 * Background color of the game (Crimson Red)
	 */
	private final Color BG_COLOR = new Color(51, 51, 51);

	/*
	 * Background color of border
	 */
	private final Color BORDER_COLOR = new Color(51, 153, 204);
	
	/*
	 * default layout of the GUI
	 */
	private final SpringLayout springLayout = new SpringLayout();
	
	/*
	 * Label of the current room name
	 */
	private JLabel myRoomLbl;
	
	/*
	 * Label of the attempted room name
	 */
	private JLabel myDirLbl;
	
	
	/*
	 * Label of the answer to the room
	 */
	private JLabel myAnsLbl;
	
	/*
	 * Maze 
	 */
	private Maze myMaze;



	/**
	 * Constructor
	 */
	public LabelPanel() {
		// TODO Auto-generated constructor stub
		myMaze = Maze.getInstance();
		

		setOpaque(false);
		setPreferredSize(new Dimension(320, 110));
		
		myRoomLbl = buildLabel("Room Label");
		myDirLbl = buildLabel("Attempted Room Label");
		myAnsLbl = buildLabel("Room answer label");
		
		
		add(myRoomLbl);
		add(myDirLbl);
		add(myAnsLbl);
		
		format();
		
		myAnsLbl.setVisible(false); //set to default off because it is a cheat

	}
	
	/**
	 * Builder to make all debug labels the same formatting
	 * 
	 * @return formatted JLabel
	 */
	private JLabel buildLabel(final String theTitle) {
		JLabel lbl = new JLabel(theTitle);
		lbl.setPreferredSize(new Dimension(315, 30));
		Border aBorder = BorderFactory.createLineBorder(BORDER_COLOR, 3);
		lbl.setBorder(aBorder);
		lbl.setBackground(BG_COLOR);
		lbl.setOpaque(true);
		lbl.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
		lbl.setForeground(Color.WHITE);
	
		return lbl;
	}
	
	/*
	 * Positioning of labels
	 */
	private void format() {
		/*
		 * springLayout.putConstraint(SpringLayout.NORTH, myRoomLbl, 5,
		 * SpringLayout.NORTH, this);
		 */
		
		springLayout.putConstraint(SpringLayout.NORTH, myDirLbl, 5,
				SpringLayout.NORTH, myRoomLbl);

		springLayout.putConstraint(SpringLayout.NORTH, myAnsLbl, 5,
				SpringLayout.NORTH, myDirLbl);
	}
	
	/*
	 * Update progression in game such as where player is
	 */
	public void updateLabels() {
		myRoomLbl.setText("Currently in: Room " + myMaze.getCurrRoom());
		myAnsLbl.setText("Answer is: " + myMaze.getAttemptRoom().getAnswer());
		if (myMaze.hasNotMoved()) {
			myDirLbl.setText("Chosen Direction: None");
		} else {
			myDirLbl.setText(
					"Attempting to go to: Room " + myMaze.getAttemptRoom());
		}
	}
	
	/**
	 * Set state of answer label cheat
	 * 
	 * @param boolean toggle state
	 */
	public void enableShowAnswer(final Boolean theState) {
		myAnsLbl.setVisible(theState);
	}

}
