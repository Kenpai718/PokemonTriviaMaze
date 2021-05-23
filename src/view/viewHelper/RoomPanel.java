package view.viewHelper;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class RoomPanel extends JPanel {

        /**
         * Create the panel.
         */
        public RoomPanel() {
                setOpaque(false);
                setLayout(new CardLayout());
                
                final TextRoomGUI textRoomGUI = new TextRoomGUI();
                add(textRoomGUI);
                
                final QuestionRoomGUI questionRoomGUI = new QuestionRoomGUI();
                add(questionRoomGUI);

        }

}
