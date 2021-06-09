package view.viewHelper;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Maze;
import view.PokemonPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Holds all the panels that the user interacts with to answer questions. Allows
 * switching of panel type using a card layout.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 *
 */

public class QuestionModePanel extends JPanel {

	/*
	 * Total gamemode panels. 0 = tutorial, 1 = multiple choice, 2 = user input
	 */
	final int NUM_PANELS = 3;

	/*
	 * default size
	 */
	private final Dimension SIZE = new Dimension(355, 800);

	/*
	 * Layout of the panel
	 */
	private final CardLayout C1 = new CardLayout();

	/*
	 * Icons for the buttons to switch gamemode
	 */
	private final ImageIcon EASY = new ImageIcon(
			"./src/images/other/pokeball.png");
	private final ImageIcon HARD = new ImageIcon(
			"./src/images/other/masterball.png");

	/*
	 * Reference to main panel
	 */
	private final PokemonPanel myPP;

	/*
	 * Reference to maze
	 */
	private final Maze myMaze;

	/*
	 * Containers that hold the question panels and buttons
	 */
	private final JPanel myFullContainer;
	private final JPanel myMCContainer;
	private final JPanel myTextContainer;
	// private final JPanel myTutContainer;

	/*
	 * Actual Jpanels with the questions/answers gamemodes for interaction
	 */
	private final QuestionRoomGUI myMC; // multiple choice
	private final TextRoomGUI myTR; // user input
	private final TutorialPanel myTP; // tutorial

	/*
	 * Buttons to switch gamemodes on the panel
	 */
	private JButton myTextSwitch;
	private JButton myMCSwitch;

	/*
	 * Keeps track of which gamemode is being used
	 */
	private int myGamemode;

	/*
	 * Map of gamemodes. Key = gamemode num, value = panel type
	 */
	Map<Integer, AbstractQuestionPanel> myQuestionPanels;

	/**
	 * Constructor. Builds multiple containers that holds the gamemode question
	 * panels that can be switched between.
	 * 
	 * @param reference to pokemon panel
	 */
	public QuestionModePanel(final PokemonPanel thePP) {
		myPP = thePP;
		myMaze = Maze.getInstance();
		myGamemode = 1; // multiple choice by default

		/*
		 * This panel setup
		 */
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setMaximumSize(SIZE);
		this.setLayout(new BorderLayout());
		this.setOpaque(false);

		/*
		 * Contains all panels in a card
		 */
		myFullContainer = new JPanel();
		myFullContainer.setPreferredSize(SIZE);
		myFullContainer.setLayout(new BorderLayout());
		myFullContainer.setLayout(C1); // set card layout to switch between
										// gamemodes
		myFullContainer.setOpaque(false);

		this.add(myFullContainer);

		/*
		 * Setup question panels
		 */
		myTP = new TutorialPanel(myPP);
		myMC = new QuestionRoomGUI(myPP);
		myTR = new TextRoomGUI(myPP);

		/*
		 * Setup container that holds the multiple choice and switch to text
		 * button
		 */
		myMCContainer = buildContainer();
		myMCContainer.add(myMC);

		/*
		 * Setup container that holds the user input and switch to mc button
		 */
		myTextContainer = buildContainer();
		myTextContainer.add(myTR);

		/*
		 * Setup buttons
		 */
		myTextSwitch = new JButton("Switch to user input (HARD)", HARD);
		myTextSwitch.addActionListener(new MCPanelListener());
		myMCSwitch = new JButton("Switch to multiple choice (EASY)", EASY);
		myMCSwitch.addActionListener(new TextPanelListener());

		/*
		 * Add buttons to container
		 */
		myMCContainer.add(myTextSwitch, BorderLayout.SOUTH);
		myTextContainer.add(myMCSwitch, BorderLayout.SOUTH);

		/*
		 * Add to container to this card layout
		 */
		myFullContainer.add(myTP, "0");
		myFullContainer.add(myMCContainer, "1"); // 1 = multiple choice
		myFullContainer.add(myTextContainer, "2"); // 2 = user input

		// set default shown
		C1.show(myFullContainer, "0");

		addListenersToPokemonPanel();

		/*
		 * Add questions panels to the hashmap
		 */
		myQuestionPanels = new HashMap<Integer, AbstractQuestionPanel>();
		myQuestionPanels.put(1, myMC);
		myQuestionPanels.put(2, myTR);

	}
	
	/**
	 * Builder design pattern to make a jpanel container
	 * 
	 * @return formatted container jpanel
	 */
	private JPanel buildContainer() {
		JPanel aPanel = new JPanel();
		aPanel.setPreferredSize(SIZE);
		aPanel.setLayout(new BorderLayout());
		aPanel.setLayout(new BorderLayout());
		aPanel.setOpaque(false);

		return aPanel;
	}



	/**
	 * Set the question gamemode 0 = tutorial, 1 = multiple choice, 2 = user
	 * input
	 * 
	 * @param theNum
	 */
	public void setGamemode(final int theNum) {
		final String gm = String.valueOf(theNum);
		if (theNum > NUM_PANELS || theNum < 1) {
			C1.show(myFullContainer, "1"); // default multiple choice if invalid
		} else {

			if (myGamemode == 0) {
				// don't update yet if user is on the tutorial page
				myGamemode = theNum;
			} else {
				myGamemode = theNum;
				C1.show(myFullContainer, gm);
			}
		}

	}

	/*
	 * Shows the tutorial panel, but dont set it as a gamemode because it is not
	 * one
	 */
	public void showTutorial() {
		C1.show(myFullContainer, "0");
	}

	/*
	 * Switch the panel to whatever the current gamemode is set to
	 */
	public void setCurrentGamemode() {
		C1.show(myFullContainer, String.valueOf(myGamemode));
	}

	/*
	 * Updates all the question panels
	 */
	public void updateQP() {
		setCurrentGamemode();
		myMC.setButtons();
		myTR.setButtons();
		enableAnswerFields();

	}

	/**
	 * Used to allow/unallow the user to answer the question. Used for when the
	 * user already answered the room so it shouldn't be answered again.
	 * 
	 */
	private void enableAnswerFields() {
		if (myMaze.getAttemptRoom().hasVisited()) {
			myMC.enableButtons(false);
			myTR.enableButtons(false);
		} else {
			myMC.enableButtons(true);
			myTR.enableButtons(true);
		}

	}
	
	/*
	 * Add listeners to the pokemon panel for the gamemode panels
	 */
	private void addListenersToPokemonPanel() {
		myPP.addListener(myMC);
		myPP.addListener(myTR);

	}
	
	/*
	 * Inner classes
	 */

	/**
	 * Switch to user input
	 *
	 */
	class MCPanelListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			setGamemode(2);

			myPP.refreshGUI();

		}

	}

	/**
	 * Switch to multiple choice
	 *
	 */
	class TextPanelListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			setGamemode(1);

			myPP.refreshGUI();

		}

	}

}
