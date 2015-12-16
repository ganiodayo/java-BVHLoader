package fr.mimus.utils;

public class Vertex {
	
	private Vector3f position;
	private Color4f color;
	private Vector2f textureUV;
	
	public Vertex(Vector3f p, Color4f c, Vector2f uv) {
		position=p;
		color=c;
		textureUV = uv;
	}

	public Vector2f getTextureUV() {
		return textureUV;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Color4f getColor() {
		return color;
	}
}
