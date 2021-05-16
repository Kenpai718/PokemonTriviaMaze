package view.viewHelper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
        buttonGroup.add(up);
        up.setAction(upAction);
        up.setOpaque(false);
        up.setBorder(null);
        add(up);
        
        final JLabel label_1 = new JLabel("");
        add(label_1);
        
        left = new JButton("");
        buttonGroup.add(left);
        left.setAction(leftAction);
        left.setOpaque(false);
        left.setBorder(null);
        add(left);
        
        final JLabel player = new JLabel("");
        player.setInheritsPopupMenu(false);
        player.setIconTextGap(0);
        final ImageIcon imageIcon = new ImageIcon(new ImageIcon(ControlPanel.class.
                   getResource("/other/Player.jpg")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        player.setIcon(imageIcon);
        add(player);
        
        right = new JButton("");
        buttonGroup.add(right);
        right.setAction(rightAction);
        right.setOpaque(false);
        right.setBorder(null);
        add(right);
        
        final JLabel label_3 = new JLabel("");
        add(label_3);
        
        down = new JButton("");
        buttonGroup.add(down);
        down.setAction(downAction);
        down.setOpaque(false);
        down.setBorder(null);
        add(down);
        
        final JLabel label_4 = new JLabel("");
        add(label_4);

    }

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
                // TODO Auto-generated method stub
                System.out.println("Fired Change");
                if ("newpos".equals(evt.getPropertyName())) {
                        System.out.println("entered if");
                        final Maze maze = Maze.getInstance();
                        final int[] pos = maze.getPlayerLocation();
                        for (int i = 0; i < pos.length; i++) {
                                final int num = pos[i];
                                if (i == 0 && (num + 1 >= maze.getRows())) {
                                        down.setIcon(null);
                                        down.setEnabled(false);
                                        up.setAction(upAction);
                                        up.setEnabled(true);
                                } else if (i == 0 && (num - 1 <= -1)) {
                                        up.setIcon(null);
                                        up.setEnabled(false);
                                        down.setEnabled(true);
                                        down.setAction(downAction);
                                } else if (i == 1 && (num + 1 >= maze.getCols())) {
                                        right.setIcon(null);
                                        right.setEnabled(false);
                                        left.setEnabled(true);
                                        left.setAction(leftAction);
                                } else if (i == 1 && (num - 1 <= -1)) {
                                        left.setIcon(null);
                                        left.setEnabled(false);
                                        right.setEnabled(true);
                                        right.setAction(rightAction);
                                }
                        }
                        
                }
        }

//    private Icon getScaledImage(final ImageIcon theImage) {
//        ImageIcon imageIcon = theImage;
//        final Image image = imageIcon.getImage(); // transform it 
//        final Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
//        imageIcon = new ImageIcon(newimg);  // transform it back
//        return imageIcon;
//    }
}
