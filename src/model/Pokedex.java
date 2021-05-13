package model;

import java.awt.image.BufferedImage;

public interface Pokedex {

	/*Get the name of the pokemon*/
	String getName();
	
	/*Get the id number of the pokemon. Example: Bulbsaur = 1*/
	int getIDNum();
	
	/* Get the png image of the Pokemon*/
	BufferedImage getModel();

}
