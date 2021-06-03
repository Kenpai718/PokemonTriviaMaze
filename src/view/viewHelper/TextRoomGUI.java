package view.viewHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import model.Maze;
import view.PokemonPanel;

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
	private final Dimension SIZE = new Dimension(350, 100);
	/*
	 * Color of maze
	 */
	final Color MAZE_BG = new Color(51, 51, 51);
	private final Color BORDER_COLOR = new Color(51, 153, 204);

	/*
	 * Border
	 */
	final Border BORDER = BorderFactory.createLineBorder(BORDER_COLOR, 5);

	/*
	 * text put in the text boxes
	 */
	@SuppressWarnings("unused")
	private final String QUESTION = "Who's that Pokemon?";
	private final String PUT_TEXT = "Enter the answer here.";

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

	/**
	 * Constructor
	 * 
	 * @param reference to pokemon panel
	 */
	public TextRoomGUI(PokemonPanel thePP) {
		super(thePP);
		setBackground(BORDER_COLOR);
		setPreferredSize(SIZE);
		setMaximumSize(SIZE);
		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		this.setBackground(MAZE_BG);
		this.setBorder(BORDER);

		// text pane that asks the question
		final JTextPane question = new JTextPane();
		question.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 11));
		question.setText("Who's that Pokemon?");
		springLayout.putConstraint(SpringLayout.NORTH, question, 10,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, question, 10,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, question, 340,
				SpringLayout.WEST, this);
		question.setEditable(false);
		add(question);

		// text field for the user to answer to
		myUserAns = new JTextField(PUT_TEXT);
		myUserAns.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 11));
		springLayout.putConstraint(SpringLayout.NORTH, myUserAns, 6,
				SpringLayout.SOUTH, question);
		springLayout.putConstraint(SpringLayout.WEST, myUserAns, 0,
				SpringLayout.WEST, question);
		springLayout.putConstraint(SpringLayout.SOUTH, myUserAns, 60,
				SpringLayout.SOUTH, question);
		springLayout.putConstraint(SpringLayout.EAST, myUserAns, 0,
				SpringLayout.EAST, question);
		add(myUserAns);
		myUserAns.setColumns(10);
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
				 * if(myUserAns.getText().isEmpty()) {
				 * myUserAns.setText(PUT_TEXT); }
				 */

			}

		});

		myMaze = Maze.getInstance();
	}

	/**
	 * Enable state of the text field
	 * 
	 * @param the text field state
	 */
	@Override
	public void enableButtons(Boolean theBool) {
		if (theBool) {
			myUserAns.setEnabled(true);
		} else {
			myUserAns.setEnabled(false);
		}

	}

	/**
	 * Startup settings of user input field
	 * 
	 */
	@Override
	public void setButtons() {
		myUserAns.setText(PUT_TEXT);

	}

	//inner class that checks for when user enters an answer
	public class EnterListener extends KeyAdapter {

		@Override
		public void keyPressed(final KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

				verifyAnswer(myUserAns.getText());

				myUserAns.setText(""); // return to default
			}
		}

	}

}
