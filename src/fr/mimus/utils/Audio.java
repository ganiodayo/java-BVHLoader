package fr.mimus.utils;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {
	public static Audio walk = new Audio("res/Walk.wav");

	Clip clip;
	FloatControl gainControl;
	String path;

	public Audio(Audio a) {
		this.clip=a.clip;
		this.gainControl=a.gainControl;
		this.path=a.path;
	}

	public Audio(String p) {
		path=p;
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(p));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			setGain(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Audio setGain(float v) {
		if(v>gainControl.getMaximum()) v = gainControl.getMaximum();
		gainControl.setValue(v);

		return this;
	}

	public Audio play() {
		clip.setFramePosition(0);
		clip.start(); 
		return this;
	}

	public Audio loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		return this;
	}

	public Audio stop() {
		clip.stop();
		return this;
	}
}
