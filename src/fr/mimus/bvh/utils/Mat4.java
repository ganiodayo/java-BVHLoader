package fr.mimus.bvh.utils;

public class Mat4 {
	public float[][] matrix = new float[4][4];
	
	public static Mat4 identity() {
		Mat4 result = new Mat4();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				result.matrix[x][y] = 0f;
			}
		}
		
		result.matrix[0][0] = 1;
		result.matrix[1][1] = 1;
		result.matrix[2][2] = 1;
		result.matrix[3][3] = 1;
		
		return result;
	}
	
	public static Mat4 translate(float x, float y, float z) {
		Mat4 result = identity();
		
		result.matrix[0][3] = x;
		result.matrix[1][3] = y;
		result.matrix[2][3] = z;
		
		return result;
	}
	
	public static Mat4 rotate(float x, float y, float z) {
		Mat4 result = identity();

		Mat4 rx = identity();
		Mat4 ry = identity();
		Mat4 rz = identity();
		
		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);
		
		rx.matrix[1][1] = (float) Math.cos(x);		
		rx.matrix[1][2] = -(float) Math.sin(x);
		rx.matrix[2][1] = (float) Math.sin(x);		
		rx.matrix[2][2] = (float) Math.cos(x);
		
		ry.matrix[0][0] = (float) Math.cos(y);		
		ry.matrix[0][2] = -(float) Math.sin(y);
		ry.matrix[2][0] = (float) Math.sin(y);		
		ry.matrix[2][2] = (float) Math.cos(y);
		
		rz.matrix[0][0] = (float) Math.cos(z);		
		rz.matrix[0][1] = -(float) Math.sin(z);
		rz.matrix[1][0] = (float) Math.sin(z);		
		rz.matrix[1][1] = (float) Math.cos(z);
		
		result = rz.mul(ry.mul(rx));

		return result;
	}
	
	public static Mat4 rotate(Vec3 forward, Vec3 up, Vec3 right) {
		Mat4 result = identity();
		
		Vec3 f = new Vec3(forward).normalize();
		Vec3 r = new Vec3(right).normalize();
		Vec3 u = new Vec3(up).normalize();
		
		result.matrix[0][0] = r.x;	
		result.matrix[0][1] = r.y;	
		result.matrix[0][2] = r.z;
		
		result.matrix[1][0] = u.x;
		result.matrix[1][1] = u.y;	
		result.matrix[1][2] = u.z;
		
		result.matrix[2][0] = f.x;
		result.matrix[2][1] = f.y;	
		result.matrix[2][2] = f.z;
		
		return result;
	}
	
	public static Mat4 scale(float x, float y, float z) {
		Mat4 result = identity();
		
		result.matrix[0][0] = x;
		result.matrix[1][1] = y;
		result.matrix[2][2] = z;
		
		return result;
	}
	
	public static Mat4 cameraView(Vec3 forward, Vec3 up) {
		Mat4 m = identity();
		
		Vec3 f = new Vec3(forward).normalize();
		Vec3 r = new Vec3(up).normalize();
		r = r.cross(f);
		Vec3 u = f.cross(r);
		
		m.matrix[0][0] = r.x;	
		m.matrix[0][1] = r.y;	
		m.matrix[0][2] = r.z;
		
		m.matrix[1][0] = u.x;
		m.matrix[1][1] = u.y;	
		m.matrix[1][2] = u.z;
		
		m.matrix[2][0] = f.x;
		m.matrix[2][1] = f.y;	
		m.matrix[2][2] = f.z;
		
		return m;
	}
	
	public Mat4 mul(Mat4 m) {
		Mat4 result = identity();
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				result.matrix[x][y] = matrix[x][0] * m.matrix[0][y] +
									matrix[x][1] * m.matrix[1][y] +
									matrix[x][2] * m.matrix[2][y] +
									matrix[x][3] * m.matrix[3][y];
			}	
		}
		
		return result;
	}
	
	public static Mat4 perspective(float fov, float aspect, float zNear, float zFar) {
		Mat4 result = identity();

		float FOV = (float) Math.tan(Math.toRadians(fov / 2));
		float r = zNear - zFar;
		
		result.matrix[0][0] = 1.0f / (FOV * aspect);
		result.matrix[1][1] = 1.0f / FOV;

		result.matrix[2][2] = (-zNear - zFar) / r;
		result.matrix[2][3] = 2 * zFar * zNear / r;

		result.matrix[3][2] = 1;
		result.matrix[3][3] = 0;

		return result;
	}
	
	public static Mat4 orthographic(float right, float left, float top, float bottom, float zNear, float zFar) {
		Mat4 m = identity();
		
		m.matrix[0][0] = 2/(right - left);
		m.matrix[0][3] = -(right + left) / (right - left);
		
		m.matrix[1][1] = 2/(top - bottom);
		m.matrix[1][3] = -(top + bottom) / (top - bottom);
		
		m.matrix[2][2] = -2/(zFar - zNear);
		m.matrix[2][3] = -(zFar + zNear) / (zFar - zNear);
		
		return m;
	}
	
	public static Vec3 transform(Mat4 m, Vec3 v){
		return new Vec3(
			m.matrix[0][0] * v.x + m.matrix[0][1] * v.y + m.matrix[0][2] * v.z + m.matrix[0][3],
			m.matrix[1][0] * v.x + m.matrix[1][1] * v.y + m.matrix[1][2] * v.z + m.matrix[1][3],
			m.matrix[2][0] * v.x + m.matrix[2][1] * v.y + m.matrix[2][2] * v.z + m.matrix[2][3]
		);
	}
}