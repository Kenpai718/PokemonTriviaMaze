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
	private final Pokemon myPokemon;

	/*
	 * Multiple choice answers
	 */
	private final List<String> myChoices;

	/*
	 * index of the answer in choices
	 */
	private final int myAnswerIndex;

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
	 * @param thePokedex data used to fill the questions with
	 */
	public QuestionAnswer() {
		// TODO Auto-generated constructor stub
		myPokedex = Pokedex.getInstance();
		myChoices = new ArrayList<String>();
		myUpper = myPokedex.getCount();
		myPokemon = generatePokemon();
		
		
		fillChoices();
		// get index of shuffled array list
		myAnswerIndex = myChoices.indexOf(this.getAnswer());
		// System.out.println("my answer num is " + myAnswerIndex);

		// randomly fill out myChoices
		
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
		        
//			if (i == myAnswerIndex) { // answer
//				myChoices[i] = myPokemon.getName();
//			} else { // other choices
//				myChoices[i] = generatePokemon().getName();
//			}
		}
		Collections.shuffle(myChoices);
	}

	private void addName() {
                // TODO Auto-generated method stub
                final String name = generatePokemonName();
	        if (!myChoices.contains(name)) {
	                myChoices.add(name);
	        } else {
	                addName();
	        }
        }

        private String generatePokemonName() {
                final int num = (int) (Math.random() * (myUpper - 1) + 1);
                return myPokedex.findPokemon(num).getName();
        }

        /**
	 * Randomly generate a pokemon with ID 1-myUpper and get it from the
	 * pokedex.
	 * 
	 * @return Pokemon randomly generated pokemon
	 */
	private Pokemon generatePokemon() {
//	        final Maze maze = Maze.getInstance();
		final int num = (int) (Math.random() * (myUpper - 1) + 1);
		// System.out.println("Random num is " + num);
		Pokemon pkmn = myPokedex.findPokemon(num);
		
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
	 * Get a deep copy of the choices array
	 * 
	 * @return String[]
	 */
	public ArrayList<String> getChoices() {

		return (ArrayList<String>) myChoices;
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
