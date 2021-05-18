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
import model.Room;
import view.viewHelper.QuestionRoomGUI;

public class TestingClass{
	
	static Pokedex myPokedex;
	static Maze myMaze;
	static QuestionAnswer myQA;
	

	public static void main(String[] args) {
		myPokedex = new Pokedex();
		myQA = new QuestionAnswer(myPokedex);
		myMaze = new Maze(myPokedex);
		Room r = myMaze.getCurrRoom();
		String[] choices = r.getChoices();
		System.out.println(choices[1]);
		
		 
		
		
	}
	
	public static void printAll() {
		for(int i = 0; i < myPokedex.getCount(); i ++) {
			try {
				Pokemon p = myPokedex.findPokemon(i);
				System.out.println(p.getName());
			} catch (Exception e) {
				System.out.println(i + " not found");
			}
		}
	}
	
	

}
