package fr.mimus.render.gui;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.mimus.render.Texture;
import fr.mimus.utils.Input;

public class ImageButton extends GuiEvent {
	Texture image;
	int width, height;
	private float etat = 0f;
	private int offset = 0;
	public ImageButton(int x, int y, int w, int h, Texture tex) {
		super(x, y);
		this.width=w;
		this.height=h;
		this.image=tex;
	}

	@Override
	public void render() {
		float ts = 12f/Texture.gui.getWidth();
		if(locked) glColor4f(1,1,1,.5f);
		image.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);			glVertex2f(x+2-offset, y+2-offset);
		glTexCoord2f(1, 0);			glVertex2f(x+width-2-offset, y+2-offset);
		glTexCoord2f(1, 1);			glVertex2f(x+width-2-offset, y+height-2-offset);
		glTexCoord2f(0, 1);			glVertex2f(x+2-offset, y+height-2-offset);
		glEnd();
		
		Texture.gui.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0+etat, ts*3);		glVertex2f(x, y);
		glTexCoord2f(ts+etat, ts*3);	glVertex2f(x+width, y);
		glTexCoord2f(ts+etat, ts*4);	glVertex2f(x+width, y+height);
		glTexCoord2f(0+etat, ts*4);		glVertex2f(x, y+height);
		glEnd();
		Texture.unbind();
		if(locked) glColor4f(1,1,1,1f);
	}

	@Override
	public void update() {
		if(locked) return;
		float ts = 12f/Texture.gui.getWidth();
		etat = 0f;
		int mx = Mouse.getX(); 
		int my = Display.getHeight() - Mouse.getY();
		offset=0;
		if(mx >= x
			&& mx < x+width
			&& my >= y
			&& my < y+height) {
			etat = ts;
			if(!this.isOver()) {
				this.setOver(true);
				if(this.listener != null) this.listener.EventOver(this);
			}
			if(Mouse.isButtonDown(0)){
				etat = ts*2;
				offset = 1;
			}
			if(Input.mouseClicked(0)){
				if(this.listener != null) this.listener.EventClickLeft(this);
			}
			if(Input.mouseClicked(1)){
				if(this.listener != null) this.listener.EventClickRight(this);
			}
		} else {
			if(this.isOver()) {
				this.setOver(false);
				if(this.listener != null) this.listener.EventOut(this);
			}
		}
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

}
