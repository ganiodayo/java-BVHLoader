package fr.mimus.render.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.mimus.render.Font;
import fr.mimus.render.Texture;
import fr.mimus.utils.Input;

public class Button extends GuiEvent {
	private String text;
	private int size; 
	private int bsize;
	
	private float etat = 0f;
	private int textOff = 0;
	
	public Button(String text, int x, int y, int size, int bsize) {
		super(x, y);
		this.text=text;
		this.size=size;
		this.bsize=bsize;
	}
	
	public void update(){
		if(locked) return;
		float ts = 12f/Texture.gui.getWidth();
		int csize = size*12/8;
		etat = 0f;
		
		int mx = Mouse.getX(); 
		int my = Display.getHeight() - Mouse.getY();
		
		textOff = 0;
		if(mx >= x
			&& mx < x+bsize
			&& my >= y
			&& my < y+csize) {
			etat = ts;
			if(!this.isOver()) {
				this.setOver(true);
				if(this.listener != null) this.listener.EventOver(this);
			}
			if(Mouse.isButtonDown(0)){
				etat = ts*2;
				textOff = 1;
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
	
	public void render() {
		float ts = 12f/Texture.gui.getWidth();
		int csize = size*12/8;
		int tsize = text.length()*size;
		//int bsize = text.length()*csize+csize*2;
		if(locked) glColor4f(1,1,1,.5f);
		Font.drawString(text, x+(bsize/2-tsize/2)+textOff, y+(csize/2-size/2)+textOff, size);
		
		Texture.gui.bind();
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, etat);			glVertex2f(x, y);
		glTexCoord2f(ts, etat);			glVertex2f(x+csize, y);
		glTexCoord2f(ts, etat+ts);		glVertex2f(x+csize, y+csize);
		glTexCoord2f(0, etat+ts);		glVertex2f(x, y+csize);
		
		for(int i=0; i<bsize-csize*2; i+=csize) {
			glTexCoord2f(ts, etat);			glVertex2f(x+i+csize, y);
			glTexCoord2f(ts*2, etat);		glVertex2f(x+i+csize*2, y);
			glTexCoord2f(ts*2, etat+ts);	glVertex2f(x+i+csize*2, y+csize);
			glTexCoord2f(ts, etat+ts);		glVertex2f(x+i+csize, y+csize);
		}
		
		glTexCoord2f(ts*2, etat);		glVertex2f(x+bsize-csize, y);
		glTexCoord2f(ts*3, etat);		glVertex2f(x+bsize, y);
		glTexCoord2f(ts*3, etat+ts);	glVertex2f(x+bsize, y+csize);
		glTexCoord2f(ts*2, etat+ts);	glVertex2f(x+bsize-csize, y+csize);
		
		glEnd();
		Texture.unbind();
		if(locked) glColor4f(1,1,1,1f);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getHeight() {
		return size*12/8;
	}
	
	public int getWidth() {
		return bsize;
	}

	public void setWidth(int bsize) {
		this.bsize = bsize;
	}
}
