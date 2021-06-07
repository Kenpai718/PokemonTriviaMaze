package view.Win_Lose;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Maze;
import view.PokemonPanel;

/**
 * Used to tell player they won
 * @author Kenneth Ahrens
 *
 */
public class WinPane extends AbstractWinLosePane {

	/*
	 * Constants that represent winning messages
	 */
	private final String FIN = "You reached the end!\nGood job!";
	private final String CLEAR_ALL = "Wow, you cleared every room in the maze!"
			+ "\nYou really are a Pokemon master!";
	private final String TITLE = "You win!";
	
	/*
	 * Icons that represent winning
	 */
	private final int DEFAULT_SIZE = 150;
	private final ImageIcon myWin;
	private final String myWinPath = "./src/images/win_lose/win_trophy.png";
	private final ImageIcon myWinAll;
	private final String myWinAllPath = "./src/images/win_lose/pikadance.gif";

	/**
	 * 
	 * @param thePanel
	 */
	public WinPane(final PokemonPanel thePanel) {
		super(thePanel);
		ImageIcon win = new ImageIcon(myWinPath);
		myWin = new ImageIcon(win.getImage().getScaledInstance(DEFAULT_SIZE,
				DEFAULT_SIZE, Image.SCALE_DEFAULT));

		myWinAll = new ImageIcon(myWinAllPath);

	}

	/**
	 * Tell player if they have won
	 */
	public void showCondition() {
		boolean cleared = super.hasWon();
		boolean clearedAll = super.hasClearedAllRooms();

		if (cleared) {
			if (clearedAll) {
				JOptionPane.showMessageDialog(null, CLEAR_ALL, TITLE,
						JOptionPane.PLAIN_MESSAGE, myWinAll);

			} else {
				JOptionPane.showMessageDialog(null, FIN, TITLE,
						JOptionPane.PLAIN_MESSAGE, myWin);
			}

			super.promptPlayAgain();
		} else {
			JOptionPane.showMessageDialog(null, null,
					"Game has not been beaten yet...", 0, null);
		}

	}

}
