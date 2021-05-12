package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class PokemonPanel extends JPanel {
	
	/*
	 * Background color of the game (Crimson Red)
	 */
	final Color BG_COLOR = new Color(220,20,60);
	
	/*
	 * Aspect ratio of Jpanel
	 */
	final Dimension PANEL_SIZE = new Dimension(1920, 1080);
	
	/*
	 * test image for a pokemon
	 */
	private ImageIcon pika = new ImageIcon("./images/pokemon/pikachu.png");
	
	/*sparkle effect behind a pokemon*/
	private ImageIcon shine = new ImageIcon("./images/sparkle_formatted.png");
	
	
	
	
	/*
	 * Constructor
	 */
	public PokemonPanel() {
		// TODO Auto-generated constructor stub
		super();
		
		//note: the pictures below are just a test rn I will delete later
		JLabel pikachu = new JLabel("");
		pikachu.setIcon(pika);
		add(pikachu);
		
		JLabel sparkle = new JLabel("");
		sparkle.setIcon(shine);
		add(sparkle);
		
		setupPanel();
		
	}
	
	/*
	 * Setup the panel for the game
	 */
	private void setupPanel() {
		setBackground(BG_COLOR);
		setPreferredSize(PANEL_SIZE);
		
	}

}
