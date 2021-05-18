package test;
/*
 * TODO: Delete this class for submission
 * 
 * Only used to test parts of the program
 */

import model.Maze;
import model.Pokedex;
import model.QuestionAnswer;

public class TestingClass{
	
	static Pokedex myPokedex;
	static Maze myMaze;
	static QuestionAnswer myQA;
	

	public static void main(String[] args) {
		myPokedex = new Pokedex();
		myQA = new QuestionAnswer(myPokedex);
		System.out.println(myQA);
		System.out.println(myQA.getAnswer());
		
	}
	
	

}
