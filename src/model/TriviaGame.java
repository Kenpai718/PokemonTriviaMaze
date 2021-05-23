package model;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * Used to initialize a trivia maze game
 * 
 * @author ajdowney
 * @author ken
 */
public class TriviaGame extends JPanel{
	
	private final Color BG= new Color(220, 20, 60);
	ImageIcon PLAYER_M = new ImageIcon("./src/images/other/Player.png");
	ImageIcon PLAYER_F = new ImageIcon("./src/images/other/PlayerF.png");
	
	
	String[] GAMEMODES = new String[] {"Multiple Choice", "User Input"};

	
	Object[] AVATARS = { PLAYER_M,PLAYER_F };
	
	Integer[] AR = {1,2};
	
	String[] NAMES = new String[] {"Player M", "Player F"};
	


	/*
	 * Holds a map that stores info on all Pokemon
	 */
	private final Pokedex myPokedex;
	/*
	 * Maze that is played on
	 */
	private final Maze myMaze;
	
	

	/*
	 * Constructor
	 */
	public TriviaGame() {
		setBackground(BG);
		setPreferredSize(new Dimension(1920, 1080));
		
		final JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/src/images/other/WhoPKMNLogo.png"));
		
		final JButton btnNewButton = new JButton("Start Game");
		
		JComboBox gamemodeBox = new JComboBox(GAMEMODES);
		
		JComboBox playerBox = new JComboBox(AVATARS);

		

		
		JButton btnNewButton_1 = new JButton("Tutorial");
		
		JLabel lblNewLabel_1 = new JLabel("Choose Avatar");
		
		JLabel lblNewLabel_2 = new JLabel("Pick Difficulty");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(738)
					.addComponent(btnNewButton_1)
					.addGap(37)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(30)
							.addComponent(playerBox, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(36)
							.addComponent(gamemodeBox, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(44)
							.addComponent(lblNewLabel_1)
							.addGap(68)
							.addComponent(lblNewLabel_2)))
					.addContainerGap(528, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(547, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addGap(426))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(130)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton_1)
								.addComponent(gamemodeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(34)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_2))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(playerBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(479, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		myPokedex = Pokedex.getInstance();
		myMaze = Maze.getInstance();
	}
	
	

	/**
	 * Pokedex getter
	 * 
	 * @return Pokedex data of pokemon
	 */
	public Pokedex getPokedex() {
		return myPokedex;
	}

	/**
	 * Maze getter
	 * 
	 * @return Maze
	 */
	public Maze getMaze() {
		return myMaze;
	}
}
