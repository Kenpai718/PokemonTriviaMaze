package test;
/*
 * TODO: Delete this class for submission
 * 
 * Only used to test parts of the program
 */

import model.Maze;
import model.Pokedex;
import model.Pokemon;
import model.QuestionAnswer;

public class TestingClass{
	
	static Pokedex myPokedex;
	static Maze myMaze;
	static QuestionAnswer myQA;
	

	public static void main(final String[] args) {
		myPokedex = Pokedex.getInstance();
		//myQA = new QuestionAnswer();
		myMaze = Maze.getInstance();
		/*
		 * Room r = myMaze.getCurrRoom(); String[] choices = r.getChoices();
		 * System.out.println(choices[1]);
		 */
		for (int i = 0; i < 300; i++) {
		        System.out.println("Resetting maze: "+ i);
		        myMaze.reset();
		}
		//myPokedex.addAllGensToDex();
		//printAll();
		//System.out.println(myPokedex.getCount());
		//System.out.println(myPokedex.findPokemon("Farfetch'd"));
		System.out.println(myPokedex.findPokemon(25));
		System.out.println(myPokedex.getPokedex().size());
	
		
		
		 
		
		
	}
	
	public static void printAll() {
		for(int i = 0; i < myPokedex.getCount(); i ++) {
			try {
				final Pokemon p = myPokedex.findPokemon(i);
				System.out.println(p.getName());
			} catch (final Exception e) {
				System.out.println(i + " not found");
			}
		}
	}
	
	

}
