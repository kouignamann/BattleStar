#version 330 core

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

layout (location = 0) in vec4 in_Position;
layout (location = 1) in vec4 in_Normal;
layout (location = 2) in vec4 in_Color;
layout (location = 3) in vec2 in_TextureCoord;

out vec4 pass_Color;
out vec2 pass_TextureCoord;

smooth out vec3 vNormal;

void main(void) {
    gl_Position = in_Position;
    // Override gl_Position with our new calculated position
    mat4 modelView = viewMatrix * modelMatrix;
    gl_Position = projectionMatrix * modelView * in_Position;
    
    mat4 normalMatrix = transpose(inverse(modelView));
	vec4 vRes = normalMatrix*in_Normal;
	vNormal = vRes.xyz;
	
    pass_Color = in_Color;
    pass_TextureCoord = in_TextureCoord;
}
