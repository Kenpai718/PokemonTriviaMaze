/**
 * 
 */
package controller.menu_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * @author ajdow
 *
 */
@SuppressWarnings("serial")
public class ExitAction extends AbstractAction {
        public ExitAction() {
                putValue(NAME, "Exit");
                putValue(SHORT_DESCRIPTION, "Exits the Game");
        }
        
        @Override
        public void actionPerformed(final ActionEvent e) {
                System.exit(0);
        }
}
