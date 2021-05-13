package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class PokemonPanel extends JPanel {
        
        /**
     * 
     */
    private static final long serialVersionUID = 8168838797214673243L;

    /*
         * Background color of the game (Crimson Red)
         */
        final Color BG_COLOR = new Color(220,20,60);
        
        /*
         * Aspect ratio of Jpanel
         */
        final Dimension PANEL_SIZE = new Dimension(1920, 1080);
        
//      /*
//       * test image for a pokemon
//       */
//      private final ImageIcon pika = new ImageIcon("./images/pokemon/pikachu.png");
//      
//      /* sparkle effect behind a pokemon */
//      private final ImageIcon shine = new ImageIcon("./images/sparkle_formatted.png");
        
        private BufferedImage imshine = null;   
        
        // TODO Dev formatting, needs to be changed
        private int shineW = 0; 
        private int shineH = 0;
        private int pokeW = 0; 
        private int pokeH = 0;

        private final BufferedImage impika;
        private final MazeGUI mazeGUI;
        private final QuestionRoomGUI questionRoomGUI;
        
        
        
        
        /*
         * Constructor
         */
        public PokemonPanel() {
                // TODO Auto-generated constructor stub
                super();
                
                mazeGUI = new MazeGUI();
                
                questionRoomGUI = new QuestionRoomGUI();
                final SpringLayout springLayout = new SpringLayout();
                springLayout.putConstraint(SpringLayout.NORTH, questionRoomGUI, 553, SpringLayout.NORTH, this);
                springLayout.putConstraint(SpringLayout.SOUTH, questionRoomGUI, -36, SpringLayout.SOUTH, this);
                springLayout.putConstraint(SpringLayout.EAST, questionRoomGUI, -107, SpringLayout.EAST, this);
                springLayout.putConstraint(SpringLayout.NORTH, mazeGUI, 10, SpringLayout.NORTH, this);
                springLayout.putConstraint(SpringLayout.WEST, mazeGUI, 1345, SpringLayout.WEST, this);
                springLayout.putConstraint(SpringLayout.EAST, mazeGUI, -10, SpringLayout.EAST, this);
                setLayout(springLayout);
                setupPanels(mazeGUI, questionRoomGUI);
                add(mazeGUI);
                add(questionRoomGUI);
                
                ControlPanel controlPanel = new ControlPanel();
                springLayout.putConstraint(SpringLayout.SOUTH, controlPanel, -60, SpringLayout.SOUTH, this);
                springLayout.putConstraint(SpringLayout.EAST, controlPanel, -131, SpringLayout.WEST, questionRoomGUI);
                add(controlPanel);
                
                
                impika = readImage("./images/pokemon/pikachu.png");
                imshine = readImage("./images/sparkle_formatted.png");      
                if (impika != null && imshine != null) {
                    shineW = imshine.getWidth();
                    shineH = imshine.getHeight();
                    pokeW = impika.getWidth();
                    pokeH = impika.getHeight();
                }
        
                
                //note: the pictures below are just a test rn I will delete later
//              final JLabel pikachu = new JLabel("");
//              pikachu.setIcon(pika);
//              add(pikachu);
//              
//              final JLabel sparkle = new JLabel("");
//              sparkle.setIcon(shine);
//              add(sparkle);
                
                setupPanel();
                
        }
        
        private void setupPanels(final MazeGUI theMazeGUI, final QuestionRoomGUI theQuestionRoomGUI) {
            
        }

        private BufferedImage readImage(final String theLocation) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(theLocation));
            } catch (final IOException e) {
                
            }
            
        return img;
    }

        /**
         * Setup the panel for the game
         */
        private void setupPanel() {
                setBackground(BG_COLOR);
                setPreferredSize(PANEL_SIZE);
                
        }
        
        /**
         *
         */
        @Override
        protected void paintComponent(final Graphics theG) {    
            super.paintComponent(theG);
            theG.drawImage(imshine, 0, -50, shineW, shineH, this);
            theG.drawImage(impika, 0, -50, pokeW, pokeH, this);
            
            
            
        }
}
