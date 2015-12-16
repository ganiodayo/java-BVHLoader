package fr.mimus.bvh.utils;

public class Transform {
	private Vec3 pos;
	private Quat rot;
	private Vec3 scale;

	private Transform parent;

	public Transform() {
		this(new Vec3());
	}

	public Transform(Vec3 pos) {
		this(pos, new Vec3(1, 1, 1));
	}

	public Transform(Transform transform) {
		this.pos = new Vec3(transform.pos);
		this.rot = new Quat(transform.rot);
		this.scale = new Vec3(transform.scale);
	}

	public Transform(Vec3 pos, Vec3 scale) {
		this.pos = pos;
		this.rot = new Quat();
		this.scale = scale;
	}

	public Transform(Vec3 pos, Quat rot) {
		this.pos = pos;
		this.rot = rot;
		this.scale = new Vec3(1);
	}

	public Mat4 toMatrix() {
		Mat4 translationMatrix = Mat4.translate(pos.x, pos.y, pos.z);
		Mat4 rotationMatrix = rot.toMatrix();
		Mat4 scaleMatrix = Mat4.scale(scale.x, scale.y, scale.z);
		Mat4 parentMatrix = Mat4.identity();
		if (parent != null) {
			parentMatrix = parent.toMatrix();
		}

		Mat4 transformationMatrix = parentMatrix.mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));

		return transformationMatrix;
	}

	public void rotate(Vec3 axis, float angle) {
		rot = new Quat(axis, (float) Math.toRadians(angle)).mul(rot).normalize();
	}

	public void setParent(Transform parent) {
		this.parent = parent;
	}

	public void add(Transform transform) {
		pos.add(transform.pos);
		rot.add(transform.rot);
		scale.add(transform.scale);
	}

	public Vec3 getPosition() {
		if (parent == null) {
			return pos;
		}
		return Mat4.transform(parent.toMatrix(), pos);
	}

	public Quat getRotation() {
		Quat parentRotation = new Quat();

		if (parent != null) {
			parentRotation = parent.getRotation();
		}

		return parentRotation.mul(rot);
	}

	public Vec3 getLocalPosition() {
		return pos;
	}

	public void setLocalPosition(Vec3 pos) {
		this.pos = pos;
	}

	public Quat getLocalRotation() {
		return rot;
	}

	public void setLocalRotation(Quat rot) {
		this.rot = rot;
	}

	public Vec3 getLocalScale() {
		return scale;
	}

	public void setLocalScale(Vec3 scale) {
		this.scale = scale;
	}
	
	public void translate(Vec3 axis, float speed) {
		pos.add(axis.mul(speed));
	}
}
