package fr.mimus.utils;

public class Vector2f {
	public float x, y;

	public Vector2f() {
		this(0, 0);
	}
	
	public Vector2f(Vector2f v) {
		this(v.x, v.y);
	}
	public Vector2f(float v) {
		this(v, v);
	}
	public Vector2f(float x, float y) {
		this.x=x;
		this.y=y;
	}
	
	public float length() {
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public Vector2f normalize() {
		x/=length();
		y/=length();
		return this;
	}

	public Vector2f add(Vector3f v) {
		x+=v.getX();
		y+=v.getY();
		return this;
	}
	
	public Vector2f sub(Vector3f v) {
		x-=v.getX();
		y-=v.getY();
		return this;
	}
	
	public Vector2f mul(Vector3f v) {
		x*=v.getX();
		y*=v.getY();
		return this;
	}
	
	public Vector2f div(Vector3f v) {
		x/=v.getX();
		y/=v.getY();
		return this;
	}

	public Vector2f add(float v) {
		x+=v;
		y+=v;
		return this;
	}
	
	public Vector2f sub(float v) {
		x-=v;
		y-=v;
		return this;
	}
	
	public Vector2f mul(float v) {
		x*=v;
		y*=v;
		return this;
	}
	
	public Vector2f div(float v) {
		x/=v;
		y/=v;
		return this;
	}
	
	
	/*
	 * TODO : X
	 */
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}
	
	public void addX(float x) {
		this.x += x;
	}
	

	/*
	 * TODO : Y
	 */
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void addY(float y) {
		this.y += y;
	}
	/*
	 * TODO: conv
	 */
	
	public float[] toFloat() {
		return new float[]{x, y};
	}

	public String toString() {
		return "x: "+x+", y: "+y;
	}
}
