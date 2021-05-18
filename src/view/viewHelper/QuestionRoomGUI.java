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

import model.QuestionAnswer;
import model.Room;

public class QuestionRoomGUI extends JPanel {
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private QuestionAnswer myQA;
    private JTextPane myQPane;
    private SpringLayout myLayout;
    
    /*
     * Multiple choice
     */
    private String[] myChoices;
    
    private Room myCurrRoom;
    
    JRadioButton myA1;
    JRadioButton myA2;
    JRadioButton myA3;
    JRadioButton myA4;
    

    /**
     * Create the panel.
     */
    public QuestionRoomGUI(Room theRoom) {
    	myCurrRoom = theRoom;
    	myChoices = theRoom.getChoices();

    	
    	setupGUI();
    	
    }
    
    private void setupGUI() {
        setBorder(new LineBorder(Color.BLACK, 4, true));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(350, 500));
        setMaximumSize(new Dimension(350, 500));
        myLayout = new SpringLayout();
        setLayout(myLayout);
        
        myQPane = new JTextPane();
        myLayout.putConstraint(myLayout.NORTH, myQPane, 22, myLayout.NORTH, this);
        myLayout.putConstraint(myLayout.SOUTH, myQPane, 68, myLayout.NORTH, this);
        myLayout.putConstraint(myLayout.EAST, myQPane, -10, myLayout.EAST, this);
        myQPane.setRequestFocusEnabled(false);
        myQPane.setText("Who's that Pokemon?");
        myQPane.setOpaque(false);
        myQPane.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 19));
        myQPane.setFocusable(false);
        myQPane.setEnabled(false);
        myQPane.setDisabledTextColor(Color.BLACK);
        myQPane.setBorder(null);
        add(myQPane);
        
        setupQuestions();
        
    }
    
    private void setupQuestions() {
    	
        myA1 = new JRadioButton(myChoices[0]);
        myA1.setOpaque(false);
        myLayout.putConstraint(myLayout.NORTH, myA1, 29, myLayout.SOUTH, myQPane);
        myLayout.putConstraint(myLayout.WEST, myQPane, -24, myLayout.WEST, myA1);
        myLayout.putConstraint(myLayout.WEST, myA1, 34, myLayout.WEST, this);
        myLayout.putConstraint(myLayout.EAST, myA1, 0, myLayout.EAST, this);
        myA1.setMnemonic('1');
        myA1.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
        buttonGroup.add(myA1);
        add(myA1);
        
        myA2 = new JRadioButton(myChoices[1]);
        myA2.setOpaque(false);
        myLayout.putConstraint(myLayout.SOUTH, myA1, -37, myLayout.NORTH, myA2);
        myLayout.putConstraint(myLayout.NORTH, myA2, 189, myLayout.NORTH, this);
        myLayout.putConstraint(myLayout.WEST, myA2, 34, myLayout.WEST, this);
        myLayout.putConstraint(myLayout.EAST, myA2, 0, myLayout.EAST, this);
        myA2.setMnemonic('2');
        myA2.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
        buttonGroup.add(myA2);
        add(myA2);
        
        myA3 = new JRadioButton(myChoices[2]);

        myA3.setOpaque(false);
        myLayout.putConstraint(myLayout.WEST, myA3, 34, myLayout.WEST, this);
        myLayout.putConstraint(myLayout.EAST, myA3, 0, myLayout.EAST, this);
        myLayout.putConstraint(myLayout.SOUTH, myA2, -42, myLayout.NORTH, myA3);
        myLayout.putConstraint(myLayout.NORTH, myA3, 286, myLayout.NORTH, this);
        myLayout.putConstraint(myLayout.SOUTH, myA3, -159, myLayout.SOUTH, this);
        myA3.setMnemonic('3');
        myA3.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
        buttonGroup.add(myA3);
        add(myA3);
        
        myA4 = new JRadioButton(myChoices[3]);
        myA4.setOpaque(false);
        myLayout.putConstraint(myLayout.NORTH, myA4, 33, myLayout.SOUTH, myA3);
        myLayout.putConstraint(myLayout.WEST, myA4, 34, myLayout.WEST, this);
        myLayout.putConstraint(myLayout.SOUTH, myA4, -71, myLayout.SOUTH, this);
        myLayout.putConstraint(myLayout.EAST, myA4, 0, myLayout.EAST, this);
        myA4.setMnemonic('4');
        myA4.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
        buttonGroup.add(myA4);
        add(myA4);
    }
    
}
