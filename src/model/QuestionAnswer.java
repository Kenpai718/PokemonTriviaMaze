package model;

import java.util.Random;

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
	 * List of pokemon in a map for #ID lookup
	 */
	private Pokedex myPokedex;
	
	private int myAnswerLocation;
	

	/*
	 * Upperbound of random generator
	 */
	private int myUpper;

	public QuestionAnswer(Pokedex thePokedex) {
		// TODO Auto-generated constructor stub
		myPokedex = thePokedex;
		myChoices = new String[NUM_CHOICES];
		myUpper = myPokedex.getCount();
		myPokemon = generatePokemon();
		
		//random location 0-3

		myAnswerLocation = (int) (Math.random() * (NUM_CHOICES));
		//System.out.println("my answer num is " + myAnswerLocation);

		fillChoices();
	}

	private void fillChoices() {

		for (int i = 0; i < NUM_CHOICES; i++) {
			// randomly generate a pokemon with ID 1-151
			if(i == myAnswerLocation) {
				myChoices[i] = myPokemon.getName();
			} else {
				myChoices[i] = generatePokemon().getName();
			}

		}
	}

	/*
	 * randomly generate a pokemon with ID 1-151
	 */
	private Pokemon generatePokemon() {
		int num = (int) (Math.random() * (myUpper - 1) + 1);
		//System.out.println("Random num is " + num);
		Pokemon pkmn = myPokedex.findPokemon(num);
		return pkmn;
	}

	public String getAnswer() {
		return myPokemon.getName();

	}

	public Pokemon getPokemon() {
		return myPokemon;

	}

	public String[] getChoices() {
		/*
		 * String[] choicesCopy = new String[NUM_CHOICES - 1]; for(int i = 0; i
		 * < NUM_CHOICES; i++) { choicesCopy[i] = myChoices[i]; } return
		 * choicesCopy;
		 */
		
		return myChoices;
	}
	
	public String getChoicesStr() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < NUM_CHOICES; i++) {
			sb.append(i + ") " + myChoices[i] + "\n");
		}

		return sb.toString();
	}


}
