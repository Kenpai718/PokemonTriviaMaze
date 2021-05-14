package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;



public class PokemonMenuBar extends JMenuBar{
        
        private JMenu myHelpMenu;
        private JMenu myFileMenu;
        private final JFrame myFrame;
	

	public PokemonMenuBar(final JFrame theFrame) {
		// TODO Auto-generated constructor stub
		super();
		myFrame = theFrame;
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
		myHelpMenu.add(tutorial);
		
		final JMenuItem cheats = new JMenuItem("Cheats");
		myHelpMenu.add(cheats);
	}
	

}
