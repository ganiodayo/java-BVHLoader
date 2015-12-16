package fr.mimus.render;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import fr.mimus.utils.Color4f;
public class Window {
	private static boolean needClose = false;
	public static void create(String title, int width, int height) {
		create(title, width, height, true);
	}
	
	public static void create(String title, int width, int height, boolean resizable) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.setResizable(resizable);
			Display.create();
			
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_DEPTH_TEST);
			glEnable(GL_ALPHA_TEST);
			glAlphaFunc(GL_GREATER, 0);
			
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void enableFog(float density, float[] color) {

		//glEnable(GL_CULL_FACE);
		glEnable(GL_FOG);
		
		FloatBuffer fogColor = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(color).flip();
		
		glFogi(GL_FOG_MODE, GL_EXP);
		glFogf(GL_FOG_DENSITY, density);
		glFog(GL_FOG_COLOR, fogColor);
	}
	public static void setResizable(boolean r) {
		Display.setResizable(r);
	}
	
	public static boolean isRunning() {
		return !Display.isCloseRequested() && !needClose;
	}
	
	public static void closeRequest() {
		needClose=true;
	}
	
	public static void clearBuffer() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public static void clearColor(Color4f c) {
		glClearColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}
	
	public static void update() {
		Display.update();
	}
	
	public static void dispose() {
		Display.destroy();
	}
	
	public static void viewPerspective() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(70.0f, (float)Display.getWidth() / (float)Display.getHeight(), .01f, 1000.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public static void viewOrtho() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 0, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public static void reziseViewPort() {
		if(Display.wasResized()) {
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
	}
	
	public static void reziseViewPort(int x, int y, int width, int height) {
		if(Display.wasResized()) {
			glViewport(x, y, width, height);
		}
	}
	
}
