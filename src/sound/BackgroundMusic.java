package sound;

import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.*;
import javax.sound.sampled.Clip;


/**
 * Music playlist and player
 * Expected to use .wav files
 * 
 * @author Katlyn Malone
 * @author Kenneth Ahrens
 *
 */
public class BackgroundMusic {

	/*
	 * List of songs
	 */
	private final ArrayList<String> mySongList;

	/*
	 * Default song used
	 */
	public final String DEFAULT_SONG = "Pokemon Theme Song.wav";
	
	/*
	 * File path where songs are stored
	 */
	private final String MUSIC_FOLDER = "./music/";

	/*
	 * clip used to play music
	 */
	private Clip myClip;
	
	/*
	 * Singleton music player
	 */
	public static BackgroundMusic mySinglePlayer = null;
	
	private BackgroundMusic() {
		mySongList = new ArrayList<String>();
		fillSongList();
		loadDefaults();
	}
	
	/**
	 * Fill song list based on what songs are in the music folder
	 */
	private void fillSongList() {
		File folder = new File(MUSIC_FOLDER);
		for (final File fileEntry : folder.listFiles()) {
			String fileName = fileEntry.getName();
			//only add .wav songs
			if(fileName.endsWith(".wav")) {
				mySongList.add(fileName);
			}
			

		}
	}
	
	/**
	 * Singleton background music instantiation
	 * 
	 * @return BackgroundMusic
	 */
	public static BackgroundMusic getInstance() {
		if (mySinglePlayer == null) {
			mySinglePlayer = new BackgroundMusic();
		}
		return mySinglePlayer;
	}

	/**
	 * Set the song clip
	 * @param theSongName
	 * @return
	 */
	private void setMusic(String theSongName) {
		Clip clip = null;
		try {
			// Open an audio input stream.
			String filePath = "./music/" + theSongName;
			File soundFile = new File(filePath);
			AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(soundFile);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-20.0f);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		if(clip != null) {
			myClip = clip;
		}
	}
	
	/**
	 * Loads a new song to player
	 * 
	 * @param theNewSong to load in to clip
	 */
	public void loadNewSong(final String theNewSong) {
		stopMusic();
		setMusic(theNewSong);
		playMusic();
	}
	
	/**
	 * Load default song
	 */
	public void loadDefaults() {
		setMusic(DEFAULT_SONG);
		
	}

	/**
	 * Play the song
	 */
	public void playMusic() {
		if (myClip == null) {
			setMusic(DEFAULT_SONG);
		}

		myClip.start();
		myClip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * Stop the song
	 */
	public void stopMusic() {
		if (myClip != null) {
			myClip.stop();
		}
	}
	
	/**
	 * Get the song list
	 * @return songlist
	 */
	public ArrayList<String> getSongList() {
		return (ArrayList<String>) mySongList.clone();
	}
}
