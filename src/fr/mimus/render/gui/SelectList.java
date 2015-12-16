package fr.mimus.render.gui;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.mimus.render.Font;
import fr.mimus.render.Texture;
import fr.mimus.utils.Input;

public class SelectList extends GuiEvent {

	int width, height;
	int selected = 0;
	int offset = 0;
	int overText = -1;
	int size = 20;
	
	String[] list = new String[] {""};
	public SelectList(int x, int y, int width, int height) {
		super(x, y);
		this.width=width;
		this.height=height;
	}

	@Override
	public void render() {
		float ts = 12f/Texture.gui.getWidth();

		
		int mi = width/size;
		int mj = height/size;
		
		int li = list.length;
		if(li*size>height) {
			li = height/size;
		}
		if(li>list.length) li = list.length;
		for(int i = offset; i <li+offset; i++) {
			if(i == overText) glColor4f(.4f, .4f, .4f, 1);
			if(i == selected) glColor3f(.8f, .2f, .2f);
			if(list[i].length()*(size-4)+(size/4)*3>width) {
				int tsx = (width-(size/4)*3)/(size-4);
				Font.drawString(list[i].substring(0, tsx-2)+"..", x + 2, y + 2 + i*size - (offset*size), (size-4));
			} else {
				Font.drawString(list[i], x + 2, y + 2 + i*size - (offset*size), (size-4));
			}
			if(i == selected) glColor3f(1, 1, 1);
			if(i == overText) glColor4f(1,1,1,1);
		}
		
		Texture.gui.bind();
		glBegin(GL_QUADS);
		
		for(int i=0; i<mi; i++) {
			for(int j=0; j<mj; j++) {
				if(i < 1 && j < 1) {
					glTexCoord2f(ts*3, ts*4);		glVertex2f(x+i*size, y+j*size);
					glTexCoord2f(ts*4, ts*4);		glVertex2f(x+i*size+size, y+j*size);
					glTexCoord2f(ts*4, ts*5);		glVertex2f(x+i*size+size, y+j*size+size);
					glTexCoord2f(ts*3, ts*5);		glVertex2f(x+i*size, y+j*size+size);
				} else if(i >= mi-1 && j < 1) {
					glTexCoord2f(ts*5, ts*4);		glVertex2f(x+i*size, y+j*size);
					glTexCoord2f(ts*6, ts*4);		glVertex2f(x+i*size+size, y+j*size);
					glTexCoord2f(ts*6, ts*5);		glVertex2f(x+i*size+size, y+j*size+size);
					glTexCoord2f(ts*5, ts*5);		glVertex2f(x+i*size, y+j*size+size);
				} else if(i >= 1 && j < 1) {
					glTexCoord2f(ts*4, ts*4);		glVertex2f(x+i*size, y+j*size);
					glTexCoord2f(ts*5, ts*4);		glVertex2f(x+i*size+size, y+j*size);
					glTexCoord2f(ts*5, ts*5);		glVertex2f(x+i*size+size, y+j*size+size);
					glTexCoord2f(ts*4, ts*5);		glVertex2f(x+i*size, y+j*size+size);
				} else if(i < 1 && j >= mj-1) {
					glTexCoord2f(ts*3, ts*6);		glVertex2f(x+i*size, y+j*size);
					glTexCoord2f(ts*4, ts*6);		glVertex2f(x+i*size+size, y+j*size);
					glTexCoord2f(ts*4, ts*7);		glVertex2f(x+i*size+size, y+j*size+size);
					glTexCoord2f(ts*3, ts*7);		glVertex2f(x+i*size, y+j*size+size);
				} else if(i >= mi-1 && j >= mj-1) {
					glTexCoord2f(ts*5, ts*6);		glVertex2f(x+i*size, y+j*size);
					glTexCoord2f(ts*6, ts*6);		glVertex2f(x+i*size+size, y+j*size);
					glTexCoord2f(ts*6, ts*7);		glVertex2f(x+i*size+size, y+j*size+size);
					glTexCoord2f(ts*5, ts*7);		glVertex2f(x+i*size, y+j*size+size);
				} else if(i < 1 && j >= 1) {
					glTexCoord2f(ts*3, ts*5);		glVertex2f(x+i*size, y+j*size);
					glTexCoord2f(ts*4, ts*5);		glVertex2f(x+i*size+size, y+j*size);
					glTexCoord2f(ts*4, ts*6);		glVertex2f(x+i*size+size, y+j*size+size);
					glTexCoord2f(ts*3, ts*6);		glVertex2f(x+i*size, y+j*size+size);
				} else if(i >= mi-1 && j >= 1) {
					glTexCoord2f(ts*5, ts*5);		glVertex2f(x+i*size, y+j*size);
					glTexCoord2f(ts*6, ts*5);		glVertex2f(x+i*size+size, y+j*size);
					glTexCoord2f(ts*6, ts*6);		glVertex2f(x+i*size+size, y+j*size+size);
					glTexCoord2f(ts*5, ts*6);		glVertex2f(x+i*size, y+j*size+size);
				} else if(i >= 1 && j >= mj-1) {
					glTexCoord2f(ts*4, ts*6);		glVertex2f(x+i*size, y+j*size);
					glTexCoord2f(ts*5, ts*6);		glVertex2f(x+i*size+size, y+j*size);
					glTexCoord2f(ts*5, ts*7);		glVertex2f(x+i*size+size, y+j*size+size);
					glTexCoord2f(ts*4, ts*7);		glVertex2f(x+i*size, y+j*size+size);
				} else {
					glTexCoord2f(ts*4, ts*5);		glVertex2f(x+i*size, y+j*size);
					glTexCoord2f(ts*5, ts*5);		glVertex2f(x+i*size+size, y+j*size);
					glTexCoord2f(ts*5, ts*6);		glVertex2f(x+i*size+size, y+j*size+size);
					glTexCoord2f(ts*4, ts*6);		glVertex2f(x+i*size, y+j*size+size);
				}
			}
		}

		glEnd();
		Texture.unbind();
	}

	@Override
	public void update() {
		int mx = Mouse.getX(); 
		int my = Display.getHeight() - Mouse.getY();
		int mj = height/size;
		if(mj < list.length) {
			if(mx >= x+width-(size/2)
					&& mx < x+width
					&& my >= y
					&& my < y+(mj*size)/2) {
				if(Input.mouseClicked(0)){
					
					offset--;
					if(offset<0) offset=0;
				}
			}
			if(mx >= x+width-(size/2)
					&& mx < x+width
					&& my >= y+(mj*size)/2
					&& my < y+(mj*size)) {
				if(Input.mouseClicked(0)){
					offset++;
					if(offset>list.length-mj) offset = list.length-mj;
				}
			}
		}
		if(mx >= x
				&& mx < x+width-(size/2)
				&& my >= y
				&& my < y+height) {
			
				if(!this.isOver()) {
					this.setOver(true);
					if(this.listener != null) this.listener.EventOver(this);
				}
				int s = (my-y)/size+offset;
				if(s>=0 && s<list.length) overText = s;
				
				if(Input.mouseClicked(0)){
					if(s>=0 && s<list.length) selected = s;
					if(this.listener != null) this.listener.EventClickLeft(this);
				}
				if(Input.mouseClicked(1)){
					if(this.listener != null) this.listener.EventClickRight(this);
				}
			} else {
				if(this.isOver()) {
					overText = -1;
					this.setOver(false);
					if(this.listener != null) this.listener.EventOut(this);
				}
			}
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public String[] getList() {
		return list;
	}

	public void setList(String[] list) {
		this.list = list;
	}

}
