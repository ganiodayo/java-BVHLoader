package fr.mimus.render.gui;

import org.lwjgl.opengl.Display;

public class GuiUtils {
	
	public static int getCenterX() {
		return Display.getWidth()/2;
	}

	public static int getCenterY() {
		return Display.getHeight()/2;
	}
	
	public static void centerX(GuiEvent e) {
		int x = Display.getWidth()/2 - e.getWidth()/2;
		e.setX(x);
	}
	
	public static void centerY(GuiEvent e) {
		int y = Display.getHeight()/2 - e.getHeight()/2;
		e.setY(y);
	}
	
	public static void centerXY(GuiEvent e) {
		centerY(e);
		centerX(e);
	}
	
	public static void alignY(int x, int y, int space, GuiEvent... events) {
		int offset = 0;
		for(int i=0; i<events.length; i++) {
			events[i].setY(y + offset);
			events[i].setX(x);
			offset += events[i].getHeight() + space;
		}
	}
	
	public static void alignYInversed(int x, int y, int space, GuiEvent... events) {
		int offset = 0;
		for(int i=0; i<events.length; i++) {
			offset += events[i].getHeight();
			events[i].setY(y-offset);
			events[i].setX(x);
			offset += space;
		}
	}
	
	public static void alignX(int x, int y, int space, GuiEvent... events) {
		int offset = 0;
		for(int i=0; i<events.length; i++) {
			events[i].setY(y);
			events[i].setX(x+offset);
			offset += events[i].getWidth() + space;
		}
	}
	
	public static void alignXInversed(int x, int y, int space, GuiEvent... events) {
		int offset = 0;
		for(int i=0; i<events.length; i++) {
			offset += events[i].getWidth();
			events[i].setY(y);
			events[i].setX(x-offset);
			offset += space;
		}
	}
	
	public static void alignCenterY(int space, GuiEvent... events) {
		int offset = 0;
		int height = 0;
		for(int i=0; i<events.length; i++) {
			height += events[i].getHeight() + space;
		}
		height /= 2;
		int y = getCenterY() - height;
		for(int i=0; i<events.length; i++) {
			centerX(events[i]);
			events[i].setY(y + offset);
			offset += events[i].getHeight() + space;
		}
	}
	
	public static void alignCenterX(int space, GuiEvent... events) {
		int offset = 0;
		int width = 0;
		for(int i=0; i<events.length; i++) {
			width += events[i].getWidth() + space;
		}
		width /= 2;
		int x = getCenterX() - width;
		for(int i=0; i<events.length; i++) {
			centerY(events[i]);
			events[i].setX(x + offset);
			offset += events[i].getWidth() + space;
		}
	}
	
}
