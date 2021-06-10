package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import exceptions.MissingPokemonPictureException;
import view.viewHelper.ImageUtility;

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
	 * Replacement picture path in the case the pokemon's picture cannot be found
	 */
	private static final String MISSING = "./src/images/other/NotFound.png";

	/*
	 * Path for all pokemon pictures
	 */
	private static final String PATH = "./src/images/pokedex/gen";
	/*
	 * Length of id
	 */
	private final int ID_LENGTH = 3;

	/*
	 * Name of pokemon
	 */
	private final String myName;
	/*
	 * id of pokemon user in filepath. ie: "001"
	 */
	private String myID;
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

	/*
	 * Game generation of the pokemon
	 */
	private final int myGenNum;

	/**
	 * Create a pokemon with given info
	 * 
	 * @param theID     official pokedex number
	 * @param theName   pokemon name
	 * @param theGenNum what game generation this pokemon is from
	 * @throws Exception
	 */
	public Pokemon(final String theID, final String theName, final int theGenNum) throws Exception {
		if (theID.length() != ID_LENGTH) {
			throw new IllegalArgumentException("ID must be length " + ID_LENGTH);
		}

		if (theGenNum < 0) {
			throw new IllegalArgumentException("Pokemon gen " + theGenNum + " does not exist in this game.");
		}

		myID = theID.trim();
		myIDNum = Integer.parseInt(myID);
		if (myIDNum == 0) {
			myID = "000";
		}
		
		myGenNum = theGenNum;
		myName = theName.replaceAll("_", "").trim(); // i.e: Mr._Mime -> Mr.Mime
		myFileName = PATH + myGenNum + "/" + theID + theName + ".png";

		try {
			myPNG = readImage(myFileName);
		} catch (MissingPokemonPictureException e) {
			System.out.println(myName + " is missing a picture! Missingno will be used.");
			myPNG = ImageUtility.readImage(MISSING);
		}

	}

	/**
	 * Finds the pokemon picture based on filepath If not found return missing
	 * picture representation
	 * 
	 * @param String theLocation file path
	 * @return BufferedImage the img
	 * @throws MissingPokemonPictureException
	 */
	private BufferedImage readImage(final String theLocation) throws MissingPokemonPictureException {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(theLocation));
		} catch (final IOException e) { 
			throw new MissingPokemonPictureException(theLocation);

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
			try {
				myPNG = readImage(myFileName);
			} catch (MissingPokemonPictureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		if (theObj instanceof Pokemon) {
			if (((Pokemon) theObj).getNum() == this.myIDNum) {
				if (((Pokemon) theObj).getName() == this.myName) {
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
