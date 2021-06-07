package view.Win_Lose;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Maze;
import view.PokemonPanel;

/**
 * Used to tell player they won
 * 
 * @author Kenneth Ahrens
 *
 */
public class WinPane extends AbstractWinLosePane {

	/*
	 * Constants that represent winning messages
	 */
	private final String FIN = "You reached the end!";
	private final String CLEAR_ALL = "Wow, you really are a Pokemon master! Congratulations!";
	private final String TITLE = "You won!";
	private final String CLEAR_TITLE = "You cleared the whole maze! You're a living Pokedex!";

	/*
	 * Icons that represent winning
	 */
	private final int DEFAULT_SIZE = 150;
	private final ImageIcon myWin;
	private final String myWinPath = "./src/images/win_lose/win_trophy.png";
	private final ImageIcon myWinAll;
	private final String myWinAllPath = "./src/images/win_lose/pikachucheer.gif";

	/**
	 * 
	 * @param thePanel
	 */
	public WinPane(final PokemonPanel thePanel) {
		super(thePanel);
		myWin = new ImageIcon(myWinPath);

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
			
				PanePanelMaker winAllPanel = new PanePanelMaker(myWinAll, CLEAR_ALL);
				JOptionPane.showMessageDialog(null, winAllPanel, CLEAR_TITLE,
						JOptionPane.PLAIN_MESSAGE, null);

			} else {
				PanePanelMaker winPanel = new PanePanelMaker(myWin, FIN);
				JOptionPane.showMessageDialog(null, winPanel, TITLE,
						JOptionPane.PLAIN_MESSAGE, null);
			}

			super.promptPlayAgain();
		} else {
			JOptionPane.showMessageDialog(null, null,
					"Game has not been beaten yet...", 0, null);
		}

	}

}
