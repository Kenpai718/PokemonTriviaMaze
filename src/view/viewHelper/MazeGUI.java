package view.viewHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import model.Maze;
import model.Room;

/**
 * Visual of the maze GUI represented by a JTable. Shows location of player, all
 * rooms in the maze matrix and blocked rooms.
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class MazeGUI extends JPanel implements PropertyChangeListener {

	/**
	     * 
	     */
	private static final long serialVersionUID = -5880241329362260869L;
	
	private static final int SIZE = 500;
	
	private static final Dimension DEFAULT_DIM = new Dimension(SIZE,SIZE);
	
	private static final double FONT_SIZE = 3.125;
	

	/*
	 * Color used for the maze background
	 */
	final Color MAZE_BG = new Color(51, 51, 51);

	/*
	 * Color used for the font
	 */
	final Color FONT_COLOR = Color.WHITE;

	/*
	 * Color used for the border
	 */
	final Color BORDER_COLOR = new Color(51, 153, 204);

	/*
	 * Pokemon font
	 */
//	final Font PKMN_FONT = 

	/*
	 * Table used for GUI visual
	 */
	private final JTable myTable;

	/*
	 * Used to build the JTable rows and columns
	 */
//	private final DefaultTableModel myDTB;

	/*
	 * Maze that is to be visualized in the GUI
	 */
	private final Maze myMaze;

	/*
	 * Matrix of the maze
	 */
	private Room[][] myMatrix;

	/*
	 * Used to draw onto table
	 */
	private final Renderer myRenderer;
	
	private final MazeModel myModel;
	
	private Font myFont;
	
	private final int myRowSize;

	/**
	 * Constructor that create the maze visual
	 */

	public MazeGUI() {
		// initialize fields
		myMaze = Maze.getInstance();
		myMatrix = myMaze.getMatrix();
		myRowSize = SIZE / myMaze.getRows();
		myFont = new Font("PKMN RBYGSC", Font.PLAIN, (int) (myRowSize / FONT_SIZE));

		// prepare GUI
		setMaximumSize(DEFAULT_DIM);
		setBackground(new Color(255, 0, 0));
		setPreferredSize(DEFAULT_DIM);
		setLayout(new BorderLayout(0, 0));

		// setup table for maze visual
//		myDTB = new DefaultTableModel(myMaze.getRows(), myMaze.getCols());
		myTable = new JTable();
		// Using Custom TableModel to correctly refresh the table when changes
		// are made
		myModel = new MazeModel();
		myTable.setModel(myModel);
//		myTable.setFont(myFont);
		myTable.setForeground(FONT_COLOR);
		myTable.setGridColor(BORDER_COLOR);
		myTable.setRowSelectionAllowed(false);
		myTable.setRowHeight(myRowSize);
		myTable.setPreferredScrollableViewportSize(DEFAULT_DIM);
		myTable.setBackground(new Color(51, 51, 51));
		myTable.setFocusable(false);
		add(myTable, BorderLayout.CENTER);

		// set player icon, blocked, room names
		myRenderer = new Renderer();
		fillTable();
		

	}

	/**
	 * Uses a custom renderer class to individually fill each cell with the
	 * corresponding icons/names
	 */
	public void fillTable() {

		for (int i = 0; i < myMaze.getCols(); i++) {
			myTable.getColumnModel().getColumn(i).setCellRenderer(myRenderer);
		}
	}
	
	

	// inner class that creates a custom implementation of AbstractTableModel
	// for the maze
	public class MazeModel extends AbstractTableModel {

		/** Serial Version ID */
		private static final long serialVersionUID = -7659261425928249389L;

		private Object[][] myData = myMatrix;

		/**
		 *
		 */
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return myData.length;
		}

		/**
		 *
		 */
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return myData[0].length;
		}

		/**
		 *
		 */
		@Override
		public Object getValueAt(final int rowIndex, final int columnIndex) {
			return myData[rowIndex][columnIndex];
		}

		/**
		 *
		 */
		@Override
		public void setValueAt(final Object theRoom, final int rowIndex,
				final int columnIndex) {
			myData[rowIndex][columnIndex] = theRoom;
			fireTableCellUpdated(rowIndex, columnIndex);
		}

		/**
		 * @param theUpdate
		 */
		public void refresh(final Object[][] theUpdate) {
		        final int oldRows = getRowCount();
			myData = theUpdate;
			fireTableDataChanged();
			if (getRowCount() - oldRows != 0) {
                                fireTableStructureChanged();
                        }
			
		}

	}

	/*
	 * Inner class that creates a JLabel with a room name or icon to render onto
	 * every JTable cell
	 */
	class Renderer extends DefaultTableCellRenderer {

		/**
		         * 
		         */
		private static final long serialVersionUID = -2696460913971414868L;
		
		private static final int OFFSET = 25;

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

		private final ImageIcon WIN = new ImageIcon(
				"./src/images/other/pokestar.png");
		
		//private final Color LIGHT_GREEN = new Color(138,255,167); //highlighter green
		/*
		 * Color used to denote visited rooms
		 */
		private final Color MY_GREEN = new Color(22,208,68); 

		/**
		 * Constructor
		 * 
		 * @param Maze to get an updated maze to fill table with
		 */
		public Renderer() {
			super();
		}

		@Override
		public Component getTableCellRendererComponent(final JTable table,
				final Object value, final boolean isSelected,
				final boolean hasFocus, final int row, final int column) {

			final JLabel lbl = new JLabel(); // label put in cells
			final Room r = myMatrix[row][column];
			final int iconSize = getRowSize() - OFFSET;

			if (r.isPlayerHere()) { // player at this cell put player icon
				final ImageIcon scaled = new ImageIcon(PLAYER.getImage()
						.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
				lbl.setIcon(scaled);
				// same process for blocked rooms put an else if here once we
				// have a data structure for it
			} else if (!r.canEnter()) { // blocked room icon
				final ImageIcon scaled = new ImageIcon(TREE.getImage()
						.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
				lbl.setIcon(scaled);
			}  else { //win icon, blocked or normal room name
				final int[] winpos = myMaze.getWinLocation();
				
				if (r == myMatrix[winpos[0]][winpos[1]]) { // winning location
															// icon
					final ImageIcon scaled = new ImageIcon(WIN.getImage()
							.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
					lbl.setIcon(scaled);
					// set visited color
				} else if (r.hasVisited()) {
	                                final String name = r.toString();
	                                lbl.setText(name);
	                                lbl.setForeground(MY_GREEN);
	                                //lbl.setForeground(Color.CYAN);
	                                lbl.setBackground(MAZE_BG);
	                                lbl.setFont(myFont);
	                        } else { // normal room name
					final String name = r.toString();
					lbl.setText(name);
					lbl.setForeground(Color.WHITE);
					lbl.setBackground(MAZE_BG);
					lbl.setFont(myFont);
				}
			}

			// format jlabel
			lbl.setVisible(true);
			lbl.setHorizontalAlignment(JLabel.CENTER);
			lbl.setVerticalAlignment(JLabel.CENTER);
			return lbl;

		}
	}

	public JTable getTable() {
		return myTable;
	}
	
	private int getRowSize() {
                return SIZE / myMatrix.length;
        }

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
                if ("model".equals(evt.getPropertyName())) {
                		//System.out.println("model called for refresh");
                        System.out.println("Model Changed");
                        final int oldRows = myMatrix.length;
                        myMatrix = (Room[][]) evt.getNewValue();
                        if (myMatrix.length - oldRows != 0) {
                                myTable.setRowHeight(getRowSize());
                                myFont = new Font("PKMN RBYGSC", Font.PLAIN, (int) (getRowSize() / FONT_SIZE));
                        }
                        myModel.refresh(myMatrix);
                        
                        
                        revalidate();
                }
                
        }
}
