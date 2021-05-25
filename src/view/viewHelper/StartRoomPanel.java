package view.viewHelper;

import java.awt.Color;
import java.awt.Dimension;


import javax.swing.JPanel;

import javax.swing.JTextPane;


import javax.swing.BorderFactory;

import java.awt.BorderLayout;

import javax.swing.border.Border;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/*
 * Used to prompt the user for controls instead of 
 * asking a question. Mainly to control the starting room.
 * 
 * TODO: not sure if we need this rn its just a template
 * 
 */

public class StartRoomPanel extends JPanel {
	
	final Color MAZE_BG = new Color(51, 51, 51);
	private final Color BORDER_COLOR = new Color(51, 153, 204);
	
	//private final ControlPanel myCP;
	
	public StartRoomPanel(ControlPanel theCP) {
		super();
		//myCP = theCP;
		setBackground(MAZE_BG);
		setPreferredSize(new Dimension(350, 350));

		
		JTextPane txtpnSelectADirection = new JTextPane();
		txtpnSelectADirection.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 11));
		txtpnSelectADirection.setText("Select a direction to start!");
		add(txtpnSelectADirection, BorderLayout.NORTH);
		
		ControlPanel controlPanel = theCP;
		add(controlPanel, BorderLayout.CENTER);
		
		final Border blueLine = BorderFactory.createLineBorder(BORDER_COLOR, 5);
		this.setBorder(blueLine);

		
	
		
		



		
	}


}
