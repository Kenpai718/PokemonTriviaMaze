package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class PokemonMenuBar extends JMenuBar{
	

	public PokemonMenuBar() {
		// TODO Auto-generated constructor stub
		super();
		setupMenuBar();
	}
	
	private void setupMenuBar() {
		
		JMenu fileMenu = new JMenu("File");
		this.add(fileMenu);
		
		JMenuItem save = new JMenuItem("Save");
		fileMenu.add(save);
		
		JMenuItem load = new JMenuItem("Load");
		fileMenu.add(load);
		
		JSeparator separator = new JSeparator();
		fileMenu.add(separator);
		
		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		
		JMenu helpMenu = new JMenu("Help");
		this.add(helpMenu);
		
		JMenuItem about = new JMenuItem("About");
		helpMenu.add(about);
		
		JMenuItem tutorial = new JMenuItem("Tutorial");
		helpMenu.add(tutorial);
		
		JMenuItem cheats = new JMenuItem("Cheats");
		helpMenu.add(cheats);
		
	
	}

}
