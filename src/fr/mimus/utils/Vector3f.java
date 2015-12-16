package fr.mimus.utils;

public class Vector3f {
	public float x, y, z;

	public Vector3f() {
		this(0, 0, 0);
	}

	public Vector3f(float s) {
		this(s, s, s);
	}

	public Vector3f(float x, float y, float z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}

	public Vector3f(Vector3f d) {
		this(d.x, d.y, d.z);
	}

	public float length() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}

	public Vector3f normalize() {
		x/=length();
		y/=length();
		z/=length();
		return this;
	}

	public Vector3f add(Vector3f v) {
		x+=v.getX();
		y+=v.getY();
		z+=v.getZ();
		return this;
	}

	public Vector3f sub(Vector3f v) {
		x-=v.getX();
		y-=v.getY();
		z-=v.getZ();
		return this;
	}

	public Vector3f mul(Vector3f v) {
		x*=v.getX();
		y*=v.getY();
		z*=v.getZ();
		return this;
	}

	public Vector3f div(Vector3f v) {
		x/=v.getX();
		y/=v.getY();
		z/=v.getZ();
		return this;
	}

	public Vector3f add(float v) {
		x+=v;
		y+=v;
		z+=v;
		return this;
	}

	public Vector3f sub(float v) {
		x-=v;
		y-=v;
		z-=v;
		return this;
	}

	public Vector3f mul(float v) {
		x*=v;
		y*=v;
		z*=v;
		return this;
	}

	public Vector3f div(float v) {
		x/=v;
		y/=v;
		z/=v;
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
	 * TODO : Z
	 */
	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void addZ(float z) {
		this.z += z;
	}

	public Vector3f cross(Vector3f r) {
		float nx = y * r.z - z * r.y;
		float ny = z * r.x - x * r.z;
		float nz = x * r.y - y * r.x;

		return new Vector3f(nx, ny, nz);
	}

	public Vector3f check() {
		float max = Math.max(Math.max(x, y), z);
		float min = Math.min(Math.min(x, y), z);
		float absMax  = Math.abs(max - 1);
		float absMin  = Math.abs(min);
		float v = 0;
		if (absMax > absMin) v = min;
		else v = max;
		int rv = 1;
		if (v < 0.5f) rv = -1;


		return new Vector3f(v == x ? rv : 0, v == y ? rv : 0, v == z ? rv : 0);
	}

	/*
	 * TODO: conv
	 */

	public float[] toFloat() {
		return new float[]{x, y, z};
	}

	public String toString() {
		return "x: "+x+", y: "+y+", z: "+z;
	}
}
