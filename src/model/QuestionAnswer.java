package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Randomly creates multiple choices for a Pokemon quiz. Provides the answers
 * and the questions.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public class QuestionAnswer {

	private final int NUM_CHOICES = 4;
	
	private static List<Pokemon> USED = new ArrayList<Pokemon>();

	/*
	 * Question/answer
	 */
	private Pokemon myPokemon;

	/*
	 * Multiple choice answers
	 */
	private List<String> myChoices;

	/*
	 * index of the answer in choices
	 */
	private int myAnswerIndex;

	/*
	 * List of pokemon in a map for #ID lookup
	 */
	private final Pokedex myPokedex;

	/*
	 * Upperbound of random generator
	 */
	private final int myUpper;
	

	/**
	 * Constructor
	 * 
	 */
	public QuestionAnswer() {
		// TODO Auto-generated constructor stub
		myPokedex = Pokedex.getInstance();
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
	public QuestionAnswer(Pokemon thePokemon) {
		// TODO Auto-generated constructor stub
		myPokedex = Pokedex.getInstance();
		myChoices = new ArrayList<String>();
		myUpper = myPokedex.getCount();
		myPokemon = thePokemon;
		
		
		fillChoices();
		// get index of shuffled array list
		myAnswerIndex = myChoices.indexOf(this.getAnswer());
		// System.out.println("my answer num is " + myAnswerIndex);

		// randomly fill out myChoices
		
	}
	
	/*
	 * Create multiple choice answers
	 */
	public void createMC() {
		myChoices = new ArrayList<String>();
		fillChoices();
		myAnswerIndex = myChoices.indexOf(this.getAnswer());
	}

	/**
	 * Fill out myChoices by randomly generating id numbers based on pokedex
	 * count. Uses random generated ID to lookup pokemon from pokedex to put in
	 * question choices.
	 */
	private void fillChoices() {
	        
	        myChoices.add(myPokemon.getName());
		for (int i = 1; i < NUM_CHOICES; i++) {
			// randomly generate a pokemon with ID 1-151
		        addName();		        
		}
		Collections.shuffle(myChoices);
	}

	/**
	 * Adds names to the choices list. checks for duplicates
	 */
	private void addName() {
                // TODO Auto-generated method stub
                final String name = generatePokemonHelper().getName();
                // check if the name was used
	        if (!myChoices.contains(name)) {
	                myChoices.add(name);
	        } else {
	                // get a new pokemon
	                addName();
	        }
        }

        /**
         * Method helper that returns a random pokemon from the pokedex
         * 
         * @return returns a random pokemon
         */
        private Pokemon generatePokemonHelper() {
                final int num = (int) (Math.random() * (myUpper - 1) + 1);
                return myPokedex.findPokemon(num);
        }

        /**
	 * Randomly generate a pokemon with ID 1-myUpper and get it from the
	 * pokedex.
	 * 
	 * @return Pokemon randomly generated pokemon
	 */
	private Pokemon generatePokemon() {
//	        final Maze maze = Maze.getInstance();
		Pokemon pkmn = generatePokemonHelper();
		
		if (!USED.contains(pkmn)) {
		        USED.add(pkmn);
		} else {
		        pkmn = generatePokemon();
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
