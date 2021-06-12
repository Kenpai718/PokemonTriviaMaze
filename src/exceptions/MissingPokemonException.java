package exceptions;

/**
 * Custom Exception that handles any Missing Pokemon.
 * 
 * @author Kenneth Ahrens
 */
public class MissingPokemonException extends Exception {
	
	/**
	 * Calls the Super class with the String "Pokemon cannot be found!".
	 */
	public MissingPokemonException() {
		super("Pokemon cannot be found!");
	}

	
	/**
	 * The Name of the Pokemon is passed for the message, theName + " cannot be found!"
	 * 
	 * @param theName
	 */
	public MissingPokemonException(final String theName) {
		super(theName + " cannot be found!");
	}

}
