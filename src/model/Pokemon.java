package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Pokemon {
	
	/*
	 * Replacement picture in the case the pokemon name cannot be found
	 */
    private final String MISSING = "./src/images/other/NotFound.png";
    private final String PATH = "./src/images/gen1/";
	
	private String myName;
	private String myID;
	private int myIDNum;
	private String myFileName;
	private BufferedImage myPNG;
	

	/*
	 * Create a pokemon with given info
	 */
	public Pokemon(final String theID, final String theName) {
		// TODO Auto-generated constructor stub
		myID = theID;
		myIDNum = Integer.parseInt(myID);
		myName = theName;
		myFileName = PATH + theID + theName + ".png";
		myPNG = readImage(myFileName);
		
	}
	
    private BufferedImage readImage(final String theLocation) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(theLocation));
        } catch (final IOException e) { //not found put missingno as the picture
        	try {
				img = ImageIO.read(new File(MISSING));
				System.out.println(myID + " " + myName + " is missing a picture!");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }

        return img;
    }
	
	public String getName() {
		return myName;
	}
	
	public String getID() {
		return myID;
	}
	
	public int getNum() {
		return myIDNum;
	}
	
	
	public BufferedImage getPNG() {
		return myPNG;
		
	}
	
	public String toString() {
		return myName;
	}

}
