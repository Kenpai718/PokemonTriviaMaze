package view.viewHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Maze;
import model.Room;

public class MazeGUI extends JPanel {
    private final JTable table;
    
    private final Maze myMaze;

    /**
     * Create the panel.
     */
    public MazeGUI() {
        myMaze = new Maze();
        final Room[][] mazeMatrix = myMaze.getMatrix();
        
        setMaximumSize(new Dimension(500, 500));
        setBackground(new Color(255, 0, 0));
        setPreferredSize(new Dimension(500, 500));
        setLayout(new BorderLayout(0, 0));
        
        table = new JTable();
        table.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 20));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(51, 153, 204));
        table.setRowSelectionAllowed(false);
        table.setRowHeight(125);
        table.setModel(new DefaultTableModel(mazeMatrix,
            new String[] {
                "", "", "", ""
            }
        ));
        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table.setBackground(new Color(51, 51, 51));
        add(table, BorderLayout.CENTER);

    }

}
