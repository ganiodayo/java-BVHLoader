package fr.mimus.render;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import fr.mimus.utils.AABB3f;
import fr.mimus.utils.Vector2f;
import fr.mimus.utils.Vector3f;

public class Camera {
	public static Camera getInstance() { return instance; }
	private static Camera instance;
	
	private Vector3f position;
	private Vector2f rotation;
	
	AABB3f bound = null;
	
	public Camera() {
		this(new Vector3f(0,0,0), new Vector2f(0,135));
	}
	
	public Camera(Vector3f pos) {
		this(pos, new Vector2f(0,135));
	}
	

	public Camera(Vector3f pos, Vector2f rot) {
		instance=this;
		position = pos;
		rotation = rot;
	}
	
	public void render() {
		glRotatef(rotation.getX(), 1, 0, 0);
		glRotatef(rotation.getY(), 0, 1, 0);
		glTranslatef(-position.getX(), -position.getY(), -position.getZ());
	}
	
	public void update() {
		rotation.addX(-Mouse.getDY()*.25f);
		rotation.addY(Mouse.getDX()*.25f);
		if(rotation.getX()>90) rotation.setX(90);
		if(rotation.getX()<-90) rotation.setX(-90);

		if(rotation.getY()>360)rotation.addY(-360);
		if(rotation.getY()<0)rotation.addY(360);
		
		float speed = 2f/60f*6f;
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			speed /= 3f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			position.add(getForward().mul(speed));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.add(getForward().mul(-speed));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.add(getSide().mul(speed));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			position.add(getSide().mul(-speed));
		}
		if(bound != null) {
			if(position.x < bound.x) position.setX(bound.x);
			if(position.x > bound.x + bound.w) position.setX(bound.x + bound.w);
			if(position.y < bound.y) position.setY(bound.y);
			if(position.y > bound.y + bound.h) position.setY(bound.y + bound.h);
			if(position.z < bound.z) position.setZ(bound.z);
			if(position.z > bound.z + bound.l) position.setZ(bound.z + bound.l);
		}
	}
	
	public Vector3f getForward() {
		Vector3f forward = new Vector3f();
		
		float cosY = (float) Math.cos(Math.toRadians(rotation.getY()-90));
		float sinY = (float) Math.sin(Math.toRadians(rotation.getY()-90));
		
		float cosX = (float) Math.cos(Math.toRadians(-rotation.getX()));
		float sinX = (float) Math.sin(Math.toRadians(-rotation.getX()));
		
		forward.setX(cosY * cosX);
		forward.setY(sinX);
		forward.setZ(sinY * cosX);
		
		forward.normalize();
		
		return forward;
	}
	
	public Vector3f getSide() {
		Vector3f side = new Vector3f();
		float cosX = (float) Math.cos(Math.toRadians(rotation.getY()));
		float sinX = (float) Math.sin(Math.toRadians(rotation.getY()));
		
		side.setX(cosX);
		side.setZ(sinX);
		side.normalize();
		
		return side;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector2f getRotation() {
		return rotation;
	}

	public void setRotation(Vector2f rotation) {
		this.rotation = rotation;
	}

	public AABB3f getBound() {
		return bound;
	}

	public void setBound(AABB3f bound) {
		this.bound = bound;
	}
	
	
}
