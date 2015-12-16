package fr.mimus.utils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {
	private static boolean mouses[] = new boolean[Mouse.getButtonCount()];
	private static boolean lastMouses[] = new boolean[Mouse.getButtonCount()];


	private static char charKeys[] = null;
	private static boolean keys[] = new boolean[255];
	private static boolean lastKeys[] = new boolean[255];

	private static char currentKey = 0;

	public static void update() {
		if(charKeys == null) {
			charKeys = new char[255];
			while(Keyboard.next()) {
				charKeys[Keyboard.getEventKey()] = Keyboard.getEventCharacter();
			}
		}
		for(int i=0; i<mouses.length; i++) {
			lastMouses[i] = mouses[i];
			mouses[i] = Mouse.isButtonDown(i);
		}
		
		for(int i=0; i<keys.length; i++) {
			lastKeys[i] = keys[i];
			keys[i] = Keyboard.isKeyDown(i);
			if(keys[i]) currentKey = charKeys[i];
		}
	}

	public static char currentKey() {
		return currentKey;
	}
	
	public static boolean keyClicked(int i) {
		if(!keys[i] && lastKeys[i]) {
			//lastKeys[i] = false;
			return true;
		}
		return false;
	}

	public static boolean mouseClicked(int i) {
		if(!mouses[i] && lastMouses[i]) {
			//lastMouses[i] = false;
			return true;
		}
		return false;
	}

	public static boolean mouseDown(int i) {
		return mouses[i];
	}

	public static boolean keyDown(int i) {
		return keys[i];
	}
}
