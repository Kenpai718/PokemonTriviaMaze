package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import controller.menu_actions.ExitAction;



public class PokemonMenuBar extends JMenuBar{
        private final ExitAction exitAction = new ExitAction();
	

	public PokemonMenuBar() {
		// TODO Auto-generated constructor stub
		super();
		setupMenuBar();
	}
	
	private void setupMenuBar() {
		
		final JMenu fileMenu = new JMenu("File");
		this.add(fileMenu);
		
		final JMenuItem save = new JMenuItem("Save");
		fileMenu.add(save);
		
		final JMenuItem load = new JMenuItem("Load");
		fileMenu.add(load);
		
		final JSeparator separator = new JSeparator();
		fileMenu.add(separator);
		
		final JMenuItem exit = new JMenuItem("Exit");
		exit.setAction(exitAction);
		fileMenu.add(exit);
		
		final JMenu helpMenu = new JMenu("Help");
		this.add(helpMenu);
		
		final JMenuItem about = new JMenuItem("About");
		helpMenu.add(about);
		
		final JMenuItem tutorial = new JMenuItem("Tutorial");
		helpMenu.add(tutorial);
		
		final JMenuItem cheats = new JMenuItem("Cheats");
		helpMenu.add(cheats);
		
	
	}
	

}
