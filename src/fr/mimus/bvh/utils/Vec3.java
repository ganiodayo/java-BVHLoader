package fr.mimus.bvh.utils;

public class Vec3 {
	
	public static final Vec3 UP = new Vec3(0, 1, 0);
	public static final Vec3 FRONT = new Vec3(0, 0, 1);
	public static final Vec3 RIGHT = new Vec3(1, 0, 0);
	
	public float x, y, z;

	public Vec3() {
		this(0, 0, 0);
	}
	
	public Vec3(float v) {
		this(v, v, v);
	}
	
	public Vec3(Vec3 v) {
		this(v.x, v.y, v.z);
	}

	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vec3 vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float sqrt() {
		return x * x + y * y + z * z;
	}
	
	public float magnitude() {
		return (float) Math.sqrt(sqrt());
	}
	
	public Vec3 normalize() {
		float mag = magnitude();
		return new Vec3(x / mag, y / mag, z / mag);
	}
	
	public Vec3 cross(Vec3 r) {
		float nx = y * r.z - z * r.y;
		float ny = z * r.x - x * r.z;
		float nz = x * r.y - y * r.x;
		
		return new Vec3(nx, ny, nz);
	}
	
	public float dot(Vec3 r) {
		return x * r.x + y * r.y + z * r.z;
	}
	
	public float max() {
		return Math.max(x, Math.max(y, z));
	}
	
	public float min() {
		return Math.min(x, Math.min(y, z));
	}
	
	public float squaredDistance(Vec3 v) {
		return (x-v.x)*(x-v.x) + (y-v.y)*(y-v.y) + (z-v.z)*(z-v.z);
	}
	
	public float distance(Vec3 v) {
		return (float) Math.sqrt(squaredDistance(v));
	}
	
	public Vec3 reflect(Vec3 normal) {
		return sub(normal.copy().mul(dot(normal) * 2.0f));
	}

	public Vec3 refract(Vec3 normal, float eta) {
		float dot = normal.dot(this);
		float k = 1.f - eta * eta * (1.f - dot * dot);
		Vec3 result = normal.mul(mul(eta).sub((float) (eta * dot + Math.sqrt(k))));
		
		if (k < 0.f)
			return new Vec3();
		else
			return result;
	}

	public Vec3 negate() {
		x = -x;
		y = -y;
		z = -z;

		return this;
	}

	public Vec3 add(Vec3 vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;

		return this;
	}

	public Vec3 sub(Vec3 vec) {
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;

		return this;
	}

	public Vec3 mul(Vec3 vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;

		return this;
	}

	public Vec3 div(Vec3 vec) {
		x /= vec.x;
		y /= vec.y;
		z /= vec.z;

		return this;
	}

	public Vec3 add(float v) {
		x += v;
		y += v;
		z += v;

		return this;
	}

	public Vec3 sub(float v) {
		x -= v;
		y -= v;
		z -= v;

		return this;
	}

	public Vec3 mul(float v) {
		x *= v;
		y *= v;
		z *= v;

		return this;
	}

	public Vec3 div(float v) {
		x /= v;
		y /= v;
		z /= v;

		return this;
	}

	public Vec3 rotate(Quat rot) {
		Quat w = rot.mul(this).mul(rot.conjugate());
		return new Vec3(w.x, w.y, w.z);
	}

	public Vec3 copy() {
		return new Vec3(x, y, z);
	}

	public String toString() {
		return x + " " + y + " " + z;
	}
	
	public boolean equals(Vec3 v) {
		return x == v.x && y == v.y && z == v.z;
	}

	public String print() {
		return " x: " + x + " y: " + y + " z: " + z;
	}
}
