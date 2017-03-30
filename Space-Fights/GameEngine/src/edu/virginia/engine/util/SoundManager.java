package edu.virginia.engine.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	private SoundManager soundManager;
	private static HashMap<String, AudioInputStream> effects;
	
	public SoundManager() {
		if(soundManager != null) {
			throw new java.lang.Error("A SoundManager instance already exists");
		}
		effects = new HashMap<String, AudioInputStream>();
	}
	
	public static void playSoundEffect(String filename) {
		try{
			String file = ("resources" + File.separator + filename);
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(file));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	}
	
	public static void playMusic(String filename) {
		try{
			String file = ("resources" + File.separator + filename);
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(file));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
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
