package view.viewHelper;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Pokedex;

/**
 * Uses Pokedex toString to show all pokemon currently in the pokedex with a
 * pokedexScrollerable text area panel
 * 
 * @author Kenneth Ahrens
 *
 */
public class PokedexScrollList extends JPanel {

	/**
	 * The Pokedex of the Game
	 */
	private final Pokedex myPokedex;
	
	/**
	 * The dimenstion of the Panel
	 */
	private final Dimension DIM = new Dimension(350, 600);

	/*
	 * Constructor
	 */
	public PokedexScrollList() {
		this.myPokedex = Pokedex.getInstance();

		setMaximumSize(DIM);
		setPreferredSize(DIM);
		setLayout(new BorderLayout(0, 0));

		// has all the text
		final JTextArea display = new JTextArea(300, 500);
		display.setText(myPokedex.toString()); // shows all pokemon and id line by line
		display.setCaretPosition(0); // start at top of the list
		display.setEditable(false); // set textArea non-editable

		// allows user to move through list
		final JScrollPane pokedexScroller = new JScrollPane(display, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(pokedexScroller, BorderLayout.CENTER);

	}

}
