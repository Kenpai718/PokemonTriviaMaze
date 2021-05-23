package view.viewHelper;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class RoomPanel extends JPanel {

        /**
	 * 
	 */
	private static final long serialVersionUID = 4314694184931278150L;

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
