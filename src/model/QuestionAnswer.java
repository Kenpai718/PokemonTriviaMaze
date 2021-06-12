package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import controller.AnswerFormatter;
import exceptions.MissingPokemonException;

/**
 * Randomly creates multiple choices for a Pokemon quiz. Provides the answers
 * and the questions.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class QuestionAnswer implements Serializable {

        /**
         * The serialized ID for Serialization
         */
	private static final long serialVersionUID = 6521846190481286645L;

	/*
	 * Number of multiple choice options
	 */
	private static final int NUM_CHOICES = 4;

	/*
	 * Static list shared to prevent duplicate Pokemon in rooms
	 */
	private static List<String> USED = new ArrayList<String>();

        /*
	 * Question/answer pokemon
	 */
	private Pokemon myPokemon;

	/*
	 * Multiple choice answers
	 */
	private ArrayList<String> myChoices;

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
	private final Random myRand;

	/**
	 * Constructor
	 * 
	 */
	public QuestionAnswer() {
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
	        this();
		myPokemon = thePokemon;
		createMC();

	}
	
	/**
         * Manually add a pokemon, and the questions for a question.
         * Mostly used for unit Testing
         * 
         * @param Pokemon that represents the question
         */
        public QuestionAnswer(final Pokemon thePokemon, final ArrayList<String> theChoices) {
                this(thePokemon);
                myChoices = theChoices;
                myAnswerIndex = myChoices.indexOf(this.getAnswer());

        }
	

	/**
	 * Create multiple choice answers
	 */
	private void createMC() {
		fillChoices();
		myAnswerIndex = myChoices.indexOf(this.getAnswer());
	}

	/**
	 * Fill out myChoices by randomly generating id numbers based on pokedex
	 * count. Uses random generated ID to lookup pokemon from pokedex to put in
	 * question choices.
	 */
	private void fillChoices() {
		myChoices.clear();
		myChoices.add(AnswerFormatter.formatMultipleChoiceAnswer(myPokemon.getName()));
		for (int i = 1; i < NUM_CHOICES; i++) {
			// randomly generate a pokemon with ID 1-151
			final String aName = makeName();
			myChoices.add(aName);
		}

		Collections.shuffle(myChoices);
	}

	/**
	 * Adds names to the choices list. checks for duplicates
	 * 
	 * @String generated pokemon name
	 */
	private String makeName() {
		myUpper = myPokedex.getCount();
		final int num = myRand.nextInt(myUpper) + 1;
		final String name = myPokedex.findPokemonName(num);
		String formatName = AnswerFormatter.formatMultipleChoiceAnswer(name);
		// check if the name was used
		if (myChoices.contains(name) || myChoices.contains(formatName)) {
			formatName = makeName();
		}

		return formatName;
	}

	/**
	 * Method helper that returns a random pokemon from the pokedex
	 * 
	 * @return returns a random pokemon
	 */
	private Pokemon generatePokemonHelper() {
		myUpper = myPokedex.getCount();
		final int num = myRand.nextInt(myUpper) + 1;
		Pokemon poke;
		try {
			poke = myPokedex.findPokemon(num);
		} catch (final MissingPokemonException e) {

			poke = generatePokemonHelper();
		}
		return poke;
	}

	/**
	 * Randomly generate a pokemon with ID 1-myUpper and get it from the
	 * pokedex. Clears USED list if the stack gets too big and throws an
	 * exception
	 * 
	 * @return Pokemon randomly generated pokemon
	 */
	private Pokemon generatePokemon() {
		// final Maze maze = Maze.getInstance();
		Pokemon pkmn = generatePokemonHelper();

		final String name = pkmn.getName();
		if (USED.contains(name)) {
			pkmn = generatePokemon();
		}
		USED.add(name);

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

		return myChoices;
	}

	/**
	 * Returns the Index of the answer in the Choices list
	 * 
	 * @return index of the answer to this multiple choice question
	 */
	public int getAnswerIndex() {
		return myAnswerIndex;
	}

	/**
	 * clear the used list of pokemon.
	 * Main purpose is to avoid stack overflow.
	 */
	public void clearUsed() {
		USED.clear();
	}
	
	/**
	 * Returns the USED Array list for unit testing
	 * 
         * @return the uSED
         */
        public static List<String> getUSED() {
                return USED;
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
