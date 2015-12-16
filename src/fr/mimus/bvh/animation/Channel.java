package fr.mimus.bvh.animation;

import java.util.ArrayList;
import java.util.List;

public class Channel {
	public static short Xposition = 0x01;
	public static short Yposition = 0x02;
	public static short Zposition = 0x04;
	public static short Zrotation = 0x10;
	public static short Xrotation = 0x20;
	public static short Yrotation = 0x40;
	
	short index;
	List<Float> frameValue = null;
	public Channel(short id) {
		index=id;
		frameValue = new ArrayList<Float>();
	}

}
