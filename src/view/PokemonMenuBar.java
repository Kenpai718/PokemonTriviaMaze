package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import model.Maze;
import model.Room;
import view.viewHelper.MazeGUI.MazeModel;

/**
 * Menubar for the trivia game Has file, help menus.
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class PokemonMenuBar extends JMenuBar{

        private JMenu myHelpMenu;
        private JMenu myFileMenu;
        private JMenu myGamemodeMenu;
        private ButtonGroup myGamemodes;
        private final Maze myMaze;
        private final JFrame myFrame;
        private final PokemonPanel myPanel;


        /**
         *  The constructor for the Menu Bar that sets up the the fields and
         *  starts the menubar gui setup
         * 
         * @param theFrame
         */
        public PokemonMenuBar(final PokemonGUI theFrame) {
                // TODO Auto-generated constructor stub
                super();
                myFrame = theFrame.getFrame();
                myPanel = theFrame.getPanel();
                myMaze = Maze.getInstance();
                setupMenuBar();
        }

        /**
         * Adds the base menus to the menu bar
         */
        private void setupMenuBar() {

                myFileMenu = new JMenu("File");
                setupFileMenu();
                this.add(myFileMenu);

                myHelpMenu = new JMenu("Help");
                setupHelpMenu();
                this.add(myHelpMenu);

                myGamemodeMenu = new JMenu("Gamemode");
                setupGamemodesMenu();
                this.add(myGamemodeMenu);

        }

        /**
         * Sets up the file menu
         */
        private void setupFileMenu() {
                // TODO Auto-generated method stub
                final JMenuItem save = new JMenuItem("Save");
                myFileMenu.add(save);

                final JMenuItem load = new JMenuItem("Load");
                myFileMenu.add(load);

                final JSeparator separator = new JSeparator();
                myFileMenu.add(separator);

                final JMenuItem exit = new JMenuItem("Exit");
                exit.addActionListener(theEvent -> System.exit(0));
                myFileMenu.add(exit);
        }

        /**
         * Sets up the Help Menu, 
         */
        private void setupHelpMenu() {
                // TODO Auto-generated method stub
                

                final JMenuItem about = new JMenuItem("About");
                about.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                // TODO Auto-generated method stub
                                JOptionPane.showMessageDialog(myFrame,
                                                "Created by: AJ Downey, Kenneth Ahrens, "
                                                                + "Katelyn Malone\n Spring 2021",
                                                "About", JOptionPane.PLAIN_MESSAGE);
                        }

                });
                myHelpMenu.add(about);

                final JMenuItem tutorial = new JMenuItem("Tutorial");
                tutorial.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                JOptionPane.showMessageDialog(myFrame, "This is a Tutorial!",
                                                "About", JOptionPane.PLAIN_MESSAGE);
                        }
                });
                myHelpMenu.add(tutorial);
                setUpCheats();
                

        }

        /**
         * Sets up the cheats menu for all of the dev cheats for debugging
         */
        private void setUpCheats() {
                // TODO Auto-generated method stub
                final MazeModel model = (MazeModel) myPanel.getTable().getModel();
                final JMenu cheats = new JMenu("Cheats");
                myHelpMenu.add(cheats);
                

                final JCheckBoxMenuItem cheat1 = new JCheckBoxMenuItem(
                                "Reveal Pokemon");
                cheat1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                final boolean selected = cheat1.isSelected();
                                myPanel.setImage();
                                if (selected) {
                                        myPanel.setImgBrightness(1);
                                } else {
                                        myPanel.setImgBrightness(0);
                                }

                        }
                });
                cheats.add(cheat1);

                final JMenuItem unlock = new JMenuItem(
                                "Unlock All Doors");
                unlock.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                // TODO Auto-generated method stub
                                final Room[][] rooms = myMaze.getMatrix();
                                for (int i = 0; i < myMaze.getRows(); i++) {
                                        for (int j = 0; j < myMaze.getCols(); j++) {
                                                rooms[i][j].setVisited(true);
                                                repaint();
                                        }
                                }
                        }
                                
                });
                cheats.add(unlock);
                
                final JMenuItem removeBlocked = new JMenuItem(
                                "Reset Blocked Rooms");
                removeBlocked.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                // TODO Auto-generated method stub
                                final Room[][] rooms = myMaze.getMatrix();
                                for (int i = 0; i < myMaze.getRows(); i++) {
                                        for (int j = 0; j < myMaze.getCols(); j++) {
                                                rooms[i][j].setEntry(true);
                                                repaint();
                                        }
                                }
                        }
                                
                });
                cheats.add(removeBlocked);
                

                final JMenuItem teleport = new JMenuItem("Teleport");
                
                teleport.addActionListener(new TeleportListener(model));
                cheats.add(teleport);
        }

        /**
         * Sets up the gamemode menu for changing the gamemode
         */
        private void setupGamemodesMenu() {

                myGamemodes = new ButtonGroup();
                final JRadioButtonMenuItem choice = new JRadioButtonMenuItem("Multiple Choice");
                myGamemodeMenu.add(choice);
                myGamemodes.add(choice);
                choice.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                //System.out.println("action fired");
                                //myPanel.setPanels(true);
                                firePropertyChange("changegm", null, true);

                        }

                });

                final JRadioButtonMenuItem input = new JRadioButtonMenuItem("User Input");
                myGamemodeMenu.add(input);
                myGamemodes.add(input);
                input.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                //System.out.println("action fired");
                                //myPanel.setPanels(false);
                                firePropertyChange("changegm", null, false);

                        }

                });
                
                //set initial gamemodechoice
                choice.setSelected(true);

        }

        /**
         * The action listener for the teleport cheat. 
         * 
         * @author ajdow
         *
         */
        class TeleportListener implements ActionListener {

                private final MazeModel myModel;

                /**
                 * Gets the table model to refresh the maze GUI
                 * 
                 * @param theModel the table model connected to the maze
                 */
                public TeleportListener(final MazeModel theModel) {
                        myModel = theModel;
                }

                /**
                 * Displays a input dialog that reads the new location and moves the
                 * player to that location
                 */
                @Override
                public void actionPerformed(final ActionEvent e) {
                        // TODO Auto-generated method stub
                        final String message = "Please Enter a new position to teleport"
                                        + " to.\n(X Y):";
                        final int[] pos = readInput(message);
                        
                        myMaze.setPlayerLocation(pos);
                        myModel.refresh(myMaze.getMatrix());
                        myPanel.setImage();
                        myPanel.getQuestionGUI().setButtons();
                }

                /**
                 * Helper method to read the input from the Input Dialog
                 * 
                 * @param theInput a string of the input
                 * @return an int[] of the two numbers input
                 */
                private int[] readInput(final String theMessage) {
                        final String input = JOptionPane.showInputDialog(theMessage);
                        int[] res = myMaze.getPlayerLocation().clone();
                        final Scanner scan;
                        if (input != null && !input.isEmpty()) {
                                if (!(input.length() < 3)) {
                                        scan = new Scanner(input.toString());
                                        try {
                                                for (int i = 0; i < 2; i++) {
                                                        final int num = scan.nextInt() - 1;
                                                        if (num < myMaze.getRows()
                                                                        && num < myMaze.getCols()) {
                                                                res[i] = num;
                                                        } else {
                                                                res = readInput("One or more numbers out "
                                                                                + "of range of maze\n(X Y):");
                                                                break;
                                                        }
                                                }
                                        } catch (final InputMismatchException e) {
                                                res = readInput("Please use integers only.\n(X Y):");
                                        } 
                                        scan.close();
                                } else {
                                        res = readInput("Invalid Input\n(X Y):");
                                }
                        } else {
                                // do nothing
                        }

                        return res;

                }
        }

}
