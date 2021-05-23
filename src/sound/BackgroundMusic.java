package sound;

import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.Clip;

public class BackgroundMusic {
	
	public static void playMusic() {	   
		try {
	         // Open an audio input stream.
	    	 File soundFile = new File("Background.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
	         // Get a sound clip resource.
	         Clip clip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         clip.open(audioIn);
	         FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	         gainControl.setValue(-20.0f);
	         clip.start();
	         clip.loop(Clip.LOOP_CONTINUOUSLY);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	   }
}
