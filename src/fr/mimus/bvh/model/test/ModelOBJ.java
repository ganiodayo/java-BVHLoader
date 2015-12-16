package fr.mimus.bvh.model.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import fr.mimus.bvh.utils.Vec3;
import fr.mimus.utils.Vector2f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class ModelOBJ {

	private ArrayList<Vec3> vertices;
	private ArrayList<Vec3> normals;
	private ArrayList<Vector2f> textures;
	private ArrayList<Face> faces;
	
	private int[] vbo = null;
	public ModelOBJ() {
		vertices=new ArrayList<Vec3>();
		normals=new ArrayList<Vec3>();
		textures=new ArrayList<Vector2f>();
		faces=new ArrayList<Face>();
	}
	
	public ModelOBJ(String file) {
		vertices=new ArrayList<Vec3>();
		normals=new ArrayList<Vec3>();
		textures=new ArrayList<Vector2f>();
		faces=new ArrayList<Face>();
		try {
			load(file);
			vbo=createVBO();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Vec3 parseXYZ(String line) {
		String parse[] = line.split(" ");
		float x = Float.valueOf(parse[1]);
		float y = Float.valueOf(parse[2]);
		float z = Float.valueOf(parse[3]);
		return new Vec3(x, y, z);
	}
	
	private Vector2f parseXY(String line) {
		String parse[] = line.split(" ");
		float x = Float.valueOf(parse[1]);
		float y = 1-Float.valueOf(parse[2]);
		return new Vector2f(x, y);
	}
	
	private Face[] parseFace(String line) {
		String[] faceIndices = line.split(" ");
		String v1[] = faceIndices[1].split("/");
		String v2[] = faceIndices[2].split("/");
		String v3[] = faceIndices[3].split("/");
        int[] vertexIndicesArray = {Integer.valueOf(v1[0]),
					                Integer.valueOf(v2[0]), 
					                Integer.valueOf(v3[0])};
        int[] normalIndicesArray = {-1, -1, -1};
        int[] normalTextureArray = {-1, -1, -1};
        if(normals.size()>0) {
	       normalIndicesArray = new int[]{Integer.valueOf(v1[2]),
				                		Integer.valueOf(v2[2]), 
			                			Integer.valueOf(v3[2])};
        }
        if(textures.size()>0) {
        	normalTextureArray = new int[]{Integer.valueOf(v1[1]),
				                		Integer.valueOf(v2[1]), 
			                			Integer.valueOf(v3[1])};
        }
        
        Face[] r;
        
        if(faceIndices.length>4) {
        	r = new Face[2];
    		String v4[] = faceIndices[4].split("/");
    		
    		int[] vertexArray = {Integer.valueOf(v3[0]),
                Integer.valueOf(v4[0]), 
                Integer.valueOf(v1[0])};
    		
			int[] normalArray = {-1, -1, -1};
			int[] textureArray = {-1, -1, -1};
			if(normals.size()>0) {
				normalArray = new int[]{Integer.valueOf(v3[2]),
			                		Integer.valueOf(v4[2]), 
			            			Integer.valueOf(v1[2])};
			}
			if(textures.size()>0) {
				textureArray = new int[]{Integer.valueOf(v3[1]),
			                		Integer.valueOf(v4[1]), 
			            			Integer.valueOf(v1[1])};
			}
			r[1] = new Face(vertexArray, normalArray, textureArray);
        } else {
        	r = new Face[1];
        }
        r[0] = new Face(vertexIndicesArray, normalIndicesArray, normalTextureArray);
        return r;
	}
	
	private Vec3 size;
	public Vec3 getSize() {
		return size;
	}

	private float x0=0, x1=0, y0=0, y1=0, z0=0, z1=0;
	public void parseLine(String line) {
		if (line.startsWith("#")) {
			return;
        }
		if (line.startsWith("vn ")) {
        	Vec3 v = parseXYZ(line);
        	normals.add(v);
        } else if (line.startsWith("vt ")) {
        	Vector2f v = parseXY(line);
        	textures.add(v);
        } else if (line.startsWith("v ")) {
        	Vec3 v = parseXYZ(line);
        	vertices.add(v);
        	// On recup des valeurs pour déterminé la taille
        	if(x0>v.x) x0=v.x;
        	if(x1<v.x) x1=v.x;
        	if(y0>v.y) y0=v.y;
        	if(y1<v.y) y1=v.y;
        	if(z0>v.z) z0=v.z;
        	if(z1<v.z) z1=v.z;
        } else if (line.startsWith("f ")) {
        	Face[] f = parseFace(line);
        	for(int i=0; i<f.length; i++)
        		faces.add(f[i]);
        }
		size=new Vec3(x1-x0, y1-y0, z1-z0);
	}
	
	public void build() {
		vbo=createVBO();
	}
	
	private void load(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = "";
		while((line=reader.readLine())!=null) {
			parseLine(line);
		}
		reader.close();
	}
	
	private int[] createVBO() {
        int vboVertexHandle = glGenBuffers();
        int vboNormalHandle = glGenBuffers();
        int vboTexturesHandle = glGenBuffers();
        
        FloatBuffer fvertices = BufferUtils.createFloatBuffer(faces.size() * 9);
        FloatBuffer fnormals = BufferUtils.createFloatBuffer(faces.size() * 9);
        FloatBuffer ftextures = BufferUtils.createFloatBuffer(faces.size() * 6);

        System.out.println("v: " + vertices.size());
        System.out.println("n: " + normals.size());
        System.out.println("f: " + faces.size());
        System.out.println("t: " + textures.size());
        for (Face face : faces) {
            fvertices.put(vertices.get(face.getVertexIndices()[0] - 1).x);
            fvertices.put(vertices.get(face.getVertexIndices()[0] - 1).y);
            fvertices.put(vertices.get(face.getVertexIndices()[0] - 1).z);
            fvertices.put(vertices.get(face.getVertexIndices()[1] - 1).x);
            fvertices.put(vertices.get(face.getVertexIndices()[1] - 1).y);
            fvertices.put(vertices.get(face.getVertexIndices()[1] - 1).z);
            fvertices.put(vertices.get(face.getVertexIndices()[2] - 1).x);
            fvertices.put(vertices.get(face.getVertexIndices()[2] - 1).y);
            fvertices.put(vertices.get(face.getVertexIndices()[2] - 1).z);
            if(normals.size()>0) {
	            fnormals.put(normals.get(face.getNormalIndices()[0] - 1).x);
	            fnormals.put(normals.get(face.getNormalIndices()[0] - 1).y);
	            fnormals.put(normals.get(face.getNormalIndices()[0] - 1).z);
	            fnormals.put(normals.get(face.getNormalIndices()[1] - 1).x);
	            fnormals.put(normals.get(face.getNormalIndices()[1] - 1).y);
	            fnormals.put(normals.get(face.getNormalIndices()[1] - 1).z);
	            fnormals.put(normals.get(face.getNormalIndices()[2] - 1).x);
	            fnormals.put(normals.get(face.getNormalIndices()[2] - 1).y);
	            fnormals.put(normals.get(face.getNormalIndices()[2] - 1).z);
            }
            if(textures.size()>0) {
            	ftextures.put(textures.get(face.getTextureIndices()[0] - 1).toFloat());
            	ftextures.put(textures.get(face.getTextureIndices()[1] - 1).toFloat());
            	ftextures.put(textures.get(face.getTextureIndices()[2] - 1).toFloat());
            }
        }
        fvertices.flip();
        fnormals.flip();
        ftextures.flip();
        
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, fvertices, GL_STATIC_DRAW);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        if(normals.size()>0) {
	        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
	        glBufferData(GL_ARRAY_BUFFER, fnormals, GL_STATIC_DRAW);
	        glNormalPointer(GL_FLOAT, 0, 0L);
	        glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
        if(textures.size()>0) {
	        glBindBuffer(GL_ARRAY_BUFFER, vboTexturesHandle);
	        glBufferData(GL_ARRAY_BUFFER, ftextures, GL_STATIC_DRAW);
	        glTexCoordPointer(2, GL_FLOAT, 0, 0);
	        glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
        
        return new int[]{vboVertexHandle, vboNormalHandle, vboTexturesHandle};
    }
	
	public void render(Vec3 pos, Vec3 scale, Vec3 rot) {
		if(vbo!=null) {
			glPushMatrix();
				glTranslatef(pos.x, pos.x, pos.x);
				glScalef(scale.x, scale.y, scale.z);
				glRotatef(rot.x, 1, 0, 0);
				glRotatef(rot.y, 0, 1, 0);
				glRotatef(rot.z, 0, 0, 1);
				
	            glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
	            glVertexPointer(3, GL_FLOAT, 0, 0L);
	            if(normals.size()>0) {
		            glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
		            glNormalPointer(GL_FLOAT, 0, 0L);
	            }
	            if(textures.size()>0) {
		            glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
		            glTexCoordPointer(2, GL_FLOAT, 0, 0);
	            }
	            
	            glEnableClientState(GL_VERTEX_ARRAY);
	            if(normals.size()>0) glEnableClientState(GL_NORMAL_ARRAY);
	            if(textures.size()>0) glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	            glDrawArrays(GL_TRIANGLES, 0, faces.size() * 3);
	            if(textures.size()>0) glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	            glDisableClientState(GL_VERTEX_ARRAY);
	            if(normals.size()>0) glDisableClientState(GL_NORMAL_ARRAY);
    		glPopMatrix();
		}
	}
	
	public void dispose() {
		glDeleteBuffers(vbo[0]);
		glDeleteBuffers(vbo[1]);
	}
}
