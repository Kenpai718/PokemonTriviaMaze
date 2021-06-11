package test;
/*
 * TODO: Delete this class for submission
 * 
 * Only used to test parts of the program
 */

import model.Maze;
import model.MazePathFinder;
import model.Pokedex;
import model.Pokemon;
import model.QuestionAnswer;
import model.Room;

public class TestingClass {

	static Pokedex myPokedex;
	static Maze myMaze;
	static QuestionAnswer myQA;

	public static void main(final String[] args) throws Exception {
		myPokedex = Pokedex.getInstance();
		// myQA = new QuestionAnswer();
		myMaze = Maze.getInstance();
		/*
		 * Room r = myMaze.getCurrRoom(); String[] choices = r.getChoices();
		 * System.out.println(choices[1]);
		 */
		/*
		 * System.out.println(myPokedex); for (int i = 0; i < 300; i++) {
		 * System.out.println("Resetting maze: "+ i); myMaze.reset(); }
		 * //myPokedex.addAllGensToDex(); //printAll();
		 * //System.out.println(myPokedex.getCount());
		 * //System.out.println(myPokedex.findPokemon("Farfetch'd"));
		 * System.out.println(myPokedex.findPokemon(25));
		 * System.out.println(myPokedex.getPokedex().size());
		 */

		// quick and dirty path find test
		/*
		 * MazePathFinder pf = new MazePathFinder();
		 * 
		 * Room[][] maze = myMaze.getMatrix(); int rows = myMaze.getRows(); int cols =
		 * myMaze.getCols();
		 * 
		 * maze[rows - 1][cols - 1].setEntry(false); int[] start = {0, 0}; int[] target
		 * = {rows - 1, cols - 1}; pf.checkForPath(start, target);
		 */

		System.out.println(myPokedex);
		// printAll();

	}

	public static void printAll() {
		boolean noErrors = true;
		for (int i = 1; i < myPokedex.getCount(); i++) {
			try {
				final Pokemon p = myPokedex.findPokemon(i);
				System.out.println(p.getName());
			} catch (final Exception e) {
				System.out.println(i + " not found");
				noErrors = false;
			}
		}

		System.out.println(noErrors);
	}

}
