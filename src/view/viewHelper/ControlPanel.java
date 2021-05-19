package view.viewHelper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private final DownAction downAction;
	private final LeftAction leftAction;
	private final RightAction rightAction;
	private final UpAction upAction;
	final JButton up;
	final JButton down;
	final JButton left;
	final JButton right;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the panel.
	 * 
	 * @param pokemonPanel
	 */
	public ControlPanel(final PokemonPanel thePanel) {
		upAction = new UpAction(thePanel);
		leftAction = new LeftAction(thePanel);
		rightAction = new RightAction(thePanel);
		downAction = new DownAction(thePanel);

		setOpaque(false);
		setPreferredSize(new Dimension(300, 300));
		setLayout(new GridLayout(0, 3, 0, 0));

		final JLabel label = new JLabel("");
		add(label);

		up = new JButton("");
		up.setHideActionText(true);
		buttonGroup.add(up);
		up.setAction(upAction);
		up.setBorder(null);
		add(up);

		final JLabel label_1 = new JLabel("");
		add(label_1);

		left = new JButton("");
		left.setHideActionText(true);
		buttonGroup.add(left);
		left.setAction(leftAction);
		left.setBorder(null);
		add(left);

		final JLabel player = new JLabel("");
		player.setInheritsPopupMenu(false);
		player.setIconTextGap(0);
		final ImageIcon imageIcon = new ImageIcon(new ImageIcon(
				ControlPanel.class.getResource("/other/Player.jpg")).getImage()
						.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		player.setIcon(imageIcon);
		add(player);

		right = new JButton("");
		right.setHideActionText(true);
		buttonGroup.add(right);
		right.setAction(rightAction);
		right.setBorder(null);
		add(right);

		final JLabel label_3 = new JLabel("");
		add(label_3);

		down = new JButton("");
		down.setHideActionText(true);
		buttonGroup.add(down);
		down.setAction(downAction);
		down.setBorder(null);
		add(down);

		final JLabel label_4 = new JLabel("");
		add(label_4);
		addListeners();
		// firePropertyChange("newpos", null, null);
	}

	private void addListeners() {
		// TODO Auto-generated method stub
		addPropertyChangeListener(this);
		final Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		while (buttons.hasMoreElements()) {
			final JButton temp = (JButton) buttons.nextElement();
			temp.addPropertyChangeListener(this);
		}
	}

	private void changeButtonState(final int i, final int num,
			final Maze maze) {
		// TODO Auto-generated method stub
		if (i == 0) {
			down.setVisible(num + 1 < maze.getRows());
			down.setEnabled(num + 1 < maze.getRows());
			up.setVisible(num - 1 >= 0);
			up.setEnabled(num - 1 >= 0);
		} else {
			right.setVisible(num + 1 < maze.getCols());
			right.setEnabled(num + 1 < maze.getCols());
			left.setVisible(num - 1 >= 0);
			left.setEnabled(num - 1 >= 0);
			// merge test comment
		}
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if ("newpos".equals(evt.getPropertyName())) {
//                        System.out.println("entered if");
			final Maze maze = Maze.getInstance();
			final int[] pos = maze.getPlayerLocation();
			for (int i = 0; i < pos.length; i++) {
				final int num = pos[i];
				changeButtonState(i, num, maze);
			}
			repaint();
		}
	}
}