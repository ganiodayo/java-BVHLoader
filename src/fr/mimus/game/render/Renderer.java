package fr.mimus.game.render;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glReadPixels;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import fr.mimus.render.Texture;

public class Renderer {	
	public static Texture front = Texture.FileTexture("res/cubemap/front.png", true);
	public static Texture back = Texture.FileTexture("res/cubemap/back.png", true);
	public static Texture top = Texture.FileTexture("res/cubemap/top.png", true);
	public static Texture bottom = Texture.FileTexture("res/cubemap/bottom.png", true);
	public static Texture left = Texture.FileTexture("res/cubemap/left.png", true);
	public static Texture right = Texture.FileTexture("res/cubemap/right.png", true);
	public static void cubeMap(float x, float y, float z) {
		//System.out.println(x+","+y+","+z);
		glDisable(GL_DEPTH_TEST);
		top.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 1);		 glVertex3f(x-.5f, y+.5f, z-.5f);
		glTexCoord2f(1, 1);		 glVertex3f(x+.5f, y+.5f, z-.5f);
		glTexCoord2f(1, 0);		 glVertex3f(x+.5f, y+.5f, z+.5f);
		glTexCoord2f(0, 0);		 glVertex3f(x-.5f, y+.5f, z+.5f);
		glEnd();

		bottom.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 1);		 glVertex3f(x+.5f, y-.5f, z-.5f);
		glTexCoord2f(1, 1);		 glVertex3f(x-.5f, y-.5f, z-.5f);
		glTexCoord2f(1, 0);		 glVertex3f(x-.5f, y-.5f, z+.5f);
		glTexCoord2f(0, 0);		 glVertex3f(x+.5f, y-.5f, z+.5f);
		glEnd();

		front.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 1);		 glVertex3f(x-.5f, y-.5f, z-.5f);
		glTexCoord2f(1, 1);		 glVertex3f(x+.5f, y-.5f, z-.5f);
		glTexCoord2f(1, 0);		 glVertex3f(x+.5f, y+.5f, z-.5f);
		glTexCoord2f(0, 0);		 glVertex3f(x-.5f, y+.5f, z-.5f);
		glEnd();

		back.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 1);		 glVertex3f(x+.5f, y-.5f, z+.5f);
		glTexCoord2f(1, 1);		 glVertex3f(x-.5f, y-.5f, z+.5f);
		glTexCoord2f(1, 0);		 glVertex3f(x-.5f, y+.5f, z+.5f);
		glTexCoord2f(0, 0);		 glVertex3f(x+.5f, y+.5f, z+.5f);
		glEnd();

		left.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 1);		 glVertex3f(x-.5f, y-.5f, z+.5f);
		glTexCoord2f(1, 1);		 glVertex3f(x-.5f, y-.5f, z-.5f);
		glTexCoord2f(1, 0);		 glVertex3f(x-.5f, y+.5f, z-.5f);
		glTexCoord2f(0, 0);		 glVertex3f(x-.5f, y+.5f, z+.5f);
		glEnd();

		right.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 1);		 glVertex3f(x+.5f, y-.5f, z-.5f);
		glTexCoord2f(1, 1);		 glVertex3f(x+.5f, y-.5f, z+.5f);
		glTexCoord2f(1, 0);		 glVertex3f(x+.5f, y+.5f, z+.5f);
		glTexCoord2f(0, 0);		 glVertex3f(x+.5f, y+.5f, z-.5f);
		glEnd();
		Texture.unbind();
		glEnable(GL_DEPTH_TEST);
	}
	
	public static void screenshot() {
		glReadBuffer(GL_FRONT);
		int width = Display.getWidth();
		int height = Display.getHeight();
		ByteBuffer buff = BufferUtils.createByteBuffer(width * height * 4);
		glReadPixels(0,0,width,height, GL_RGBA, GL_UNSIGNED_BYTE, buff);
		
		BufferedImage screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				int i = (x + (y*width)) * 4;
				int r = buff.get(i) & 0xff;
				int g = buff.get(i + 1) & 0xff;
				int b = buff.get(i + 2) & 0xff;
				int a = buff.get(i + 3) & 0xff;
				screen.setRGB(x, height - (y+1), (a << 24) | (r << 16) | (g << 8) | b);
			}
		}
		int id = (new Random()).nextInt(Integer.MAX_VALUE);
		File file = new File("screen_"+id+".png");
		try {
			ImageIO.write(screen, "png", file);
		} catch (IOException e) {}
	}
}
