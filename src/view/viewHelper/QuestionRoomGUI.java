package view.viewHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

public class QuestionRoomGUI extends JPanel {
    private final ButtonGroup buttonGroup = new ButtonGroup();

    /**
     * Create the panel.
     */
    public QuestionRoomGUI() {
        setBorder(new LineBorder(Color.BLACK, 4, true));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(350, 500));
        setMaximumSize(new Dimension(350, 500));
        final SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        
        final JTextPane questionTextPane = new JTextPane();
        springLayout.putConstraint(SpringLayout.NORTH, questionTextPane, 22, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.SOUTH, questionTextPane, 68, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.EAST, questionTextPane, -10, SpringLayout.EAST, this);
        questionTextPane.setRequestFocusEnabled(false);
        questionTextPane.setText("Who's that Pokemon?");
        questionTextPane.setOpaque(false);
        questionTextPane.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 19));
        questionTextPane.setFocusable(false);
        questionTextPane.setEnabled(false);
        questionTextPane.setDisabledTextColor(Color.BLACK);
        questionTextPane.setBorder(null);
        add(questionTextPane);
        
        final JRadioButton answer1 = new JRadioButton("Answer 1");
        answer1.setOpaque(false);
        springLayout.putConstraint(SpringLayout.NORTH, answer1, 29, SpringLayout.SOUTH, questionTextPane);
        springLayout.putConstraint(SpringLayout.WEST, questionTextPane, -24, SpringLayout.WEST, answer1);
        springLayout.putConstraint(SpringLayout.WEST, answer1, 34, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, answer1, 0, SpringLayout.EAST, this);
        answer1.setMnemonic('1');
        answer1.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
        buttonGroup.add(answer1);
        add(answer1);
        
        final JRadioButton answer2 = new JRadioButton("Answer 2");
        answer2.setOpaque(false);
        springLayout.putConstraint(SpringLayout.SOUTH, answer1, -37, SpringLayout.NORTH, answer2);
        springLayout.putConstraint(SpringLayout.NORTH, answer2, 189, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, answer2, 34, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, answer2, 0, SpringLayout.EAST, this);
        answer2.setMnemonic('2');
        answer2.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
        buttonGroup.add(answer2);
        add(answer2);
        
        final JRadioButton answer3 = new JRadioButton("Answer 3");
        answer3.setOpaque(false);
        springLayout.putConstraint(SpringLayout.WEST, answer3, 34, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, answer3, 0, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, answer2, -42, SpringLayout.NORTH, answer3);
        springLayout.putConstraint(SpringLayout.NORTH, answer3, 286, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.SOUTH, answer3, -159, SpringLayout.SOUTH, this);
        answer3.setMnemonic('3');
        answer3.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
        buttonGroup.add(answer3);
        add(answer3);
        
        final JRadioButton answer4 = new JRadioButton("Answer 4\r\n");
        answer4.setOpaque(false);
        springLayout.putConstraint(SpringLayout.NORTH, answer4, 33, SpringLayout.SOUTH, answer3);
        springLayout.putConstraint(SpringLayout.WEST, answer4, 34, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, answer4, -71, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, answer4, 0, SpringLayout.EAST, this);
        answer4.setMnemonic('4');
        answer4.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
        buttonGroup.add(answer4);
        add(answer4);

    }
}
