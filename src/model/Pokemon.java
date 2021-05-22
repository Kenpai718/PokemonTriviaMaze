package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Stores information about a Pokemon such as the ID number, name and picture
 * png representation.
 * 
 * TODO: Currently only has implementation for gen 1. Would like to add more in
 * the future.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class Pokemon {

	/*
	 * Replacement picture path in the case the pokemon's picture cannot be
	 * found
	 */
	private final String MISSING = "./src/images/other/NotFound.png";

	/*
	 * Path for all pokemon pictures
	 */
	private final String PATH = "./src/images/gen1/";

	/*
	 * Name of pokemon
	 */
	private String myName;
	/*
	 * id of pokemon user in filepath. ie: "001"
	 */
	private String myID;
	/*
	 * #id of pokemon. ie: "001" = 1
	 */
	private int myIDNum;
	/*
	 * file name of the pokemon
	 */
	private String myFileName;
	/*
	 * PNG of the pokemon
	 */
	private BufferedImage myPNG;

	/**
	 * Create a pokemon with given info
	 */
	public Pokemon(final String theID, final String theName) {
		myID = theID;
		myIDNum = Integer.parseInt(myID);
		myName = theName.replaceAll("_", "");
		myFileName = PATH + theID + theName + ".png";
		myPNG = readImage(myFileName);

	}

	/**
	 * Finds the pokemon picture based on filepath If not found return missing
	 * picture representation
	 * 
	 * @param String theLocation file path
	 * @return BufferedImage the img
	 */
	private BufferedImage readImage(final String theLocation) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(theLocation));
		} catch (final IOException e) { // not found put missingno as the
										// picture
			try {
				img = ImageIO.read(new File(MISSING));
				System.out.println(
						myID + " " + myName + " is missing a picture!");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return img;
	}

	/**
	 * Get name
	 * 
	 * @return String pokemon name
	 */
	public String getName() {
		return myName;
	}

	/**
	 * Get identifier string of ID ie: "001" = bulbasaur
	 * 
	 * @return String id
	 */
	public String getID() {
		return myID;
	}

	/**
	 * Get identifier string of ID ie: 1 = bulbasaur
	 * 
	 * @return int id
	 */
	public int getNum() {
		return myIDNum;
	}

	/**
	 * Get identifier string of ID ie: 1 = bulbasaur
	 * 
	 * @return int id
	 */
	public BufferedImage getPNG() {
		return myPNG;

	}

	@Override
	public String toString() {
		return myID + myName;
	}

}
