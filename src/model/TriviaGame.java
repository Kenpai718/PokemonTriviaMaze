package model;

/**
 * Used to initialize a trivia maze game
 * 
 * @author ajdowney
 * @author ken
 */
public class TriviaGame {

	/*
	 * Holds a map that stores info on all Pokemon
	 */
	private final Pokedex myPokedex;
	/*
	 * Maze that is played on
	 */
	private final Maze myMaze;

	/*
	 * Constructor
	 */
	public TriviaGame() {
		myPokedex = Pokedex.getInstance();
		myMaze = Maze.getInstance();
	}

	/**
	 * Pokedex getter
	 * 
	 * @return Pokedex data of pokemon
	 */
	public Pokedex getPokedex() {
		return myPokedex;
	}

	/**
	 * Maze getter
	 * 
	 * @return Maze
	 */
	public Maze getMaze() {
		return myMaze;
	}

}
