package fr.mimus.utils;

import static org.lwjgl.opengl.GL11.glColor4f;

import java.util.Random;

public class Color4f {
	public static Color4f WHITE = new Color4f(1f,1f,1f,1f);
	public static Color4f BLACK = new Color4f(0f,0f,0f,1f);
	public static Color4f GRAY = new Color4f(.5f,.5f,.5f,1f);
	public static Color4f DARK_GRAY = new Color4f(.25f,.25f,.25f,1f);
	public static Color4f LIGHT_GRAY = new Color4f(.75f,.75f,.75f,1f);
	public static Color4f RED = new Color4f(1f,0f,0f,1f);
	public static Color4f LIGHT_RED = new Color4f(1f,.5f,.5f,1f);
	public static Color4f DARK_RED = new Color4f(.25f,0f,0f,1f);
	public static Color4f GREEN = new Color4f(0f,1f,0f,1f);
	public static Color4f LIGHT_GREEN = new Color4f(.5f,1f,.5f,1f);
	public static Color4f DARK_GREEN = new Color4f(.5f,1f,0f,1f);
	public static Color4f BLUE = new Color4f(0f,0f,1f,1f);
	public static Color4f LIGHT_BLUE = new Color4f(.5f,.5f,1,1f);
	public static Color4f DARK_BLUE = new Color4f(0f,0f,.5f,1f);
	public static Color4f CYAN = new Color4f(0f,1f,1f,1f);
	public static Color4f LIGHT_CYAN = new Color4f(.5f,1f,1f,1f);
	public static Color4f DARK_CYAN = new Color4f(0f,.5f,.5f,1f);
	public static Color4f PINK = new Color4f(1f,0f,1f,1f);
	public static Color4f LIGHT_PINK = new Color4f(1f,.5f,1f,1f);
	public static Color4f DARK_PINK = new Color4f(.5f,0f,.5f,1f);
	public static Color4f YELLOW = new Color4f(1f,1f,0f,1f);
	public static Color4f LIGHT_YELLOW = new Color4f(1f,1f,.5f,1f);
	public static Color4f DARK_YELLOW = new Color4f(.5f,.5f,0f,1f);
	public static Color4f ORANGE = new Color4f(1f,.5f,0f,1f);
	public static Color4f LIGHT_ORANGE = new Color4f(1f,.75f,.5f,1f);
	public static Color4f DARK_ORANGE = new Color4f(.5f,.25f,0f,1f);
	public static Color4f PURPLE = new Color4f(.5f,0f,1f,1f);
	public static Color4f LIGHT_PURPLE = new Color4f(.75f,.5f,1f,1f);
	public static Color4f DARK_PURPLE = new Color4f(.25f,0f,.5f,1f);
	
	public static Color4f randomColor() {
		Random rand = new Random();
		return new Color4f(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	}
	
	
	
	float red, green, blue, alpha;
	
	public Color4f(Color4f c) {
		this(c.red, c.green, c.blue, c.alpha);
	}
		
	public Color4f(int hexa) {
		this((hexa & 0xff000000) >> 24, (hexa & 0xff0000) >> 16, (hexa & 0xff00) >> 8, (hexa & 0xff));		
	}
	
	public Color4f(int r, int g, int b) {
		this((float)r/255f, (float)g/255f, (float)b/255f, 1f);
	}
	
	public Color4f(int r, int g, int b, int a) {
		this((float)r/255f, (float)g/255f, (float)b/255f, a/255f);
	}
	
	public Color4f(float r, float g, float b) {
		this(r, g, b, 1f);
	}
	
	public Color4f(float r, float g, float b, float a) {
		red=r;
		green=g;
		blue=b;
		alpha=a;
	}
	
	public float[] toArray() {
		return new float[] {red, green, blue, alpha};
	}

	public float getRed() {
		return red;
	}

	public void setRed(float red) {
		this.red = red;
	}

	public float getGreen() {
		return green;
	}

	public void setGreen(float green) {
		this.green = green;
	}

	public float getBlue() {
		return blue;
	}

	public void setBlue(float blue) {
		this.blue = blue;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public Color4f mul(float v) {
		red*=v;
		green*=v;
		blue*=v;
		return this;
	}
	
	public Color4f mul(Color4f v) {
		red*=v.red;
		green*=v.green;
		blue*=v.blue;
		return this;
	}
	
	public void bind() {
		glColor4f(red, green, blue, alpha);
	}
	
	public void bind(int t) {
		float p = (float) t/255f;
		bind(p);
	}
	public void bind(float t) {
		if(t<0) t=0;
		if(t>1) t=1;
		glColor4f(red*t, green*t, blue*t, alpha);
	}
	public void bind(float t, float a) {
		if(t<0) t=0;
		if(t>1) t=1;
		glColor4f(red*t, green*t, blue*t, a);
	}
}
