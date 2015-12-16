package fr.mimus.render;

import static org.lwjgl.opengl.ARBBufferObject.GL_STATIC_DRAW_ARB;
import static org.lwjgl.opengl.ARBBufferObject.glBindBufferARB;
import static org.lwjgl.opengl.ARBBufferObject.glBufferDataARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import fr.mimus.utils.Color4f;
import fr.mimus.utils.Vector2f;
import fr.mimus.utils.Vector3f;
import fr.mimus.utils.Vertex;

public class Tiler {
	public enum TilerType {
		DIRECT, DISPLAY_LIST, VBO;
	}
	
	public static Tiler newDirectMode() {
		return new Tiler(TilerType.DIRECT);
	}

	public static Tiler newDisplayList() {
		return new Tiler(TilerType.DISPLAY_LIST);
	}
	
	public static Tiler newVBO() {
		return new Tiler(TilerType.VBO);
	}
	
	private TilerType type;
	private ArrayList<Vertex> vertex;
	
	private int index = 0;
	private int size;
	private boolean updated = false;
	private boolean noTexture = false;
	
	
	public Tiler(TilerType tt) {
		type = tt;
		vertex = new ArrayList<Vertex>();
	}
	
	public void addVector(Vector3f v) {
		addVector(v, new Color4f(1, 1, 1), new Vector2f());
	}
	
	public void addVector(Vector3f v, Color4f c) {
		addVector(v, c, new Vector2f());
	}
	
	public void addVector(Vector3f v, Vector2f uv) {
		addVector(v, new Color4f(1, 1, 1), uv);
	}
	
	public void addVector(Vector3f v, Color4f c, Vector2f uv) {
		vertex.add(new Vertex(v, c, uv));
	}
	
	public void addVector(ArrayList<Vertex> v) {		
		vertex.addAll(v);
	}
	
	public void clear() {
		updated = false;
		vertex.clear();
	}
	
	public void render () {
		if(vertex.size()<=0) return;
		switch(type) {
		case DIRECT:
			renderDirect();
			break;
		case DISPLAY_LIST:
			renderDisplayList();
			break;
		case VBO:
			renderVBO();
			break;
		default:
			renderDirect();
			break;
		}
	}

	private void renderDirect() {
		glBegin(GL_TRIANGLES);
			for(Vertex v : vertex) {
				glColor4f(v.getColor().getRed(), v.getColor().getGreen(), v.getColor().getBlue(), v.getColor().getAlpha());
				if(!noTexture) glTexCoord2f(v.getTextureUV().getX(), v.getTextureUV().getY());
				glVertex3f(v.getPosition().getX(), v.getPosition().getY(), v.getPosition().getZ());
			}
		glEnd();
	}
	
	private void renderDisplayList() {
		if(!updated) {
			update();
			return;
		}
		glCallList(index);
	}
	
	private void renderVBO() {
		if(!updated) {
			update();
			return;
		}
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, index);

		glVertexPointer(3, GL_FLOAT, 8 << 2, 0 << 2);
		glColorPointer(3, GL_FLOAT, 8 << 2, 3 << 2);
		glTexCoordPointer(2, GL_FLOAT, 8 << 2, 6 << 2);

		glDrawArrays(GL_TRIANGLES, 0, size/8);
		
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		
		
	}
	
	private void update() {
		if(vertex.size()<=0) return;
		if(type == TilerType.VBO && !updated) {			
			size=vertex.size()*8;
			FloatBuffer buffer = BufferUtils.createFloatBuffer(size);
			for(Vertex v : vertex) {
				buffer.put(v.getPosition().toFloat());
				float[] color = v.getColor().toArray();
				buffer.put(new float[] {color[0], color[1], color[2]});
				buffer.put(v.getTextureUV().toFloat());
			}
			buffer.flip();
			
			
			if(index == 0) index=glGenBuffers();
			glBindBufferARB(GL_ARRAY_BUFFER_ARB, index);
			glBufferDataARB(GL_ARRAY_BUFFER_ARB, buffer, GL_STATIC_DRAW_ARB);
			glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);
			updated=true;
		}
		
		if(type == TilerType.DISPLAY_LIST && !updated) {
			if(index == 0) index = glGenLists(1);
			glNewList(index, GL_COMPILE);
				renderDirect();
			glEndList();
			updated=true;
		}
	}

	public boolean isNoTexture() {
		return noTexture;
	}

	public void setNoTexture(boolean noTexture) {
		this.noTexture = noTexture;
	}
	
	public void dispose() {
		if(!updated) return;
		updated = false;
		switch(type) {
		case DISPLAY_LIST:
			glDeleteLists(index, 1);
			break;
		case VBO:
			glDeleteBuffers(index);
			break;
		default:
			break;
		}
	}
	
	public boolean hasVertex() {
		return vertex != null && vertex.size()>0;
	}
}
