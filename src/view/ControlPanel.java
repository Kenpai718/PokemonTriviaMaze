package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.movement_actions.DownAction;
import controller.movement_actions.LeftAction;
import controller.movement_actions.RightAction;
import controller.movement_actions.UpAction;

public class ControlPanel extends JPanel {
    private final LeftAction leftAction = new LeftAction();
    private final RightAction rightAction = new RightAction();
    private final UpAction upAction = new UpAction();
    private final DownAction downAction = new DownAction();

    /**
     * Create the panel.
     */
    public ControlPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(300, 300));
        setLayout(new GridLayout(0, 3, 0, 0));
        
        final JLabel label = new JLabel("");
        add(label);
        
        final JButton up = new JButton("");
        up.setOpaque(false);
        up.setAction(upAction);
        up.setBorder(null);
        add(up);
        
        final JLabel label_1 = new JLabel("");
        add(label_1);
        
        final JButton left = new JButton("");
        left.setOpaque(false);
        left.setIcon(new ImageIcon(ControlPanel.class.getResource("/arrows/left.png")));
        left.setAction(leftAction);
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
        right.setOpaque(false);
        right.setAction(rightAction);
        right.setBorder(null);
        add(right);
        
        final JLabel label_3 = new JLabel("");
        add(label_3);
        
        final JButton down = new JButton("");
        down.setOpaque(false);
        down.setAction(downAction);
        down.setBorder(null);
        add(down);
        
        final JLabel label_4 = new JLabel("");
        add(label_4);

    }

    private Icon getScaledImage(final ImageIcon theImage) {
        ImageIcon imageIcon = theImage;
        final Image image = imageIcon.getImage(); // transform it 
        final Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        return imageIcon;
    }
}
