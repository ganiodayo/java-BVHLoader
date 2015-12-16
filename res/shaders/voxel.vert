#version 330
layout(location = 0) in vec3 in_pos;
layout(location = 1) in vec3 in_color;


out vec4 fragPos;
out vec4 color;

void main() {
	fragPos = vec4(in_pos, 1.0);
	color = vec4(in_color, 1.0);
	gl_Position = gl_ModelViewProjectionMatrix * vec4(in_pos, 1.0);
}