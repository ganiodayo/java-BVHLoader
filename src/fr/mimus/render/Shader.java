package fr.mimus.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import fr.mimus.utils.FileLoader;
import fr.mimus.utils.Vector3f;

public class Shader {
	public int program;	
	public Shader(String shader) {
		program = glCreateProgram();

		if (program == GL_FALSE) {
			System.err.println("Shader program error");
			System.exit(1);
		}

		createShader(FileLoader.loadFileToString("res/shaders/"+shader+".vert"), GL_VERTEX_SHADER);
		createShader(FileLoader.loadFileToString("res/shaders/"+shader+".frag"), GL_FRAGMENT_SHADER);

		glLinkProgram(program);
		glValidateProgram(program);
	}
	
	private void createShader(String source, int type) {
		int shader = glCreateShader(type);
		if (shader == GL_FALSE) {
			System.err.println("Shader error: " + shader);
			System.exit(1);
		}
		glShaderSource(shader, source);
		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(shader, 2048));
			System.exit(1);
		}
		glAttachShader(program, shader);
	}

	public void setUniform(String name, float v) {
		glUniform1f(glGetUniformLocation(program, name), v);
	}

	public void setUniform(String name, Vector3f v) {
		glUniform3f(glGetUniformLocation(program, name), v.getX(), v.getY(), v.getZ());
	}
	
	public void bind() {
		glUseProgram(program);
	}
	
	public static void unbind() {
		glUseProgram(0);
	}

	public void dispose() {
		glDeleteProgram(program);
	}
}
