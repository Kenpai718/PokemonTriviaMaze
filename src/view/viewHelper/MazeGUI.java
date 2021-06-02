package view.viewHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
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
	 * Constants
	 */
	private static final long serialVersionUID = -5880241329362260869L;
	private static final int SIZE = 500;
	private static final Dimension DEFAULT_DIM = new Dimension(SIZE, SIZE);
	private static final double FONT_SIZE = 3.125;
	private final String myGrassPath = "./src/images/maze_gui/maze_grass_bg.png";
	private final Color TRANSPARENT = new Color(1f, 0f, 0f, .5f);

	/*
	 * Color used for the maze background
	 */
	// final Color MAZE_BG = new Color(51, 51, 51);
	final Color MAZE_BG = new Color(112, 200, 160);

	/*
	 * Color used for the font
	 */
	final Color FONT_COLOR = Color.WHITE;

	/*
	 * Table used for GUI visual
	 */
	private final JTable myTable;

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

	/*
	 * Model of the maze visual
	 */
	private final MazeModel myModel;

	/*
	 * Font used/size
	 */
	private Font myFont;

	/*
	 * Size of the table visual
	 */
	private final int myRowSize;

	/*
	 * background of this jpanel
	 */
	private final BufferedImage myGrassBG;

	/*
	 * Background color of border: dark green
	 */
	private final Color GRID_COLOR = new Color(0, 100, 0);

	/*
	 * Border color
	 */
	private final Color BORDER_COLOR = Color.black;

	/*
	 * Border of jpanel
	 */
	final Border BORDER = BorderFactory.createLineBorder(BORDER_COLOR, 5);

	/**
	 * Constructor that create the maze visual
	 */

	public MazeGUI() {
		// initialize fields
		myMaze = Maze.getInstance();
		myMatrix = myMaze.getMatrix();
		myRowSize = SIZE / myMaze.getRows();
		myFont = new Font("PKMN RBYGSC", Font.PLAIN,
				(int) (myRowSize / FONT_SIZE));
		myGrassBG = readImage(myGrassPath);
		myTable = new JTable();
		myModel = new MazeModel();
		myRenderer = new Renderer();
		
		//setup visuals
		prepareGUI();
		setupTable();


	}
	
	/*
	 * Setup the gui for this jpanel
	 */
	private void prepareGUI() {
		setMaximumSize(DEFAULT_DIM);
		setPreferredSize(DEFAULT_DIM);
		setLayout(new BorderLayout(0, 0));
		// set background transparent to draw a new background
		setOpaque(false); // background of parent will be painted first
		setBackground(TRANSPARENT);
		this.setBorder(BORDER);
		repaint();
	
	}
	
	/*
	 * Setup jtable visual of the maze
	 * Using Custom TableModel to correctly refresh the table when changes
	 * are made
	 */
	private void setupTable() {
		myTable.setModel(myModel);
		myTable.setFont(myFont);
		myTable.setForeground(FONT_COLOR);
		myTable.setGridColor(GRID_COLOR);
		myTable.setRowSelectionAllowed(false);
		myTable.setRowHeight(myRowSize);
		myTable.setPreferredScrollableViewportSize(DEFAULT_DIM);
		myTable.setOpaque(false);
		myTable.setBackground(TRANSPARENT);
		myTable.setFocusable(false);
		add(myTable, BorderLayout.CENTER);

		// set player icon, blocked, room names
		fillTable();
	}

	/**
	 * Helper method to read an Image given a filepath
	 * 
	 * @param String theLocation filepath
	 * @return BufferedImage the new image
	 */
	public BufferedImage readImage(final String theLocation) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(theLocation));
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return img;
	}

	/**
	 * Paints the grass background
	 */
	@Override
	protected void paintComponent(final Graphics theG) {
		super.paintComponent(theG);
		theG.drawImage(myGrassBG, 0, 0, SIZE, SIZE, this);

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
			// Get row count before change
			final int oldRows = this.getRowCount();
			myData = theUpdate;
			// Check if the row count has changed and update the respective
			// fields
			if (this.getRowCount() - oldRows != 0) {
				myFont = new Font("PKMN RBYGSC", Font.PLAIN,
						(int) (getRowSize() / FONT_SIZE));
				fireTableStructureChanged();
				myTable.setRowHeight(getRowSize());
				// Refills the table with the updated font size
				fillTable();
			}
			fireTableDataChanged();

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
				"./src/images/maze_gui/PlayerIcon.png");

		/*
		 * Icon that represents the player
		 */
		private final ImageIcon GRASS = new ImageIcon(
				"./src/images/maze_gui/grass_icon.png");

		/*
		 * Icon that represents a blocked room
		 */
		private final ImageIcon TREE = new ImageIcon(
				"./src/images/maze_gui/tree.png");

		/*
		 * Icon to represent the end goal
		 */
		private final ImageIcon WIN = new ImageIcon(
				"./src/images/maze_gui/pokestar.png");

		/*
		 * Icon to represent visited room
		 */
		private final ImageIcon POKEBALL = new ImageIcon(
				"./src/images/maze_gui/visited_icon.png");

		/**
		 * Constructor
		 * 
		 * @param Maze to get an updated maze to fill table with
		 */
		public Renderer() {
			super();
		}


		/**
		 * Draws the icons for each cell of the maze
		 */
		@Override
		public Component getTableCellRendererComponent(final JTable table,
				final Object value, final boolean isSelected,
				final boolean hasFocus, final int row, final int column) {

			JLabel lbl = new JLabel(); // label put in cells
			final Room r = myMatrix[row][column];
			final int iconSize = getRowSize() - OFFSET;
			final int grassSize = iconSize + 10;
			final int visitedSize = iconSize / 2;

			if (r.isPlayerHere()) { // player at this cell put player icon
				lbl = makeImageLabel(PLAYER, iconSize);
			} else if (!r.canEnter()) { // blocked room icon
				lbl = makeImageLabel(TREE, iconSize);
			} else { // win icon, blocked or normal room name
				final int[] winpos = myMaze.getWinLocation();

				if (r == myMatrix[winpos[0]][winpos[1]]) {
					// winning location icon
					lbl = makeImageLabel(WIN, iconSize);
				} else if (r.hasVisited()) { // pokeball for unvisited rooms
					lbl = makeImageLabel(POKEBALL, visitedSize);
				} else { // tall grass for unvisited rooms
					lbl = makeImageLabel(GRASS, grassSize);
				}
			}

			return lbl;

		}
	}

	/**
	 * Make a label and put an image on it and scale it
	 * 
	 * @param ImageIcon theImg
	 * @param ImageIcon theSize
	 * @return formamted label with an image on it
	 */
	private JLabel makeImageLabel(final ImageIcon theImg, final int theSize) {
		final JLabel lbl = new JLabel(); // label put in cells
		final ImageIcon scaled = new ImageIcon(theImg.getImage()
				.getScaledInstance(theSize, theSize, Image.SCALE_DEFAULT));
		lbl.setIcon(scaled);
		lbl.setVisible(true);
		lbl.setHorizontalAlignment(JLabel.CENTER);
		lbl.setVerticalAlignment(JLabel.CENTER);

		return lbl;
	}

	/**
	 * Make a label for the room name
	 * 
	 * @param String theName
	 * @param Color  theColor
	 * @param int    theSize
	 */
	private JLabel makeTextLabel(final String theName, final Color theColor) {
		final JLabel lbl = new JLabel(); // label put in cells
		final String name = theName;
		lbl.setText(name);
		lbl.setForeground(theColor);
		lbl.setBackground(TRANSPARENT);
		lbl.setFont(myFont);
		lbl.setVisible(true);
		lbl.setHorizontalAlignment(JLabel.CENTER);
		lbl.setVerticalAlignment(JLabel.CENTER);

		return lbl;
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
			// System.out.println("model called for refresh");
			// System.out.println("Model Changed");
			myMaze.setMatrix((Room[][]) evt.getNewValue());
			myMatrix = myMaze.getMatrix();
			myModel.refresh(myMatrix);
			// if (myMatrix.length - oldRows != 0) {
			// myTable.setRowHeight(getRowSize());
			// }

			revalidate();
		}

	}
}
