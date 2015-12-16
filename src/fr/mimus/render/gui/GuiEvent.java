package fr.mimus.render.gui;

public abstract class GuiEvent {
	

	protected int x, y;
	protected GuiListener listener;
	protected boolean locked = false;
	private boolean over = false;
	
	public GuiEvent(int x, int y) {
		this.x=x;
		this.y=y;
	}

	public abstract void render();
	public abstract void update();
	public abstract int getWidth();
	public abstract int getHeight();
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public GuiListener getListener() {
		return listener;
	}
	public GuiEvent setListener(GuiListener listener) {
		this.listener = listener;
		return this;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}
}
