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

public class QuestionRoomGUI extends RoomPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	// private QuestionAnswer myQA;
	private JTextPane myQPane;
	private SpringLayout myLayout;

	private JRadioButton myA1;
	private JRadioButton myA2;
	private JRadioButton myA3;
	private JRadioButton myA4;
	private final int POKE_W = 600;
	private final int POKE_H = 600;

	private Maze myMaze;

	// /*
	// * Multiple choice
	// */
	// private final String[] myChoices;
	//
	// private final Room myCurrRoom;

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

	@SuppressWarnings("static-access")
	private void setupGUI() {
		setBorder(new LineBorder(Color.BLACK, 4, true));
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(350, 500));
		setMaximumSize(new Dimension(350, 500));
		myLayout = new SpringLayout();
		setLayout(myLayout);

		myQPane = new JTextPane();
		myLayout.putConstraint(myLayout.NORTH, myQPane, 22, myLayout.NORTH, this);
		myLayout.putConstraint(myLayout.SOUTH, myQPane, 68, myLayout.NORTH, this);
		myLayout.putConstraint(myLayout.EAST, myQPane, -10, myLayout.EAST, this);
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

	@SuppressWarnings("static-access")
	private void setupQuestions() {

		myA1 = new JRadioButton("");
		myA1.setOpaque(false);
		myLayout.putConstraint(myLayout.NORTH, myA1, 29, myLayout.SOUTH, myQPane);
		myLayout.putConstraint(myLayout.WEST, myQPane, -24, myLayout.WEST, myA1);
		myLayout.putConstraint(myLayout.WEST, myA1, 34, myLayout.WEST, this);
		myLayout.putConstraint(myLayout.EAST, myA1, 0, myLayout.EAST, this);
		myA1.setMnemonic('1');
		myA1.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
		myA1.addActionListener(new AnswerDisplay());
		buttonGroup.add(myA1);
		add(myA1);

		final JRadioButton myA2 = new JRadioButton("");
		myA2.setOpaque(false);
		myLayout.putConstraint(myLayout.SOUTH, myA1, -37, myLayout.NORTH, myA2);
		myLayout.putConstraint(myLayout.NORTH, myA2, 179, myLayout.NORTH, this);
		myLayout.putConstraint(myLayout.WEST, myA2, 34, myLayout.WEST, this);
		myLayout.putConstraint(myLayout.EAST, myA2, 0, myLayout.EAST, this);
		myA2.setMnemonic('2');
		myA2.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
		myA2.addActionListener(new AnswerDisplay());
		buttonGroup.add(myA2);
		add(myA2);

		final JRadioButton myA3 = new JRadioButton("");

		myA3.setOpaque(false);
		myLayout.putConstraint(myLayout.WEST, myA3, 34, myLayout.WEST, this);
		myLayout.putConstraint(myLayout.EAST, myA3, 0, myLayout.EAST, this);
		myLayout.putConstraint(myLayout.SOUTH, myA2, -42, myLayout.NORTH, myA3);
		myLayout.putConstraint(myLayout.NORTH, myA3, 276, myLayout.NORTH, this);
		myA3.setMnemonic('3');
		myA3.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
		myA3.addActionListener(new AnswerDisplay());
		buttonGroup.add(myA3);
		add(myA3);

		final JRadioButton myA4 = new JRadioButton("");
		myA4.setOpaque(false);
		myLayout.putConstraint(myLayout.NORTH, myA4, 47, myLayout.SOUTH, myA3);
		myLayout.putConstraint(myLayout.WEST, myA4, 34, myLayout.WEST, this);
		myLayout.putConstraint(myLayout.SOUTH, myA4, -55, myLayout.SOUTH, this);
		myLayout.putConstraint(myLayout.EAST, myA4, 0, myLayout.EAST, this);
		myA4.setMnemonic('4');
		myA4.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
		myA4.addActionListener(new AnswerDisplay());
		buttonGroup.add(myA4);

		setButtons();
		add(myA4);
	}

	public void setButtons() {
		// TODO Auto-generated method stub
		buttonGroup.clearSelection();
		final Maze maze = Maze.getInstance();
		final ArrayList<String> choices = maze.getAttemptRoom().getChoices();
		final Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		int i = 0;
		while (buttons.hasMoreElements()) {
			final JRadioButton temp = (JRadioButton) buttons.nextElement();
			temp.setText(choices.get(i));
			temp.setForeground(Color.BLACK);
			i++;
		}
	}

	public void setButtonsAnswer() {
		final Maze maze = Maze.getInstance();
		final ArrayList<String> choices = maze.getAttemptRoom().getChoices();
		final Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		int answerIndex = maze.getAttemptRoom().getAnswerIndex();
		int i = 0;
		while (buttons.hasMoreElements()) {
			final JRadioButton temp = (JRadioButton) buttons.nextElement();
			temp.setText(choices.get(i));
			if (i == answerIndex) {
				temp.setForeground(Color.GREEN);
			} else {
				temp.setForeground(Color.RED);
			}
			i++;
		}
	}

	public void answerPopUp() {
		String userAns = "";
		final Maze maze = Maze.getInstance();
		final ArrayList<String> choices = maze.getAttemptRoom().getChoices();
		int answerIndex = maze.getAttemptRoom().getAnswerIndex();
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				userAns = button.getText();
			}
		}

		verifyAnswer(userAns);

	}

	public void reset() {

	}

	class AnswerDisplay implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setButtonsAnswer();
			answerPopUp();
		}
	}

}
