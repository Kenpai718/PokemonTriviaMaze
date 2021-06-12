package exceptions;

/**
 * Custom Exception that handles any Missing Pokemon Pictures.
 * 
 * @author Kenneth Ahrens
 */
public class MissingPokemonPictureException extends Exception {
	
        /**
         * The Name of the Pokemon is passed for the message, theName + " cannot be found!"
         * 
         * @param theName
         */
	public MissingPokemonPictureException(final String theName) {
		super(theName + " is missing a picture!");
	}

}
