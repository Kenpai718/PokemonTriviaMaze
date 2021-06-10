package exceptions;

public class MissingPokemonPictureException extends Exception {
	
	public MissingPokemonPictureException(String theName) {
		super(theName + " is missing a picture!");
	}

}
