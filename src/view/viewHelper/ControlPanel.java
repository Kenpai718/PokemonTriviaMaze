package view.viewHelper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.movement_actions.DownAction;
import controller.movement_actions.LeftAction;
import controller.movement_actions.RightAction;
import controller.movement_actions.UpAction;
import view.PokemonPanel;

public class ControlPanel extends JPanel {
        private final DownAction downAction;
        private final LeftAction leftAction;
        private final RightAction rightAction;
        private final UpAction upAction;

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
        
        final JButton up = new JButton("");
        up.setAction(upAction);
        up.setOpaque(false);
        up.setBorder(null);
        add(up);
        
        final JLabel label_1 = new JLabel("");
        add(label_1);
        
        final JButton left = new JButton("");
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
        
        final JButton right = new JButton("");
        right.setAction(rightAction);
        right.setOpaque(false);
        right.setBorder(null);
        add(right);
        
        final JLabel label_3 = new JLabel("");
        add(label_3);
        
        final JButton down = new JButton("");
        down.setAction(downAction);
        down.setOpaque(false);
        down.setBorder(null);
        add(down);
        
        final JLabel label_4 = new JLabel("");
        add(label_4);

    }

//    private Icon getScaledImage(final ImageIcon theImage) {
//        ImageIcon imageIcon = theImage;
//        final Image image = imageIcon.getImage(); // transform it 
//        final Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
//        imageIcon = new ImageIcon(newimg);  // transform it back
//        return imageIcon;
//    }
}
