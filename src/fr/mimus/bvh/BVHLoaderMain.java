package fr.mimus.bvh;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import fr.mimus.bvh.animation.BVHLoader;
import fr.mimus.game.render.GameCore;
import fr.mimus.game.utils.Camera;
import fr.mimus.render.Font;
import fr.mimus.utils.Input;

public class BVHLoaderMain extends GameCore {
	private static BVHLoaderMain instance = null;
	public static BVHLoaderMain getInstance() {
		return instance;
	}
	
	public BVHLoaderMain(String title, int width, int height, boolean rezise) {
		super(title, width, height, rezise);
	}

	Camera cam;
	BVHLoader bvh;
	protected void init() {
		cam = new Camera();
		bvh = new BVHLoader();
		bvh.loadBVH("res/test.bvh");
	}

	protected void update(int tick) {
		if(tick % 2 == 0) {
			current++;
			if(current >= bvh.frameNumber) {
				current = 0;
			}
		}
		if(Mouse.isGrabbed()) {
			if(Input.keyClicked(Keyboard.KEY_ESCAPE)) {
				Mouse.setGrabbed(false);
			} else {
				cam.update();
				if(Input.keyClicked(Keyboard.KEY_RIGHT)) {
					current++;
					if(current >= bvh.frameNumber) {
						current = 0;
					}
				}
				if(Input.keyClicked(Keyboard.KEY_LEFT)) {
					current--;
					if(current < 0) {
						current = bvh.frameNumber - 1;
					}
				}
			}
		} else {
			Mouse.setGrabbed(Input.mouseClicked(0));
		}
	}

	protected void render3D() {
		cam.render();
		if(bvh.bones != null) {
			bvh.bones.render(current);
		}
	}

	int current = 0;
	protected void render2D() {
		Font.drawString("FPS: "+getFPS(), 5, 5, 12);
		Font.drawString("UPS: "+getUPS(), 5, 22, 12);
		
		float x = (float) cam.getPosition().x;
		float y = (float) cam.getPosition().y;
		float z = (float) cam.getPosition().z;
	
		Font.drawString("x: "+x, 5, 39, 12);
		Font.drawString("y: "+y, 5, 56, 12);
		Font.drawString("z: "+z, 5, 73, 12);

		Font.drawString("Frames: "+bvh.frameNumber, 5, 100, 12);
		Font.drawString("Time: "+bvh.frameTime, 5, 117, 12);
		Font.drawString("bones: "+bvh.bones.getBonesNumber(), 5, 134, 12);
		Font.drawString("c: "+current, 5, 151, 12);
	}
	
	protected void dispose() {
		
	}

	public static void main(String[] args) {
		instance = new BVHLoaderMain("BVH Loader", 720, 720*9/16, true);
		instance.start();
	}
}
