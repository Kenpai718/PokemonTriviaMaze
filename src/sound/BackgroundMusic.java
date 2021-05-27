package sound;

import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.Clip;

public class BackgroundMusic {
	
	static Clip myClip;
	
	private BackgroundMusic() {
		
	}
	
	public static void loadMusic() {	   
		try {
	         // Open an audio input stream.
	    	 File soundFile = new File("Background.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
	         // Get a sound clip resource.
	         myClip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         myClip.open(audioIn);
	         FloatControl gainControl = (FloatControl) myClip.getControl(FloatControl.Type.MASTER_GAIN);
	         gainControl.setValue(-20.0f);
	         myClip.start();
	         myClip.loop(Clip.LOOP_CONTINUOUSLY);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	   }
	
	public static void stopMusic() {
		myClip.stop();
	}
}
