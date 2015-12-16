package fr.mimus.utils;

public class AABB3f {
	public float x, y, z, w, h, l;
	public AABB3f(float x, float y, float z) {
		this(x, y, z, 1, 1, 1);
	}
	public AABB3f(float x, float y, float z, float w, float h, float l) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
		this.h=h;
		this.l=l;
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getW() {
		return w;
	}
	public void setW(float w) {
		this.w = w;
	}
	public float getH() {
		return h;
	}
	public void setH(float h) {
		this.h = h;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	public float getL() {
		return l;
	}
	public void setL(float l) {
		this.l = l;
	}

	public boolean collid(Vector3f v) {
		return this.collid(v.x, v.y, v.z);
	}

	public boolean collid(float x, float y, float z) {
		if(x>=this.x && x<this.x+this.w 
				&& y>=this.y && y<this.y+this.h
				&& z>=this.z && z<this.z+this.l)
			return true;
		return false;
	}

	public boolean collid(AABB3f box) {
		if(box.x>= this.x+this.w
				|| box.x + box.w < this.x
				|| box.y>= this.y+this.h
				|| box.y + box.h < this.y
				|| box.z>= this.z+this.l
				|| box.z + box.l < this.z) {
			return false;
		}
		return true;
	}
	
	public Vector3f getDifference(AABB3f box) {
		if(box.x>= this.x+this.w
				|| box.x + box.w < this.x
				|| box.y>= this.y+this.h
				|| box.y + box.h < this.y
				|| box.z>= this.z+this.l
				|| box.z + box.l < this.z) {
			return new Vector3f(0,0,0);
		}

		float x=0, y=0, z=0;
		if(box.x + box.w >= this.x && box.x + box.w < this.x+this.w) {
			x = box.x-(this.x-box.w);
		} else if(box.x < this.x+this.w) {
			x = box.x-(this.x+this.w);
		}

		if(box.y + box.h >= this.y && box.y + box.h < this.y+this.h) {
			y = box.y-(this.y-box.h);
		} else if(box.y < this.y+this.h) {
			y = box.y-(this.y+this.h);
		}


		if(box.z + box.l >= this.z && box.z + box.l < this.z+this.l) {
			z = box.z-(this.z-box.l);
		} else if(box.z < this.z+this.l) {
			z = box.z-(this.z+this.l);
		}
		return new Vector3f(x,y,z);
	}
}
