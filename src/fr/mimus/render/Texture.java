package fr.mimus.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
	public static Texture font = FileTexture("res/font/default.png", true);
	public static Texture gui = FileTexture("res/gui.png", true);
	public static Texture background = FileTexture("res/background.png", true);
	public static Texture select = FileTexture("res/select.png", true);
	
	private int index;
	private int width, height;
	private int[] pixels = null;	
	public static Texture FileTexture(String path, boolean nearest) {
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			return new Texture(image, nearest);
		} catch (IOException e) {
			System.err.println("Erreur TEXTURE: Impossible de charger la texture: "+path);
		}
		return new Texture(errorTexture(), nearest);
	}

	private static BufferedImage errorTexture() {
		BufferedImage buff = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buff.createGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, 64, 64);
			g.setColor(Color.pink);
			g.fillRect(0, 0, 32, 32);
			g.fillRect(32, 32, 64, 64);
		g.dispose();
		return buff;
	}
	
	public Texture(BufferedImage image, boolean nearest) {
		width=image.getWidth();
		height=image.getHeight();
		pixels = new int[width*height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		
		int[] data = new int[width*height];
		for(int i=0; i<data.length; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		int id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);

		if(nearest) {
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		} else {
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		}

		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		this.index=id;
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, index);
	}

	public static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void dispose() {
		glDeleteTextures(index);
	}
	
	public static void destroy() {
		font.dispose();
		gui.dispose();
		background.dispose();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
