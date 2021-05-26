package view;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import javax.swing.KeyStroke;

import model.Maze;
import sound.BackgroundMusic;
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

        /**
	 * 
	 */
	private static final long serialVersionUID = -6748686814206614562L;
		private JMenu myHelpMenu;
        private JMenu myFileMenu;
        private JMenu myGamemodeMenu;
        private ButtonGroup myGamemodes;
        private final Maze myMaze;
        private final JFrame myFrame;
        private final PokemonPanel myPanel;


        public PokemonMenuBar(final PokemonGUI theFrame) {
                super();
                myFrame = theFrame.getFrame();
                myPanel = theFrame.getPanel();
                myMaze = Maze.getInstance();
                setupMenuBar();
        }

        private void setupMenuBar() {

                myFileMenu = new JMenu("File");
                myFileMenu.setMnemonic(KeyEvent.VK_F);
                setupFileMenu();
                this.add(myFileMenu);

                myHelpMenu = new JMenu("Help");
                myHelpMenu.setMnemonic(KeyEvent.VK_H);
                setupHelpMenu();
                this.add(myHelpMenu);

                myGamemodeMenu = new JMenu("Gamemode");
                myGamemodeMenu.setMnemonic(KeyEvent.VK_G);
                setupGamemodesMenu();
                this.add(myGamemodeMenu);

        }

        /**
         * 
         */
        private void setupFileMenu() {
                final JMenuItem save = new JMenuItem("Save");
                save.setMnemonic(KeyEvent.VK_S);
                save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
                myFileMenu.add(save);
                
              //  save.addActionListener(new ActionListener() {

        	//	});


                final JMenuItem load = new JMenuItem("Load");
                load.setMnemonic(KeyEvent.VK_L);
                load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
                myFileMenu.add(load);
                
              //  load.addActionListener(new ActionListener() {
        		
        		//});


                final JSeparator separator = new JSeparator();
                myFileMenu.add(separator);
                
                final JMenuItem reset = new JMenuItem("Reset");
                reset.setMnemonic(KeyEvent.VK_R);
                myFileMenu.add(reset);
                
                reset.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
                });
                
                myFileMenu.add(separator);
                
                final JMenuItem exit = new JMenuItem("Exit");
                exit.setMnemonic(KeyEvent.VK_E);
                exit.addActionListener(theEvent -> System.exit(0));
                myFileMenu.add(exit);
        }
        
        /**
    	 * Creates the saved file and saves it to the computer
    	 * 
    	 * @param theFrame                 the window that opens when you save the file
    	 * @throws FileNotFoundException   
    	 * @throws IOException
    	 */
    	public void saveToFile(JFrame theFrame)
    			throws FileNotFoundException, IOException {

    		/**FileDialog fd; // A file dialog box that will let the user
    						// specify the output file.

    		// start file dialog to prompt user for file name/location to save to
    		fd = new FileDialog(theFrame, "Save to File", FileDialog.SAVE);


    		String fileName = fd.getFile(); // gets what the user input for file
    										// name

    		// if user closes without entering a name
    		if (fileName == null) {
    			JOptionPane.showMessageDialog(null, "Save was canceled.");
    			return;
    		} else {

    			// add a file extension if user did not do so
    			if (!fileName.endsWith(".pkmn"))
    				fileName += ".pkmn";

    			String directoryName = fd.getDirectory();

    			// make the file
    			File file = new File(directoryName, fileName);

    			//write object to a file
    			ObjectOutputStream out;

    			try {
    				// write info the file
    				out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

    				// get the current list of shapes from the panel
    				Stack<PaintShape> shapesList = myPanel.getMaze();

    				// save the serialized object to the file
    				out.writeObject(shapesList);
    				out.close();

    				// tell user it worked
    				JOptionPane.showMessageDialog(null,
    						fileName + " saved sucessfully!");
    				
    			//error catching
    			} catch (FileNotFoundException e) {
    				JOptionPane.showMessageDialog(null, e);
    			} catch (IOException e) {
    				JOptionPane.showMessageDialog(null, e);
    			}
    		}*/

    	}
    	
    	/**
    	 * Reads the saved files and loads it in
    	 * 
    	 * @param theFrame the window that opens when you load the file
    	 */
    	public void loadFromFile(JFrame theFrame) {

    		/**FileDialog fd; // A file dialog box that will let the user
    		// specify the input file.

    		// start file dialog to prompt user for file name/location to load from
    		fd = new FileDialog(theFrame, "Save to File", FileDialog.LOAD);

    		String fileName = fd.getFile();

    		// cancel load if user doesnt input anything
    		if (fileName == null) {
    			JOptionPane.showMessageDialog(null, "Load was canceled.");
    			return;
    		} else {

    			String directoryName = fd.getDirectory();

    			File file = new File(directoryName, fileName);

    			ObjectInputStream in;

    			try {
    				in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));

    				//get the paintshape data from the input file
    				Object obj = in.readObject();
    				Stack<PaintShape> shapeData = (Stack<PaintShape>) obj;
    				
    				//send the loaded shapes to panel to redraw
    				myPanel.loadShapes(shapeData);

    				//tell user it worked
    				JOptionPane.showMessageDialog(null,
    						fileName + " loaded successfully!");
    				
    			//error catching below
    			} catch (FileNotFoundException e) {
    				JOptionPane.showMessageDialog(null, e);
    			} catch (IOException e) {
    				JOptionPane.showMessageDialog(null, e);
    			} catch (ClassNotFoundException e) {
    				JOptionPane.showMessageDialog(null, e);
    			}
    		}*/

    	}


        /**
         * 
         */
        private void setupHelpMenu() {
                final JMenuItem about = new JMenuItem("About");
                about.setMnemonic(KeyEvent.VK_A);
                about.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                JOptionPane.showMessageDialog(myFrame,
                                                "Created by: AJ Downey, Kenneth Ahrens, "
                                                                + "Katlyn Malone\n Spring 2021",
                                                "About", JOptionPane.PLAIN_MESSAGE);
                        }

                });
                myHelpMenu.add(about);

                final JMenuItem tutorial = new JMenuItem("Tutorial");
                tutorial.setMnemonic(KeyEvent.VK_E);
                tutorial.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                JOptionPane.showMessageDialog(myFrame, "This is a Tutorial!",
                                                "About", JOptionPane.PLAIN_MESSAGE);
                        }
                });
                myHelpMenu.add(tutorial);

                final JMenu cheats = new JMenu("Cheats");
                cheats.setMnemonic(KeyEvent.VK_C);
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

                final JCheckBoxMenuItem cheat2 = new JCheckBoxMenuItem(
                                "Unlock All Doors");
                cheats.add(cheat2);

                final JMenuItem teleport = new JMenuItem("Teleport");
                final MazeModel model = (MazeModel) myPanel.getTable().getModel();
                teleport.addActionListener(new TeleportListener(model));
                cheats.add(teleport);

        }

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

        public class TeleportListener implements ActionListener {

                private final MazeModel myModel;

                public TeleportListener(final MazeModel theModel) {
                        myModel = theModel;
                }

                @Override
                public void actionPerformed(final ActionEvent e) {
                        final String message = "Please Enter a new position to teleport"
                                        + " to.\n(X Y):";
                        final int[] pos = readInput(message);
//                        
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
