package fr.mimus.render.gui;

public interface GuiListener {
	public void EventClickLeft(GuiEvent source);
	public void EventClickRight(GuiEvent source);
	public void EventOver(GuiEvent source);
	public void EventOut(GuiEvent source);
}
