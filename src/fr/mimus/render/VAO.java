package fr.mimus.render;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import fr.mimus.utils.Color4f;
import fr.mimus.utils.Vector2f;
import fr.mimus.utils.Vector3f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VAO {
	public final static int SIZE = 0x00ffffff;
	private static VAO current = null;
	public static VAO getVAO() {
		return current;
	}
	public void bind() {
		current = this;
	}

	protected static FloatBuffer verticesBuffer = null;
	protected static FloatBuffer normalsBuffer = null;
	protected static FloatBuffer colorsBuffer = null;
	protected static FloatBuffer uv0Buffer = null;
	
	protected int vaoIndex = 0;
	protected int vboIndex = 0;
	protected int normalsIndex = 0;
	protected int colorsIndex = 0;
	protected int uv0Index = 0;
	protected int count = 0;
	
	public VAO() {
		if(verticesBuffer == null) verticesBuffer = BufferUtils.createFloatBuffer(SIZE);
		if(normalsBuffer == null) normalsBuffer = BufferUtils.createFloatBuffer(SIZE);
		if(colorsBuffer == null) colorsBuffer = BufferUtils.createFloatBuffer(SIZE);
		if(uv0Buffer == null) uv0Buffer = BufferUtils.createFloatBuffer(SIZE);
	}
	
	public void add(Vector3f vertice, Vector3f normal, Color4f color, Vector2f uv) {
		verticesBuffer.put(vertice.toFloat());
		normalsBuffer.put(normal.toFloat());
		colorsBuffer.put(color.getRed());
		colorsBuffer.put(color.getGreen());
		colorsBuffer.put(color.getBlue());
		uv0Buffer.put(uv.toFloat());
		count++;
	}
	
	public void build() {	
		vaoIndex = glGenVertexArrays();
		vboIndex = glGenBuffers();
		normalsIndex = glGenBuffers();
		colorsIndex = glGenBuffers();
		uv0Index = glGenBuffers();
	}
	
	public void update() {
		verticesBuffer.flip();
		normalsBuffer.flip();
		colorsBuffer.flip();
		uv0Buffer.flip();	
		
		glBindVertexArray(vaoIndex);
		glBindBuffer(GL_ARRAY_BUFFER, vboIndex);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3*4, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, normalsIndex);
		glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 3*4, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, colorsIndex);
		glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 3*4, 0);
				
		glBindBuffer(GL_ARRAY_BUFFER, uv0Index);
		glBufferData(GL_ARRAY_BUFFER, uv0Buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(3, 2, GL_FLOAT, false, 2*4, 0);
		
		clear();
	}
	
	public void clear() {
		verticesBuffer.clear();
		normalsBuffer.clear();
		colorsBuffer.clear();
		uv0Buffer.clear();	
	}
	
	public void clearCount() {
		count = 0;
	}
	
	public void render() {
		glBindVertexArray(vaoIndex);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		
		glDrawArrays(GL_TRIANGLES, 0, count);
		
		glDisableVertexAttribArray(3);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
	public void dispose() {
		glDeleteBuffers(vboIndex);
		glDeleteBuffers(normalsIndex);
		glDeleteBuffers(colorsIndex);
		glDeleteBuffers(uv0Index);
		glDeleteVertexArrays(vaoIndex);
	}

	public int getCount() {
		return count;
	}
	public boolean isBuild() {
		return vaoIndex != 0 && vboIndex != 0 && normalsIndex != 0 && colorsIndex != 0 && uv0Index != 0;
	}
}
