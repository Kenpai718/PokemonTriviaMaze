/**
 * 
 */
package controller.movement_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import model.Maze;
import view.PokemonPanel;
import view.viewHelper.MazeGUI.MazeModel;

/**
 * @author ajdow
 *
 */
public class MovementAction extends AbstractAction {

        
        protected Maze myMaze = Maze.getInstance();
        private final PokemonPanel myPanel;
        
        protected MovementAction(final String theName, final ImageIcon theIcon, final PokemonPanel thePanel) {
                super(theName, theIcon);
                myPanel = thePanel;
        }
        
        @Override
        public void actionPerformed(final ActionEvent e) {
                // TODO Auto-generated method stub
                // Do nothing
        }
        
        protected void movePlayer(final int[] theMove) {
                final int[] newPos = myMaze.getPlayerLocation().clone();
                for (int i = 0; i < newPos.length; i++) {
                        newPos[i] = newPos[i] + theMove[i];
                }
                myMaze.setPlayerLocation(newPos);
                final MazeModel model = (MazeModel) myPanel.getTable().getModel();
//                firePropertyChange("newpos", null, null);
                model.refresh(myMaze.getMatrix());
                firePropertyChange("newpos", null, null);
                myPanel.setImage();
                myPanel.getQustionGUI().setButtons();
                
        }

}
