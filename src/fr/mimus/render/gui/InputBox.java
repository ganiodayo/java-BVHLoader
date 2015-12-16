package fr.mimus.render.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.mimus.render.Font;
import fr.mimus.render.Texture;
import fr.mimus.utils.Input;

public class InputBox extends GuiEvent {

	private String text = "";
	private int size; 
	private int bsize;
	
	private float etat = 0f;
	private boolean focus = false;
	private int lenghtMax = 0;
	
	private String chars = Font.chars;
	
	public InputBox(int x, int y, int size, int bsize) {
		super(x, y);
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
		if(focus) {
			etat=ts;
			while(Keyboard.next()) {
				//int keyID = Keyboard.getEventKey();
				char keyChar = Keyboard.getEventCharacter();
				if(Input.keyClicked(Keyboard.KEY_BACK)) {
					if(text.length()>0) {
						text = text.substring(0, text.length()-1);
					}
				} else {
					if(chars.indexOf(keyChar) >= 0){
						if(lenghtMax > 0) {
							if(text.length() < lenghtMax) {
								text += keyChar;
							}
						} else {
							text += keyChar;
						}
						
					}
				}
				//charKeys[Keyboard.getEventKey()] = Keyboard.getEventCharacter();
			}
		}
		if(mx >= x
			&& mx < x+bsize
			&& my >= y
			&& my < y+csize) {
		
			if(!this.isOver()) {
				this.setOver(true);
				if(this.listener != null) this.listener.EventOver(this);
			}
			if(Input.mouseClicked(0)){
				focus = true;
				if(this.listener != null) this.listener.EventClickLeft(this);
			}
			if(Input.mouseClicked(1)){
				if(this.listener != null) this.listener.EventClickRight(this);
			}
		} else {
			if(focus) {
				if(Input.mouseDown(0) || Input.mouseDown(1) || Input.mouseDown(2)) {
					focus = false;
				}
			} else {
				if(this.isOver()) {
					focus = false;
					this.setOver(false);
					if(this.listener != null) this.listener.EventOut(this);
				}
			}
		}
	}
	
	public void render() {
		float ts = 12f/Texture.gui.getWidth();
		int csize = size*12/8;
		//int tsize = text.length()*size;
		//int bsize = text.length()*csize+csize*2;
		if(locked) glColor4f(1,1,1,.5f);
		
		String viewText = text;
		
		if(focus) {
			if(text.length()*size > getWidth()-4) {
				int tsx = (getWidth()-4)/size;
				viewText = text.substring(text.length()-tsx);
			}
		} else {
			if(text.length()*size > getWidth()-4) {
				int tsx = (getWidth()-4)/size;
				viewText = text.substring(0, tsx-2);
				viewText+="..";
			}
		}
		
		
		Font.drawString(viewText, x+2, y+(csize/2-size/2), size);
		
		Texture.gui.bind();
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, etat+ts*4);			glVertex2f(x, y);
		glTexCoord2f(ts, etat+ts*4);			glVertex2f(x+csize, y);
		glTexCoord2f(ts, etat+ts*5);		glVertex2f(x+csize, y+csize);
		glTexCoord2f(0, etat+ts*5);		glVertex2f(x, y+csize);
		
		for(int i=0; i<bsize-csize*2; i+=csize) {
			glTexCoord2f(ts, etat+ts*4);			glVertex2f(x+i+csize, y);
			glTexCoord2f(ts*2, etat+ts*4);		glVertex2f(x+i+csize*2, y);
			glTexCoord2f(ts*2, etat+ts*5);	glVertex2f(x+i+csize*2, y+csize);
			glTexCoord2f(ts, etat+ts*5);		glVertex2f(x+i+csize, y+csize);
		}
		
		glTexCoord2f(ts*2, etat+ts*4);		glVertex2f(x+bsize-csize, y);
		glTexCoord2f(ts*3, etat+ts*4);		glVertex2f(x+bsize, y);
		glTexCoord2f(ts*3, etat+ts*5);	glVertex2f(x+bsize, y+csize);
		glTexCoord2f(ts*2, etat+ts*5);	glVertex2f(x+bsize-csize, y+csize);
		
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

	public int getLenghtMax() {
		return lenghtMax;
	}

	public void setLenghtMax(int lenghtMax) {
		this.lenghtMax = lenghtMax;
	}

	public String getChars() {
		return chars;
	}

	public void setChars(String chars) {
		this.chars = chars;
	}

	public boolean isFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}

}
