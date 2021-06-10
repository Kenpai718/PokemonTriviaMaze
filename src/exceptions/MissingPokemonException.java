package exceptions;

public class MissingPokemonException extends Exception {
	
	public MissingPokemonException() {
		super("Pokemon cannot be found!");
	}

	
	public MissingPokemonException(String theName) {
		super(theName + " cannot be found!");
	}

}
