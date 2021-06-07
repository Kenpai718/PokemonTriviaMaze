package view.Win_Lose;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Maze;
import view.PokemonPanel;

/**
 * Used to tell player they lost
 * @author Kenneth Ahrens
 *
 */
public class LosePane extends AbstractWinLosePane {
	
	/*
	 * Constants to represent losing messages
	 */
	private final String LOST_MSG = "Looks like you should have brought HM Cut with you!";
	private final String LOST_TITLE = "All paths are blocked... you lost!";
	
	/*
	 * Icons shown upon losing
	 */
	private final ImageIcon myLose;
	private final String myLosePath = "./src/images/win_lose/pikachulaugh.gif";
	//private final String myLosePath = "./src/images/win_lose/mocking.gif";
	

	/**
	 * 
	 * @param thePanel
	 */
	public LosePane(final PokemonPanel thePanel) {
		super(thePanel);
		myLose = new ImageIcon(myLosePath);

	}

	/**
	 * Option pane to show lose message
	 */
	public void showCondition() {
		boolean lost = super.hasLost();

		if (lost) {
			PanePanelMaker losePane = new PanePanelMaker(myLose, LOST_MSG);
			JOptionPane.showMessageDialog(null, losePane, LOST_TITLE, JOptionPane.PLAIN_MESSAGE, null);

			super.promptPlayAgain();
		} else {
			String msg = "Have not lost yet!";
			JOptionPane.showMessageDialog(null, null, msg, 0, null);
		}

	}

}
