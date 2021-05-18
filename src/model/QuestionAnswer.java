package model;

import java.util.Random;

public class QuestionAnswer {
	
	private final int NUM_CHOICES = 4;
	
	/*
	 * Question
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
	
	/*
	 * Upperbound of random generator
	 */
	private int myUpper;
	

	public QuestionAnswer(Pokedex thePokedex) {
		// TODO Auto-generated constructor stub
		myPokedex = thePokedex;
		myChoices = new String[4];
		myUpper = myPokedex.getCount();
		myPokemon = generatePokemon();
		
		fillChoices();
	}
	
	private void fillChoices() {
		//TODO: test. Currently places correct answer in first place
		myChoices[0] = myPokemon.getName();

		
		for(int i = 1; i < NUM_CHOICES; i++) {
			//randomly generate a pokemon with ID 1-151
			myChoices[i] = generatePokemon().getName();
		}
	}
	
	/*
	 * randomly generate a pokemon with ID 1-151
	 */
	private Pokemon generatePokemon() {
		int num = (int) (Math.random() * myUpper) + 1;
		Pokemon pkmn = myPokedex.findPokemon(num);
		return pkmn;
	}
	
	public String getAnswer() {
		return myPokemon.getName();
		
	}
	
	public String[] getChoices() {
		return myChoices.clone();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < NUM_CHOICES; i++) {
			sb.append(i + ") " + myChoices[i] + "\n");
		}
		
		return sb.toString();
		
	}

}
