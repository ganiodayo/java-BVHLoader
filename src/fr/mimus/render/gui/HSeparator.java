package fr.mimus.render.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import fr.mimus.render.Texture;

public class HSeparator extends GuiEvent {

	int width, height;
	public HSeparator(int x, int y, int w, int h) {
		super(x, y);
		this.width=w;
		this.height=h;
	}

	@Override
	public void render() {
		float ts = 12f/Texture.gui.getWidth();
		if(locked) glColor4f(1,1,1,.5f);
		Texture.gui.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(ts*3, ts*3);		glVertex2f(x-(width/3), y);
		glTexCoord2f(ts*4, ts*3);		glVertex2f(x+width-(width/3), y);
		glTexCoord2f(ts*4, ts*4);		glVertex2f(x+width-(width/3), y+height);
		glTexCoord2f(ts*3, ts*4);		glVertex2f(x-(width/3), y+height);
		glEnd();
		Texture.unbind();
		if(locked) glColor4f(1,1,1,1f);
	}

	@Override
	public void update() {}

	@Override
	public int getWidth() {
		return (width/3);
	}

	@Override
	public int getHeight() {
		return height;
	}

}
