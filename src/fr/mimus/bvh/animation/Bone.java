package fr.mimus.bvh.animation;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.ArrayList;
import java.util.List;

import fr.mimus.bvh.utils.Transform;
import fr.mimus.bvh.utils.Vec3;


public class Bone {
	
	String name;
	Transform offset;
	List<Bone> childs = null;
	List<Channel> channels = null;
	//Transform
	Bone parent;
	public Bone(String n, Bone p) {
		name = n;
		parent = p;
		offset = new Transform();
		if(p != null) {
			offset.setParent(p.offset);
		}
		
		channels = new ArrayList<Channel>();
	}
	
	public Transform getOffset(int frame) {
//		Transform f = new Transform(offset);
//		if(parent != null) {
//			return new Vec3(parent.getOffset(frame)).add(f.getPosition());
//		}
		Transform f = new Transform(offset);
		if(parent != null) f.setParent(parent.getOffset(frame));
		for(int i=0; i<channels.size(); i++) {
			Channel chan = channels.get(i);
			if(chan.index == Channel.Xposition) {
				f.translate(new Vec3(1, 0, 0), chan.frameValue.get(frame));
				continue;
			}
			if(chan.index == Channel.Yposition) {
				f.translate(new Vec3(0, 1, 0), chan.frameValue.get(frame));
				continue;
			}
			if(chan.index == Channel.Zposition) {
				f.translate(new Vec3(0, 0, 1), chan.frameValue.get(frame));
				continue;
			}
			if(chan.index == Channel.Xrotation) {
				f.rotate(new Vec3(1, 0, 0), chan.frameValue.get(frame));
				continue;
			}
			if(chan.index == Channel.Yrotation) {
				f.rotate(new Vec3(0, 1, 0), chan.frameValue.get(frame));
				continue;
			}
			if(chan.index == Channel.Zrotation) {
				f.rotate(new Vec3(0, 0, 1), chan.frameValue.get(frame));
				continue;
			}
		}
		return f;
	}
	
	public void render(int frame) {
		if(childs == null) return;
		Vec3 a = getOffset(frame).getPosition();
		for(int i = 0; i<childs.size(); i++) {
			Vec3 cOffset = childs.get(i).getOffset(frame).getPosition();
			glBegin(GL_LINES);
				glColor3f(1, 0, 0);
				glVertex3f(a.x, a.y, a.z);
				
				glColor3f(1, 1, 1);
				glVertex3f(cOffset.x, cOffset.y, cOffset.z);
			glEnd();
			childs.get(i).render(frame);
		}
		
	}

	public int getBonesNumber() {
		int nb = 1;
		if(childs != null) {
			for(int i = 0; i<childs.size(); i++) {
				nb += childs.get(i).getBonesNumber();
			}
		}
		return nb;
	}

}
