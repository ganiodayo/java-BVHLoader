#version 330
uniform sampler2D tex;

in vec4 fragPos;
in vec4 color;

void main() {
	gl_FragColor = color;
}