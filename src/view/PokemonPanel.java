package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

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
	private final ImageIcon pika = new ImageIcon("./images/pokemon/pikachu.png");
	
	final BufferedImage impika = readImage("./images/pokemon/pikachu.png");
	
	final BufferedImage imshine = readImage("./images/sparkle_formatted.png");
	
	/*sparkle effect behind a pokemon*/
	private final ImageIcon shine = new ImageIcon("./images/sparkle_formatted.png");
	
	private final int shineW, shineH;
	private final int pokeW;

    private final int pokeH;
	
	
	
	
	/*
	 * Constructor
	 */
	public PokemonPanel() {
		// TODO Auto-generated constructor stub
		super();
		
		shineW = imshine.getWidth();
		shineH = imshine.getHeight();
		pokeW = impika.getWidth();
		pokeH = impika.getHeight();
		
		//note: the pictures below are just a test rn I will delete later
//		final JLabel pikachu = new JLabel("");
//		pikachu.setIcon(pika);
//		add(pikachu);
//		
//		final JLabel sparkle = new JLabel("");
//		sparkle.setIcon(shine);
//		add(sparkle);
		
		setupPanel();
		
	}
	
	private BufferedImage readImage(final String theLocation) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(theLocation));
            } catch (final IOException e) {
                
            }
            
        return img;
    }

	/**
	 * Setup the panel for the game
	 */
	private void setupPanel() {
		setBackground(BG_COLOR);
		setPreferredSize(PANEL_SIZE);
		
	}
	
	/**
	 *
	 */
	@Override
        protected void paintComponent(final Graphics theG) {    
	    super.paintComponent(theG);
	    theG.drawImage(imshine, 0, -50, shineW, shineH, this);
            theG.drawImage(impika, 0, -50, pokeW, pokeH, this);
	    
	    
	    
	}

}
