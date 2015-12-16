package fr.mimus.bvh.animation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import fr.mimus.bvh.utils.Vec3;

public class BVHLoader {
	private Scanner sc;
	public Bone bones;
	public int frameNumber = 0;
	public float frameTime = 1;
	
	public void loadBVH(String path) {
		try {
			sc = new Scanner(new FileReader(path));
			sc.useLocale(Locale.US);
			String value = sc.next();
			if(value.equals("HIERARCHY")) {
				value = sc.next();
				if(value.equals("ROOT")) {
					bones = loadBone(null);
					value = sc.next();
				}
			}
			if(value.equals("MOTION")) {
				value = sc.next();
				if(value.startsWith("Frames")) {
					frameNumber = sc.nextInt();
					value = sc.next();
				}
				if(value.equals("Frame")) {
					value = sc.next();
					if(value.startsWith("Time")) {
						frameTime = sc.nextFloat();
					}
				}
				for(int i = 0; i < frameNumber; i++) {
					readFrameMotion(bones);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void readFrameMotion(Bone bone) {
		if(!bone.channels.isEmpty()) {
			for(Channel chan : bone.channels) {
				chan.frameValue.add(sc.nextFloat());
			}
			if(bone.childs != null && !bone.childs.isEmpty()) {
				for(Bone b : bone.childs) {
					readFrameMotion(b);
				}
			}
		}
	}

	public Bone loadBone(Bone parent) {
		Bone bone = new Bone(sc.next(), parent);
		String value = sc.next();
		if(value.equals("{")) {
			value = sc.next();
			if(value.equals("OFFSET")) {
				
				bone.offset.setLocalPosition(new Vec3(sc.nextFloat(), sc.nextFloat(), sc.nextFloat())); 
//				bone.offset.getPosition().x = sc.nextFloat();
//				bone.offset.getPosition().y = sc.nextFloat();
//				bone.offset.getPosition().z = sc.nextFloat();
				value = sc.next();
			}
			if(value.equals("CHANNELS")) {
				int nb = sc.nextInt();
				for(int i = 0; i < nb; i++) {
					String chan = sc.next();
					if(chan.equals("Xposition")) {
						bone.channels.add(new Channel(Channel.Xposition));
						continue;
					} 
					if(chan.equals("Yposition")) {
						bone.channels.add(new Channel(Channel.Yposition));
						continue;
					}
					if(chan.equals("Zposition")) {
						bone.channels.add(new Channel(Channel.Zposition));
						continue;
					}
					if(chan.equals("Xrotation")) {
						bone.channels.add(new Channel(Channel.Xrotation));
						continue;
					}
					if(chan.equals("Yrotation")) {
						bone.channels.add(new Channel(Channel.Yrotation));
						continue;
					}
					if(chan.equals("Zrotation")) {
						bone.channels.add(new Channel(Channel.Zrotation));
						continue;
					}
				}
				value = sc.next();
			}
			while(value.equals("JOINT") || value.equals("End")) {
				if(bone.childs == null) {
					bone.childs = new ArrayList<Bone>();
				}
				bone.childs.add(loadBone(bone));
				value = sc.next();
			}
		}
		return bone;
	}
}
