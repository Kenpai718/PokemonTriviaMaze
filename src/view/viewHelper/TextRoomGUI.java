package view.viewHelper;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import model.Maze;
import model.Room;

import javax.swing.JTextField;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.CardLayout;
import javax.swing.JTextPane;

import java.awt.event.KeyAdapter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.SystemColor;
import java.awt.Font;

/*
 * Used in the case of a input text gamemode
 */

public class TextRoomGUI extends JPanel{
	
	Dimension SIZE = new Dimension(350,100);
	private final Color BORDER_COLOR = new Color(51, 153, 204);
	
	private String QUESTION = "Who's that Pokemon?";
	private String PUT_TEXT = "Type answer here.";
	
	private JTextField myUserAns;
	Maze myMaze;
	
	
	

	public TextRoomGUI() {
		setBackground(BORDER_COLOR);
		setPreferredSize(SIZE);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		
		JTextPane question = new JTextPane();
		question.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 11));
		question.setText("Who's that Pokemon?");
		springLayout.putConstraint(SpringLayout.NORTH, question, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, question, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, question, 340, SpringLayout.WEST, this);
		question.setEditable(false);
		add(question);
		
		myUserAns = new JTextField();
		myUserAns.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 11));
		springLayout.putConstraint(SpringLayout.NORTH, myUserAns, 6, SpringLayout.SOUTH, question);
		springLayout.putConstraint(SpringLayout.WEST, myUserAns, 0, SpringLayout.WEST, question);
		springLayout.putConstraint(SpringLayout.SOUTH, myUserAns, 60, SpringLayout.SOUTH, question);
		springLayout.putConstraint(SpringLayout.EAST, myUserAns, 0, SpringLayout.EAST, question);
		add(myUserAns);
		myUserAns.setColumns(10);
		myUserAns.addKeyListener(new EnterListener());
		myUserAns.addFocusListener((FocusListener) new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				myUserAns.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				/*
				 * if(myUserAns.getText().isEmpty()) {
				 * myUserAns.setText(PUT_TEXT); }
				 */
				
			}
			
		});
		
		// TODO Auto-generated constructor stub
		//setupGUI();
		myMaze = Maze.getInstance();
	}
	
	public void setupGUI() {
		
	}

	
	public class EnterListener extends KeyAdapter {
		
		public void keyPressed(KeyEvent evt)
        {
            if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            {
            	//obtain info for answer
        		Room curr = myMaze.getCurrRoom();
        		String correctAns = curr.getAnswer();
        		String userAns = myUserAns.getText();
        		String correct = correctAns + " was the correct answer!";
        		String incorrect = "Sorry, but " + userAns + " is incorrect... ";
        		
        		//format answer to prevent errors
        		correctAns = correctAns.toLowerCase().strip();
        		userAns = userAns.toLowerCase().strip();
        		
        		if(userAns.equals(correctAns)) {
        			JOptionPane.showMessageDialog(null, "Good job! " + correct);
        			
        		} else {
        			JOptionPane.showMessageDialog(null, incorrect + correct);
        		}
        		
        		myUserAns.setText(""); //return to default
            }
        }



		
	}
}
