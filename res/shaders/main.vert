#version 330
layout(location = 0) in vec3 in_pos;
layout(location = 1) in vec3 in_normal;
layout(location = 2) in vec3 in_color;
layout(location = 3) in vec2 in_texCoord;


out vec4 fragPos;
out vec4 normal;
out vec4 color;
out vec2 texCoord;

void main() {
	// On recupère le vertex actuel
	fragPos = vec4(in_pos, 1.0);
	// On recupère coordonné de texture actuellement utilisé.
	//texCoord = gl_MultiTexCoord0.xy;
	texCoord = in_texCoord;
	normal = vec4(in_normal, 1.0);
	color = vec4(in_color, 1.0);
	
	// On recupère la position de la caméra
	//gl_Position = ftransform();
	gl_Position = gl_ModelViewProjectionMatrix * vec4(in_pos, 1.0);
}