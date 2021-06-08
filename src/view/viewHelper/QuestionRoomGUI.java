package view.viewHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import model.Maze;
import model.Room;
import view.PokemonGUI;
import view.PokemonPanel;

/**
 * Has the multiple choice question for the room
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class QuestionRoomGUI extends AbstractQuestionPanel {

	/**
	 * constants
	 */
	private static final long serialVersionUID = 1L;
	final Color MAZE_BG = new Color(51, 51, 51);
	private final ButtonGroup myButtonGroup = new ButtonGroup();

	// private QuestionAnswer myQA;
	private JTextPane myQPane;
	private SpringLayout myLayout;

	/*
	 * Multiple choice buttons
	 */
	private JRadioButton myA1;
	private JRadioButton myA2;
	private JRadioButton myA3;
	private JRadioButton myA4;

	/*
	 * Maze
	 */
	private Maze myMaze;

	/**
	 * Create the panel.
	 */
	public QuestionRoomGUI(PokemonPanel thePP) {
		super(thePP);
		// myCurrRoom = theRoom;
		// myChoices = theRoom.getChoices();

		myMaze = Maze.getInstance();
		setupGUI();

	}

	/*
	 * Make the multiple choice gui
	 */
	@SuppressWarnings("static-access")
	private void setupGUI() {
		setBorder(new LineBorder(Color.BLACK, 5, true));
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(350, 500));
		setMaximumSize(new Dimension(350, 500));
		myLayout = new SpringLayout();
		setLayout(myLayout);
		this.setBackground(Color.WHITE);

		myQPane = new JTextPane();
		myLayout.putConstraint(myLayout.NORTH, myQPane, 22, myLayout.NORTH,
				this);
		myLayout.putConstraint(myLayout.SOUTH, myQPane, 68, myLayout.NORTH,
				this);
		myLayout.putConstraint(myLayout.EAST, myQPane, -10, myLayout.EAST,
				this);
		myQPane.setRequestFocusEnabled(false);
		myQPane.setText("Who's that Pokemon?");
		myQPane.setOpaque(false);
		myQPane.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 19));
		myQPane.setFocusable(false);
		myQPane.setEnabled(false);
		myQPane.setDisabledTextColor(Color.BLACK);
		myQPane.setBorder(null);
		add(myQPane);

		setupQuestions();

	}

	/*
	 * Make the questions the panel uses
	 */
	@SuppressWarnings("static-access")
	private void setupQuestions() {

		myA1 = buildButton('1');
		myLayout.putConstraint(myLayout.NORTH, myA1, 29, myLayout.SOUTH,
				myQPane);
		myLayout.putConstraint(myLayout.WEST, myQPane, -24, myLayout.WEST,
				myA1);
		myLayout.putConstraint(myLayout.WEST, myA1, 34, myLayout.WEST, this);
		myLayout.putConstraint(myLayout.EAST, myA1, 0, myLayout.EAST, this);
		myButtonGroup.add(myA1);
		add(myA1);

		final JRadioButton myA2 = buildButton('2');
		myLayout.putConstraint(myLayout.SOUTH, myA1, -37, myLayout.NORTH, myA2);
		myLayout.putConstraint(myLayout.NORTH, myA2, 179, myLayout.NORTH, this);
		myLayout.putConstraint(myLayout.WEST, myA2, 34, myLayout.WEST, this);
		myLayout.putConstraint(myLayout.EAST, myA2, 0, myLayout.EAST, this);
		myButtonGroup.add(myA2);
		add(myA2);

		final JRadioButton myA3 = buildButton('3');
		myLayout.putConstraint(myLayout.WEST, myA3, 34, myLayout.WEST, this);
		myLayout.putConstraint(myLayout.EAST, myA3, 0, myLayout.EAST, this);
		myLayout.putConstraint(myLayout.SOUTH, myA2, -42, myLayout.NORTH, myA3);
		myLayout.putConstraint(myLayout.NORTH, myA3, 276, myLayout.NORTH, this);
		myButtonGroup.add(myA3);
		add(myA3);

		final JRadioButton myA4 = buildButton('4');
		myLayout.putConstraint(myLayout.NORTH, myA4, 47, myLayout.SOUTH, myA3);
		myLayout.putConstraint(myLayout.WEST, myA4, 34, myLayout.WEST, this);
		myLayout.putConstraint(myLayout.SOUTH, myA4, -55, myLayout.SOUTH, this);
		myLayout.putConstraint(myLayout.EAST, myA4, 0, myLayout.EAST, this);
		myButtonGroup.add(myA4);

		setButtons();
		add(myA4);
	}

	/**
	 * Builder for a radio button
	 * 
	 * @return multiple choice button
	 */
	private JRadioButton buildButton(final char theMnemonic) {
		final JRadioButton myMC = new JRadioButton("");
		myMC.setOpaque(false);
		myMC.setMnemonic(theMnemonic);
		myMC.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
		myMC.addActionListener(new AnswerDisplay());
		return myMC;

	}

	/**
	 * Put multiple choice text on the buttons
	 */
	public void setButtons() {
		// set answers if its a room already visited
		if (myMaze.getAttemptRoom().hasVisited()) {
			setButtonsAnswer();
		} else {
			myButtonGroup.clearSelection();
			final Maze maze = Maze.getInstance();
			final ArrayList<String> choices = maze.getAttemptRoom()
					.getChoices();
			final Enumeration<AbstractButton> buttons = myButtonGroup
					.getElements();
			int i = 0;
			while (buttons.hasMoreElements()) {
				final JRadioButton temp = (JRadioButton) buttons.nextElement();
				temp.setText(choices.get(i));
				temp.setForeground(Color.BLACK);
				i++;
			}
		}
	}

	/*
	 * Set the color of the text after the user answers Green = correct, red =
	 * incorrect
	 */
	public void setButtonsAnswer() {
		final Maze maze = Maze.getInstance();
		final ArrayList<String> choices = maze.getAttemptRoom().getChoices();
		final Enumeration<AbstractButton> buttons = myButtonGroup.getElements();
		int answerIndex = maze.getAttemptRoom().getAnswerIndex();
		int i = 0;
		while (buttons.hasMoreElements()) {
			final JRadioButton temp = (JRadioButton) buttons.nextElement();
			temp.setText(choices.get(i));
			if (i == answerIndex) {
				temp.setSelected(true);
				temp.setForeground(Color.GREEN);
			} else {
				temp.setForeground(Color.RED);
			}
			i++;
		}
	}

	/**
	 * Pop up an option pane when the user answers and tell result
	 */
	public void answerPopUp() {
		String userAns = "";
		final Maze maze = Maze.getInstance();
		final ArrayList<String> choices = maze.getAttemptRoom().getChoices();
		int answerIndex = maze.getAttemptRoom().getAnswerIndex();
		for (Enumeration<AbstractButton> buttons = myButtonGroup
				.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				userAns = button.getText();
			}
		}

		verifyAnswer(userAns);

	}

	/**
	 * Set all multiple choice buttons state on or off
	 * 
	 * @boolean the state of buttons
	 */
	@Override
	public void enableButtons(Boolean theBool) {
		final Enumeration<AbstractButton> buttons = myButtonGroup.getElements();
		while (buttons.hasMoreElements()) {
			JRadioButton temp = (JRadioButton) buttons.nextElement();
			if (theBool) {
				temp.setEnabled(true);
			} else {
				temp.setEnabled(false);
			}

		}

	}

	// inner class called when user clicks an answer button
	class AnswerDisplay implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setButtonsAnswer();
			answerPopUp();
		}
	}

}
