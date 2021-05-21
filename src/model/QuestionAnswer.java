package model;


/**
 * Randomly creates multiple choices for a Pokemon quiz. Provides the answers
 * and the questions.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class QuestionAnswer {

	private final int NUM_CHOICES = 4;

	/*
	 * Question/answer
	 */
	private Pokemon myPokemon;

	/*
	 * Multiple choice answers
	 */
	private String[] myChoices;

	/*
	 * index of the answer in choices
	 */
	private int myAnswerIndex;

	/*
	 * List of pokemon in a map for #ID lookup
	 */
	private Pokedex myPokedex;

	/*
	 * Upperbound of random generator
	 */
	private int myUpper;
	

	/**
	 * Constructor
	 * 
	 * @param thePokedex data used to fill the questions with
	 */
	public QuestionAnswer(Pokedex thePokedex) {
		myPokedex = thePokedex;
		myChoices = new String[NUM_CHOICES];
		myUpper = myPokedex.getCount();
		myPokemon = generatePokemon();

		// random location 0-3
		myAnswerIndex = (int) (Math.random() * (NUM_CHOICES));
		// System.out.println("my answer num is " + myAnswerIndex);

		// randomly fill out myChoices
		fillChoices();
	}

	/**
	 * Fill out myChoices by randomly generating id numbers based on pokedex
	 * count. Uses random generated ID to lookup pokemon from pokedex to put in
	 * question choices.
	 */
	private void fillChoices() {

		for (int i = 0; i < NUM_CHOICES; i++) {
			// randomly generate a pokemon with ID 1-151
			if (i == myAnswerIndex) { // answer
				myChoices[i] = myPokemon.getName();
			} else { // other choices
				myChoices[i] = generatePokemon().getName();
			}

		}
	}

	/**
	 * Randomly generate a pokemon with ID 1-myUpper and get it from the
	 * pokedex.
	 * 
	 * @return Pokemon randomly generated pokemon
	 */
	private Pokemon generatePokemon() {
		int num = (int) (Math.random() * (myUpper - 1) + 1);
		// System.out.println("Random num is " + num);
		Pokemon pkmn = myPokedex.findPokemon(num);
		return pkmn;
	}

	/**
	 * get the name of the Pokemon answer
	 * 
	 * @return String the pokemon answer name
	 */
	public String getAnswer() {
		return myPokemon.getName();

	}

	/**
	 * Get the Pokemon used for the question/answer
	 * 
	 * @return Pokemon the pokemon in the R
	 */
	public Pokemon getPokemon() {
		return myPokemon;

	}

	/**
	 * Get a deep copy of the choices array
	 * 
	 * @return String[]
	 */
	public String[] getChoices() {

		return myChoices.clone();
	}
	
	public int getAnswerIndex() {
		return myAnswerIndex;
	}

	/**
	 * Formats to a string the choices for debugging
	 * 
	 * @return String the choices
	 */
	public String getChoicesStr() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < NUM_CHOICES; i++) {
			sb.append((i + 1) + ") " + myChoices[i] + "\n");
		}

		return sb.toString();
	}
	
	
	

}
