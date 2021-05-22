package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import model.Maze;
import view.viewHelper.MazeGUI.MazeModel;

/**
 * Menubar for the trivia game Has file, help menus.
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class PokemonMenuBar extends JMenuBar {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2139646030569655705L;
	private JMenu myHelpMenu;
	private JMenu myFileMenu;
	private final Maze myMaze;
	private final JFrame myFrame;
	public final PokemonPanel myPanel;

	public PokemonMenuBar(final PokemonGUI theFrame) {
		// TODO Auto-generated constructor stub
		super();
		myFrame = theFrame.getFrame();
		myPanel = theFrame.getPanel();
		myMaze = Maze.getInstance();
		setupMenuBar();
	}

	private void setupMenuBar() {

		myFileMenu = new JMenu("File");
		setupFileMenu();
		this.add(myFileMenu);

		myHelpMenu = new JMenu("Help");
		setupHelpMenu();
		this.add(myHelpMenu);
	}

	/**
	 * 
	 */
	private void setupFileMenu() {
		// TODO Auto-generated method stub
		final JMenuItem save = new JMenuItem("Save");
		myFileMenu.add(save);

		final JMenuItem load = new JMenuItem("Load");
		myFileMenu.add(load);

		final JSeparator separator = new JSeparator();
		myFileMenu.add(separator);

		final JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(theEvent -> System.exit(0));
		myFileMenu.add(exit);
	}

	/**
	 * 
	 */
	private void setupHelpMenu() {
		// TODO Auto-generated method stub
		final JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(myFrame,
						"Created by: AJ Downey, Kenneth Ahrens, "
								+ "Katlyn Malone\n Spring 2021",
						"About", JOptionPane.PLAIN_MESSAGE);
			}

		});
		myHelpMenu.add(about);

		final JMenuItem tutorial = new JMenuItem("Tutorial");
		tutorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				JOptionPane.showMessageDialog(myFrame, "This is a Tutorial!",
						"About", JOptionPane.PLAIN_MESSAGE);
			}
		});
		myHelpMenu.add(tutorial);

		final JMenu cheats = new JMenu("Cheats");
		myHelpMenu.add(cheats);

		final JCheckBoxMenuItem cheat1 = new JCheckBoxMenuItem(
				"Reveal Pokemon");
		cheat1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final boolean selected = cheat1.isSelected();
				myPanel.setImage();
				if (selected) {
					myPanel.setImgBrightness(1);
				} else {
					myPanel.setImgBrightness(0);
				}

			}
		});
		cheats.add(cheat1); 

		final JCheckBoxMenuItem cheat2 = new JCheckBoxMenuItem(
				"Unlock All Doors");
		cheats.add(cheat2);

		final JMenuItem teleport = new JMenuItem("Teleport");
		final MazeModel model = (MazeModel) myPanel.getTable().getModel();
		teleport.addActionListener(new TeleportListener(model));
		cheats.add(teleport);

	}

	public class TeleportListener implements ActionListener {

		private final MazeModel myModel;

		public TeleportListener(final MazeModel theModel) {
			myModel = theModel;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			// TODO Auto-generated method stub
			final String message = "Please Enter a new position to teleport"
					+ " to.\n(X Y):";
			final int[] pos = readInput(message);
//                        
			myMaze.setPlayerLocation(pos);
			myModel.refresh(myMaze.getMatrix());
		}

		/**
		 * Helper method to read the input from the Input Dialog
		 * 
		 * @param theInput a string of the input
		 * @return an int[] of the two numbers input
		 */
		private int[] readInput(final String theMessage) {
			final StringBuilder input = new StringBuilder();
			input.append(JOptionPane.showInputDialog(theMessage));
			int[] res = myMaze.getPlayerLocation().clone();
			final Scanner scan;
			if ((input != null && !input.isEmpty()) && !(input.length() < 3)) {
				scan = new Scanner(input.toString());
				try {
					for (int i = 0; i < 2; i++) {
						final int num = scan.nextInt() - 1;
						if (num < myMaze.getRows() && num < myMaze.getCols()) {
							res[i] = num;
						} else {
							res = readInput("One or more numbers out "
									+ "of range of maze\n(X Y):");
							break;
						}
					}
				} catch (final InputMismatchException e) {
					res = readInput("Please use integers only.\n(X Y):");
				}
				scan.close();
			} else {
				res = readInput(theMessage);
			}

			return res;

		}
	}

}
