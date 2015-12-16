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

public class HScrollBar extends GuiEvent {
	int maxValue = 255, value = 255, offValue = 0;
	private int size = 16, r = 5;
	private boolean viewText=true;
	private boolean inversed = false;


	public HScrollBar(int x, int y, int size) {
		super(x, y);
		this.maxValue=size;
	}

	public void render() {
		float ts = 12f/Texture.gui.getWidth();

		float tx0 = 3*ts;

		int mx = r*size+size*2;
		int v = (value*mx)/maxValue;

		if(viewText) {
			if(inversed) {
				Font.drawString("-"+(value-offValue), x+getWidth()+2, y+4, 8);
			} else {
				Font.drawString(""+(value-offValue), x+getWidth()+2, y+4, 8);
			}
			glColor4f(1, 1, 1, 1);
		}

		Texture.gui.bind();
		if(locked) glColor4f(1,1,1,.5f);
		glBegin(GL_QUADS);

		if(!locked) {
			glTexCoord2f(tx0, ts);					glVertex2f(x-8+v, y);
			glTexCoord2f(tx0+ts, ts);				glVertex2f(x+size-8+v, y);
			glTexCoord2f(tx0+ts, ts*2);				glVertex2f(x+size-8+v, y+size);
			glTexCoord2f(tx0, ts*2);				glVertex2f(x-8+v, y+size);
		} else {
			glTexCoord2f(tx0+ts, ts);					glVertex2f(x-8+v, y);
			glTexCoord2f(tx0+ts*2, ts);				glVertex2f(x+size-8+v, y);
			glTexCoord2f(tx0+ts*2, ts*2);				glVertex2f(x+size-8+v, y+size);
			glTexCoord2f(tx0+ts, ts*2);				glVertex2f(x-8+v, y+size);
		}

		glTexCoord2f(tx0, 0);					glVertex2f(x, y);
		glTexCoord2f(tx0+ts, 0);				glVertex2f(x+size, y);
		glTexCoord2f(tx0+ts, ts);				glVertex2f(x+size, y+size);
		glTexCoord2f(tx0, ts);					glVertex2f(x, y+size);

		for(int i=0; i<r; i++) {
			glTexCoord2f(tx0+ts, 0);				glVertex2f(x+((i+1)*size), y);
			glTexCoord2f(tx0+ts*2, 0);				glVertex2f(x+((i+1)*size)+size, y);
			glTexCoord2f(tx0+ts*2, ts);				glVertex2f(x+((i+1)*size)+size, y+size);
			glTexCoord2f(tx0+ts, ts);				glVertex2f(x+((i+1)*size), y+size);
		}

		glTexCoord2f(tx0+ts*2, 0);				glVertex2f(x+size*(r+1), y);
		glTexCoord2f(tx0+ts*3, 0);				glVertex2f(x+size*(r+1)+size, y);
		glTexCoord2f(tx0+ts*3, ts);				glVertex2f(x+size*(r+1)+size, y+size);
		glTexCoord2f(tx0+ts*2, ts);				glVertex2f(x+size*(r+1), y+size);


		/*glVertex2f(x, y);
		glVertex2f(x+mx, y);
		glVertex2f(x+mx, y+size);
		glVertex2f(x, y+size);*/
		glEnd();

		if(locked) glColor4f(1,1,1,1f);
		Texture.unbind();
	}

	public void update() {
		int mx = r*size+size*2;
		//int v = (value*mx)/maxValue;

		int posX = Mouse.getX();
		int posY = Display.getHeight() - Mouse.getY();
		int tol = 2;
		if(posX>=x-tol && posX<x+this.getWidth()+tol
				&& posY>=y && posY<y+this.getHeight()) {
			if(Input.mouseDown(0) && !locked) {
				int v = posX - x;
				//System.out.println(pos.toString() + " :: " +v);

				value = (v*maxValue)/mx;
				if(value < 0) value = 0;
				if(value > maxValue) value = maxValue;
				if(this.listener != null) this.listener.EventClickLeft(this);
			}
			if(Input.mouseClicked(1)){
				if(this.listener != null) this.listener.EventClickRight(this);
			}
		}
	}

	@Override
	public int getHeight() {
		return size;
	}

	public int getWidth() {
		return size*(r+1)+size;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getValue() {
		if(inversed) return -(value - offValue);
		return value - offValue;
	}

	public void setValue(int value) {
		this.value = value + offValue;
	}

	public int getOffValue() {
		return offValue;
	}

	public void setOffValue(int offValue) {
		this.offValue = offValue;
	}

	public boolean isViewText() {
		return viewText;
	}

	public void setViewText(boolean viewText) {
		this.viewText = viewText;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public boolean isInversed() {
		return inversed;
	}

	public void setInversed(boolean inversed) {
		this.inversed = inversed;
	}

}
