#version 330
uniform sampler2D tex;

in vec4 fragPos;
in vec4 normal;
in vec4 color;
in vec2 texCoord;

void main() {
	//gl_FragColor = texture2D(tex, texCoord) * calcLight(lightDir, normal.xyz);
	//gl_FragColor = color * calcLight(lightDir, normal.xyz);
	vec4 texUV = texture(tex, texCoord);
	//gl_FragColor = texUV * calcLight(lightDir, normal.xyz);
	gl_FragColor = texUV * color;
}