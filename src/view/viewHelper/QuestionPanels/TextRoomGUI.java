package view.viewHelper.QuestionPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import model.Maze;
import view.PokemonPanel;
import view.viewHelper.AbstractQuestionPanel;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 * Used in the case of a input text gamemode
 * 
 * @author Ken Ahrens
 */

public class TextRoomGUI extends AbstractQuestionPanel {

	/**
	 * constants
	 */
	private static final long serialVersionUID = -8894683351403359585L;

	/*
	 * Info on how to answer in user input mode
	 */
	private final String USER_HINTS = 
			"User Input Guide: "
			+ "\n*Type Pokemon name in textbox."
			+ "\n*Spacing/case does not matter," 
			+ "\n but spelling/letter order does."
			+ "\n*Special characters and spacing "
			+ "\n will be ignored."
			+ "\n*Press \"enter\" on keyboard to submit." 
			+ "\n\nUnique Cases:" 
			+ "\n*Mega: \"mega\" + \"Pokemon Name\""
			+ "\n EX: Mega Mewtwo X"
			+ "\n*Form: \"Pokemon Name\" + \"form name\""
			+ "\n EX: Shaymin Sky"
			+ "\n*Regional: \"Pokemon Name\" + \"region\"" 
			+ "\n EX: Vulpix Alolan"
			+ "\n*You don't need to specify gender.";

	/*
	 * Color of maze
	 */
	final Color MAZE_BG = new Color(51, 51, 51);
	final LineBorder BLACK_BORDER = new LineBorder(Color.BLACK, 2);
	private final Color LIGHT_BLUE = new Color(51, 153, 204);
	/*
	 * text put in the text boxes
	 */
	@SuppressWarnings("unused")
	private final String QUESTION = "Who's that Pokemon?";
	private final String PUT_TEXT = "Enter the answer here.";

	/*
	 * Border
	 */
	final Border BORDER = BorderFactory.createLineBorder(LIGHT_BLUE, 5);

	/*
	 * Text field that stores the user answer
	 */
	private final JTextField myUserAns;
	/*
	 * Maze reference
	 */
	private Maze myMaze;
	/*
	 * Panel reference
	 */
	private PokemonPanel myPP;

	/*
	 * Dimension
	 */
	private final Dimension mySize;

	/*
	 * Hints for how to do user input
	 */
	// private final JPanel myHintPanel;

	private final JPanel myContainer;
	private JTextArea txtrUserInputHints;

	/**
	 * Constructor
	 * 
	 * @param reference to pokemon panel
	 */
	public TextRoomGUI(PokemonPanel thePP) {
		super(thePP);
		mySize = super.DEFAULT_DIM;
		// mySize = new Dimension(350, 500);
		myPP = thePP;
		this.setBackground(MAZE_BG);
		this.setPreferredSize(mySize);
		this.setMaximumSize(mySize);
		this.setLayout(new BorderLayout());
		this.setBackground(MAZE_BG);
		this.setBorder(BORDER);

		SpringLayout springLayout = new SpringLayout();
		myContainer = new JPanel();
		Dimension myContSize = new Dimension(320, 500);
		myContainer.setSize(myContSize);
		myContainer.setBackground(MAZE_BG);
		myContainer.setPreferredSize(myContSize);
		myContainer.setMaximumSize(myContSize);
		myContainer.setLayout(springLayout);
		this.add(myContainer, BorderLayout.NORTH);

		// text pane that asks the question
		final JTextPane question = new JTextPane();
		question.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 11));
		question.setText("Who's that Pokemon?");
		question.setEditable(false);
		myContainer.add(question);

		// text field for the user to answer to
		myUserAns = new JTextField(PUT_TEXT);
		myUserAns.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 11));
		myContainer.add(myUserAns);

		txtrUserInputHints = new JTextArea();

		springLayout.putConstraint(SpringLayout.SOUTH, txtrUserInputHints, 336, SpringLayout.SOUTH, myUserAns);
		txtrUserInputHints.setText(USER_HINTS);
		/// txtrUserInputHints.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 8));
		txtrUserInputHints.setEditable(false);
		txtrUserInputHints.setBackground(Color.LIGHT_GRAY);
		txtrUserInputHints.setBorder(BLACK_BORDER);

		myContainer.add(txtrUserInputHints);
		myUserAns.addKeyListener(new EnterListener());
		myUserAns.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(final FocusEvent e) {
				// clear text if user clicks on here
				myUserAns.setText("");

			}

			@Override
			public void focusLost(final FocusEvent e) {
				/*
				 * if(myUserAns.getText().isEmpty()) { myUserAns.setText(PUT_TEXT); }
				 */

			}

		});

		myMaze = Maze.getInstance();

		// component constraints
		springLayout.putConstraint(SpringLayout.NORTH, question, 10, SpringLayout.NORTH, myContainer);
		springLayout.putConstraint(SpringLayout.WEST, question, 10, SpringLayout.WEST, myContainer);
		springLayout.putConstraint(SpringLayout.EAST, question, -10, SpringLayout.EAST, myContainer);

		springLayout.putConstraint(SpringLayout.SOUTH, question, -15, SpringLayout.NORTH, myUserAns);
		springLayout.putConstraint(SpringLayout.NORTH, myUserAns, 45, SpringLayout.NORTH, myContainer);
		springLayout.putConstraint(SpringLayout.WEST, myUserAns, 10, SpringLayout.WEST, myContainer);
		springLayout.putConstraint(SpringLayout.SOUTH, myUserAns, -419, SpringLayout.SOUTH, myContainer);
		springLayout.putConstraint(SpringLayout.EAST, myUserAns, -10, SpringLayout.EAST, myContainer);
		springLayout.putConstraint(SpringLayout.NORTH, txtrUserInputHints, 30, SpringLayout.SOUTH, myUserAns);
		springLayout.putConstraint(SpringLayout.WEST, txtrUserInputHints, 10, SpringLayout.WEST, myContainer);
		springLayout.putConstraint(SpringLayout.EAST, txtrUserInputHints, 0, SpringLayout.EAST, question);
	}

	/**
	 * Enable state of the text field
	 * 
	 * @param the text field state
	 */
	public void enableButtons(Boolean theBool) {
		if (theBool) {
			myUserAns.setEnabled(true);
		} else {
			myUserAns.setEnabled(false);
		}

	}

	/**
	 * Startup settings of user input field Makes textbox focused for convience
	 * 
	 */
	public void setButtons() {
		// myUserAns.setText(PUT_TEXT);
		if (myMaze.getAttemptRoom().hasVisited()) {
			myUserAns.setText(myMaze.getAttemptRoom().getAnswer());
		} else {
			myUserAns.requestFocusInWindow();
		}

	}

	// inner class that checks for when user enters an answer
	public class EnterListener extends KeyAdapter {

		@Override
		public void keyPressed(final KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

				verifyAnswer(myUserAns.getText());

				// remove focus so the movement keybinds still work
				myPP.requestFocusInWindow();
			}
		}

	}
}
