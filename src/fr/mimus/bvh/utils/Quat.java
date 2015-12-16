package fr.mimus.bvh.utils;

public class Quat {
	public float x, y, z, w;
	
	public Quat() {
		this(0, 0, 0, 1);
	}
	
	public Quat(Quat v) {
		this(v.x, v.y, v.z, v.w);
	}
	
	public Quat(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quat(Vec3 axis, float angle) {
		float sin = (float) Math.sin(angle / 2);
		float cos = (float) Math.cos(angle / 2);

		this.x = axis.x * sin;
		this.y = axis.y * sin;
		this.z = axis.z * sin;
		this.w = cos;
	}
	
	public Quat(Quat q, Vec3 axis, float angle) {
		float sin = (float) Math.sin(angle / 2);
		float cos = (float) Math.cos(angle / 2);

		this.x = q.x + (axis.x * sin);
		this.y = q.y + (axis.y * sin);
		this.z = q.z + (axis.z * sin);
		this.w = q.w + (cos);
	}
	
	public Quat(Vec3 euler) {
		float c1 = (float) Math.cos(euler.x / 2);
		float s1 = (float) Math.sin(euler.x / 2);
		
		float c2 = (float) Math.cos(euler.y / 2);
		float s2 = (float) Math.sin(euler.y / 2);
		
		float c3 = (float) Math.cos(euler.z / 2);
		float s3 = (float) Math.sin(euler.z / 2);
		
		float c1c2 = c1 * c2;
		float s1s2 = s1 * s2;

		x = c1c2 * s3 + s1s2 * c3;
		y = s1 * c2 * c3 + c1 * s2 * s3;
		z = c1 * s2 * c3 - s1 * c2 * s3;
		w = c1c2 * c3 - s1s2 * s3;
	}

	public Quat set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		
		return this;
	}
	
	public Quat set(Quat q) {
		this.x = q.x;
		this.y = q.y;
		this.z = q.z;
		this.w = q.w;
		
		return this;
	}
	
	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public Quat normalize() {
		float mag = magnitude();
		return new Quat(x / mag, y / mag, z / mag, w / mag);
	}
	
	public Quat conjugate() {
		return new Quat(-x, -y, -z, w);
	}
	
	public Quat sub(Quat value) {
		return new Quat(x - value.x, y - value.y, z - value.z, w - value.w);
	}

	public Quat add(Quat value) {
		return new Quat(x + value.x, y + value.y, z + value.z, w + value.w);
	}
	
	public Quat mul(float value) {
		return new Quat(x * value, y * value, z * value, w * value);
	}
	
	public Quat mul(Quat r) {
		float nw = w * r.w - x * r.x - y * r.y - z * r.z;
		float nx = x * r.w + w * r.x + y * r.z - z * r.y;
		float ny = y * r.w + w * r.y + z * r.x - x * r.z;
		float nz = z * r.w + w * r.z + x * r.y - y * r.x;

		return new Quat(nx, ny, nz, nw);
	}

	public Quat mul(Vec3 r) {
		float nw = -x * r.x - y * r.y - z * r.z;
		float nx = 	w * r.x + y * r.z - z * r.y;
		float ny = 	w * r.y + z * r.x - x * r.z;
		float nz = 	w * r.z + x * r.y - y * r.x;

		return new Quat(nx, ny, nz, nw);
	}
	
	public Mat4 toMatrix() {
		Vec3 forward = new Vec3(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vec3 up = new Vec3(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vec3 right = new Vec3(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));
		
		return Mat4.rotate(forward, up, right);
	}
	
	public Quat(Mat4 rot) {
		float trace = rot.matrix[0][0] + rot.matrix[1][1] + rot.matrix[2][2];
		if (trace > 0) {
			float s = 0.5f / (float) Math.sqrt(trace + 1.0f);
			w = 0.25f / s;
			x = (rot.matrix[1][2] - rot.matrix[2][1]) * s;
			y = (rot.matrix[2][0] - rot.matrix[0][2]) * s;
			z = (rot.matrix[0][1] - rot.matrix[1][0]) * s;
		} else {
			if (rot.matrix[0][0] > rot.matrix[1][1] && rot.matrix[0][0] > rot.matrix[2][2]) {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.matrix[0][0] - rot.matrix[1][1] - rot.matrix[2][2]);
				w = (rot.matrix[1][2] - rot.matrix[2][1]) / s;
				x = 0.25f * s;
				y = (rot.matrix[1][0] + rot.matrix[0][1]) / s;
				z = (rot.matrix[2][0] + rot.matrix[0][2]) / s;
			} else if (rot.matrix[1][1] > rot.matrix[2][2]) {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.matrix[1][1] - rot.matrix[0][0] - rot.matrix[2][2]);
				w = (rot.matrix[2][0] - rot.matrix[0][2]) / s;
				x = (rot.matrix[1][0] + rot.matrix[0][1]) / s;
				y = 0.25f * s;
				z = (rot.matrix[2][1] + rot.matrix[1][2]) / s;
			} else {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.matrix[2][2] - rot.matrix[0][0] - rot.matrix[1][1]);
				w = (rot.matrix[0][1] - rot.matrix[1][0]) / s;
				x = (rot.matrix[2][0] + rot.matrix[0][2]) / s;
				y = (rot.matrix[1][2] + rot.matrix[2][1]) / s;
				z = 0.25f * s;
			}
		}
		float length = (float) Math.sqrt(x * x + y * y + z * z + w * w);
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}
	
	public Vec3 getForward() {
		return new Vec3(0, 0, 1).rotate(this);
	}

	public Vec3 getBack() {
		return new Vec3(0, 0, -1).rotate(this);
	}

	public Vec3 getRight() {
		return new Vec3(1, 0, 0).rotate(this);
	}

	public Vec3 getLeft() {
		return new Vec3(-1, 0, 0).rotate(this);
	}

	public Vec3 getUp() {
		return new Vec3(0, 1, 0).rotate(this);
	}

	public Vec3 getDown() {
		return new Vec3(0, -1, 0).rotate(this);
	}
}