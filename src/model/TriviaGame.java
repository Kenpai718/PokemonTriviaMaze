package model;

/*
 * Used to intialize a trivia game
 *
 */
public class TriviaGame {
	
	private Pokedex myPokedex;
	private Maze myMaze;


	public TriviaGame() {
		// TODO Auto-generated constructor stub
		myPokedex = new Pokedex();
		myMaze = new Maze(myPokedex);
	}
	
	public Pokedex getPokedex() {
		return myPokedex;
	}
	
	public Maze getMaze() {
		return myMaze;
	}

}
