package view.Win_Lose;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * Makes a panel with an image on top and text on bottom. Makes sure it is
 * centered with border layout + grid bag layout.
 * 
 * @author Kenneth Ahrens
 *
 */
public class PanePanelMaker extends JPanel {

	/**
	 * 
	 * @param theIcon
	 * @param theText
	 */
	public PanePanelMaker(ImageIcon theIcon, String theText) {
		this.setLayout(new GridBagLayout()); // centers everything on this panel
		JPanel container = new JPanel(); // panel that contains the image and
											// text

		// put image and text on a dummy panel
		JPanel imgTextPanel = new JPanel();
		imgTextPanel.setLayout(new BorderLayout(0, 0));
		JLabel imgLabel = new JLabel("");
		imgLabel.setIcon(theIcon);
		imgTextPanel.add(imgLabel, BorderLayout.CENTER);
		
		//format text
		JLabel txtLabel = new JLabel(theText);
		txtLabel.setHorizontalAlignment(JLabel.CENTER);
		txtLabel.setVerticalAlignment(JLabel.CENTER);
		imgTextPanel.add(txtLabel, BorderLayout.SOUTH);


		// put imgPanel onto this panel
		this.add(imgTextPanel);
	}

}
