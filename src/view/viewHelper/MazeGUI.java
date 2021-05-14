package view.viewHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Maze;
import model.Room;

public class MazeGUI extends JPanel {

	/*
	 * Color used for the maze background
	 */
	Color MAZE_BG = new Color(51, 51, 51);
	
	/*
	 * Color used for the font
	 */
	Color FONT_COLOR = Color.WHITE;
	
	/*
	 * Color used for the border
	 */
	Color BORDER_COLOR = new Color(51, 153, 204);
	
	/*
	 * Pokemon font
	 */
	Font PKMN_FONT = new Font("PKMN RBYGSC", Font.PLAIN, 40);
	
	/*
	 * Table used for GUI visual
	 */
	private final JTable myTable;
	
	/*
	 * Used to build the JTable rows and columns
	 */
	private final DefaultTableModel myDTB;

	/*
	 * Maze that is to be visualized in the GUI
	 */
	private final Maze myMaze;

	/*
	 * Matrix of the maze
	 */
	final private Room[][] myMatrix;
	
	/*
	 * Used to draw onto table
	 */
	private final Renderer myRenderer;

	/**
	 * Constructor that create the maze visual
	 */
	
	//TODO: Pass in the maze instead of making a new maze later
	public MazeGUI() {
		//initialize fields
		myMaze = Maze.getInstance();
		myMatrix = myMaze.getMatrix();

		//prepare GUI 
		setMaximumSize(new Dimension(500, 500));
		setBackground(new Color(255, 0, 0));
		setPreferredSize(new Dimension(500, 500));
		setLayout(new BorderLayout(0, 0));

		//setup table for maze visual
		myDTB = new DefaultTableModel(myMaze.getRows(), myMaze.getCols());
		myTable = new JTable(myDTB);
//		myTable.getModel().addTableModelListener(new TableModelListener()  {
//
//                        @Override
//                        public void tableChanged(final TableModelEvent e) {
//                                // TODO Auto-generated method stub
//                                myTable.repaint();
//                                myTable.revalidate();
//                        }
//		        
//		});
//		myTable.addPropertyChangeListener(new PropertyChangeListener() {
//		        @Override
//                        public void propertyChange(final PropertyChangeEvent theEvt) {
//		                if ("newpos".equals(theEvt.getPropertyName())) {
//		                        myTable.repaint();
//		                        myTable.revalidate();
//		                        
//		                }
//		        }
//		});
		myTable.setFont(PKMN_FONT);
		myTable.setForeground(FONT_COLOR);
		myTable.setGridColor(BORDER_COLOR);
		myTable.setRowSelectionAllowed(false);
		myTable.setRowHeight(125);
		myTable.setPreferredScrollableViewportSize(new Dimension(500, 500));
		myTable.setBackground(new Color(51, 51, 51));
		myTable.setFocusable(false);
		add(myTable, BorderLayout.CENTER);

		// set player icon, blocked, room names
		myRenderer = new Renderer();
		fillTable();

	}
	
	/*
	 * Uses a custom renderer class to individually fill each cell with the corresponding icons/names
	 */
	public void fillTable() {
		
		for (int i = 0; i < myMaze.getCols(); i++) {
			myTable.getColumnModel().getColumn(i)
					.setCellRenderer(myRenderer);
		}
	}
	
	

	/*
	 * Inner class that creates a JLabel with a room name or icon to render onto every JTable cell
	 */
	class Renderer extends DefaultTableCellRenderer {
		
		/*
		 * Icon that represents the player
		 */
		private final ImageIcon PLAYER = new ImageIcon(
				"./src/images/other/PlayerIcon.png");
		
		/*
		 * Icon that represents a blocked room
		 */
		private final ImageIcon TREE = new ImageIcon(
				"./src/images/other/tree.png");



		/*
		 * Constructor
		 * 
		 * @param Maze to get an updated maze to fill table with
		 */
		public Renderer() {
			super();
		}
		
		

		@Override
		public Component getTableCellRendererComponent(final JTable table,
				final Object value, final boolean isSelected, final boolean hasFocus, final int row,
				final int column) {
			
			final JLabel lbl = new JLabel(); //label put in cells
//			final int[]playerLocation = myMaze.getPlayerLocation();
			final Room r = myMatrix[row][column];

			if (r.isPlayerHere()) { //player at this cell put player icon
				final ImageIcon scaled = new ImageIcon(PLAYER.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				lbl.setIcon(scaled);
			//same process for blocked rooms put an else if here once we have a data structure for it

			} else { //put the room name in cell				
				final String name = r.toString();
				lbl.setText(name);
				lbl.setForeground(Color.WHITE);
				lbl.setBackground(MAZE_BG);
				lbl.setFont(PKMN_FONT);

			}

			
			//format jlabel 
			lbl.setVisible(true);
			lbl.setHorizontalAlignment(JLabel.CENTER);
			lbl.setVerticalAlignment(JLabel.CENTER);
			return lbl;

		}
	}

}
