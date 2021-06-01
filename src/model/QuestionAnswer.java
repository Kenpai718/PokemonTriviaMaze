package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Randomly creates multiple choices for a Pokemon quiz. Provides the answers
 * and the questions.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class QuestionAnswer implements Serializable {

	/**
	     * 
	     */
	private static final long serialVersionUID = 6521846190481286645L;

	private static final int NUM_CHOICES = 4;

	/*
	 * Static list shared to prevent duplicate Pokemon in rooms
	 */
	private static List<Pokemon> USED = new ArrayList<Pokemon>();

	/*
	 * Question/answer
	 */
	private Pokemon myPokemon;

	/*
	 * Multiple choice answers
	 */
	private final List<String> myChoices;

	/*
	 * index of the answer in choices
	 */
	private int myAnswerIndex;

	/*
	 * List of pokemon in a map for #ID lookup
	 */
	private transient final Pokedex myPokedex;

	/*
	 * Upperbound of random generator
	 */
	private transient int myUpper;
	
	/*
	 * Random generator for pokemon id lookup and creation
	 */
	private Random myRand;

	/**
	 * Constructor
	 * 
	 */
	public QuestionAnswer() {
		// TODO Auto-generated constructor stub
		myPokedex = Pokedex.getInstance();
		myRand = new Random();
		myChoices = new ArrayList<String>();
		myUpper = myPokedex.getCount();
		myPokemon = generatePokemon();

		createMC();

	}

	/**
	 * Manually add a pokemon for a question
	 * 
	 * @param Pokemon that represents the question
	 */
	public QuestionAnswer(final Pokemon thePokemon) {
		// TODO Auto-generated constructor stub
		myPokedex = Pokedex.getInstance();
		myRand = new Random();
		myChoices = new ArrayList<String>();
		myUpper = myPokedex.getCount();
		myPokemon = thePokemon;
		createMC();

	}

	/*
	 * Create multiple choice answers
	 */
	public void createMC() {
		fillChoices();
		myAnswerIndex = myChoices.indexOf(this.getAnswer());
	}

	/**
	 * Fill out myChoices by randomly generating id numbers based on pokedex count.
	 * Uses random generated ID to lookup pokemon from pokedex to put in question
	 * choices.
	 */
	private void fillChoices() {
	        myChoices.clear();
		myChoices.add(myPokemon.getName());
		for (int i = 1; i < NUM_CHOICES; i++) {
			// randomly generate a pokemon with ID 1-151
			String aName = makeName();
			myChoices.add(aName);
		}
		Collections.shuffle(myChoices);
	}

	/**
	 * Adds names to the choices list. checks for duplicates
	 */
	private String makeName() {
		// TODO Auto-generated method stub
		String name = generatePokemonHelper().getName();
		// check if the name was used
		if (myChoices.contains(name)) {
			name = makeName();
		}
		
		return name;
	}

	/**
	 * Method helper that returns a random pokemon from the pokedex
	 * 
	 * @return returns a random pokemon
	 */
	private Pokemon generatePokemonHelper() {
		myUpper = myPokedex.getCount();
		final int num = myRand.nextInt(myUpper) + 1;
		return myPokedex.findPokemon(num);
	}

	/**
	 * Randomly generate a pokemon with ID 1-myUpper and get it from the pokedex.
	 * Clears USED list if the stack gets too big and throws an exception
	 * 
	 * @return Pokemon randomly generated pokemon
	 */
	private Pokemon generatePokemon() {
//	        final Maze maze = Maze.getInstance();
		Pokemon pkmn = generatePokemonHelper();

		// TODO:
		// clear the pokemon list if it gets full so it does not throw
		// stack overflow error

		try {
			if (!USED.contains(pkmn)) {
				USED.add(pkmn);
			} else {
				pkmn = generatePokemon();
			}
		} catch (final StackOverflowError e) {
			//System.out.println("USED list is full. It will now be cleared.\n");
			//System.out.println("OLD USED: " + USED);
			USED.clear();
			pkmn = generatePokemon();
			//System.out.println("NEW USED: " + USED);

		}
		
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
	 * Set new pokemon for the question/answer
	 */
	public void setNewPokemon(final Pokemon thePkmn) {
		myPokemon = thePkmn;
		createMC();
	}

	/**
	 * Get a deep copy of the choices array
	 * 
	 * @return String[]
	 */
	public ArrayList<String> getChoices() {

		return (ArrayList<String>) myChoices;
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
		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < NUM_CHOICES; i++) {
			sb.append((i + 1) + ") " + myChoices.get(i) + "\n");
		}

		return sb.toString();
	}

}
