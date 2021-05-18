package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import model.Maze;
import model.TriviaGame;
import view.viewHelper.BrightnessUtility;
import view.viewHelper.ControlPanel;
import view.viewHelper.MazeGUI;
import view.viewHelper.QuestionRoomGUI;

/**
 * @author ajdowney
 * @author ken
 */
public class PokemonPanel extends JPanel {
	/*
	 * TO adjust the picture position
	 */
    private final int X_OFFSET = 120;
    private final int Y_OFFSET = 230;

    /**
    * 
    */
    private static final long serialVersionUID = 8168838797214673243L;

    /*
     * Background color of the game (Crimson Red)
     */
    final Color BG_COLOR = new Color(220, 20, 60);

    Color BORDER_COLOR = new Color(51, 153, 204);

    /*
     * Aspect ratio of Jpanel
     */
    final Dimension PANEL_SIZE = new Dimension(1920, 1080);

    // /*
    // * test image for a pokemon
    // */
    // private final ImageIcon pika = new ImageIcon("./images/pokemon/pikachu.png");
    //
    // /* sparkle effect behind a pokemon */
    // private final ImageIcon shine = new
    // ImageIcon("./images/sparkle_formatted.png");

    private BufferedImage imshine = null;
    
    //KEN: testing setting up the game
    private final TriviaGame myGame;
    private final Maze myMaze;
    private BufferedImage myHiddenPic;
    

    // TODO Dev formatting, needs to be changed
    private int shineW = 0;
    private int shineH = 0;
    private int pokeW = 0;
    private int pokeH = 0;

    private BufferedImage myPokeLight;
    private BufferedImage myPokeDark;
    private BufferedImage impika;
    private final MazeGUI mazeGUI;
    private final QuestionRoomGUI questionRoomGUI;
    private boolean myDark;
  

    /*
     * Constructor
     */
    public PokemonPanel() {
        // TODO Auto-generated constructor stub
        super();

        //start a new game on the panel
        myGame = new TriviaGame();
        myMaze = Maze.getInstance();
        mazeGUI = new MazeGUI();
        questionRoomGUI = new QuestionRoomGUI();
      
        
        //put stuff on the panel
        final Border blueLine = BorderFactory.createLineBorder(BORDER_COLOR, 5);
        mazeGUI.setBorder(blueLine);
        
        final SpringLayout springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.NORTH, questionRoomGUI, 553, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.SOUTH, questionRoomGUI, -36, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, questionRoomGUI, -107, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.NORTH, mazeGUI, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, mazeGUI, 1345, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, mazeGUI, -10, SpringLayout.EAST, this);
        setLayout(springLayout);
        add(mazeGUI);
        add(questionRoomGUI);

        final ControlPanel controlPanel = new ControlPanel(this);
        springLayout.putConstraint(SpringLayout.SOUTH, controlPanel, -60, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, controlPanel, -131, SpringLayout.WEST, questionRoomGUI);
        addPropertyChangeListener(controlPanel);
        add(controlPanel);
        
        setImage();
        setImgBrightness(0);
        
        imshine = readImage("./src/images/other/sparkle_formatted.png");
        if (impika != null && imshine != null) {
            shineW = imshine.getWidth();
            shineH = imshine.getHeight();
            pokeW = impika.getWidth();
            pokeH = impika.getHeight();
        }


        setupPanel();

    }

    public void setImage() {
        // TODO Auto-generated method stub
            
            myPokeLight = myMaze.getCurrRoom().getPokemon().getPNG();
            myPokeDark = (BufferedImage) BrightnessUtility.adjustBrighness(myPokeLight, 0f);
            impika = myDark ? myPokeDark : myPokeLight;
            repaint();
        
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
//        final BufferedImage impika = (BufferedImage) BrightnessUtility.adjustBrighness(impika, 0f);
        theG.drawImage(imshine, 0, 0, shineW, shineH, this);
        theG.drawImage(impika, X_OFFSET, Y_OFFSET, pokeW, pokeH, this);
        firePropertyChange("newpos", null, null);
        // theG.drawImage(impika, 0, 0, pokeW, pokeH, this);

    }
    
    public void setImgBrightness(final int thePercentage) {            
            impika = thePercentage == 0 ? myPokeDark : myPokeLight;
            myDark = thePercentage == 0;
            repaint();
            
    }

    public JTable getTable( ) {
            return mazeGUI.getTable();
    } 
    
    public QuestionRoomGUI getQustionGUI() {
            return questionRoomGUI;
    }


}
