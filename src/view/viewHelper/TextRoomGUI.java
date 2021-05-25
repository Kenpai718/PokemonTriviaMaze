package view.viewHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;

import model.Maze;
import view.PokemonPanel;

/*
 * Used in the case of a input text gamemode
 */

public class TextRoomGUI extends AbstractRoomPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8894683351403359585L;
	Dimension SIZE = new Dimension(350,100);
	private final Color BORDER_COLOR = new Color(51, 153, 204);
	
	@SuppressWarnings("unused")
	private final String QUESTION = "Who's that Pokemon?";
	private final String PUT_TEXT = "Type answer here.";
	
	private final JTextField myUserAns;
	private Maze myMaze;
	private PokemonPanel myPP;
	
	
	

	public TextRoomGUI(PokemonPanel thePP) {
		super(thePP);
		setBackground(BORDER_COLOR);
		setPreferredSize(SIZE);
		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		
		final JTextPane question = new JTextPane();
		question.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 11));
		question.setText("Who's that Pokemon?");
		springLayout.putConstraint(SpringLayout.NORTH, question, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, question, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, question, 340, SpringLayout.WEST, this);
		question.setEditable(false);
		add(question);
		
		myUserAns = new JTextField(PUT_TEXT);
		myUserAns.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 11));
		springLayout.putConstraint(SpringLayout.NORTH, myUserAns, 6, SpringLayout.SOUTH, question);
		springLayout.putConstraint(SpringLayout.WEST, myUserAns, 0, SpringLayout.WEST, question);
		springLayout.putConstraint(SpringLayout.SOUTH, myUserAns, 60, SpringLayout.SOUTH, question);
		springLayout.putConstraint(SpringLayout.EAST, myUserAns, 0, SpringLayout.EAST, question);
		add(myUserAns);
		myUserAns.setColumns(10);
		myUserAns.addKeyListener(new EnterListener());
		myUserAns.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(final FocusEvent e) {
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
		
		//setupGUI();
		myMaze = Maze.getInstance();
	}
	
	public void setupGUI() {
		
	}

	
	public class EnterListener extends KeyAdapter {
		
		@Override
                public void keyPressed(final KeyEvent evt)
        {
            if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            {

        		verifyAnswer(myUserAns.getText());
        		
        		myUserAns.setText(""); //return to default
            }
        }



		
	}
}
