package view.Win_Lose;

import java.awt.Container;

import javax.swing.JOptionPane;

import model.Maze;
import model.Room;
import view.PokemonPanel;

/**
 * Template method to allow a win and lose class to access similar methods
 * like resetting the game.
 * 
 * Extends container to add property change listeners.
 * 
 * @author Kenneth Ahrens
 *
 */

public abstract class AbstractWinLosePane extends Container {
	

	/*
	 * game panel
	 */
	private final PokemonPanel myPanel;
	/*
	 * game maze
	 */
	private final Maze myMaze;

	/**
	 * 
	 * @param thePP
	 */
	public AbstractWinLosePane(final PokemonPanel thePP) {
		// TODO Auto-generated constructor stub
		myPanel = thePP;
		myMaze = Maze.getInstance();
		//addListeners();
	}
	
	/**
	 * Add listeners to important components this option pane should control
	 * 
	 */
	private void addListeners() {
		this.addPropertyChangeListener(myPanel);
		this.addPropertyChangeListener(myPanel.getMazeGUI());
		this.addPropertyChangeListener(myPanel.getMyControlPanel());
	}

	/**
	 * Ask player if they want to play the game again.
	 * Resets the game on yes and close the game on no.
	 */
	protected void promptPlayAgain() {
		String title = "Do you want to play again?";
		String msg = "\"Yes\" to reset | \"No\" to exit the game. ";
		int selected = JOptionPane.showOptionDialog(null, msg, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, null, null);

		if (selected == JOptionPane.YES_OPTION) {
			resetGame();
		} else {
			exitGame();
		}
	}

	/**
	 * Checks special win condition on if the player finished every room
	 * 
	 * @return boolean if player cleared all rooms
	 */
	protected boolean hasClearedAllRooms() {
		Room[][] rooms = myMaze.getMatrix();
		boolean result = true;
		for (int i = 0; i < myMaze.getRows(); i++) {
			for (int j = 0; j < myMaze.getCols(); j++) {
				// mark false if any room has been unvisited
				if (!rooms[i][j].hasVisited()) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Checks if player has won
	 * @return boolean if won in maze
	 */
	protected boolean hasWon() {
		return myMaze.hasWon();

	}

	/**
	 * 
	 * @return lose condition in maze
	 */
	protected boolean hasLost() {
		return myMaze.hasLost();
	}

	/**
	 * resets all state of the game and fires changes
	 * to update the gui.
	 */
	protected void resetGame() {
		myMaze.reset();
		//firePropertyChange("model", null, myMaze.getMatrix());
		myPanel.refreshGUI();
		JOptionPane.showMessageDialog(null, "The game has been reset!");
	}

	protected void exitGame() {
		JOptionPane.showMessageDialog(null,
				"Game will now close. Thanks for playing!");
		System.exit(0);
	}

	/**
	 * Show win or lose
	 */
	public abstract void showCondition();

}
