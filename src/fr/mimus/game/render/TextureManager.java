package fr.mimus.game.render;

import java.util.HashMap;

import fr.mimus.render.Texture;

public class TextureManager {
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture getTexture(String name) {
		return textures.get(name);
	}
	
	public static void addTexture(String name, String path) {
		textures.put(name, Texture.FileTexture(path, true));
	}

	public static void dispose() {
		for(Texture tex : textures.values()) {
			tex.dispose();
		}
		textures.clear();
	}
}
