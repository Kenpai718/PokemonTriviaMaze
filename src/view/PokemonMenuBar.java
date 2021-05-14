package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;



public class PokemonMenuBar extends JMenuBar{
        
        private JMenu myHelpMenu;
        private JMenu myFileMenu;
        private final JFrame myFrame;
        private final JPanel myPanel;

	

	public PokemonMenuBar(final PokemonGUI theFrame) {
		// TODO Auto-generated constructor stub
		super();
		myFrame = theFrame.getFrame();
		myPanel = theFrame.getPanel();
		setupMenuBar();
	}
	
	private void setupMenuBar() {
		
	        myFileMenu = new JMenu("File");
		setupFileMenu();
		this.add(myFileMenu);
		
		myHelpMenu = new JMenu("Help");
		setupHelpMenu();
		this.add(myHelpMenu);
	}
	
	/**
	 * 
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
         * 
         */
        private void setupHelpMenu() {
                // TODO Auto-generated method stub
                final JMenuItem about = new JMenuItem("About");
                about.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                // TODO Auto-generated method stub
                                JOptionPane.showMessageDialog(myFrame, "Created by: AJ Downey, Kenneth Ahrens, "
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
		
		final JMenu cheats = new JMenu("Cheats");
                myHelpMenu.add(cheats);
                
                final JCheckBoxMenuItem cheat1 = new JCheckBoxMenuItem("Reveal Pokemon");
                cheats.add(cheat1);
                
                final JCheckBoxMenuItem cheat2 = new JCheckBoxMenuItem("Unlock All Doors");
                cheats.add(cheat2);
                
                final JMenuItem teleport = new JMenuItem("Teleport");
                cheats.add(teleport);
        
	}
	


}
