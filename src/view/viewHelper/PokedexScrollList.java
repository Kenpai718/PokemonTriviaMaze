package view.viewHelper;

import javax.swing.JPanel;

import model.Pokedex;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;

/**
 * Uses Pokedex toString to show all pokemon currently in the pokedex
 * with a pokedexScrollerable text area panel
 * @author Kenneth Ahrens
 *
 */
public class PokedexScrollList extends JPanel {

	private final Pokedex myPokedex;
	private final Dimension DIM = new Dimension(350, 600);

	/*
	 * Constructor
	 */
	public PokedexScrollList() {
		this.myPokedex = Pokedex.getInstance();
		
		setMaximumSize(DIM);
		setPreferredSize(DIM);
		setLayout(new BorderLayout(0, 0));
		
		//has all the text
	    JTextArea display = new JTextArea (300, 500);
		display.setText(myPokedex.toString()); //shows all pokemon and id line by line
		display.setCaretPosition(0); //start at top of the list
	    display.setEditable ( false ); // set textArea non-editable
	    
	    //allows user to move through list
	    JScrollPane pokedexScroller = new JScrollPane (display,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    this.add(pokedexScroller, BorderLayout.CENTER);
		
		


	}

}
