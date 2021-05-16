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

public class MazeGUI extends JPanel implements PropertyChangeListener{

	/**
         * 
         */
        private static final long serialVersionUID = -5880241329362260869L;

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
	final Font PKMN_FONT = new Font("PKMN RBYGSC", Font.PLAIN, 40);
	
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
//		myDTB = new DefaultTableModel(myMaze.getRows(), myMaze.getCols());
		myTable = new JTable();
		myTable.setModel(new MazeModel());
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
	
	public class MazeModel extends AbstractTableModel {

	        /**
                 * 
                 */
                private static final long serialVersionUID = -7659261425928249389L;
                
                String col[];
//	        ArrayList<Room> myData;
                private Object[][] myData = myMatrix;
	        
//	        public CustomModel(final Room[][] theMatrix) {
//	                myData ;
////	                myData = (ArrayList<Room>) Arrays.stream(theMatrix)
////	                                .flatMap(Arrays::stream)
////	                                .collect(Collectors.toList());
//	        }
	        
                @Override
                public int getRowCount() {
                        // TODO Auto-generated method stub
                        return myData.length;
                }

                @Override
                public int getColumnCount() {
                        // TODO Auto-generated method stub
                        return myData[0].length;
                }

                @Override
                public Object getValueAt(final int rowIndex, final int columnIndex) {
                        return myData[rowIndex][columnIndex];
                }
                
                @Override
                public void setValueAt(final Object theRoom, final int rowIndex, final int columnIndex) {
                    myData[rowIndex][columnIndex] = theRoom;
                    fireTableCellUpdated(rowIndex, columnIndex);
                }
                
                public void refresh(final Object[][] theUpdate) {
                        myData = theUpdate;
                        fireTableDataChanged();
                }
	        
	}

	/*
	 * Inner class that creates a JLabel with a room name or icon to render onto every JTable cell
	 */
	class Renderer extends DefaultTableCellRenderer {
		
		/**
                 * 
                 */
                private static final long serialVersionUID = -2696460913971414868L;

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

        @Override
        public void propertyChange(final PropertyChangeEvent theEvt) {
                // TODO Auto-generated method stub
                if ("newpos".equals(theEvt.getPropertyName())) {
                      System.out.println("PropertyChange Called");
                      myTable.invalidate();
                      
              }
        }

        public JTable getTable() {
                return myTable;
        }
}
