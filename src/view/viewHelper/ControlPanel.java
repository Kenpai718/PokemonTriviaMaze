package view.viewHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controller.movement_actions.AbstractMovementAction;
import controller.movement_actions.DownAction;
import controller.movement_actions.LeftAction;
import controller.movement_actions.RightAction;
import controller.movement_actions.UpAction;
import model.Maze;
import view.PokemonPanel;

/**
 * Main property change controller to update the GUI components when something
 * happens. IE: player moved in maze update visual.
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class ControlPanel extends JPanel implements PropertyChangeListener {
        
        /**
         * The serialized ID for Serialization
         */
	private static final long serialVersionUID = 1L;

	/*
	 * Actions used to make player move
	 */
	private final DownAction downAction;
	private final LeftAction leftAction;
	private final RightAction rightAction;
	private final UpAction upAction;

	/*
	 * Buttons to allow player to click and move through maze
	 */
	final JButton up;
	final JButton down;
	final JButton left;
	final JButton right;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Maze used to move in
	 */
	private final Maze myMaze;

	/*
	 * The panel this control panel resides on
	 */
	private final PokemonPanel myPanel;

	/*
	 * Action selected after a click
	 */
	private AbstractMovementAction myAction;

	/**
	 * Create the control panel.
	 * 
	 * @param pokemonPanel
	 */
	public ControlPanel(final PokemonPanel thePanel) {
		upAction = new UpAction(thePanel);
		leftAction = new LeftAction(thePanel);
		rightAction = new RightAction(thePanel);
		downAction = new DownAction(thePanel);
		myMaze = Maze.getInstance();
		myPanel = thePanel;

		// set panel settings
		setOpaque(false);
		setPreferredSize(new Dimension(300, 300));
		setLayout(new GridLayout(0, 3, 0, 0));

		// build buttons for the control panel
		// NOTE: the empty jlabels are here to format the buttons with the grid
		// layout
		final JLabel label = new JLabel("");
		add(label);

		up = buildMoveButton(upAction);
		add(up);

		final JLabel label_1 = new JLabel("");
		add(label_1);

		left = buildMoveButton(leftAction);
		add(left);

		final JLabel player = buildPlayer();
		add(player);

		right = buildMoveButton(rightAction);
		add(right);

		final JLabel label_3 = new JLabel("");
		add(label_3);

		down = buildMoveButton(downAction);
		add(down);

		final JLabel label_4 = new JLabel("");
		add(label_4);

		addListeners();
	}

	/**
	 * 
	 * @return direction clicked on
	 */
	public AbstractMovementAction getDirection() {
		return myAction;
	}

	/**
	 * Builder to make a movement action button
	 * Also assigns the keybind.
	 * 
	 * @param theAction to give the button
	 * @return formatted jbutton with associated action
	 */
	private JButton buildMoveButton(final AbstractMovementAction theAction) {
		final JButton jb = new JButton("");
		jb.setHideActionText(true);
		buttonGroup.add(jb);
		jb.setAction(theAction);
		jb.setBorder(null);
		jb.setFocusable(true);
		
		//set keybind to movement action
		final String actionTitle = theAction.toString() + "_ACTION";
		jb.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(theAction.getMovementKey(), actionTitle);
		jb.getActionMap().put(actionTitle, theAction);
		
		return jb;

	}

	/**
	 * Builds a jlabel with the player model
	 * 
	 * @return player jlabel
	 */
	private JLabel buildPlayer() {
		final JLabel player = new JLabel("");
		player.setInheritsPopupMenu(false);
		player.setIconTextGap(0);
		final ImageIcon imageIcon = new ImageIcon(new ImageIcon(
				ControlPanel.class.getResource("/other/Player.png")).getImage()
						.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		player.setIcon(imageIcon);
		return player;
	}

	/**
	 * Add listeners for buttons on the control panel
	 */
	private void addListeners() {
		addPropertyChangeListener(this);
		final Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		while (buttons.hasMoreElements()) {
			final JButton temp = (JButton) buttons.nextElement();
			temp.addPropertyChangeListener(this);
			temp.addMouseListener(new UserMouseListener(temp));


		}
	}

	/**
	 * Change which buttons are visible based on position in maze.
	 * 
	 * Ensures player cannot click on a button and go out of bounds
	 * 
	 * @param i
	 * @param num
	 */
	private void changeButtonState(final int i, final int num) {
		// TODO Auto-generated method stub
		if (i == 0) {
			final Boolean dc = checkRooms("down");
			final Boolean uc = checkRooms("up");
			down.setVisible(dc);
			down.setEnabled(dc);
			up.setVisible(uc);
			up.setEnabled(uc);
		} else {
			final Boolean rc = checkRooms("right");
			final Boolean lc = checkRooms("left");
			right.setVisible(rc);
			right.setEnabled(rc);
			left.setVisible(lc);
			left.setEnabled(lc);
			// merge test comment
		}
	}

	/**
	 * Check room in the direction for if they can enter the room.
	 * 
	 * @param theDir
	 * @return if player can move in that direction
	 */
	private Boolean checkRooms(final String theDir) {
		Boolean res = null;
		final int[] currRoom = myMaze.getPlayerLocation();
		try {
			switch (theDir) {
			case "down":
				res = myMaze.getRoom(currRoom[0] + 1, currRoom[1]).canEnter();
				break;
			case "up":
				res = myMaze.getRoom(currRoom[0] - 1, currRoom[1]).canEnter();
				break;
			case "right":
				res = myMaze.getRoom(currRoom[0], currRoom[1] + 1).canEnter();
				break;
			case "left":
				res = myMaze.getRoom(currRoom[0], currRoom[1] - 1).canEnter();
				break;
			default:
				res = true;
			}
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	/*
	 * Update other components
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if ("newpos".equals(evt.getPropertyName())) {

			final int[] pos = myMaze.getPlayerLocation();
			for (int i = 0; i < pos.length; i++) {
				final int num = pos[i];
				changeButtonState(i, num);
			}
			repaint();
		}
	}

	/*
	 * Inner classes
	 */

	/*
	 * Highlight the button when user hovers over it Sets the action after
	 * clicking a button
	 */
	public class UserMouseListener extends MouseAdapter {
		private final Color B_COLOR = new Color(58, 175, 220); // highlighter
																// blue
		final Border BORDER = BorderFactory.createLineBorder(B_COLOR, 5);
		final Border EMPTY_BORDER = BorderFactory.createEmptyBorder();
		private final JButton myButton;

		public UserMouseListener(final JButton theButton) {
			myButton = theButton;
		}

		/*
		 * Add border if user hovers mouse
		 */
		@Override
		public void mouseEntered(final MouseEvent me) {
			// System.out.println("hovering");
			myButton.setBorder(BORDER);
		}

		/**
		 * Set the chosen direction after a click
		 */
		@Override
		public void mousePressed(final MouseEvent me) {
			myAction = (AbstractMovementAction) myButton.getAction();
		}

		/*
		 * Remove border after user scrolls off
		 */
		@Override
		public void mouseExited(final MouseEvent me) {
			// System.out.println("not hovering");
			myButton.setBorder(EMPTY_BORDER);
		}
	}

}
