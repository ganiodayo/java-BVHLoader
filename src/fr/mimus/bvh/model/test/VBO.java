package fr.mimus.bvh.model.test;

import static org.lwjgl.opengl.ARBBufferObject.GL_STATIC_DRAW_ARB;
import static org.lwjgl.opengl.ARBBufferObject.glBindBufferARB;
import static org.lwjgl.opengl.ARBBufferObject.glBufferDataARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import fr.mimus.bvh.utils.Vec3;
import fr.mimus.utils.Vector2f;

public class VBO {
	public static VBO newVBO() {
		return new VBO();
	}
	
	private int vbo;
	private int size;
	public VBO() {
		vertices = new ArrayList<Vec3>();
		textures = new ArrayList<Vector2f>();
	}

	private ArrayList<Vec3> vertices;
	private ArrayList<Vector2f> textures;
	public void addVertices(Vec3 v, Vector2f t) {
		vertices.add(v);
		textures.add(t);
	}
	
	public void removeVertice(int index) {
		if(index < 0) return;
		if(index >= vertices.size()) return;
		vertices.remove(index);
		textures.remove(index);
	}
	
	public void clearVertices() {
		vertices.clear();
		textures.clear();
	}
	
	public void build() {
		size=vertices.size()*3+textures.size()*2;
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size()*3*2);
		for(int i = 0; i<vertices.size(); i++) {
			Vec3 v = vertices.get(i);
			buffer.put(v.x);
			buffer.put(v.y);
			buffer.put(v.z);
			Vector2f t = textures.get(i);
			buffer.put(t.toFloat());
		}
		buffer.flip();
		
		vbo = glGenBuffers();
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vbo);
	    glBufferDataARB(GL_ARRAY_BUFFER_ARB, buffer, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);
	}
	
	public void render() {
		glEnableClientState(GL_VERTEX_ARRAY);
		//glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vbo);
		glVertexPointer(3, GL_FLOAT, (3+2) << 2, 0 << 2);
		//glVertexPointer(3, GL_FLOAT, (3 * 2 + 2) << 2, 0 << 2); // float at index 0
	    //glColorPointer(3, GL_FLOAT, (3 * 2 + 2) << 2,(3*1) << 2); // float at index 3
	    glTexCoordPointer(2, GL_FLOAT, (3+2) << 2, 3 << 2);
	    
		glDrawArrays(GL_QUADS, 0, size*4);
		
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		//glDisableClientState(GL_COLOR_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	public void dispose() {
		glDeleteBuffers(vbo);
	}
}
