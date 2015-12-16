package fr.mimus.render.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import fr.mimus.render.Font;
import fr.mimus.render.Texture;

public abstract class GuiBase {
	
	private List<GuiEvent> component = new ArrayList<GuiEvent>();
	private String title = null;
	private String subtitle = null;
	
	public GuiBase() {}
	public GuiBase(String t) {
		title=t;
	}
	
	public void update() {
		for(int i=0; i<component.size(); i++) component.get(i).update();
	}
	
	public void render() {
		if(title != null) {
			int size = 32;
			int x = Display.getWidth()/2 - (title.length()*size)/2;
			Font.drawString(title, x, 5, size);
			if(subtitle != null) {
				size = 16;
				x = Display.getWidth()/2 - (subtitle.length()*size)/2;
				Font.drawString(subtitle, x, 37, size);
			}
		} else {
			if(subtitle != null) {
				int size = 24;
				int x = Display.getWidth()/2 - (title.length()*size)/2;
				Font.drawString(subtitle, x, 5, size);
			}
		}
		for(int i=0; i<component.size(); i++) component.get(i).render();
		if(onlyView()) {
			Texture.background.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0); glVertex2f(0, 0);
			glTexCoord2f(1, 0); glVertex2f(Display.getWidth(), 0);
			glTexCoord2f(1, 1); glVertex2f(Display.getWidth(), Display.getHeight());
			glTexCoord2f(0, 1); glVertex2f(0, Display.getHeight());
			glEnd();
			Texture.unbind();
		}
	}
	

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addComponent(GuiEvent event) {
		component.add(event);
	}
	
	public void addComponent(GuiEvent... events) {
		for(GuiEvent e : events) component.add(e);
	}
	
	public void setListener(GuiListener listener, GuiEvent... events) {
		for(GuiEvent e : events) e.setListener(listener);
	}
	
	public boolean onlyView() {
		return true;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
}
