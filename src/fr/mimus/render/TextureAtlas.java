package fr.mimus.render;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import fr.mimus.utils.file.filter.FileImageFilter;

public class TextureAtlas {
	private static TextureAtlas instance;
	public static TextureAtlas getInstance() {
		return instance;
	}
	private static final int MAX_SIZE_X = 16;
	
	private Texture texture;
	int sizeY;
	private int width, height;
	
	Map<String, Integer> texList = new HashMap<String, Integer>();
	BufferedImage images[];
	public TextureAtlas(String path, boolean nearest) throws Exception {
		instance = this;
		File file = new File(path);
		if(!file.isDirectory()) {
			throw new Exception("TextureAtlas error, no directory!");
		}
		String list[] = file.list(new FileImageFilter());
		images = new BufferedImage[list.length];
		for(int i = 0; i<list.length; i++) {
			texList.put(list[i].split("\\.")[0], i);
			images[i] = ImageIO.read(new FileInputStream(path+list[i]));
		}
		sizeY = list.length/MAX_SIZE_X+1;
		
		width=MAX_SIZE_X * 64;
		height=sizeY * 64;
		BufferedImage atlas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = atlas.createGraphics();
		for(int i = 0; i<list.length; i++) {
			int x = i%MAX_SIZE_X;
			int y = i/MAX_SIZE_X;
			g.drawImage(images[i], x*64, y*64, 64, 64, null);
		}
		g.dispose();
		texture = new Texture(atlas, true);
	}
	
	public int getIndex(String key) {
		if(texList.get(key) == null) return 0;
		return texList.get(key);
	}
	
	public void bind() {
		texture.bind();
	}
	
	public static void unbind() {
		Texture.unbind();
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public static int getSizeX() {
		return MAX_SIZE_X;
	}

	public int getSizeY() {
		return sizeY;
	}

	public float getTextureX() {
		return 1f/16f;
	}
	
	public float getTextureY() {
		return 1f/ (float) sizeY;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public void dispose() {
		texList.clear();
		texture.dispose();
	}

}
