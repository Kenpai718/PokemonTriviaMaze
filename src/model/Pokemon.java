package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * Stores information about a Pokemon such as the ID number, name and picture
 * png representation.
 * 
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class Pokemon implements Serializable {

	/**
         * 
         */
        private static final long serialVersionUID = -647364516676291280L;

    /*
	 * Replacement picture path in the case the pokemon's picture cannot be
	 * found
	 */
	private static final String MISSING = "./src/images/other/NotFound.png";

	/*
	 * Path for all pokemon pictures
	 */
	private static final String PATH = "./src/images/Pokedex/";

	/*
	 * Name of pokemon
	 */
	private final String myName;
	/*
	 * id of pokemon user in filepath. ie: "001"
	 */
	private final String myID;
	/*
	 * #id of pokemon. ie: "001" = 1
	 */
	private final int myIDNum;
	/*
	 * file name of the pokemon
	 */
	private final String myFileName;
	/*
	 * PNG of the pokemon
	 */
	private transient BufferedImage myPNG;

	/**
	 * Create a pokemon with given info
	 */
	public Pokemon(final String theID, final String theName) {
		myID = theID.trim();
		myIDNum = Integer.parseInt(myID);
		myName = theName.replaceAll("_", "").trim(); //i.e: Mr._Mime -> Mr.Mime
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
			} catch (final IOException e1) {
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
		if (myPNG == null) {
		        myPNG = readImage(myFileName);
		}
	        return myPNG;

	}
	
	/** 
	 * Used to compare Pokemon objects
	 * 
	 * @return int -1 = not the same, 1 = same
	 */
	public int compareTo(final Object theObj) {
		int res = -1;
		if(theObj instanceof Pokemon) {
			if(((Pokemon) theObj).getNum() == this.myIDNum) {
				if(((Pokemon) theObj).getName() == this.myName) {
					res = 1;
				}
			}
		}
		return res;
	}

	@Override
	public String toString() {
		return myID + myName;
	}

}
